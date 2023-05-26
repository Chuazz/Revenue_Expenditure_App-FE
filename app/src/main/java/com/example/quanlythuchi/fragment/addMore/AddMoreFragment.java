package com.example.quanlythuchi.fragment.addMore;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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
import com.example.quanlythuchi.model.GiaoDich;
import com.example.quanlythuchi.model.NguoiDung;
import com.example.quanlythuchi.model.ThuNhap;
import com.example.quanlythuchi.service.ChiPhiService;
import com.example.quanlythuchi.service.LayoutService;
import com.example.quanlythuchi.service.NguoiDungService;
import com.example.quanlythuchi.service.ThuNhapService;
import com.example.quanlythuchi.util.Commas;
import com.example.quanlythuchi.util.CustomTextWatcher;
import com.example.quanlythuchi.util.CustomToast;
import com.google.android.material.datepicker.MaterialDatePicker;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

public class AddMoreFragment extends Fragment {
    View view;
    EditText moneyInput;
    EditText typeInput;
    EditText dateAddInput;
    Button chooseTypeBtn;
    boolean isOptionChecked = false;
    boolean isPay = true;
    boolean isUpdate = false;
    ConstraintLayout actionUpdateBlock;
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
    ConstraintLayout removeBtn;
    ConstraintLayout saveBtn;

    public AddMoreFragment() {
    }

    public static AddMoreFragment newInstance(Date date, GiaoDich giaoDich, Boolean isUpdate) {
        AddMoreFragment fragment = new AddMoreFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("date", date);
        bundle.putSerializable("giao_dich", giaoDich);
        bundle.putBoolean("is_update", isUpdate);
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
        actionUpdateBlock = view.findViewById(R.id.addMore_actionUpdate);
        removeBtn = view.findViewById(R.id.addMore_removeBtn);
        saveBtn = view.findViewById(R.id.addMore_saveBtn);

        progressDialog.setMessage("Đang cập nhập thông tin của bạn");

        onDateAddClick();
        onChooseTypeClick();
        onCreateView();
        onOptionClick();
        onOptionItemClick();
        onCheckBtnClick();
        onRemoveBtnClick();
        onSaveBtnClick();
        moneyInput.addTextChangedListener(new CustomTextWatcher(moneyInput));

        return view;
    }

    void onSaveBtnClick() {
        saveBtn.setOnClickListener(e -> {
            Bundle args = getArguments();
            progressDialog.show();
            // Chưa sửa nè
            GiaoDich giaoDich = (GiaoDich) args.getSerializable("giao_dich");
            Document queryFilter  = new Document("maGD", giaoDich.getMaGD());

            // Sửa rồi nè
            Integer money = Integer.parseInt(Commas.remove(String.valueOf(moneyInput.getText())));
            String description = String.valueOf(descriptionInput.getText());
            String date = String.valueOf(dateAddInput.getText());

            if(isPay){
                Document updateDocument  = new Document("$set",
                        new Document("ghiChu", description).append("tienChi", money).append("ngayChi", date));
                CompletableFuture<Long> updateFuture = chiPhiService.updateOne(queryFilter, updateDocument);
                updateFuture.thenAccept((result) -> {
                    if(result == 1) {
                        customToast.show("Cập nhập thành công");
                    } else {
                        customToast.show("Cập nhập thất bại");
                    }
                    progressDialog.cancel();
                });
            }
            else{
                Document updateDocument  = new Document("$set",
                        new Document("ghiChu", description).append("tienThu", money).append("ngayThu", date));
                CompletableFuture<Long> updateFuture = thuNhapService.updateOne(queryFilter, updateDocument);
                updateFuture.thenAccept((result) -> {
                    if(result == 1) {
                        customToast.show("Cập nhập thành công");
                    } else {
                        customToast.show("Cập nhập thất bại");
                    }
                    progressDialog.cancel();
                });
            }
        });
    }

