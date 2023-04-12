package com.example.quanlythuchi.model;

import java.util.Date;

public class DinhMuc {
    private NguoiDung NguoiDung;
    private NhacChuong NhacChuong;
    private long TienDinhMuc;
    private Date ThoiGianBD;
    private Date ThoiGianKT;
    private boolean ThongBao;

    public DinhMuc() {
    }

    public DinhMuc(NguoiDung nguoiDung, NhacChuong nhacChuong, long tienDinhMuc, Date thoiGianBD, Date thoiGianKT, boolean thongBao) {
        NguoiDung = nguoiDung;
        NhacChuong = nhacChuong;
        TienDinhMuc = tienDinhMuc;
        ThoiGianBD = thoiGianBD;
        ThoiGianKT = thoiGianKT;
        ThongBao = thongBao;
    }

    public NguoiDung getNguoiDung() {
        return NguoiDung;
    }

    public void setNguoiDung(NguoiDung nguoiDung) {
        NguoiDung = nguoiDung;
    }

    public NhacChuong getNhacChuong() {
        return NhacChuong;
    }

    public void setNhacChuong(NhacChuong nhacChuong) {
        NhacChuong = nhacChuong;
    }

    public long getTienDinhMuc() {
        return TienDinhMuc;
    }

    public void setTienDinhMuc(long tienDinhMuc) {
        TienDinhMuc = tienDinhMuc;
    }

    public Date getThoiGianBD() {
        return ThoiGianBD;
    }

    public void setThoiGianBD(Date thoiGianBD) {
        ThoiGianBD = thoiGianBD;
    }

    public Date getThoiGianKT() {
        return ThoiGianKT;
    }

    public void setThoiGianKT(Date thoiGianKT) {
        ThoiGianKT = thoiGianKT;
    }

    public boolean isThongBao() {
        return ThongBao;
    }

    public void setThongBao(boolean thongBao) {
        ThongBao = thongBao;
    }
}
