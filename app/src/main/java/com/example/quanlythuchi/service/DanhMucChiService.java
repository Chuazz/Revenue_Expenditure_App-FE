package com.example.quanlythuchi.service;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import android.util.Log;

import com.example.quanlythuchi.activity.LoginActivity;
import com.example.quanlythuchi.model.DanhMucChi;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.Arrays;
import java.util.List;

import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;

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

    public DanhMucChi find(String id) {

        return null;
    }

    public void insertOne(DanhMucChi danhMucChi) {
        this.mongoCollection.insertOne(danhMucChi).getAsync(task -> {
            if (task.isSuccess()) {
                Log.v("EXAMPLE", "insertOne thành công!");
            } else {
                Log.e("EXAMPLE", "insertOne thất bại!");
            }
        });
    }


    public void insertMany(List<DanhMucChi> danhMucChis) {
        this.mongoCollection.insertMany(danhMucChis).getAsync(task -> {
            if (task.isSuccess()) {
                Log.v("EXAMPLE", "insertMany thành công!");
            } else {
                Log.v("EXAMPLE", "insertMany thất bại!");
            }
        });
    }

    public void updateOne(DanhMucChi danhMucChi) {

    }

    public void updateMany(List<DanhMucChi> danhMucChis) {

    }

    public void deleteOne(DanhMucChi danhMucChi) {

    }

    public void deleteMany(List<DanhMucChi> danhMucChis) {

    }

    public  void test() {
        App app = new App(new AppConfiguration.Builder(LoginActivity.APP_ID).build());
        User user = app.currentUser();
        MongoClient mongoClient =
                user.getMongoClient("mongodb-atlas");
        MongoDatabase mongoDatabase =
                mongoClient.getDatabase("quan_ly_thu_chi");
        // registry to handle POJOs (Plain Old Java Objects)
        CodecRegistry pojoCodecRegistry = fromRegistries(AppConfiguration.DEFAULT_BSON_CODEC_REGISTRY,
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoCollection<DanhMucChi> mongoCollection =
                mongoDatabase.getCollection(
                        "danh_muc_chi",
                        DanhMucChi.class).withCodecRegistry(pojoCodecRegistry);

        List<DanhMucChi> danhMucChis  = Arrays.asList(
                new DanhMucChi("Ăn nhậu"),
                new DanhMucChi("Ăn chơi"),
                new DanhMucChi("Ăn phá"),
                new DanhMucChi("Ăn no"));



        mongoCollection.insertMany(danhMucChis).getAsync(task -> {
            if (task.isSuccess()) {
                Log.v("EXAMPLE", "Test thành công!");
            } else {
                Log.e("EXAMPLE", "Test thất bại!");
            }
        });
    }
}
