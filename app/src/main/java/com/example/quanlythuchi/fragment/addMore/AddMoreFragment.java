package com.example.quanlythuchi.fragment.addMore;

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
import androidx.fragment.app.FragmentManager;

import com.example.quanlythuchi.R;
import com.example.quanlythuchi.activity.LoginActivity;
import com.example.quanlythuchi.model.ChiPhi;
import com.example.quanlythuchi.model.DanhMucChi;
import com.example.quanlythuchi.model.DanhMucThu;
import com.example.quanlythuchi.model.ThuNhap;
import com.example.quanlythuchi.service.ChiPhiService;
import com.example.quanlythuchi.service.LayoutService;
import com.example.quanlythuchi.service.NguoiDungService;
import com.example.quanlythuchi.service.ThuNhapService;
import com.example.quanlythuchi.util.CustomTextWatcher;
import com.example.quanlythuchi.util.CustomToast;
import com.google.android.material.datepicker.MaterialDatePicker;

public class AddMoreFragment extends Fragment {
    View view;
    EditText moneyInput;
    EditText typeInput;
    EditText dateAddInput;
    Button chooseTypeBtn;
    boolean isOptionChecked = false;
    boolean isPay = true;
    ConstraintLayout optionBlock;
    TextView optionText;
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
    ThuNhapService thuNhapService;
    FragmentManager fragmentManager;
    ImageView payCheck;
    ImageView receiveCheck;
    ProgressDialog progressDialog;
    CustomToast customToast;
    ImageView optionImg;

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
        optionBlock = view.findViewById(R.id.addMore_optionBlock);
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
        thuNhapService = new ThuNhapService();
        fragmentManager = requireActivity().getSupportFragmentManager();
        payCheck = view.findViewById(R.id.addMore_payCheck);
        receiveCheck = view.findViewById(R.id.addMore_receiveCheck);
        progressDialog = new ProgressDialog(getContext());
        customToast = new CustomToast(getContext());
        optionImg = view.findViewById(R.id.addMore_optionImg);
        optionText = view.findViewById(R.id.addMore_optionText);

        onDateAddClick();
        onChooseTypeClick();
        onCreateView();
        onOptionClick();
        onOptionItemClick();
        onCheckBtnClick();
        moneyInput.addTextChangedListener(new CustomTextWatcher(moneyInput));

