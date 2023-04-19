package com.example.quanlythuchi.callback.danhmucthu;

import com.example.quanlythuchi.model.DanhMucChi;
import com.example.quanlythuchi.model.DanhMucThu;

import java.util.List;

public interface FindCallback<T> {
    void onSuccess(List<DanhMucThu> results);
    void onFailure();
}

