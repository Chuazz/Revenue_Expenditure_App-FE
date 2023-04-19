package com.example.quanlythuchi.callback.chiphi;

import com.example.quanlythuchi.model.ChiPhi;
import com.example.quanlythuchi.model.DanhMucChi;

public interface FindOneCallback {
    void onSuccess(ChiPhi result);
    void onFailure();
}
