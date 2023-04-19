package com.example.quanlythuchi.service;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import com.example.quanlythuchi.callback.InsertCallback;
import com.example.quanlythuchi.callback.UpOrDeCallback;
import com.example.quanlythuchi.callback.nguoidung.FindCallback;
import com.example.quanlythuchi.callback.nguoidung.FindManyCallback;
import com.example.quanlythuchi.callback.nguoidung.FindOneCallback;
import com.example.quanlythuchi.model.NguoiDung;

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

    public void findAll(FindCallback callback) {
        this.mongoCollection.find().iterator().getAsync(task -> {
            if (task.isSuccess()) {
                MongoCursor<NguoiDung> results = task.get();
                List<NguoiDung> danhMucList = new ArrayList<>();
                while (results.hasNext()) {
                    NguoiDung chi = results.next();
                    danhMucList.add(chi);
                }
                callback.onSuccess(danhMucList);
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
        RealmResultTask<MongoCursor<NguoiDung>> findTask = mongoCollection.find(queryFilter).iterator();
        findTask.getAsync(task -> {
            if (task.isSuccess()) {
                callback.onSuccess(task.get());
            } else {
                callback.onFailure();
            }
        });
    }

    public void insertOne(NguoiDung nguoiDung, InsertCallback callback) {
        this.mongoCollection.insertOne(nguoiDung).getAsync(task -> {
            if (task.isSuccess()) {
                callback.onSuccess();
            } else {
                callback.onFailure();
            }
        });
    }

    public void insertMany(List<NguoiDung> nguoiDungs, InsertCallback callback) {
        this.mongoCollection.insertMany(nguoiDungs).getAsync(task -> {
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
