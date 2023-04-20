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
import com.example.quanlythuchi.callback.InsertCallback;
import com.example.quanlythuchi.callback.nguoidung.FindOneCallback;
import com.example.quanlythuchi.model.ChiPhi;
import com.example.quanlythuchi.model.DanhMucChi;
import com.example.quanlythuchi.model.NguoiDung;
import com.example.quanlythuchi.service.ChiPhiService;
import com.example.quanlythuchi.service.LayoutService;
import com.example.quanlythuchi.service.NguoiDungService;
import com.example.quanlythuchi.util.CustomToast;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import org.bson.Document;

public class AddMoreFragment extends Fragment {
    View view;
    EditText moneyInput;
    EditText typeInput;
    EditText dateAddInput;
    Button chooseTypeBtn;
    boolean isOptionChecked = false;
    Button optionBtn;
    LinearLayout optionContainer;
    ImageView checkBtn;
    LayoutService layoutService;
    NguoiDungService nguoiDungService;
    Button dateAddBtn;
    ChiPhiService chiPhiService;
    DanhMucChi danhMucChi;
    EditText descriptionInput;

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

        if (savedInstanceState != null) {
            DanhMucChi danhMucChi = (DanhMucChi) savedInstanceState.getSerializable("muc_chi");

            Log.i(TAG, "onCreateView: " + danhMucChi.getTenDMChi());
        }
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
        nguoiDungService = new NguoiDungService();
        dateAddBtn = view.findViewById(R.id.addMore_chooseDateAddBtn);
        typeInput = view.findViewById(R.id.addMore_typeInput);
        chiPhiService = new ChiPhiService();
        danhMucChi = new DanhMucChi();
        descriptionInput = view.findViewById(R.id.addMore_descriptionInput);

        onDateAddClick();
        onChooseTypeClick();
        onCreateView();
        onOptionClick();
        onOptionItemClick();
        onCheckBtnClick();

        return view;
    }

    void onDateAddClick(){
        dateAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialDatePicker<Long> materialDatePicker =  MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Ngày chi")
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds()).build();

                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selection) {
                        dateAddInput.setText(materialDatePicker.getHeaderText());
                    }
                });
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

        Bundle bundle = getArguments();
        if (bundle != null) {
            DanhMucChi danhMucChi = (DanhMucChi) bundle.getSerializable("muc_chi");
            this.danhMucChi = danhMucChi;

            if(danhMucChi != null){
                typeInput.setText(danhMucChi.getTenDMChi());
            }
        }
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
                ChiPhi chiPhi = new ChiPhi(LoginActivity.nguoiDung, danhMucChi,
                        Long.parseLong(String.valueOf(moneyInput.getText())),
                        String.valueOf(dateAddInput.getText()), String.valueOf(descriptionInput.getText()));
                chiPhiService.insertOne(chiPhi, new InsertCallback() {
                    @Override
                    public void onSuccess() {
                        new CustomToast(getContext()).show("Thêm thành công");
                    }

                    @Override
                    public void onFailure() {
                        new CustomToast(getContext()).show("Lỗi mẹ rồi");
                    }
                });
            }
        });
    }
}