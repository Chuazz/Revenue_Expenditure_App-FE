package com.example.quanlythuchi.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlythuchi.R;
import com.example.quanlythuchi.callback.InsertCallback;
import com.example.quanlythuchi.callback.UpOrDeCallback;
import com.example.quanlythuchi.callback.danhmucchi.FindOneCallback;
import com.example.quanlythuchi.model.ChiPhi;
import com.example.quanlythuchi.model.DanhMucChi;
import com.example.quanlythuchi.model.DanhMucThu;
import com.example.quanlythuchi.model.NguoiDung;
import com.example.quanlythuchi.service.ChiPhiService;
import com.example.quanlythuchi.service.DanhMucChiService;
import com.example.quanlythuchi.service.DanhMucThuService;
import com.example.quanlythuchi.service.LayoutService;
import com.example.quanlythuchi.service.NguoiDungService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    LayoutService layoutService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layoutService = new LayoutService(getSupportFragmentManager());

        layoutService.loadDashboardHeader();
        onBottomNavItemClick();

//        NguoiDungService nguoiDungService = new NguoiDungService();
//        Document queryNguoiDung = new Document("", "");

        DanhMucChiService danhMucChiService = new DanhMucChiService();
        Document queryDMChi = new Document("tenDMChi", "Thuê nhà ở");

        danhMucChiService.findOne(queryDMChi, new FindOneCallback() {
            @Override
            public void onSuccess(DanhMucChi result) {
                ChiPhiService chiPhiService = new ChiPhiService();
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.MONTH, 0);
                Date date0 = calendar.getTime();
                calendar.add(Calendar.MONTH, 1);
                Date date1 = calendar.getTime();
                calendar.add(Calendar.MONTH, 2);
                Date date2 = calendar.getTime();

                List<ChiPhi> chiPhis  = Arrays.asList(
                new ChiPhi(null, result, 3500000, date0),
                new ChiPhi(null, result, 3600000, date1),
                new ChiPhi(null, result, 3700000, date2)
                );
                chiPhiService.insertMany(chiPhis, new InsertCallback() {
                    @Override
                    public void onSuccess() {
                        Log.v("EXAMPLE", "THÀNH CÔNG!");
                    }

                    @Override
                    public void onFailure() {
                        Log.v("EXAMPLE", "THẤT BẠI!");
                    }
                });

            }
            @Override
            public void onFailure() {

            }
        });

        ChiPhiService chiPhiService = new ChiPhiService();
//        List<ChiPhi> plants  = Arrays.asList(
//                new ChiPhi(NguoiDung nguoiDung, DanhMucChi danhMucChi, long tienChi, Date ngayChi),
//                new ChiPhi()
//                );


    }

    void onBottomNavItemClick(){
        BottomNavigationView bottomNavigationView = findViewById(R.id.main_bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.bottomNav_home: {
                        layoutService.loadDashboardHeader();
                        break;
                    }
                    case R.id.bottomNav_account: {
                        layoutService.loadAccountHeader();
                        break;
                    }
                    case R.id.bottomNav_addMore: {
                        layoutService.loadAddMoreHeader();
                        layoutService.loadAddMoreBody();
                        break;
                    }
                    case R.id.bottomNav_report: {
                        layoutService.loadReportHeader();
                        break;
                    }
                    case R.id.bottomNav_other: {
                        return false;
                    }
                }
                return true;
            }
        });
    }
}