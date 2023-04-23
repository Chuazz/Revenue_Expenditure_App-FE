package com.example.quanlythuchi.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class shareService {

    public static Date convertStringToDate(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
        try {
            Date date = format.parse(dateString);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return  null;
        }
    }

    public static String convertDateToString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
        String strDate = formatter.format(date);
        return  strDate;
    }
}
