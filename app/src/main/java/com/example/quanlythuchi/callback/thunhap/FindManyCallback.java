package com.example.quanlythuchi.callback.thunhap;

import com.example.quanlythuchi.model.ChiPhi;
import com.example.quanlythuchi.model.ThuNhap;

import io.realm.mongodb.mongo.iterable.MongoCursor;

public interface FindManyCallback {
    void onSuccess(MongoCursor<ThuNhap> results);
    void onFailure();
}
