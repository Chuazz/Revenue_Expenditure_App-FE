package com.example.quanlythuchi.model;

import java.io.Serializable;

public class DanhMucChi implements Serializable {
    private String TenDMChi;

    public DanhMucChi() {
    }

    public DanhMucChi(String tenDMChi) {
        TenDMChi = tenDMChi;
    }

    public void setTenDMChi(String tenDMChi) {
        TenDMChi = tenDMChi;
    }

    public String getTenDMChi() {
        return TenDMChi;
    }
}
