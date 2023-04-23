package com.example.quanlythuchi.model;

public class GiaoDich {
    private String DanhMuc;
    private String GhiChu;
    private long Tien;

    private boolean ThuNhap; // True là Thu, False là chi

    public GiaoDich() { }

    public GiaoDich(String danhMuc, String ghiChu, long tien, boolean thuNhap) {
        DanhMuc = danhMuc;
        GhiChu = ghiChu;
        Tien = tien;
        ThuNhap = thuNhap;
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
