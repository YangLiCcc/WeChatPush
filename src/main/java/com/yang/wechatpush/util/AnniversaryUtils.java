package com.yang.wechatpush.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AnniversaryUtils {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static int before(String date) {

        int day = 0;
        try {
            long time = dateFormat.parse(date).getTime() - System.currentTimeMillis();
            day = (int) (time / 86400000L);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return day;
    }

    public static int after(String date) {

        int day = 0;
        try {
            long time = System.currentTimeMillis() - dateFormat.parse(date).getTime();
            day = (int) (time / 86400000L);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return day;
    }

    public static int getRelationship(String date) {
        return after(date);
    }

    public static int getBirthday(String date) {
        return before(date);
    }

}
