package com.example.quanlythuchi.service;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import android.util.Log;

import com.example.quanlythuchi.model.DanhMucChi;
import com.example.quanlythuchi.model.DanhMucThu;

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

public class DanhMucThuService {
    MongoDatabase mongoDatabase;
    MongoCollection<DanhMucThu> mongoCollection;

    public DanhMucThuService() {
        this.mongoDatabase = ConnectService.start();
        CodecRegistry pojoCodecRegistry = fromRegistries(AppConfiguration.DEFAULT_BSON_CODEC_REGISTRY,
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        this.mongoCollection =
                mongoDatabase.getCollection(
                        "danh_muc_thu",
                        DanhMucThu.class).withCodecRegistry(pojoCodecRegistry);
    }

    public CompletableFuture<List<DanhMucThu>> findAll() {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        CompletableFuture<List<DanhMucThu>> future = CompletableFuture.supplyAsync(() -> {
            RealmResultTask<MongoCursor<DanhMucThu>> task = mongoCollection.find().iterator();
            MongoCursor<DanhMucThu> dmcMongoCursor = task.get();
            List<DanhMucThu> danhMucThus = new ArrayList<>();
            while (dmcMongoCursor.hasNext()) {
                DanhMucThu thu = dmcMongoCursor.next();
                danhMucThus.add(thu);
            }
            return danhMucThus;
        }, executor);
        return future;
    }

    public CompletableFuture<DanhMucThu> findOne(Document document) {
        CompletableFuture<DanhMucThu> future = new CompletableFuture<>();
        this.mongoCollection.findOne(document).getAsync(task -> {
            if (task.isSuccess()) {
                future.complete(task.get());
            } else {
                future.completeExceptionally(task.getError());
            }
        });
        return future;
    }

    public CompletableFuture<Void> insertOne(DanhMucThu danhMucThu) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        this.mongoCollection.insertOne(danhMucThu).getAsync(task -> {
            if (task.isSuccess()) {
                future.complete(null);
            } else {
                future.completeExceptionally(task.getError());
            }
        });
        return future;
    }
    public CompletableFuture<Void> insertMany(List<DanhMucThu> danhMucThus) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        this.mongoCollection.insertMany(danhMucThus).getAsync(task -> {
            if (task.isSuccess()) {
                future.complete(null);
            } else {
                future.completeExceptionally(task.getError());
            }
        });
        return future;
    }
}
