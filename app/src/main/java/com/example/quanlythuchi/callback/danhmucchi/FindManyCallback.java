package com.example.quanlythuchi.callback.danhmucchi;

import com.example.quanlythuchi.model.DanhMucChi;

import io.realm.mongodb.mongo.iterable.MongoCursor;

public interface FindManyCallback {
    void onSuccess(MongoCursor<DanhMucChi> results);
    void onFailure();
}
