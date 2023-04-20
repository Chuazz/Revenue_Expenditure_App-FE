package com.example.quanlythuchi.service;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.quanlythuchi.R;
import com.example.quanlythuchi.fragment.account.AccountFragment;
import com.example.quanlythuchi.fragment.addMore.AddMoreFragment;
import com.example.quanlythuchi.fragment.dashboard.DashboardFragment;
import com.example.quanlythuchi.fragment.payType.PayTypeFragment;
import com.example.quanlythuchi.fragment.report.ReportFragment;

public class LayoutService {
    private final FragmentManager fragmentManager;

    public LayoutService(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void loadDashboard(){
        change(R.id.main_fragmentBody, DashboardFragment.newInstance());
    }

    public void loadAccount(){
        change(R.id.main_fragmentBody, AccountFragment.newInstance());
    }

    public void loadAddMore(){
        change(R.id.main_fragmentBody, AddMoreFragment.newInstance());
    }

    public void loadReport(){
        change(R.id.main_fragmentBody, ReportFragment.newInstance());
    }

    public void loadOther(){

    }

    public void loadPayType(){
        change(R.id.main_fragmentBody, PayTypeFragment.newInstance());
    }

    public void change(int id, Fragment fragment){
        FragmentTransaction transaction = this.fragmentManager.beginTransaction();
        transaction.replace(id, fragment);
        transaction.commit();
    }
}
