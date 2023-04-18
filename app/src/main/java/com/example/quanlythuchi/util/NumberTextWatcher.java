package com.example.quanlythuchi.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import java.text.DecimalFormat;

public class NumberTextWatcher implements TextWatcher {

    private final DecimalFormat df;
    private final EditText et;

    public NumberTextWatcher(EditText editText) {
        this.et = editText;
        df = new DecimalFormat("#,###.##");
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        et.removeTextChangedListener(this);

        try {
            String text = s.toString().replace(",", "");
            double value = Double.parseDouble(text);
            String formattedText = df.format(value);
            et.setText(formattedText);
            et.setSelection(formattedText.length());
        } catch (NumberFormatException ex) {
            // do nothing
        }

        et.addTextChangedListener(this);
    }

    // implement other methods of TextWatcher interface
}
