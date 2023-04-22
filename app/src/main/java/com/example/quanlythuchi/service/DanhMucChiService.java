package com.example.quanlythuchi.service;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import com.example.quanlythuchi.model.DanhMucChi;

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


public class DanhMucChiService {
    MongoDatabase mongoDatabase;
    MongoCollection<DanhMucChi> mongoCollection;

    public DanhMucChiService() {
        this.mongoDatabase = ConnectService.start();
        CodecRegistry pojoCodecRegistry = fromRegistries(AppConfiguration.DEFAULT_BSON_CODEC_REGISTRY,
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        this.mongoCollection =
                mongoDatabase.getCollection(
                        "danh_muc_chi",
                        DanhMucChi.class).withCodecRegistry(pojoCodecRegistry);
    }

    public CompletableFuture<List<DanhMucChi>> findAll() {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        CompletableFuture<List<DanhMucChi>> future = CompletableFuture.supplyAsync(() -> {
            RealmResultTask<MongoCursor<DanhMucChi>> task = mongoCollection.find().iterator();
            MongoCursor<DanhMucChi> dmcMongoCursor = task.get();
            List<DanhMucChi> danhMucChis = new ArrayList<>();
            while (dmcMongoCursor.hasNext()) {
                DanhMucChi chi = dmcMongoCursor.next();
                danhMucChis.add(chi);
            }
            return danhMucChis;
        }, executor);
        return future;
    }

    public CompletableFuture<DanhMucChi> findOne(Document document) {
        CompletableFuture<DanhMucChi> future = new CompletableFuture<>();
        this.mongoCollection.findOne(document).getAsync(task -> {
            if (task.isSuccess()) {
                future.complete(task.get());
            } else {
                future.completeExceptionally(task.getError());
            }
        });
        return future;
    }

    public CompletableFuture<Void> insertOne(DanhMucChi danhMucChi) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        this.mongoCollection.insertOne(danhMucChi).getAsync(task -> {
            if (task.isSuccess()) {
                future.complete(null);
            } else {
                future.completeExceptionally(task.getError());
            }
        });
        return future;
    }

    public CompletableFuture<Void> insertMany(List<DanhMucChi> danhMucChis) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        this.mongoCollection.insertMany(danhMucChis).getAsync(task -> {
            if (task.isSuccess()) {
                future.complete(null);
            } else {
                future.completeExceptionally(task.getError());
            }
        });
        return future;
    }
}
