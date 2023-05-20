package com.example.quanlythuchi.service;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import android.util.Log;
import com.example.quanlythuchi.model.ChiPhi;
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

public class ChiPhiService {
    MongoDatabase mongoDatabase;
    MongoCollection<ChiPhi> mongoCollection;

    public ChiPhiService() {
        this.mongoDatabase = ConnectService.start();
        CodecRegistry pojoCodecRegistry = fromRegistries(AppConfiguration.DEFAULT_BSON_CODEC_REGISTRY,
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        this.mongoCollection =
                mongoDatabase.getCollection(
                        "chi_phi",
                        ChiPhi.class).withCodecRegistry(pojoCodecRegistry);
    }

    public CompletableFuture<List<ChiPhi>> findAll() {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        CompletableFuture<List<ChiPhi>> future = CompletableFuture.supplyAsync(() -> {
            RealmResultTask<MongoCursor<ChiPhi>> task = mongoCollection.find().iterator();
            MongoCursor<ChiPhi> dmcMongoCursor = task.get();
            List<ChiPhi> chiPhis = new ArrayList<>();
            while (dmcMongoCursor.hasNext()) {
                ChiPhi chi = dmcMongoCursor.next();
                chiPhis.add(chi);
            }
            return chiPhis;
        }, executor);
        return future;
    }

    public CompletableFuture<ChiPhi> findOne(Document document) {
        CompletableFuture<ChiPhi> future = new CompletableFuture<>();
        this.mongoCollection.findOne(document).getAsync(task -> {
            if (task.isSuccess()) {
                future.complete(task.get());
            } else {
                future.completeExceptionally(task.getError());
            }
        });
        return future;
    }

    public CompletableFuture<Void> insertOne(ChiPhi chiPhi) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        this.mongoCollection.insertOne(chiPhi).getAsync(task -> {
            if (task.isSuccess()) {
                future.complete(null);
            } else {
                future.completeExceptionally(task.getError());
            }
        });
        return future;
    }

    public CompletableFuture<Void> insertMany(List<ChiPhi> chiPhis) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        this.mongoCollection.insertMany(chiPhis).getAsync(task -> {
            if (task.isSuccess()) {
                future.complete(null);
            } else {
                future.completeExceptionally(task.getError());
            }
        });
        return future;
    }

    public CompletableFuture<Void> insertOne1(ChiPhi chiPhi) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        this.mongoCollection.insertOne(chiPhi).getAsync(task -> {
            if (task.isSuccess()) {
                future.complete(null);
            } else {
                future.completeExceptionally(task.getError());
            }
        });
        return future;
    }

    public CompletableFuture<Long> updateOne(Document queryFilter, Document updateDocument) {
        CompletableFuture<Long> future = new CompletableFuture<>();
        this.mongoCollection.updateOne(queryFilter, updateDocument).getAsync(task -> {
            if (task.isSuccess()) {
                future.complete(task.get().getModifiedCount());
            } else {
                future.completeExceptionally(task.getError());
            }
        });
        return future;
    }

    public CompletableFuture<Long> deleteOne(Document queryFilter) {
        CompletableFuture<Long> future = new CompletableFuture<>();
        this.mongoCollection.deleteOne(queryFilter).getAsync(task -> {
            if (task.isSuccess()) {
                future.complete(task.get().getDeletedCount());
            } else {
                future.completeExceptionally(task.getError());
            }
        });
        return future;
    }

    public CompletableFuture<Long> totalUserSpend(Document tenDangNhap) {
        NguoiDungService nguoiDungService = new NguoiDungService();
        CompletableFuture<NguoiDung> root = nguoiDungService.findOne(tenDangNhap);
        CompletableFuture<Long> future = new CompletableFuture<>();

        root.thenAccept(user -> {
            if (user != null) {
                Document nguoiDung = new Document("nguoiDung", user);
                RealmResultTask<MongoCursor<ChiPhi>> findTask = mongoCollection.find(nguoiDung).iterator();
                findTask.getAsync(task -> {
                    if (task.isSuccess()) {
                        List<ChiPhi> chiPhis = new ArrayList<>();
                        MongoCursor<ChiPhi> results = task.get();

                        while (results.hasNext()) {
                            chiPhis.add(results.next());
                        }

                        long totalTienChi = chiPhis.stream().mapToLong(ChiPhi::getTienChi).sum();
                        future.complete(totalTienChi);
                    } else {
                        future.completeExceptionally(task.getError());
                    }
                });
            } else {
                Log.v("EXAMPLE", "User not found");
                future.complete(null);
            }
        }).exceptionally(e -> {
            Log.v("EXAMPLE", "Error occurred while finding user: " + e.getMessage());
            future.completeExceptionally(e);
            return null;
        });
        return future;
    }

    public CompletableFuture<List<ChiPhi>> getSpendingHistory(Document tenDangNhap) {
        NguoiDungService nguoiDungService = new NguoiDungService();
        CompletableFuture<NguoiDung> root = nguoiDungService.findOne(tenDangNhap);
        CompletableFuture<List<ChiPhi>> future = new CompletableFuture<>();

        root.thenAccept(user -> {
            if(user != null) {
                Document nguoiDung = new Document("nguoiDung", user);
                RealmResultTask<MongoCursor<ChiPhi>> findTask = mongoCollection.find(nguoiDung).iterator();
                findTask.getAsync(task -> {
                    if (task.isSuccess()) {
                        List<ChiPhi> chiPhis = new ArrayList<>();
                        MongoCursor<ChiPhi> results = task.get();
                        while (results.hasNext()) {
                            chiPhis.add(results.next());
                        }
                        future.complete(chiPhis);
                    } else {
                        future.completeExceptionally(task.getError());
                    }
                });
            }
            else {
                Log.v("EXAMPLE", "Không có lịch sử chi tiêu");
                future.complete(null);
            }
        }).exceptionally(e -> {
            Log.v("EXAMPLE", "Lỗi xảy ra khi đang tìm kiếm lịch sử chi tiêu: " + e.getMessage());
            future.completeExceptionally(e);
            return null;
        });
        return future;
    }
}

/*
* Code mẫu Future nè
*
public CompletableFuture<Long> tongChi(Document user) {
    ExecutorService executor = Executors.newSingleThreadExecutor();

    CompletableFuture<Long> future = CompletableFuture.supplyAsync(() -> {
        long sum = 0;
        RealmResultTask<MongoCursor<ChiPhi>> findTask = mongoCollection.find(user).iterator();

        sum = 10;
        MongoCursor<ChiPhi> chiPhis = findTask.get();

        while (chiPhis.hasNext()) {
            ChiPhi chiPhi = chiPhis.next();
            sum += chiPhi.getTienChi();
            // Xử lý đối tượng ChiPhi tại đây
        }

        return sum;
    }, executor);

    return future;
}

*
* Gọi ra sài nè
CompletableFuture<Long> future = tongChi(user);
Long result = future.join();
* */
