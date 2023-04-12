package com.example.quanlythuchi.service;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.quanlythuchi.R;
import com.example.quanlythuchi.fragment.DashboardHeaderFragment;

public class LayoutService {
    private final FragmentManager fragmentManager;

    public LayoutService(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void loadDashboardHeader(){
        FragmentTransaction transaction = this.fragmentManager.beginTransaction();
        transaction.replace(R.id.main_fragmentHeader, DashboardHeaderFragment.newInstance());
        transaction.commit();
    }
}
