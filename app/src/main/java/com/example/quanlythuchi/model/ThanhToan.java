package com.example.quanlythuchi.model;

import java.util.Date;

public class ThanhToan {
    private HanThanhToan HanThanhToan;
    private long TienThanhToan;
    private Date NgayThanhToan;

    public ThanhToan() {
    }

    public ThanhToan(HanThanhToan hanThanhToan, long tienThanhToan, Date ngayThanhToan) {
        HanThanhToan = hanThanhToan;
        TienThanhToan = tienThanhToan;
        NgayThanhToan = ngayThanhToan;
    }

    public HanThanhToan getHanThanhToan() {
        return HanThanhToan;
    }

    public void setHanThanhToan(HanThanhToan hanThanhToan) {
        HanThanhToan = hanThanhToan;
    }

    public long getTienThanhToan() {
        return TienThanhToan;
    }

    public void setTienThanhToan(long tienThanhToan) {
        TienThanhToan = tienThanhToan;
    }

    public Date getNgayThanhToan() {
        return NgayThanhToan;
    }

    public void setNgayThanhToan(Date ngayThanhToan) {
        NgayThanhToan = ngayThanhToan;
    }
}
