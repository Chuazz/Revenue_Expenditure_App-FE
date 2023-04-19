package com.example.quanlythuchi.service;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import com.example.quanlythuchi.callback.danhmucchi.FindManyCallback;
import com.example.quanlythuchi.callback.danhmucchi.FindOneCallback;
import com.example.quanlythuchi.callback.InsertCallback;
import com.example.quanlythuchi.callback.UpOrDeCallback;
import com.example.quanlythuchi.model.DanhMucChi;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.List;


import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.RealmResultTask;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;
import io.realm.mongodb.mongo.iterable.MongoCursor;

public class DanhMucChiService {
    MongoCollection<DanhMucChi> mongoCollection;
    public DanhMucChiService() {
        MongoDatabase mongoDatabase = ConnectService.start();
        CodecRegistry pojoCodecRegistry = fromRegistries(AppConfiguration.DEFAULT_BSON_CODEC_REGISTRY,
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        this.mongoCollection =
                mongoDatabase.getCollection(
                        "danh_muc_chi",
                        DanhMucChi.class).withCodecRegistry(pojoCodecRegistry);
    }

    // Tự động tắt nè
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
        RealmResultTask<MongoCursor<DanhMucChi>> findTask = mongoCollection.find(queryFilter).iterator();
        findTask.getAsync(task -> {
            if (task.isSuccess()) {
                callback.onSuccess(task.get());
                //while (results.hasNext()) {
                //    Log.v("EXAMPLE", results.next().toString());
                //}
            } else {
                callback.onFailure();
            }
        });
    }

    public void insertOne(DanhMucChi danhMucChi, InsertCallback callback) {
        this.mongoCollection.insertOne(danhMucChi).getAsync(task -> {
            if (task.isSuccess()) {
                callback.onSuccess();
            } else {
                callback.onFailure();
            }
        });
    }

    public void insertMany(List<DanhMucChi> danhMucChis, InsertCallback callback) {
        this.mongoCollection.insertMany(danhMucChis).getAsync(task -> {
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
