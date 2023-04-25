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
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class HistoryFragment extends Fragment {
    List<LichSu> lichSus = new ArrayList<>();
    RecyclerView listItem;
    View view;

    public HistoryFragment() {
        // Required empty public constructor
    }

    public static HistoryFragment newInstance() {
        HistoryFragment fragment = new HistoryFragment();
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
        view = inflater.inflate(R.layout.fragment_history, container, false);
        listItem = view.findViewById(R.id.history_List);

        getHistory(new historyCallback() {
            @Override
            public View onHistoryLoaded(Map<String, List<GiaoDich>> mapLichsu, View view) {
                for (Map.Entry<String, List<GiaoDich>> entry : mapLichsu.entrySet()) {
                    String ngay = entry.getKey();

                    List<GiaoDich> giaoDichs = entry.getValue();

                    lichSus.add(new LichSu(ngay, giaoDichs));
                }

                Log.i(TAG, "getHistory: " + lichSus);

                HistoryAdapter historyAdapter = new HistoryAdapter(lichSus);
                listItem.setAdapter(historyAdapter);
                listItem.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
                return view;
            }
        });
        // return null temporarily
        return null;

    }

    void getHistory(historyCallback callback) {
        LichSuChiTieuService lichSuChiTieuService = new LichSuChiTieuService();
        Document document = new Document("tenDangNhap", LoginActivity.nguoiDung.getTenDangNhap());

        lichSuChiTieuService.getTransactionHistory(document).thenAccept(map -> {
            TreeMap<String, List<GiaoDich>> sortedMap = new TreeMap<>(Collections.reverseOrder());

            sortedMap.putAll(map);

            callback.onHistoryLoaded(sortedMap, this.view);
        }).exceptionally(e -> {
            return null;
        });
    }


    void getHistoryList(){
//        getHistory(new historyCallback() {
//            @Override
//            public void onHistoryLoaded(Map<String, List<GiaoDich>> mapLichsu) {
//                for (Map.Entry<String, List<GiaoDich>> entry : mapLichsu.entrySet()) {
//                    String ngay = entry.getKey();
//
//                    List<GiaoDich> giaoDichs = entry.getValue();
//
//                    lichSus.add(new LichSu(ngay, giaoDichs));
//                }
//
//                Log.i(TAG, "getHistory: " + lichSus);
//
//                HistoryAdapter historyAdapter = new HistoryAdapter(lichSus);
//                listItem.setAdapter(historyAdapter);
//                listItem.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
//            }
//        });
    }

    void initData(){
        GiaoDich giaoDich = new GiaoDich("Test", "", 100000, true);
        GiaoDich giaoDich1 = new GiaoDich("Test", "", 100000, true);
        GiaoDich giaoDich2 = new GiaoDich("Test", "", 100000, true);
        GiaoDich giaoDich3 = new GiaoDich("Test", "", 100000, true);
        GiaoDich giaoDich4 = new GiaoDich("Test", "", 100000, true);

        List<GiaoDich> giaoDiches = new ArrayList<>();
        giaoDiches.add(giaoDich);
        giaoDiches.add(giaoDich1);
        giaoDiches.add(giaoDich2);
        giaoDiches.add(giaoDich3);
        giaoDiches.add(giaoDich4);

        lichSus.add(new LichSu("12", giaoDiches));

        Log.i(TAG, "initData: " + lichSus);
    }

//    void getHistory(historyCallback callback){
//        LichSuChiTieuService lichSuChiTieuService = new LichSuChiTieuService();
//        Document document = new Document("tenDangNhap", LoginActivity.nguoiDung.getTenDangNhap());
//
//        lichSuChiTieuService.getTransactionHistory(document).thenAccept(map -> {
//            TreeMap<String, List<GiaoDich>> sortedMap = new TreeMap<>(Collections.reverseOrder());
//
//            sortedMap.putAll(map);
//            callback.onHistoryLoaded(sortedMap);
//
//        }).exceptionally(e -> {
//            return null;
//        });
//    }
}