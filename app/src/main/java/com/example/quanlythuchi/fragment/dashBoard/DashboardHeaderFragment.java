package com.example.quanlythuchi.fragment.dashBoard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.quanlythuchi.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashboardHeaderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardHeaderFragment extends Fragment {
    public DashboardHeaderFragment() {
        // Required empty public constructor
    }
    public static DashboardHeaderFragment newInstance() {
        DashboardHeaderFragment fragment = new DashboardHeaderFragment();
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
        // Inflate the LayoutService for this fragment
        return inflater.inflate(R.layout.fragment_dashboard_header, container, false);
    }
}