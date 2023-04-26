package com.example.quanlythuchi.fragment.dashboard;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.quanlythuchi.R;
import com.example.quanlythuchi.activity.LoginActivity;
import com.example.quanlythuchi.model.GiaoDich;
import com.example.quanlythuchi.model.LichSu;
import com.example.quanlythuchi.service.LayoutService;
import com.example.quanlythuchi.service.LichSuChiTieuService;
import com.example.quanlythuchi.service.NguoiDungService;
import com.example.quanlythuchi.service.shareService;

import org.bson.Document;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends Fragment {
    View view;
    TextView userName;
    TextView userBalance;
    NguoiDungService nguoiDungService;
    DecimalFormat decimalFormat = new DecimalFormat("#,###");
    ImageView showMoney;
    boolean isOpen = true;
    Long userMoney;
    TextView labelDescription2;
    LinearLayout historyBtn;
    LayoutService layoutService;
    Bundle args;
    List<LichSu> lichSus;


    public DashboardFragment() {
        // Required empty public constructor
    }

    public static DashboardFragment newInstance() {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        userName = view.findViewById(R.id.dashboard_useName);
        userBalance = view.findViewById(R.id.dashboard_textBalance);
        nguoiDungService = new NguoiDungService();
        showMoney = view.findViewById(R.id.dashboard_showMoney);
        labelDescription2 = view.findViewById(R.id.dashboard_labelDescription2);
        historyBtn = view.findViewById(R.id.dashboard_historyRecord);
        layoutService = new LayoutService(getParentFragmentManager());
        lichSus = new ArrayList<>();
        args = new Bundle();

        onCreateView();
        getHistory();
        onShowMoneyClick();
        onHistoryBtnClick();

        return view;
    }

    void getHistory(){
        LichSuChiTieuService lichSuChiTieuService = new LichSuChiTieuService();
        Document document = new Document("tenDangNhap", LoginActivity.nguoiDung.getTenDangNhap());

        lichSuChiTieuService.getTransactionHistory(document).thenAccept(map -> {
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
            args.putSerializable("lich_su", (Serializable) lichSus);
        }).exceptionally(e -> {
            Log.v("EXAMPLE", "Lỗi xảy ra trong quá trình lấy lịch sử chi tiêu!");
            return null;
        });
    }

    void onHistoryBtnClick(){
        historyBtn.setOnClickListener(view -> {
            layoutService.loadHistory(args);
        });
    }


    @SuppressLint("SetTextI18n")
    void onShowMoneyClick(){
        showMoney.setOnClickListener(view -> {
            if(isOpen){
                showMoney.setImageResource(R.drawable.ic_close_eye);
                userBalance.setText("***000đ");
            }
            else{
                showMoney.setImageResource(R.drawable.ic_open_eye);
                userBalance.setText(decimalFormat.format(userMoney) + "đ");
            }

            isOpen = !isOpen;
        });
    }

    @SuppressLint("SetTextI18n")
    void onCreateView(){
        userName.setText("Chào " + LoginActivity.nguoiDung.getHoTen() + "!");

        Document tenDangNhap = new Document("tenDangNhap", LoginActivity.nguoiDung.getTenDangNhap());
        nguoiDungService.theUserExistingMoney(tenDangNhap).thenAccept(sum -> {
            if (sum != null) {
                userBalance.setText(decimalFormat.format(sum) + "đ");
                userMoney = sum;

                if(sum >= 0 ){
                    userBalance.setTextColor(Color.parseColor("#39A5F3"));
                }
                else{
                    userBalance.setTextColor(Color.parseColor("#FF6565"));
                }
            } else {
                System.out.println("Error occurred while calculating the user's existing money.");
            }
        }).exceptionally(e -> {
            System.err.println("Error occurred while calculating the user's existing money: " + e.getMessage());
            return null;
        });
    }
}