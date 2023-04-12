package com.example.quanlythuchi.util;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlythuchi.R;

public class CustomToast {
    private Toast toast;
    private TextView text;

    public CustomToast(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.custom_toast, null);
        text = layout.findViewById(R.id.toast_text);
        toast = new Toast(context);

        toast.setGravity(Gravity.TOP, 0, 100);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
    }

    public void show(String message) {
        text.setText(message);
        toast.show();
    }
}
