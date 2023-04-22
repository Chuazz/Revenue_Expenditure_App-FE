package com.example.quanlythuchi.fragment.addMore;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.example.quanlythuchi.model.ChiPhi;
import com.example.quanlythuchi.model.DanhMucChi;
import com.example.quanlythuchi.model.DanhMucThu;
import com.example.quanlythuchi.service.ChiPhiService;
import com.example.quanlythuchi.service.LayoutService;
import com.example.quanlythuchi.service.NguoiDungService;
import com.example.quanlythuchi.util.CustomToast;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

public class AddMoreFragment extends Fragment {
    View view;
    EditText moneyInput;
    EditText typeInput;
    EditText dateAddInput;
    Button chooseTypeBtn;
    boolean isOptionChecked = false;
    boolean isPay = true;
    Button optionBtn;
    LinearLayout optionContainer;
    ImageView checkBtn;
    LayoutService layoutService;
    NguoiDungService nguoiDungService;
    Button dateAddBtn;
    ChiPhiService chiPhiService;
    DanhMucChi danhMucChi;
    DanhMucThu danhMucThu;
    EditText descriptionInput;
    Bundle oldValue;

    public AddMoreFragment() {
    }

    public static AddMoreFragment newInstance() {
        AddMoreFragment fragment = new AddMoreFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("test", String.valueOf(moneyInput.getText()));

        Log.i(TAG, "onSaveInstanceState: ");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            Log.i(TAG, "onCreate: " + String.valueOf(savedInstanceState.getString("test")));
        }
    }

    void loadOldForm(Bundle savedInstanceState){
        if(savedInstanceState != null){
            if(savedInstanceState.get("so_tien") != null){
                moneyInput.setText(String.valueOf(savedInstanceState.get("so_tien")));
            }
            if(savedInstanceState.get("ghi_chu") != null){
                descriptionInput.setText(String.valueOf(savedInstanceState.get("ghi_chu")));
            }
            if(savedInstanceState.get("ngay_chi") != null){
                dateAddInput.setText(String.valueOf(savedInstanceState.get("ngay_chi")));
            }
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
        oldValue = new Bundle();

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
                layoutService.loadReceiveType();
            }
        });
    }

    void onCreateView(){
        optionContainer.setVisibility(View.GONE);

        Bundle bundle = getArguments();
        if (bundle != null) {
            DanhMucChi danhMucChi = (DanhMucChi) bundle.getSerializable("muc_chi");
            DanhMucThu danhMucThu = (DanhMucThu) bundle.getSerializable("muc_thu");


            if(danhMucChi != null){
                this.danhMucChi = danhMucChi;
                typeInput.setText(danhMucChi.getTenDMChi());
            }

            if(danhMucThu != null){
                this.danhMucThu = danhMucThu;
                typeInput.setText(danhMucThu.getTenDMThu());
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
                            isPay = true;
                            break;
                        }
                        case R.id.addMore_receiveBtn:{
                            optionBtn.setText("Thu tiền");
                            payCheck.setVisibility(View.INVISIBLE);
                            receiveCheck.setVisibility(View.VISIBLE);
                            isPay = false;
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
                        new CustomToast(getContext()).show("Thông tin bạn cung cấp không hợp lệ");
                    }
                });
            }
        });
    }
}