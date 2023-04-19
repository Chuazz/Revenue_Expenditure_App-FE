package com.example.quanlythuchi.callback.danhmucthu;

import com.example.quanlythuchi.model.DanhMucChi;
import com.example.quanlythuchi.model.DanhMucThu;

import io.realm.mongodb.mongo.iterable.MongoCursor;

public interface FindManyCallback {
    void onSuccess(MongoCursor<DanhMucThu> results);
    void onFailure();
}
