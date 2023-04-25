package com.example.quanlythuchi.model;

import java.util.List;

public class LichSu {
    String ngay;
    List<GiaoDich> giaoDich;

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public List<GiaoDich> getGiaoDich() {
        return giaoDich;
    }

    public void setGiaoDich(List<GiaoDich> giaoDich) {
        this.giaoDich = giaoDich;
    }

    public LichSu(String ngay, List<GiaoDich> giaoDich) {
        this.ngay = ngay;
        this.giaoDich = giaoDich;
    }
}
