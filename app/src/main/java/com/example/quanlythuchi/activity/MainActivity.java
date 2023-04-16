package com.example.quanlythuchi.activity;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlythuchi.R;
import com.example.quanlythuchi.service.LayoutService;
import com.github.javafaker.Faker;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarItemView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    LayoutService layoutService;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();
        layoutService = new LayoutService(getSupportFragmentManager());
        bottomNavigationView = findViewById(R.id.main_bottomNav);

        layoutService.loadDashboardHeader();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.bottomNav_home:{
                        layoutService.loadDashboardHeader();
                        break;
                    }
                    case R.id.bottomNav_account:{

                        break;
                    }
                    case R.id.bottomNav_addMore:{
                        break;
                    }
                    case R.id.bottomNav_report:{
                        break;
                    }
                    case R.id.bottomNav_other:{
                        break;
                    }
                }

                return true;
            }
        });
    }

    void onBottomNavItemClick(){
    }
}