package com.example.quanlythuchi.util;

import java.text.DecimalFormat;

public class Commas {
    private static DecimalFormat decimalFormat = new DecimalFormat("#,###");

    public static String add(long value){
        return decimalFormat.format(value);
    }

    public static String remove(String value){
        return String.valueOf(value).replace(".", "");
    }
}
