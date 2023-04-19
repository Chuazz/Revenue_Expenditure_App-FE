package com.example.quanlythuchi.callback.chiphi;

import com.example.quanlythuchi.model.ChiPhi;
import com.example.quanlythuchi.model.DanhMucChi;

import java.util.List;

public interface FindCallback<T> {
    void onSuccess(List<ChiPhi> results);
    void onFailure();
}

