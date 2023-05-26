package com.example.quanlythuchi.model;

import java.util.UUID;

public class ChiPhi {
    public String getMaChi() {
        return MaChi;
    }

    public void setMaChi(String MaChi) {
        this.MaChi = MaChi;
    }

    private String MaChi;
    private NguoiDung NguoiDung;
    private DanhMucChi DanhMucChi;
    private long TienChi;
    private String NgayChi;

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String ghiChu) {
        GhiChu = ghiChu;
    }

    private String GhiChu;

    public ChiPhi() {
    }

    public ChiPhi(NguoiDung nguoiDung, DanhMucChi danhMucChi, long tienChi, String ngayChi, String ghiChu) {
        UUID uuid = UUID.randomUUID();
        this.MaChi = uuid.toString();
        NguoiDung = nguoiDung;
        DanhMucChi = danhMucChi;
        TienChi = tienChi;
        NgayChi = ngayChi;
        GhiChu = ghiChu;
    }

    public NguoiDung getNguoiDung() {
        return NguoiDung;
    }

    public void setNguoiDung(NguoiDung nguoiDung) {
        NguoiDung = nguoiDung;
    }

    public DanhMucChi getDanhMucChi() {
        return DanhMucChi;
    }

    public void setDanhMucChi(DanhMucChi danhMucChi) {
        DanhMucChi = danhMucChi;
    }

    public long getTienChi() {
        return TienChi;
    }

    public void setTienChi(long tienChi) {
        TienChi = tienChi;
    }

    public String getNgayChi() {
        return NgayChi;
    }

    public void setNgayChi(String ngayChi) {
        NgayChi = ngayChi;
    }
}
