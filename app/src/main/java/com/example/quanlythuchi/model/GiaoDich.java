package com.example.quanlythuchi.model;

import java.io.Serializable;

public class GiaoDich implements Serializable {
    private String MaGD;
    private String DanhMuc;
    private String GhiChu;
    private long Tien;

    private boolean ThuNhap; // True là Thu nhập, False là Chi tiêu

    public GiaoDich() { }

    public GiaoDich(String maGD, String danhMuc, String ghiChu, long tien, boolean thuNhap) {
        MaGD = maGD;
        DanhMuc = danhMuc;
        GhiChu = ghiChu;
        Tien = tien;
        ThuNhap = thuNhap;
    }

    public String getMaGD() {
        return MaGD;
    }

    public void setMaGD(String maGD) {
        MaGD = maGD;
    }

    public String getDanhMuc() {
        return DanhMuc;
    }

    public void setDanhMuc(String danhMuc) {
        DanhMuc = danhMuc;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String ghiChu) {
        GhiChu = ghiChu;
    }

    public long getTien() {
        return Tien;
    }

    public void setTien(long tien) {
        Tien = tien;
    }

    public boolean isThuNhap() {
        return ThuNhap;
    }

    public void setThuNhap(boolean thuNhap) {
        ThuNhap = thuNhap;
    }
}
