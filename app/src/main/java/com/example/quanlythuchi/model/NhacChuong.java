package com.example.quanlythuchi.model;

public class NhacChuong {
    private String TenNhac;
    private String URL_Nhac;

    public NhacChuong() {
    }

    public NhacChuong(String tenNhac, String URL_Nhac) {
        TenNhac = tenNhac;
        this.URL_Nhac = URL_Nhac;
    }

    public String getTenNhac() {
        return TenNhac;
    }

    public void setTenNhac(String tenNhac) {
        TenNhac = tenNhac;
    }

    public String getURL_Nhac() {
        return URL_Nhac;
    }

    public void setURL_Nhac(String URL_Nhac) {
        this.URL_Nhac = URL_Nhac;
    }
}
