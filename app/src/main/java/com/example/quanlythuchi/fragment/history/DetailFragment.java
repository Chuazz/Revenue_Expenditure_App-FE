package com.example.quanlythuchi.fragment.history;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.quanlythuchi.R;
import com.example.quanlythuchi.model.GiaoDich;
import com.example.quanlythuchi.util.Commas;
import com.example.quanlythuchi.util.CustomToast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {
    EditText money;
    EditText type;
    EditText note;
    EditText date;
    TextView optionBlockText;
    boolean isOptionChecked = false;
    boolean isReceive = true;
    ConstraintLayout optionBlockBtn;
    LinearLayout optionContainer;
    ConstraintLayout removeBtn;
    Button dateAddBtn;
    ImageView payCheck;
    ImageView receiveCheck;
    ProgressDialog progressDialog;
    CustomToast customToast;
    ImageView optionImg;

    ConstraintLayout saveBtn;

    public DetailFragment() {
    }

    public static DetailFragment newInstance(Date date, GiaoDich giaoDich) {
        DetailFragment fragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("date", date);
        bundle.putSerializable("giao_dich", giaoDich);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        money = view.findViewById(R.id.detail_addMoneyText);
        type = view.findViewById(R.id.detail_typeInput);
        note = view.findViewById(R.id.detail_descriptionInput);
        date = view.findViewById(R.id.detail_dateAddInput);
        optionBlockBtn = view.findViewById(R.id.detail_optionBlock);
        optionContainer = view.findViewById(R.id.detail_optionContainer);
        optionImg = view.findViewById(R.id.detail_optionImg);
        optionBlockText = view.findViewById(R.id.detail_optionText);
        payCheck = view.findViewById(R.id.detail_payCheck);
        receiveCheck = view.findViewById(R.id.detail_receiveCheck);
        removeBtn = view.findViewById(R.id.detail_removeBtn);
        saveBtn = view.findViewById(R.id.detail_saveBtn);

        onCreate();

        return view;
    }

    @SuppressLint("SetTextI18n")
    void onCreate() {
        this.setForm();

        this.onOptionBlockBtnClick();

        if(isReceive){
            optionBlockText.setText("Thu tiền");
            payCheck.setVisibility(View.INVISIBLE);
            money.setTextColor(Color.parseColor("#36CB2B"));
            receiveCheck.setVisibility(View.VISIBLE);
        }
        else{
            optionBlockText.setText("Chi tiền");
            payCheck.setVisibility(View.VISIBLE);
            money.setTextColor(Color.parseColor("#FF6565"));
            receiveCheck.setVisibility(View.INVISIBLE);
        }
    }

    @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
    void setForm(){
        Bundle bundle = getArguments();

        if(bundle != null){
            Date date_bundle = (Date) bundle.getSerializable("date");
            GiaoDich giaoDich = (GiaoDich) bundle.getSerializable("giao_dich");
            SimpleDateFormat targetFormat = new SimpleDateFormat("MMM d, yyyy");

            date.setText(targetFormat.format(date_bundle));
            money.setText(Commas.add(giaoDich.getTien()));
            type.setText(giaoDich.getDanhMuc());
            note.setText(giaoDich.getGhiChu());
            isReceive = giaoDich.isThuNhap();

            if(!isReceive){
                optionBlockText.setText("Thu tiền");
            }
        }
    }

    void onOptionBlockBtnClick(){
        optionBlockBtn.setOnClickListener(view -> {
            if(isOptionChecked){
                optionContainer.setVisibility(View.GONE);
                optionImg.setRotation(0);
            }
            else{
                optionContainer.setVisibility(View.VISIBLE);
                optionImg.setRotation(180);
            }
            isOptionChecked = !isOptionChecked;
        });
    }
}