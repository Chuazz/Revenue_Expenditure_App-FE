package com.example.quanlythuchi.callback.chiphi;

import com.example.quanlythuchi.model.ChiPhi;
import com.example.quanlythuchi.model.DanhMucChi;

import io.realm.mongodb.mongo.iterable.MongoCursor;

public interface FindManyCallback {
    void onSuccess(MongoCursor<ChiPhi> results);
    void onFailure();
}
