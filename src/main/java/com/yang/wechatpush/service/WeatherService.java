package com.yang.wechatpush.service;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.yang.wechatpush.util.HttpUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

    @Value("${weather.AK}")
    private String accessKey;

    @Value("${weather.districtId}")
    private String districtId;

    @Value("${weather.location}")
    private String location;

    @Value("${weather.key}")
    private String API_KEY;

    @Value("${weather.baseUrl}")
    private String API_URL;

    public JSONObject weather() {

        JSONObject today = null;
        try {
            String response = HttpUtils.getUrl(API_URL + "location=" + location + "&key=" + API_KEY);
            JSONObject jsonObject = JSONObject.parseObject(response);
            int code = jsonObject.getIntValue("code");
            if (code == 200) {
                System.out.println("-==========-> 调用天气接口成功 <-==========-");
                JSONArray daily = jsonObject.getJSONArray("daily");
                today = daily.getJSONObject(0);
            } else {
                System.out.println("-==========-> 调用天气接口失败 <-==========-");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return today;
    }

    public JSONObject getWeather() {
        String res = null;
        JSONObject today = new JSONObject();

        try {
            res = HttpUtils.getUrl("https://api.map.baidu.com/weather/v1/?district_id=" + districtId + "&data_type=all&ak=" + accessKey);
            JSONObject temp = JSONObject.parseObject(res);
            if (temp.getString("message").equals("success")) {
                JSONArray array = temp.getJSONObject("result").getJSONArray("forecasts");
                today = array.getJSONObject(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return today;
    }

}
