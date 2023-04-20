package com.example.quanlythuchi.fragment.addMore;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.quanlythuchi.R;
import com.example.quanlythuchi.activity.ChoosePayTypeActivity;
import com.google.android.material.datepicker.MaterialDatePicker;

public class AddMoreBodyFragment extends Fragment {
    View view;
    EditText moneyInput;
    EditText dateAddInput;
    Button chooseTypeBtn;

    public AddMoreBodyFragment() {
    }

    public static AddMoreBodyFragment newInstance() {
        AddMoreBodyFragment fragment = new AddMoreBodyFragment();
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
        view = inflater.inflate(R.layout.fragment_add_more_body, container, false);
        moneyInput = view.findViewById(R.id.addMore_addMoneyText);
        dateAddInput = view.findViewById(R.id.addMore_dateAddInput);
        chooseTypeBtn = view.findViewById(R.id.addMore_chooseTypeBtn);

        onDateAddClick();
        onChooseTypeClick();

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
                Intent intent = new Intent(getContext(), ChoosePayTypeActivity.class);
                startActivity(intent);
            }
        });
    }
}