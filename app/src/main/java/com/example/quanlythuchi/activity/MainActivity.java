package com.example.quanlythuchi.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlythuchi.R;
import com.example.quanlythuchi.callback.danhmucchi.FindOneCallback;
import com.example.quanlythuchi.model.DanhMucChi;
import com.example.quanlythuchi.service.DanhMucChiService;
import com.example.quanlythuchi.service.LayoutService;
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

        DanhMucChiService danhMucChiService = new DanhMucChiService();
        Document queryFilter  = new Document("_id", "643eaac133e44dfb0aadd12c");

        danhMucChiService.findOne(queryFilter, new FindOneCallback() {
            @Override
            public void onSuccess(DanhMucChi result) {
                Log.v("EXAMPLE", "Danh mục chi nè ");
                Log.v("EXAMPLE", result.getTenDMChi());
            }
            @Override
            public void onFailure() {
                Log.v("EXAMPLE", "THẤT BẠI!");
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