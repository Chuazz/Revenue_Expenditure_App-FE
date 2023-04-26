package com.example.quanlythuchi.service;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import android.util.Log;

import com.example.quanlythuchi.activity.LoginActivity;
import com.example.quanlythuchi.model.ChiPhi;
import com.example.quanlythuchi.model.DanhMucChi;
import com.example.quanlythuchi.model.GiaoDich;
import com.example.quanlythuchi.model.ThuNhap;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;

public class ThuanTestService {
    Document tenDangNhap, tenDangNhap2;
    public ThuanTestService() {
        tenDangNhap = new Document("tenDangNhap", "thuanpt182@gmail.com");
        tenDangNhap2 = new Document("tenDangNhap", "anhsonbeo97@gmail.com");
    }

    public void test_LichSuChiTieu_getTransactionHistory() {
        LichSuChiTieuService lichSuChiTieuService = new LichSuChiTieuService();

        lichSuChiTieuService.getTransactionHistory(this.tenDangNhap).thenAccept(map -> {
            Log.v("EXAMPLE", "Lịch sử chi tiêu nè!");
            //lichSuChiTieuService.printf(map);
        }).exceptionally(e -> {
            Log.v("EXAMPLE", "Lỗi xảy ra trong quá trình lấy lịch sử chi tiêu!");
            return null;
        });
    }

    public void test_ChiPhi_getSpendingHistory() {
        ChiPhiService chiPhiService = new ChiPhiService();

        chiPhiService.getSpendingHistory(this.tenDangNhap).thenAccept(history -> {
            if(history != null) {
                for (ChiPhi chiPhi: history) {
                    Log.v("EXAMPLE", "Ghi chú nè: " + chiPhi.getGhiChu());
                }
            }
            else {
                Log.v("EXAMPLE", "Không tìm thấy lịch sử chi tiêu!");
            }

        }).exceptionally(e -> {
            Log.v("EXAMPLE", "Lỗi xảy ra khi đang tìm kiếm lịch sử chi tiêu: " + e.getMessage());
            return null;
        });
    }

    public void test_Share_convertStringToDate() {
        Date date = shareService.convertStringToDate("Apr 22, 2023");
        Log.v("EXAMPLE", "" + date);
    }

    public void test_Share_convertDateToString() {

    }

    public void test_ThuNhap_getEarningsHistory() {
        ThuNhapService thuNhapService = new ThuNhapService();

        thuNhapService.getEarningsHistory(this.tenDangNhap).thenAccept(history -> {
            if(history != null) {
                for (ThuNhap thuNhap: history) {
                    Log.v("EXAMPLE", "Ghi chú nè: " + thuNhap.getNgayThu());

                }
            }
            else {
                Log.v("EXAMPLE", "Không tìm thấy lịch sử thu nhập!");
            }

        }).exceptionally(e -> {
            Log.v("EXAMPLE", "Lỗi xảy ra khi đang tìm kiếm lịch sử thu nhập: " + e.getMessage());
            return null;
        });
    }

    public void test_NguoiDung_TheUserExistingMoney() {
        NguoiDungService nguoiDungService = new NguoiDungService();


        nguoiDungService.theUserExistingMoney(this.tenDangNhap).thenAccept(sum -> {
            if (sum != null) {
                Log.v("EXAMPLE", "The user's existing money: " + sum);
            } else {
                Log.v("EXAMPLE", "Error occurred while calculating the user's existing money.");
            }
        }).exceptionally(e -> {
            Log.v("EXAMPLE", "Error occurred while calculating the user's existing money: " + e.getMessage());
            return null;
        });
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
