package com.example.quanlythuchi.fragment.dashboard;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quanlythuchi.R;
import com.example.quanlythuchi.activity.LoginActivity;
import com.example.quanlythuchi.adapter.HistoryAdapter;
import com.example.quanlythuchi.callback.historyCallback;
import com.example.quanlythuchi.model.ChiPhi;
import com.example.quanlythuchi.model.GiaoDich;
import com.example.quanlythuchi.model.LichSu;
import com.example.quanlythuchi.service.ChiPhiService;
import com.example.quanlythuchi.service.LayoutService;
import com.example.quanlythuchi.service.LichSuChiTieuService;
import com.example.quanlythuchi.service.ThuNhapService;
import com.example.quanlythuchi.service.shareService;
import com.example.quanlythuchi.util.Commas;

import org.bson.Document;
import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class HistoryFragment extends Fragment {
    List<LichSu> lichSus = new ArrayList<>();
    RecyclerView listItem;
    View view;
    TextView totalReceive;
    TextView totalPay;
    ThuNhapService thuNhapService;
    ChiPhiService chiPhiService;
    Document nguoiDung;
    ProgressDialog progressDialog;
    ImageView backBtn;
    LayoutService layoutService;

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
        progressDialog = new ProgressDialog(getContext());
        backBtn = view.findViewById(R.id.history_backBtn);
        layoutService = new LayoutService(getParentFragmentManager());

        progressDialog.setMessage("Đang lấy thông tin của bạn");
        progressDialog.setCancelable(false);
        progressDialog.show();

        onCreate();
        setTotalPayReceive();
        getHistory();
        onClickBackBtn();

        return view;
    }

    void getHistory(){
        LichSuChiTieuService lichSuChiTieuService = new LichSuChiTieuService();
        Document document = new Document("tenDangNhap", LoginActivity.nguoiDung.getTenDangNhap());

        CompletableFuture<Void> processedFuture = lichSuChiTieuService.getTransactionHistory(document).thenAccept(map -> {
            TreeMap<Date, List<GiaoDich>> sortedMap = new TreeMap<>(Collections.reverseOrder());

            sortedMap.putAll(map);

            for (Map.Entry<Date, List<GiaoDich>> entry : sortedMap.entrySet()) {
                Date key = entry.getKey();
                List<GiaoDich> giaoDiches = entry.getValue();

                LichSu lichSu = new LichSu(key, giaoDiches);

                // Kiểm tra có phải ngày hôm nay không
                if(key.compareTo(new Date()) == 0) {
                    lichSu.setToday(true);
                }

                // Lấy thứ, ngày, tháng, năm
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(key);
                lichSu.set_thu(shareService.getDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK)));
                lichSu.set_ngay(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
                lichSu.set_thang(String.valueOf(calendar.get(Calendar.MONTH) + 1));
                lichSu.set_nam(String.valueOf(calendar.get(Calendar.YEAR)));

                // Tính tổng thu/chi trong 1 ngày
                for (GiaoDich giaoDich : giaoDiches) {
                    if(giaoDich.isThuNhap()) {
                        lichSu.plusThu(giaoDich.getTien());
                    }
                    else {
                        lichSu.plusChi(giaoDich.getTien());
                    }
                }

                lichSus.add(lichSu);
            }
            listItem.post(() -> {
                HistoryAdapter historyAdapter = new HistoryAdapter(lichSus);
                listItem.setAdapter(historyAdapter);
                progressDialog.cancel();
            });
        }).exceptionally(e -> {
            Log.v("EXAMPLE", "Lỗi xảy ra trong quá trình lấy lịch sử chi tiêu!");
            return null;
        });
    }

    void onCreate(){
        Bundle bundle = getArguments();

//        if(bundle != null){
//            HistoryAdapter historyAdapter = new HistoryAdapter((List<LichSu>) bundle.getSerializable("lich_su"));
//            listItem.setAdapter(historyAdapter);
//        }
    }

    void onClickBackBtn(){
        backBtn.setOnClickListener(e -> {
            layoutService.loadDashboard();
        });
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