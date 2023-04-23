package com.example.quanlythuchi.service;

import android.util.Log;

import com.example.quanlythuchi.model.ChiPhi;
import com.example.quanlythuchi.model.GiaoDich;
import com.example.quanlythuchi.model.ThuNhap;

import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LichSuChiTieuService {
    Document tenDangNhap, tenDangNhap2;
    List<ThuNhap> thuNhaps;
    List<ChiPhi> chiPhis;
    public static HashMap<String, List<GiaoDich>> MapLichSuChiTieu = new HashMap<>();

    public LichSuChiTieuService(Document userName) {
        this.tenDangNhap = userName; // Chạy thật

        // Lấy thu nhập nè
        ThuNhapService thuNhapService = new ThuNhapService();
        thuNhapService.getEarningsHistory(this.tenDangNhap).thenAccept(history -> {
            this.thuNhaps = history;
            Log.v("EXAMPLE", "Vô thu nhập rổi!" + this.thuNhaps.size());
        }).exceptionally(e -> {
            return null;
        });

        // Lấy chi tiêu nè
        ChiPhiService chiPhiService = new ChiPhiService();
        chiPhiService.getSpendingHistory(this.tenDangNhap).thenAccept(history -> {
            this.chiPhis = history;
            Log.v("EXAMPLE", "Vô chi phí rồi!" + this.chiPhis.size());
        }).exceptionally(e -> {
            return null;
        });
    }

    public void getTransactionHistory() {
        // Đưa Thu nhập vào Map
        if(this.thuNhaps != null) {
            for (ThuNhap thuNhap: this.thuNhaps) {
                GiaoDich giaoDich = new GiaoDich(thuNhap.getDanhMucThu().getTenDMThu(), thuNhap.getGhiChu(), thuNhap.getTienThu(), true);
                List<GiaoDich> giaoDiches = new ArrayList<>();
                if(MapLichSuChiTieu.containsKey(thuNhap.getNgayThu())) {
                    giaoDiches = MapLichSuChiTieu.get(thuNhap.getNgayThu());
                }
                giaoDiches.add(giaoDich);
                MapLichSuChiTieu.put(thuNhap.getNgayThu(), giaoDiches);
            }
        }
        // Đưa Chi tiêu vào Map
        if(this.chiPhis != null) {
            for (ChiPhi chiPhi: this.chiPhis) {
                GiaoDich giaoDich = new GiaoDich(chiPhi.getDanhMucChi().getTenDMChi(), chiPhi.getGhiChu(), chiPhi.getTienChi(), false);
                List<GiaoDich> giaoDiches = new ArrayList<>();
                if(MapLichSuChiTieu.containsKey(chiPhi.getNgayChi())) {
                    giaoDiches = MapLichSuChiTieu.get(chiPhi.getNgayChi());
                }
                giaoDiches.add(giaoDich);
                MapLichSuChiTieu.put(chiPhi.getNgayChi(), giaoDiches);
            }
        }
    }

    public void printf() {
        Log.v("EXAMPLE", "Bắt đầu in nè nha!");
        for (Map.Entry<String, List<GiaoDich>> entry : MapLichSuChiTieu.entrySet()) {
            String key = entry.getKey();
            System.out.println("Key: " + key);

            // Lấy giá trị của key hiện tại
            List<GiaoDich> giaoDiches = entry.getValue();

            // Duyệt qua các phần tử của Giao dịch hiện tại
            for (GiaoDich giaoDich : giaoDiches) {
                if(giaoDich.isThuNhap()) {
                    Log.v("EXAMPLE", giaoDich.getDanhMuc() + " ++ " + giaoDich.getGhiChu() + " ++ " + giaoDich.getTien());
                }
                else {
                    Log.v("EXAMPLE",giaoDich.getDanhMuc() + " -- " + giaoDich.getGhiChu() + " -- " + giaoDich.getTien());
                }
            }
        }
    }
}
