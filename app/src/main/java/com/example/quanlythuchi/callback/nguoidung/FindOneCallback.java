package com.example.quanlythuchi.callback.nguoidung;

import com.example.quanlythuchi.model.DanhMucThu;
import com.example.quanlythuchi.model.NguoiDung;

public interface FindOneCallback {
    void onSuccess(NguoiDung result);
    void onFailure();
}
