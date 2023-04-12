package com.example.quanlythuchi.model;

public class NguoiDung {
    private String TenDangNhap;
    private String HoTen;
    private String Pass;

    public NguoiDung() { }

    public NguoiDung(String tenDangNhap, String hoTen, String pass, String SDT, String ngaySinh) {
        TenDangNhap = tenDangNhap;
        HoTen = hoTen;
        Pass = pass;
        this.SDT = SDT;
        NgaySinh = ngaySinh;
    }

    private String SDT;
    private String NgaySinh;

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
