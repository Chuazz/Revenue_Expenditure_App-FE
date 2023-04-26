package com.example.quanlythuchi.model;

import java.util.Date;
import java.util.List;

public class LichSu {
    Date ngay;
    String _thu, _ngay, _thang, _nam;
    boolean isToday = false;
    long tongThu = 0;
    long tongChi = 0;
    List<GiaoDich> giaoDich;

    public String get_thu() {
        return _thu;
    }

    public void set_thu(String _thu) {
        this._thu = _thu;
    }

    public String get_ngay() {
        return _ngay;
    }

    public void set_ngay(String _ngay) {
        this._ngay = _ngay;
    }

    public String get_thang() {
        return _thang;
    }

    public void set_thang(String _thang) {
        this._thang = _thang;
    }

    public String get_nam() {
        return _nam;
    }

    public void set_nam(String _nam) {
        this._nam = _nam;
    }

    public Date getNgay() {
        return ngay;
    }

    public void setNgay(Date ngay) {
        this.ngay = ngay;
    }

    public List<GiaoDich> getGiaoDich() {
        return giaoDich;
    }

    public void setGiaoDich(List<GiaoDich> giaoDich) {
        this.giaoDich = giaoDich;
    }

    public long getTongThu() {
        return tongThu;
    }

    public void setTongThu(long tongThu) {
        this.tongThu = tongThu;
    }
    public void plusThu(long thu) {
        this.tongThu += thu;
    }

    public long getTongChi() {
        return tongChi;
    }

    public void setTongChi(long tongChi) {
        this.tongChi = tongChi;
    }
    public void plusChi(long chi) {
        this.tongChi += chi;
    }

    public boolean isToday() {
        return isToday;
    }

    public void setToday(boolean today) {
        isToday = today;
    }

    public LichSu() { }

    public LichSu(Date ngay, List<GiaoDich> giaoDich) {
        this.ngay = ngay;
        this.giaoDich = giaoDich;
    }
}
