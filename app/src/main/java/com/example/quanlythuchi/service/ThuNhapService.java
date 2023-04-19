package com.example.quanlythuchi.service;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import com.example.quanlythuchi.callback.InsertCallback;
import com.example.quanlythuchi.callback.UpOrDeCallback;
import com.example.quanlythuchi.callback.thunhap.FindCallback;
import com.example.quanlythuchi.callback.thunhap.FindManyCallback;
import com.example.quanlythuchi.callback.thunhap.FindOneCallback;
import com.example.quanlythuchi.model.ThuNhap;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.ArrayList;
import java.util.List;

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
                        "chi_phi",
                        ThuNhap.class).withCodecRegistry(pojoCodecRegistry);
    }

    public void findAll(FindCallback callback) {
        this.mongoCollection.find().iterator().getAsync(task -> {
            if (task.isSuccess()) {
                MongoCursor<ThuNhap> results = task.get();
                List<ThuNhap> thuNhaps = new ArrayList<>();
                while (results.hasNext()) {
                    ThuNhap chi = results.next();
                    thuNhaps.add(chi);
                }
                callback.onSuccess(thuNhaps);
            } else {
                callback.onFailure();
            }
        });
    }

    public void findOne(Document queryFilter, FindOneCallback callback) {
        this.mongoCollection.findOne(queryFilter).getAsync(task -> {
            if (task.isSuccess()) {
                callback.onSuccess(task.get());
            } else {
                callback.onFailure();
            }
        });
    }

    public void findMany(Document queryFilter, FindManyCallback callback) {
        RealmResultTask<MongoCursor<ThuNhap>> findTask = mongoCollection.find(queryFilter).iterator();
        findTask.getAsync(task -> {
            if (task.isSuccess()) {
                callback.onSuccess(task.get());
            } else {
                callback.onFailure();
            }
        });
    }

    public void insertOne(ThuNhap thuNhap, InsertCallback callback) {
        this.mongoCollection.insertOne(thuNhap).getAsync(task -> {
            if (task.isSuccess()) {
                callback.onSuccess();
            } else {
                callback.onFailure();
            }
        });
    }

    public void insertMany(List<ThuNhap> thuNhaps, InsertCallback callback) {
        this.mongoCollection.insertMany(thuNhaps).getAsync(task -> {
            if (task.isSuccess()) {
                callback.onSuccess();
            } else {
                callback.onFailure();
            }
        });
    }

    public void updateOne(Document queryFilter, Document updateDocument, UpOrDeCallback callback) {
        //Document queryFilter = new Document("name", "petunia");
        //Document updateDocument = new Document("$set", new Document("sunlight", "partial"));
        this.mongoCollection.updateOne(queryFilter, updateDocument).getAsync(task -> {
            if (task.isSuccess()) {
                callback.onSuccess(task.get().getModifiedCount());
            } else {
                callback.onFailure();
            }
        });
    }

    public void updateMany(Document queryFilter, Document updateDocument, UpOrDeCallback callback) {
        mongoCollection.updateMany(queryFilter, updateDocument).getAsync(task -> {
            if (task.isSuccess()) {
                callback.onSuccess(task.get().getModifiedCount());
            } else {
                callback.onFailure();
            }
        });
    }

    public void deleteOne(Document queryFilter, UpOrDeCallback callback) {
        mongoCollection.deleteOne(queryFilter).getAsync(task -> {
            if (task.isSuccess()) {
                callback.onSuccess(task.get().getDeletedCount());
            } else {
                callback.onFailure();
            }
        });
    }

    public void deleteMany(Document queryFilter, UpOrDeCallback callback) {
        this.mongoCollection.deleteMany(queryFilter).getAsync(task -> {
            if (task.isSuccess()) {
                callback.onSuccess(task.get().getDeletedCount());
            } else {
                callback.onFailure();
            }
        });
    }
}
