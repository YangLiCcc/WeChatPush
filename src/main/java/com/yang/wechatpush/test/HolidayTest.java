package com.yang.wechatpush.test;

import com.alibaba.fastjson2.JSONObject;
import com.yang.wechatpush.util.HttpUtils;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class HolidayTest {

    @Value("${api.holiday.url}")
    private static String url;

    @Value("${api.key}")
    private static String key;

    public static void main(String[] args) {
        holiday("2022-08-17");
    }

    public static void holiday(String date) {
        JSONObject today = null;
        try {
            String response = HttpUtils.getUrl("http://api.tianapi.com/jiejiari/index?key=7bb0514f68fcbfedb34dbf5c4e653465&date=" + date);
            JSONObject temp = JSONObject.parseObject(response);
            System.out.println(temp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
