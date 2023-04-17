package com.example.quanlythuchi.fragment.report;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quanlythuchi.R;

public class ReportHeaderFragment extends Fragment {

    public ReportHeaderFragment() {
        // Required empty public constructor
    }

    public static ReportHeaderFragment newInstance() {
        ReportHeaderFragment fragment = new ReportHeaderFragment();
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
        return inflater.inflate(R.layout.fragment_report_header, container, false);
    }
}