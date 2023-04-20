package com.example.quanlythuchi.model;

public class NguoiDung {
    private String TenDangNhap;
    private String HoTen;
    private String Pass;

    private String NgaySinh;
    private String SDT;

    public NguoiDung() { }

    public NguoiDung(String tenDangNhap, String hoTen, String pass, String ngaySinh, String SDT) {
        this.TenDangNhap = tenDangNhap;
        this.HoTen = hoTen;
        this.Pass = pass;
        this.NgaySinh = ngaySinh;
        this.SDT = SDT;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }

    public void setNgaySinh(String ngaySinh) {
        NgaySinh = ngaySinh;
    }

    public void setPass(String pass) {
        Pass = pass;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public void setTenDangNhap(String tenDangNhap) {
        TenDangNhap = tenDangNhap;
    }

    public String getHoTen() {
        return HoTen;
    }

    public String getNgaySinh() {
        return NgaySinh;
    }

    public String getPass() {
        return Pass;
    }

    public String getSDT() {
        return SDT;
    }

    public String getTenDangNhap() {
        return TenDangNhap;
    }
}
