package com.example.quanlythuchi.callback.thunhap;

import com.example.quanlythuchi.model.ChiPhi;
import com.example.quanlythuchi.model.ThuNhap;

public interface FindOneCallback {
    void onSuccess(ThuNhap result);
    void onFailure();
}
