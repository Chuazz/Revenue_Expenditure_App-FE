package com.example.quanlythuchi.fragment.addMore;

import static android.view.View.inflate;
import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.quanlythuchi.R;
import com.example.quanlythuchi.activity.LoginActivity;
import com.example.quanlythuchi.callback.nguoidung.FindOneCallback;
import com.example.quanlythuchi.model.NguoiDung;
import com.example.quanlythuchi.service.LayoutService;
import com.example.quanlythuchi.service.NguoiDungService;
import com.google.android.material.datepicker.MaterialDatePicker;

import org.bson.Document;

public class AddMoreFragment extends Fragment {
    View view;
    EditText moneyInput;
    EditText dateAddInput;
    Button chooseTypeBtn;
    boolean isOptionChecked = false;
    Button optionBtn;
    LinearLayout optionContainer;
    ImageView checkBtn;
    LayoutService layoutService;

    public AddMoreFragment() {
    }

    public static AddMoreFragment newInstance() {
        AddMoreFragment fragment = new AddMoreFragment();
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
        view = inflater.inflate(R.layout.fragment_add_more, container, false);
        moneyInput = view.findViewById(R.id.addMore_addMoneyText);
        dateAddInput = view.findViewById(R.id.addMore_dateAddInput);
        chooseTypeBtn = view.findViewById(R.id.addMore_chooseTypeBtn);
        optionBtn = view.findViewById(R.id.addMore_optionBtn);
        optionContainer = view.findViewById(R.id.addMore_optionContainer);
        checkBtn = view.findViewById(R.id.addMore_checkBtn);
        layoutService = new LayoutService(getParentFragmentManager());

        onDateAddClick();
        onChooseTypeClick();
        onCreateView();
        onOptionClick();
        onOptionItemClick();
        onCheckBtnClick();

        return view;
    }

    void onDateAddClick(){
        dateAddInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialDatePicker<Long> materialDatePicker =  MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Ngày thu")
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds()).build();

                materialDatePicker.show(getParentFragmentManager(), "TAG");
            }
        });
    }

    void onChooseTypeClick(){
        chooseTypeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutService.loadPayType();
            }
        });
    }

    void onCreateView(){
        optionContainer.setVisibility(View.GONE);
    }

    void onOptionItemClick(){
        ImageView payCheck = (ImageView) view.findViewById(R.id.addMore_payCheck);
        ImageView receiveCheck = (ImageView) view.findViewById(R.id.addMore_receiveCheck);

        for (int i = 0; i < optionContainer.getChildCount(); i++) {
            ConstraintLayout item = (ConstraintLayout) optionContainer.getChildAt(i);

            item.setOnClickListener(new View.OnClickListener() {
                @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
                @Override
                public void onClick(View target) {
                    switch (target.getId()){
                        case R.id.addMore_payBtn:{
                            optionBtn.setText("Chi tiền");
                            payCheck.setVisibility(View.VISIBLE);
                            receiveCheck.setVisibility(View.INVISIBLE);
                            break;
                        }
                        case R.id.addMore_receiveBtn:{
                            optionBtn.setText("Thu tiền");
                            payCheck.setVisibility(View.INVISIBLE);
                            receiveCheck.setVisibility(View.VISIBLE);
                            break;
                        }
                    }
                    optionContainer.setVisibility(View.GONE);
                }
            });
        }
    }

    void onOptionClick(){
        optionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View target) {
                if(isOptionChecked){
                    optionContainer.setVisibility(View.GONE);
                }
                else{
                    optionContainer.setVisibility(View.VISIBLE);
                }
                isOptionChecked = !isOptionChecked;

            }
        });
    }

    void onCheckBtnClick(){
        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NguoiDungService nguoiDungService = new NguoiDungService();
                Document query = new Document("TenDangNhap", LoginActivity.userName);

                Log.i(TAG, "onClick: " + LoginActivity.userName);

                nguoiDungService.findOne(query, new FindOneCallback() {
                    @Override
                    public void onSuccess(NguoiDung result) {
                        Log.i(TAG, "onSuccess: " + result.getHoTen());
                        Log.i(TAG, "onSuccess: " + result);
                    }

                    @Override
                    public void onFailure() {

                    }
                });
            }
        });
    }
}