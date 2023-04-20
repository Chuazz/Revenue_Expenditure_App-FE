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
import com.example.quanlythuchi.model.NguoiDung;
import com.example.quanlythuchi.service.LayoutService;
import com.example.quanlythuchi.service.NguoiDungService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.bson.Document;


public class MainActivity extends AppCompatActivity {
    LayoutService layoutService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layoutService = new LayoutService(getSupportFragmentManager());

        layoutService.loadDashboardHeader();
        onBottomNavItemClick();

        NguoiDungService nguoiDungService = new NguoiDungService();

        Document document = new Document("tenDangNhap", "thuanpt182@gmail.com");
        Log.v("EXAMPLE", "Da chay!");
        nguoiDungService.findOne(document, new FindOneCallback() {
            @Override
            public void onSuccess(NguoiDung result) {
                Log.v("EXAMPLE", "Duoc roi!");
                //Log.v("EXAMPLE", "" + result);
                Log.v("EXAMPLE", "" + result.getHoTen());
            }

            @Override
            public void onFailure() {

            }
        });

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
                        layoutService.loadDashboardBody();
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