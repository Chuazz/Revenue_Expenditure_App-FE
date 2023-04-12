package com.example.quanlythuchi.model;

import java.util.Date;

public class ThuNhap {
    private NguoiDung NguoiDung;
    private DanhMucThu DanhMucThu;
    private long TienThu;
    private Date NgayThu;

    public ThuNhap() {
    }

    public ThuNhap(NguoiDung nguoiDung, DanhMucThu danhMucThu, long tienThu, Date ngayThu) {
        NguoiDung = nguoiDung;
        DanhMucThu = danhMucThu;
        TienThu = tienThu;
        NgayThu = ngayThu;
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

    public Date getNgayThu() {
        return NgayThu;
    }

    public void setNgayThu(Date ngayThu) {
        NgayThu = ngayThu;
    }
}
