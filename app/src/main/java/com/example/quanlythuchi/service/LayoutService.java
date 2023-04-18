package com.example.quanlythuchi.service;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.quanlythuchi.R;
import com.example.quanlythuchi.fragment.account.AccountHeaderFragment;
import com.example.quanlythuchi.fragment.addMore.AddMoreBodyFragment;
import com.example.quanlythuchi.fragment.addMore.AddMoreHeaderFragment;
import com.example.quanlythuchi.fragment.dashboard.DashboardHeaderFragment;
import com.example.quanlythuchi.fragment.report.ReportHeaderFragment;

public class LayoutService {
    private final FragmentManager fragmentManager;

    public LayoutService(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void loadDashboardHeader(){
        change(R.id.main_fragmentHeader, DashboardHeaderFragment.newInstance());
    }

    public void loadAccountHeader(){
        change(R.id.main_fragmentHeader, AccountHeaderFragment.newInstance());
    }

    public void loadAddMoreHeader(){
        change(R.id.main_fragmentHeader, AddMoreHeaderFragment.newInstance());
    }

    public void loadReportHeader(){
        change(R.id.main_fragmentHeader, ReportHeaderFragment.newInstance());
    }

    public void loadAddMoreBody(){
        change(R.id.main_fragmentBody, AddMoreBodyFragment.newInstance());
    }

    public void change(int id, Fragment fragment){
        FragmentTransaction transaction = this.fragmentManager.beginTransaction();
        transaction.replace(id, fragment);
        transaction.commit();
    }
}
