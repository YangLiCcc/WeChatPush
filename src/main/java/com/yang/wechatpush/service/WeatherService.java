package com.yang.wechatpush.service;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.yang.wechatpush.constants.ApiConstants;
import com.yang.wechatpush.util.HttpUtils;
import com.yang.wechatpush.util.TimeUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

    public JSONObject weather() {

        JSONObject today = null;
        JSONObject nextDay = null;
        try {
            String response = HttpUtils.getUrl(ApiConstants.WEATHER_BASE_URL + "location=" + ApiConstants.WEATHER_LOCATION + "&key=" + ApiConstants.WEATHER_KEY);
            JSONObject jsonObject = JSONObject.parseObject(response);
            int code = jsonObject.getIntValue("code");
            if (code == 200) {
                System.out.println("-==========-> 调用天气接口成功 <-==========-");
                JSONArray daily = jsonObject.getJSONArray("daily");
                today = daily.getJSONObject(0);
                nextDay = daily.getJSONObject(1);
            } else {
                System.out.println("-==========-> 调用天气接口失败 <-==========-");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * 判断当前时间是否为 18点以后
         * true 则返回第二天的天气
         * false 则返回当天的天气
         */
        if (TimeUtils.isNight()) {
            return nextDay;
        } else {
            return today;
        }
    }

}
