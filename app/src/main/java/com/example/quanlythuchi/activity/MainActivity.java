package com.example.quanlythuchi.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlythuchi.R;
import com.example.quanlythuchi.callback.InsertCallback;
import com.example.quanlythuchi.callback.nguoidung.FindOneCallback;
import com.example.quanlythuchi.model.DanhMucChi;
import com.example.quanlythuchi.model.NguoiDung;
import com.example.quanlythuchi.service.ChiPhiService;
import com.example.quanlythuchi.service.DanhMucChiService;
import com.example.quanlythuchi.service.LayoutService;
import com.example.quanlythuchi.service.NguoiDungService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;


public class MainActivity extends AppCompatActivity {
    LayoutService layoutService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layoutService = new LayoutService(getSupportFragmentManager());

        layoutService.loadDashboard();

        onBottomNavItemClick();

//        Log.v("EXAMPLE", "Vo roi");
//
//        DanhMucChiService danhMucChiService = new DanhMucChiService();
//
//        DanhMucChi danhMucChi = new DanhMucChi("x");
//        danhMucChiService.insertOne(danhMucChi);
//
//        List<DanhMucChi> arr = Arrays.asList(
//                new DanhMucChi("1"),
//                new DanhMucChi("2"),
//                new DanhMucChi("3"));
//        danhMucChiService.insertMany(arr);


    }

    void onBottomNavItemClick(){
        BottomNavigationView bottomNavigationView = findViewById(R.id.main_bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.bottomNav_dashboard: {
                        layoutService.loadDashboard();
                        break;
                    }
                    case R.id.bottomNav_account: {
                        layoutService.loadAccount();
                        break;
                    }
                    case R.id.bottomNav_addMore: {
                        layoutService.loadAddMore();
                        break;
                    }
                    case R.id.bottomNav_report: {
                        layoutService.loadReport();
                        break;
                    }
                    case R.id.bottomNav_other: {
                        layoutService.loadOther();
                        return false;
                    }
                }
                return true;
            }
        });
    }
}