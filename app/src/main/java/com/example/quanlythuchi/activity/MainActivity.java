package com.example.quanlythuchi.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlythuchi.R;
import com.example.quanlythuchi.fragment.dashboard.service.LayoutService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    LayoutService layoutService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layoutService = new LayoutService(getSupportFragmentManager());

        layoutService.loadDashboardHeader();
        onBottomNavItemClick();
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