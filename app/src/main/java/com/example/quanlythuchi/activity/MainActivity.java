package com.example.quanlythuchi.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlythuchi.R;
import com.example.quanlythuchi.service.LayoutService;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {
    LayoutService layoutService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layoutService = new LayoutService(getSupportFragmentManager());

        layoutService.loadDashboard();

        onBottomNavItemClick();
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