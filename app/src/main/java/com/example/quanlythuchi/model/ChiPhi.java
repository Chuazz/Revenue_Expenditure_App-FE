package com.example.quanlythuchi.model;

import java.util.Date;

public class ChiPhi {
    private NguoiDung NguoiDung;
    private DanhMucChi DanhMucChi;
    private long TienChi;
    private Date NgayChi;

    public ChiPhi() {
    }

    public ChiPhi(NguoiDung nguoiDung, DanhMucChi danhMucChi, long tienChi, Date ngayChi) {
        NguoiDung = nguoiDung;
        DanhMucChi = danhMucChi;
        TienChi = tienChi;
        NgayChi = ngayChi;
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

    public Date getNgayChi() {
        return NgayChi;
    }

    public void setNgayChi(Date ngayChi) {
        NgayChi = ngayChi;
    }
}
