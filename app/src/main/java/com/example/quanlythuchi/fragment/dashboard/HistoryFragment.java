package com.example.quanlythuchi.fragment.dashboard;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quanlythuchi.R;
import com.example.quanlythuchi.activity.LoginActivity;
import com.example.quanlythuchi.adapter.HistoryAdapter;
import com.example.quanlythuchi.callback.historyCallback;
import com.example.quanlythuchi.model.GiaoDich;
import com.example.quanlythuchi.model.LichSu;
import com.example.quanlythuchi.service.LichSuChiTieuService;

import org.bson.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class HistoryFragment extends Fragment {
    List<LichSu> lichSus = new ArrayList<>();
    RecyclerView listItem;
    View view;

    public HistoryFragment() {
        // Required empty public constructor
    }

    public static HistoryFragment newInstance(Bundle args) {
        HistoryFragment fragment = new HistoryFragment();
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
        view = inflater.inflate(R.layout.fragment_history, container, false);
        listItem = view.findViewById(R.id.history_List);

        Bundle bundle = getArguments();

        if(bundle != null){
            HistoryAdapter historyAdapter = new HistoryAdapter((List<LichSu>) bundle.getSerializable("lich_su"));
            listItem.setAdapter(historyAdapter);
        }

        return view;
    }
}