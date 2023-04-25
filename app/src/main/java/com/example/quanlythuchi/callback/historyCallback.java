package com.example.quanlythuchi.callback;

import android.view.View;

import com.example.quanlythuchi.model.GiaoDich;

import java.util.List;
import java.util.Map;

public interface historyCallback {
    View onHistoryLoaded(Map<String, List<GiaoDich>> mapLichsu, View view);
}
