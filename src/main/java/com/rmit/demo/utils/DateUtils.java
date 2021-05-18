package com.rmit.demo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtils {

    private DateUtils() {};

    public static Date parseDate(String dateStr) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = formatter.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date parseDatetime(String datetimeStr) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        Date date = null;
        try {
            date = formatter.parse(datetimeStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String dateToString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String stringDate = null;
        try {
            stringDate = formatter.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringDate;
    }
}
