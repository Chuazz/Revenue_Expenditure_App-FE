package com.example.quanlythuchi.callback.nguoidung;

import com.example.quanlythuchi.model.DanhMucThu;
import com.example.quanlythuchi.model.NguoiDung;

import java.util.List;

public interface FindCallback<T> {
    void onSuccess(List<NguoiDung> results);
    void onFailure();
}

