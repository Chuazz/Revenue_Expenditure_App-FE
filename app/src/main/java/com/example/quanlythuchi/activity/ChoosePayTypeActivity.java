package com.example.quanlythuchi.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.quanlythuchi.R;
import com.example.quanlythuchi.adapter.PayTypeAdapter;
import com.example.quanlythuchi.callback.danhmucthu.FindCallback;
import com.example.quanlythuchi.model.DanhMucThu;
import com.example.quanlythuchi.service.DanhMucThuService;

import java.util.List;

import jp.wasabeef.recyclerview.animators.ScaleInAnimator;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

public class ChoosePayTypeActivity extends AppCompatActivity {
    RecyclerView listDanhMucThu;
    List<DanhMucThu> danhMucThus;
    DanhMucThuService danhMucThuService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_pay_type);

        listDanhMucThu = findViewById(R.id.choosePayType_listItem);
        danhMucThuService = new DanhMucThuService();

        onCreate();
    }

    void onCreate(){
        danhMucThuService.findAll(new FindCallback() {
            @Override
            public void onSuccess(List results) {
                danhMucThus = results;
                PayTypeAdapter payTypeAdapter = new PayTypeAdapter(getApplicationContext(), danhMucThus);
                listDanhMucThu.setItemAnimator(new SlideInLeftAnimator());

                listDanhMucThu.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }

            @Override
            public void onFailure() {

            }
        });
    }


}