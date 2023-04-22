package com.example.quanlythuchi.model;

import java.io.Serializable;

public class DanhMucThu implements Serializable {
    private String TenDMThu;

    public DanhMucThu() {
    }

    public DanhMucThu(String tenDMThu) {
        TenDMThu = tenDMThu;
    }

    public void setTenDMThu(String tenDMThu) {
        TenDMThu = tenDMThu;
    }

    public String getTenDMThu() {
        return TenDMThu;
    }
}
