package com.example.quanlythuchi.fragment.dashboard;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.quanlythuchi.R;
import com.example.quanlythuchi.activity.LoginActivity;
import com.example.quanlythuchi.adapter.HistoryAdapter;
import com.example.quanlythuchi.callback.historyCallback;
import com.example.quanlythuchi.model.ChiPhi;
import com.example.quanlythuchi.model.GiaoDich;
import com.example.quanlythuchi.model.LichSu;
import com.example.quanlythuchi.service.ChiPhiService;
import com.example.quanlythuchi.service.LichSuChiTieuService;
import com.example.quanlythuchi.service.ThuNhapService;
import com.example.quanlythuchi.util.Commas;

import org.bson.Document;
import org.w3c.dom.Text;

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
    TextView totalReceive;
    TextView totalPay;
    ThuNhapService thuNhapService;
    ChiPhiService chiPhiService;
    Document nguoiDung;

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
        totalPay = view.findViewById(R.id.history_totalPayText);
        totalReceive = view.findViewById(R.id.history_totalReceiveText);
        thuNhapService = new ThuNhapService();
        chiPhiService = new ChiPhiService();
        nguoiDung = new Document("tenDangNhap", LoginActivity.nguoiDung.getTenDangNhap());


        onCreate();
        setTotalPayReceive();

        return view;
    }

    void onCreate(){
        Bundle bundle = getArguments();

        if(bundle != null){
            HistoryAdapter historyAdapter = new HistoryAdapter((List<LichSu>) bundle.getSerializable("lich_su"));
            listItem.setAdapter(historyAdapter);
        }
    }

    @SuppressLint("SetTextI18n")
    void setTotalPayReceive(){
        thuNhapService.totalRevenueOfUsers(nguoiDung).thenAccept(aLong -> {
            totalReceive.setText(Commas.add(aLong) + "đ");
        });

        chiPhiService.totalUserSpend(nguoiDung).thenAccept(aLong -> {
            totalPay.setText(Commas.add(aLong) + "đ");
        });
    }

}