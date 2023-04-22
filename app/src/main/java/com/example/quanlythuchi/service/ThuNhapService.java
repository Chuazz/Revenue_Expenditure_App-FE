package com.example.quanlythuchi.service;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import com.example.quanlythuchi.model.ChiPhi;
import com.example.quanlythuchi.model.NguoiDung;
import com.example.quanlythuchi.model.ThuNhap;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.RealmResultTask;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;
import io.realm.mongodb.mongo.iterable.MongoCursor;

public class ThuNhapService {
    MongoDatabase mongoDatabase;
    MongoCollection<ThuNhap> mongoCollection;

    public ThuNhapService() {
        this.mongoDatabase = ConnectService.start();
        CodecRegistry pojoCodecRegistry = fromRegistries(AppConfiguration.DEFAULT_BSON_CODEC_REGISTRY,
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        this.mongoCollection =
                mongoDatabase.getCollection(
                        "thu_nhap",
                        ThuNhap.class).withCodecRegistry(pojoCodecRegistry);
    }

    public CompletableFuture<List<ThuNhap>> findAll() {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        CompletableFuture<List<ThuNhap>> future = CompletableFuture.supplyAsync(() -> {
            RealmResultTask<MongoCursor<ThuNhap>> task = mongoCollection.find().iterator();
            MongoCursor<ThuNhap> dmcMongoCursor = task.get();
            List<ThuNhap> thuNhaps = new ArrayList<>();
            while (dmcMongoCursor.hasNext()) {
                ThuNhap thu = dmcMongoCursor.next();
                thuNhaps.add(thu);
            }
            return thuNhaps;
        }, executor);
        return future;
    }

    public CompletableFuture<ThuNhap> findOne(Document document) {
        CompletableFuture<ThuNhap> future = new CompletableFuture<>();
        this.mongoCollection.findOne(document).getAsync(task -> {
            if (task.isSuccess()) {
                future.complete(task.get());
            } else {
                future.completeExceptionally(task.getError());
            }
        });
        return future;
    }

    public CompletableFuture<Void> insertOne(ThuNhap thuNhap) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        this.mongoCollection.insertOne(thuNhap).getAsync(task -> {
            if (task.isSuccess()) {
                future.complete(null);
            } else {
                future.completeExceptionally(task.getError());
            }
        });
        return future;
    }

    public CompletableFuture<Void> insertMany(List<ThuNhap> thuNhaps) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        this.mongoCollection.insertMany(thuNhaps).getAsync(task -> {
            if (task.isSuccess()) {
                future.complete(null);
            } else {
                future.completeExceptionally(task.getError());
            }
        });
        return future;
    }

    public CompletableFuture<Long> totalRevenueOfUsers(Document tenDangNhap) {
        NguoiDungService nguoiDungService = new NguoiDungService();
        CompletableFuture<NguoiDung> root = nguoiDungService.findOne(tenDangNhap);
        CompletableFuture<Long> future = new CompletableFuture<>();

        root.thenAccept(user -> {
            if (user != null) {
                Document nguoiDung = new Document("nguoiDung", user);
                RealmResultTask<MongoCursor<ThuNhap>> findTask = mongoCollection.find(nguoiDung).iterator();
                findTask.getAsync(task -> {
                    if (task.isSuccess()) {
                        List<ThuNhap> thuNhaps = new ArrayList<>();
                        MongoCursor<ThuNhap> results = task.get();

                        while (results.hasNext()) {
                            thuNhaps.add(results.next());
                        }

                        long totalTienChi = thuNhaps.stream().mapToLong(ThuNhap::getTienThu).sum();
                        future.complete(totalTienChi);
                    } else {
                        future.completeExceptionally(task.getError());
                    }
                });
            } else {
                System.out.println("User not found");
                future.complete(null);
            }
        }).exceptionally(e -> {
            System.err.println("Error occurred while finding user: " + e.getMessage());
            future.completeExceptionally(e);
            return null;
        });

        return future;
    }
}