        return view;
    }

    void onDateAddClick(){
        dateAddBtn.setOnClickListener(view -> {
            MaterialDatePicker<Long> materialDatePicker =  MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Ngày chi")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds()).build();

            materialDatePicker.addOnPositiveButtonClickListener(selection -> dateAddInput.setText(materialDatePicker.getHeaderText()));
            materialDatePicker.show(getParentFragmentManager(), "TAG");
        });
    }

    void onChooseTypeClick(){
        chooseTypeBtn.setOnClickListener(view -> {
            if(isPay){
                layoutService.loadPayType(getFragmentBundle());
            }
            else{
                layoutService.loadReceiveType(getFragmentBundle());
            }
        });
    }

    Bundle getFragmentBundle(){
        Bundle bundle = new Bundle();

        if(moneyInput.getText() != null){
            bundle.putString("so_tien", String.valueOf(moneyInput.getText()));
        }
        if(descriptionInput.getText() != null){
            bundle.putString("ghi_chu", String.valueOf(descriptionInput.getText()));
        }
        if(dateAddInput.getText() != null){
            bundle.putString("ngay_them", String.valueOf(dateAddInput.getText()));
        }
        bundle.putBoolean("isPay", isPay);

        return bundle;
    }

    @SuppressLint("SetTextI18n")
    void onCreateView(){
        optionContainer.setVisibility(View.GONE);

        Bundle bundle = getArguments();
        if (bundle != null) {
            DanhMucChi danhMucChi = (DanhMucChi) bundle.getSerializable("muc_chi");
            DanhMucThu danhMucThu = (DanhMucThu) bundle.getSerializable("muc_thu");
            oldValue = bundle.getBundle("old_value");

            if(danhMucChi != null){
                this.danhMucChi = danhMucChi;
                typeInput.setText(danhMucChi.getTenDMChi());

            }

            if(danhMucThu != null){
                this.danhMucThu = danhMucThu;
                typeInput.setText(danhMucThu.getTenDMThu());
            }

            if(oldValue != null){
                if(oldValue.getString("so_tien") != null){
                    moneyInput.setText(oldValue.getString("so_tien"));
                }
                if(oldValue.getString("ghi_chu") != null){
                    descriptionInput.setText(oldValue.getString("ghi_chu"));
                }
                if(oldValue.getString("ngay_them") != null){
                    dateAddInput.setText(oldValue.getString("ngay_them"));
                }

                isPay = oldValue.getBoolean("isPay");
            }
        }

        if(isPay){
            optionText.setText("Chi tiền");
            payCheck.setVisibility(View.VISIBLE);
            moneyInput.setTextColor(Color.parseColor("#FF6565"));
            receiveCheck.setVisibility(View.INVISIBLE);
        }
        else{
            optionText.setText("Thu tiền");
            payCheck.setVisibility(View.INVISIBLE);
            moneyInput.setTextColor(Color.parseColor("#36CB2B"));
            receiveCheck.setVisibility(View.VISIBLE);
        }

    }

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    void onOptionItemClick(){
        for (int i = 0; i < optionContainer.getChildCount(); i++) {
            ConstraintLayout item = (ConstraintLayout) optionContainer.getChildAt(i);

            item.setOnClickListener(target -> {
                switch (target.getId()){
                    case R.id.addMore_payBtn:{
                        optionText.setText("Chi tiền");
                        payCheck.setVisibility(View.VISIBLE);
                        receiveCheck.setVisibility(View.INVISIBLE);
                        isPay = true;
                        break;
                    }
                    case R.id.addMore_receiveBtn:{
                        optionText.setText("Thu tiền");
                        payCheck.setVisibility(View.INVISIBLE);
                        receiveCheck.setVisibility(View.VISIBLE);
                        isPay = false;
                        break;
                    }
                }
                optionContainer.setVisibility(View.GONE);
                optionImg.setRotation(0);
                resetForm();
            });
        }
    }

    void onOptionClick(){
        optionBlock.setOnClickListener(target -> {
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

    void resetForm(){
        moneyInput.setText("");
        typeInput.setText("");
        descriptionInput.setText("");
        dateAddInput.setText("");
    }

    void onCheckBtnClick(){

        checkBtn.setOnClickListener(view -> {
            progressDialog.setMessage("Đang cập nhập thông tin của bạn");
            progressDialog.show();

            if(isPay){

                ChiPhi chiPhi = new ChiPhi(LoginActivity.nguoiDung, danhMucChi,
                        Long.parseLong(String.valueOf(moneyInput.getText()).replace(",", "")),
                        String.valueOf(dateAddInput.getText()), String.valueOf(descriptionInput.getText()));

                chiPhiService.insertOne(chiPhi).thenAccept(value -> {
                    customToast.show("Cập nhập thành công");
                    progressDialog.cancel();
                }).exceptionally(err -> {
                    progressDialog.cancel();
                    return null;
                });
            }
            else{
                ThuNhap thuNhap = new ThuNhap(LoginActivity.nguoiDung, danhMucThu,
                        Long.parseLong(String.valueOf(moneyInput.getText()).replace(",", "")),
                        String.valueOf(dateAddInput.getText()), String.valueOf(descriptionInput.getText()));

                thuNhapService.insertOne(thuNhap).thenAccept(value -> {
                    customToast.show("Cập nhập thất bại");
                    progressDialog.cancel();
                }).exceptionally(err -> {
                    progressDialog.cancel();
                    return null;
                });
            }
        });
    }
}