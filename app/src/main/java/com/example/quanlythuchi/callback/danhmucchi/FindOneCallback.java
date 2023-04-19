package com.example.quanlythuchi.callback.danhmucchi;

import com.example.quanlythuchi.model.DanhMucChi;

public interface FindOneCallback {
    void onSuccess(DanhMucChi result);
    void onFailure();
}
