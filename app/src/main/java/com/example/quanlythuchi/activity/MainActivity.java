package com.example.quanlythuchi.activity;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlythuchi.R;
import com.example.quanlythuchi.service.LayoutService;
import com.example.quanlythuchi.service.TestService;
import com.github.javafaker.Faker;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    LayoutService layoutService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();
        layoutService = new LayoutService(getSupportFragmentManager());

        layoutService.loadDashboardHeader();

        TestService test = new TestService();

        test.test();


    }
}