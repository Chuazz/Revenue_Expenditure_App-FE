package com.example.quanlythuchi.callback.thunhap;

import com.example.quanlythuchi.model.ChiPhi;
import com.example.quanlythuchi.model.ThuNhap;

import java.util.List;

public interface FindCallback<T> {
    void onSuccess(List<ThuNhap> results);
    void onFailure();
}

