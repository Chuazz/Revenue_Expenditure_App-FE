package com.example.quanlythuchi.service;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import android.util.Log;
import com.example.quanlythuchi.model.NguoiDung;

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

public class NguoiDungService {
    MongoDatabase mongoDatabase;
    MongoCollection<NguoiDung> mongoCollection;

    public NguoiDungService() {
        this.mongoDatabase = ConnectService.start();
        CodecRegistry pojoCodecRegistry = fromRegistries(AppConfiguration.DEFAULT_BSON_CODEC_REGISTRY,
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        this.mongoCollection =
                mongoDatabase.getCollection(
                        "nguoi_dung",
                        NguoiDung.class).withCodecRegistry(pojoCodecRegistry);
    }

    public CompletableFuture<List<NguoiDung>> findAll() {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        CompletableFuture<List<NguoiDung>> future = CompletableFuture.supplyAsync(() -> {
            RealmResultTask<MongoCursor<NguoiDung>> task = mongoCollection.find().iterator();
            MongoCursor<NguoiDung> dmcMongoCursor = task.get();
            List<NguoiDung> users = new ArrayList<>();
            while (dmcMongoCursor.hasNext()) {
                NguoiDung user = dmcMongoCursor.next();
                users.add(user);
            }
            return users;
        }, executor);
        return future;
    }

    public CompletableFuture<NguoiDung> findOne(Document document) {
        CompletableFuture<NguoiDung> future = new CompletableFuture<>();
        this.mongoCollection.findOne(document).getAsync(task -> {
            if (task.isSuccess()) {
                future.complete(task.get());
            } else {
                future.completeExceptionally(task.getError());
            }
        });
        return future;
    }

    public CompletableFuture<Void> insertOne(NguoiDung user) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        this.mongoCollection.insertOne(user).getAsync(task -> {
            if (task.isSuccess()) {
                future.complete(null);
            } else {
                future.completeExceptionally(task.getError());
            }
        });
        return future;
    }
}
