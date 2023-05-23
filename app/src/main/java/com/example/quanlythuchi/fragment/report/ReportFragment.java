package com.example.quanlythuchi.fragment.report;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.quanlythuchi.R;
import com.example.quanlythuchi.service.LayoutService;

public class ReportFragment extends Fragment {
    View view;
    LayoutService layoutService;
    LinearLayout currentMoneyBtn;
    Bundle args;

    public ReportFragment() {
        // Required empty public constructor
    }

    public static ReportFragment newInstance() {
        ReportFragment fragment = new ReportFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_report, container, false);
        currentMoneyBtn = view.findViewById(R.id.report_currentMoney);
        layoutService = new LayoutService(getParentFragmentManager());
        args = new Bundle();

        oncurrentMoneyBtnClick();

        return view;
    }

    void oncurrentMoneyBtnClick(){
        currentMoneyBtn.setOnClickListener(view -> {
            layoutService.loadCurrentMoney(args);
        });
    }
}