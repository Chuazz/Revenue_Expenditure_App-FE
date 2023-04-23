package com.example.quanlythuchi.fragment.dashboard;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quanlythuchi.R;
import com.example.quanlythuchi.activity.LoginActivity;
import com.example.quanlythuchi.service.NguoiDungService;
import com.example.quanlythuchi.util.Editor;

import org.bson.Document;

import java.text.DecimalFormat;

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

        onCreateView();
        onShowMoneyClick();
        Editor.makeBold(labelDescription2, "Sổ Thu Chi");

        return view;
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