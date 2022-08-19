package com.yang.wechatpush.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class TimeUtils {

    public static int getCurrentHour() {
        GregorianCalendar calendar = new GregorianCalendar();

        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static boolean isNight() {
        return getCurrentHour() > 18;
    }

}