    void onRemoveBtnClick() {
        removeBtn.setOnClickListener(e -> {
            Bundle args = getArguments();
            progressDialog.show();

            GiaoDich giaoDich = (GiaoDich) args.getSerializable("giao_dich");
            Document queryFilter  = new Document("maGD", giaoDich.getMaGD());

            if(isPay) {
                CompletableFuture<Long> deleteFuture = chiPhiService.deleteOne(queryFilter);
                deleteFuture.thenAccept((result) -> {
                    if(result != 0){
                        customToast.show("Xóa giao dịch thành công");
                        layoutService.loadHistory(null);
                    }
                    else{
                        customToast.show("Xóa giao dịch thất bại");
                    }
                    progressDialog.cancel();
                }).exceptionally(error -> {
                    Log.v("EXAMPLE", "Đã xảy ra lỗi khi xóa: " + error.getMessage());
                    return null;
                });
            }
            else {
                CompletableFuture<Long> deleteFuture = thuNhapService.deleteOne(queryFilter);
                deleteFuture.thenAccept((result) -> {
                    if(result != 0){
                        customToast.show("Xóa giao dịch thành công");
                        layoutService.loadHistory(null);
                    }
                    else{
                        customToast.show("Xóa giao dịch thất bại");
                    }
                    progressDialog.cancel();
                }).exceptionally(error -> {
                    Log.v("EXAMPLE", "Đã xảy ra lỗi khi xóa: " + error.getMessage());
                    return null;
                });
            }
        });
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
            Bundle bundle = new Bundle();
            bundle.putBoolean("is_pay", isPay);
            bundle.putString("money", String.valueOf(moneyInput.getText()));
            bundle.putString("description", String.valueOf(descriptionInput.getText()));
            bundle.putString("date", String.valueOf(dateAddInput.getText()));
            bundle.putBoolean("is_update", isUpdate);

            if(isPay){
                layoutService.loadPayType(bundle);
            }
            else{
                layoutService.loadReceiveType(bundle);
            }
        });
    }

    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    void onCreateView(){
        optionContainer.setVisibility(View.GONE);

        Bundle bundle = getArguments();
        if (bundle != null) {
            DanhMucChi danhMucChi = (DanhMucChi) bundle.getSerializable("muc_chi");
            DanhMucThu danhMucThu = (DanhMucThu) bundle.getSerializable("muc_thu");
            oldValue = bundle.getBundle("old_value");

            setFormDataDetail(bundle);

            if(danhMucChi != null){
                this.danhMucChi = danhMucChi;
                typeInput.setText(danhMucChi.getTenDMChi());
            }

            if(danhMucThu != null){
                this.danhMucThu = danhMucThu;
                typeInput.setText(danhMucThu.getTenDMThu());
            }

            if(oldValue != null){
                if(oldValue.getString("money") != null){
                    moneyInput.setText(oldValue.getString("money"));
                }
                if(oldValue.getString("description") != null){
                    descriptionInput.setText(oldValue.getString("description"));
                }
                if(oldValue.getString("date") != null){
                    dateAddInput.setText(oldValue.getString("date"));
                }
                isPay = oldValue.getBoolean("is_pay");
                isUpdate = oldValue.getBoolean("is_update");
            }

            if(isUpdate){
                actionUpdateBlock.setVisibility(View.VISIBLE);
                this.refreshForm();
            }
            else{
                actionUpdateBlock.setVisibility(View.GONE);
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

    @SuppressLint("SimpleDateFormat")
    void setFormDataDetail(Bundle bundle){
        GiaoDich giaoDich = (GiaoDich) bundle.getSerializable("giao_dich");

        if(giaoDich != null){
            Date date_bundle = (Date) bundle.getSerializable("date");
            SimpleDateFormat targetFormat = new SimpleDateFormat("MMM d, yyyy");

            this.moneyInput.setText(Commas.add(giaoDich.getTien()));
            this.typeInput.setText(giaoDich.getDanhMuc());
            this.descriptionInput.setText(giaoDich.getGhiChu());
            this.dateAddInput.setText(targetFormat.format(date_bundle));
            this.isUpdate = bundle.getBoolean("is_update");
            this.isPay = !giaoDich.isThuNhap();
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
                if(isUpdate){
                    refreshForm();
                }
                else{
                    resetForm();
                }
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

    @SuppressLint("SetTextI18n")
    void resetForm(){
        moneyInput.setText("0");
        typeInput.setText("");
        this.refreshForm();
    }

    @SuppressLint("SetTextI18n")
    void refreshForm(){
        if(!isUpdate){
            descriptionInput.setText("");
            dateAddInput.setText("");
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

    void onCheckBtnClick(){
        checkBtn.setOnClickListener(view -> {
            progressDialog.show();

            if(!isUpdate){
                if(isPay){
                    ChiPhi chiPhi = new ChiPhi(LoginActivity.nguoiDung, danhMucChi,
                            Long.parseLong(String.valueOf(moneyInput.getText()).replace(",", "")),
                            String.valueOf(dateAddInput.getText()), String.valueOf(descriptionInput.getText()));

                    chiPhiService.insertOne(chiPhi).thenAccept(value -> {
                        customToast.show("Cập nhập thành công");
                        progressDialog.cancel();
                        resetForm();
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
                        customToast.show("Cập nhập thành công");
                        progressDialog.cancel();
                        resetForm();
                    }).exceptionally(err -> {
                        progressDialog.cancel();
                        return null;
                    });
                }
            }
        });
    }
}