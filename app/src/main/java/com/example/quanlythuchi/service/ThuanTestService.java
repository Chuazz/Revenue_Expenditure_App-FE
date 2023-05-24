package com.example.quanlythuchi.service;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import android.content.Intent;
import android.util.Log;

import com.example.quanlythuchi.activity.LoginActivity;
import com.example.quanlythuchi.activity.MainActivity;
import com.example.quanlythuchi.model.ChiPhi;
import com.example.quanlythuchi.model.DanhMucChi;
import com.example.quanlythuchi.model.GiaoDich;
import com.example.quanlythuchi.model.NguoiDung;
import com.example.quanlythuchi.model.ThuNhap;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

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

    public void update_ChiPhi() {
        NguoiDungService nguoiDungService = new NguoiDungService();
        Document document = new Document("tenDangNhap", "thuanpt182@gmail.com");
        CompletableFuture<NguoiDung> findOneFuture = nguoiDungService.findOne(document);
        findOneFuture.thenAccept(user -> {
            if (user != null) {
                ChiPhiService chiPhiService = new ChiPhiService();
                //Document queryFilter  = new Document("ghiChu", "Tien nha o cua Thuan").append("ngayChi", "Apr 22, 2023"); // Ưng nối bao nhiêu thì nối
                Document queryFilter  = new Document("nguoiDung", user);
                Document updateDocument  = new Document("$set", new Document("ghiChu", "Tien nha o cua Thuanpt"));

                // Xong bước này là dữ liệu đã được cập nhật
                CompletableFuture<Long> updateFuture = chiPhiService.updateOne(queryFilter, updateDocument );

                // Bước này thực hiện để xem kêt quả trả về như thế nào
                // Nếu không cần kiểm tra thì có thể bỏ qua mà dữ liệu vẫn sẽ cập nhật
                updateFuture.thenAccept((result) -> {
                    if(result == 1) {
                        Log.v("EXAMPLE", "success");
                    } else {
                        Log.v("EXAMPLE", "fail");
                    }
                });
            } else {
                System.out.println("Không tìm thấy người dùng phù hợp.");
            }
        }).exceptionally(throwable -> {
            Log.i(TAG, "onFailure: " + "Loi mia r");
            return null;
        });
    }

    public void delete_ChiPhi() {
        ChiPhiService chiPhiService = new ChiPhiService();
        Document queryFilter  = new Document("_id", new ObjectId("64441a5af57ba99cb437f55b"));
        CompletableFuture<Long> deleteFuture = chiPhiService.deleteOne(queryFilter);

        deleteFuture.thenAccept((result) -> {
            Log.v("EXAMPLE", "Số tài liệu đã bị xóa: " + result);
        }).exceptionally(error -> {
            Log.v("EXAMPLE", "Đã xảy ra lỗi khi xóa: " + error.getMessage());
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
