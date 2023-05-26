package com.example.quanlythuchi.model;

import java.util.UUID;

public class ThuNhap {
    public String getMaGD() {
        return MaGD;
    }

    public void setMaGD(String maGD) {
        MaGD = maGD;
    }

    private String MaGD;
    private NguoiDung NguoiDung;
    private DanhMucThu DanhMucThu;
    private long TienThu;
    private String NgayThu;

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String ghiChu) {
        GhiChu = ghiChu;
    }

    private String GhiChu;

    public ThuNhap() {
    }

    public ThuNhap(NguoiDung nguoiDung, DanhMucThu danhMucThu, long tienThu, String ngayThu, String ghiChu) {
        UUID uuid = UUID.randomUUID();
        this.MaGD = uuid.toString();
        NguoiDung = nguoiDung;
        DanhMucThu = danhMucThu;
        TienThu = tienThu;
        NgayThu = ngayThu;
        GhiChu = ghiChu;
    }

    public NguoiDung getNguoiDung() {
        return NguoiDung;
    }

    public void setNguoiDung(NguoiDung nguoiDung) {
        NguoiDung = nguoiDung;
    }

    public DanhMucThu getDanhMucThu() {
        return DanhMucThu;
    }

    public void setDanhMucThu(DanhMucThu danhMucThu) {
        DanhMucThu = danhMucThu;
    }

    public long getTienThu() {
        return TienThu;
    }

    public void setTienThu(long tienThu) {
        TienThu = tienThu;
    }

    public String getNgayThu() {
        return NgayThu;
    }

    public void setNgayThu(String ngayThu) {
        NgayThu = ngayThu;
    }
}
