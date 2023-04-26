package com.example.quanlythuchi.service;

import android.util.Log;

import com.example.quanlythuchi.model.ChiPhi;
import com.example.quanlythuchi.model.GiaoDich;
import com.example.quanlythuchi.model.ThuNhap;

import org.bson.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;

public class LichSuChiTieuService {
    public LichSuChiTieuService() { }

    public CompletableFuture<Map<String, List<GiaoDich>>> getTransactionHistory(Document tenDangNhap) {
        CompletableFuture<List<ThuNhap>> earningFuture = new ThuNhapService().getEarningsHistory(tenDangNhap);
        CompletableFuture<List<ChiPhi>> spendingFuture = new ChiPhiService().getSpendingHistory(tenDangNhap);

        return CompletableFuture.allOf(earningFuture, spendingFuture)
                .thenApplyAsync(_void -> {
                    Map<String, List<GiaoDich>> MapLichSuChiTieu = new HashMap<>();

                    if (earningFuture.isCompletedExceptionally()) {
                        return MapLichSuChiTieu;
                    }

                    List<ThuNhap> earnings = earningFuture.join();
                    if (earnings != null) {
                        for (ThuNhap thuNhap: earnings) {
                            GiaoDich giaoDich = new GiaoDich(thuNhap.getDanhMucThu().getTenDMThu(), thuNhap.getGhiChu(), thuNhap.getTienThu(), true);
                            List<GiaoDich> giaoDiches = MapLichSuChiTieu.get(thuNhap.getNgayThu());
                            if (giaoDiches == null) {
                                giaoDiches = new ArrayList<>();
                            }
                            giaoDiches.add(giaoDich);
                            MapLichSuChiTieu.put(thuNhap.getNgayThu(), giaoDiches);
                        }
                    }

                    if (spendingFuture.isCompletedExceptionally()) {
                        return MapLichSuChiTieu;
                    }

                    List<ChiPhi> spendings = spendingFuture.join();
                    if (spendings != null) {
                        for (ChiPhi chiPhi: spendings) {
                            GiaoDich giaoDich = new GiaoDich(chiPhi.getDanhMucChi().getTenDMChi(), chiPhi.getGhiChu(), chiPhi.getTienChi(), false);
                            List<GiaoDich> giaoDiches = MapLichSuChiTieu.get(chiPhi.getNgayChi());
                            if (giaoDiches == null) {
                                giaoDiches = new ArrayList<>();
                            }
                            giaoDiches.add(giaoDich);
                            MapLichSuChiTieu.put(chiPhi.getNgayChi(), giaoDiches);
                        }
                    }
                    TreeMap<String, List<GiaoDich>> sortedMap = new TreeMap<>(Collections.reverseOrder());
                    sortedMap.putAll(MapLichSuChiTieu);
                    return sortedMap;
                });
    }

    public void printf(Map<String, List<GiaoDich>> map) {
        TreeMap<String, List<GiaoDich>> sortedMap = new TreeMap<>(Collections.reverseOrder());

        sortedMap.putAll(map);

        for (Map.Entry<String, List<GiaoDich>> entry : sortedMap.entrySet()) {
            String key = entry.getKey();
            Log.v("EXAMPLE","Key: " + key);

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
