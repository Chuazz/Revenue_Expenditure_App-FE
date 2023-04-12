package com.example.quanlythuchi.model;

import java.util.Date;

public class HanThanhToan {
    private NguoiDung NguoiDung;
    private NhacChuong NhacChuong;
    private long TienNo;

    private long TienConLai;
    private Date NgayHetHan;
    private boolean ThongBao;

    public HanThanhToan() {
    }

    public HanThanhToan(NguoiDung nguoiDung, NhacChuong nhacChuong, long tienNo, long tienConLai, Date ngayHetHan, boolean thongBao) {
        NguoiDung = nguoiDung;
        NhacChuong = nhacChuong;
        TienNo = tienNo;
        TienConLai = tienConLai;
        NgayHetHan = ngayHetHan;
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

    public long getTienNo() {
        return TienNo;
    }

    public void setTienNo(long tienNo) {
        TienNo = tienNo;
    }

    public long getTienConLai() {
        return TienConLai;
    }

    public void setTienConLai(long tienConLai) {
        TienConLai = tienConLai;
    }

    public Date getNgayHetHan() {
        return NgayHetHan;
    }

    public void setNgayHetHan(Date ngayHetHan) {
        NgayHetHan = ngayHetHan;
    }

    public boolean isThongBao() {
        return ThongBao;
    }

    public void setThongBao(boolean thongBao) {
        ThongBao = thongBao;
    }
}
