package com.example.quanlythuchi.callback.nguoidung;

import com.example.quanlythuchi.model.DanhMucThu;
import com.example.quanlythuchi.model.NguoiDung;

import io.realm.mongodb.mongo.iterable.MongoCursor;

public interface FindManyCallback {
    void onSuccess(MongoCursor<NguoiDung> results);
    void onFailure();
}
