package com.example.quanlythuchi.callback.danhmucchi;

import com.example.quanlythuchi.model.DanhMucChi;

import java.util.List;

import io.realm.RealmResults;

public interface FindCallback<T> {
    void onSuccess(List<DanhMucChi> results);
    void onFailure();
}

