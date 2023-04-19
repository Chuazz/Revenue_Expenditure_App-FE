package com.example.quanlythuchi.callback.danhmucthu;

import com.example.quanlythuchi.model.DanhMucChi;
import com.example.quanlythuchi.model.DanhMucThu;

public interface FindOneCallback {
    void onSuccess(DanhMucThu result);
    void onFailure();
}
