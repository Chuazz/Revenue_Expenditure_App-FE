package com.example.quanlythuchi.util;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.widget.EditText;
import android.widget.TextView;

public class Editor {
    public static void makeBold(TextView original, String needBold){
        String text = original.getText().toString();
        SpannableString spannableString = new SpannableString(text);
        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        int startIndex = text.indexOf(needBold);
        int endIndex = startIndex + needBold.length();
        spannableString.setSpan(boldSpan, startIndex, endIndex, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        original.setText(spannableString);
    }
}
