package com.example.quanlythuchi.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Objects;

public class CustomTextWatcher implements TextWatcher {
    private String current = "";
    private DecimalFormat decimalFormat = new DecimalFormat("#,###");
    EditText editText;

    public CustomTextWatcher(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!s.toString().equals(current)) {
            editText.removeTextChangedListener(this);

            String replaceable = String.format("[%s,.\\s]", Objects.requireNonNull(NumberFormat.getCurrencyInstance().getCurrency()).getSymbol());
            String cleanString = s.toString().replaceAll(replaceable, "");

            double parsed;
            try {
                parsed = Double.parseDouble(cleanString);
            } catch (NumberFormatException e) {
                parsed = 0.00;
            }

            String formatted = decimalFormat.format(parsed);
            current = formatted;
            editText.setText(formatted);
            editText.setSelection(formatted.length());
            editText.addTextChangedListener(this);
        }
    }
}
