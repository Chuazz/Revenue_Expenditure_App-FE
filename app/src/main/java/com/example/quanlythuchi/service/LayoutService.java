package com.example.quanlythuchi.service;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.quanlythuchi.R;
import com.example.quanlythuchi.fragment.history.DetailFragment;
import com.example.quanlythuchi.fragment.history.HistoryFragment;
import com.example.quanlythuchi.fragment.account.AccountFragment;
import com.example.quanlythuchi.fragment.addMore.AddMoreFragment;
import com.example.quanlythuchi.fragment.dashboard.DashboardFragment;
import com.example.quanlythuchi.fragment.payReceiveType.PayTypeFragment;
import com.example.quanlythuchi.fragment.payReceiveType.ReceiveTypeFragment;
import com.example.quanlythuchi.fragment.report.CurrentMoneyFragment;
import com.example.quanlythuchi.fragment.report.ReportFragment;
import com.example.quanlythuchi.model.GiaoDich;

import java.util.Date;

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

    public void loadPayType(Bundle args){
        change(R.id.main_fragmentBody, PayTypeFragment.newInstance(args));
    }

    public void loadReceiveType(Bundle args) {
        change(R.id.main_fragmentBody, ReceiveTypeFragment.newInstance(args));
    }

    public void loadHistory(Bundle args){
        change(R.id.main_fragmentBody, HistoryFragment.newInstance(args));
    }

    public void loadCurrentMoney(Bundle args){
        change(R.id.main_fragmentBody, CurrentMoneyFragment.newInstance(args));
    }

    public void loadDetail(Date date, GiaoDich giaoDich){
        change(R.id.main_fragmentBody, DetailFragment.newInstance(date, giaoDich));
    }

    public void change(int id, Fragment fragment){
        FragmentTransaction transaction = this.fragmentManager.beginTransaction();
        transaction.replace(id, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
