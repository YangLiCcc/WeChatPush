package com.yang.wechatpush.service;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.yang.wechatpush.util.HttpUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HolidayService {

    @Value("${api.holiday.url}")
    private String url;

    @Value("${api.key}")
    private String key;

    public JSONObject holiday(String date) {

        JSONObject today = null;
        try {
            String response = HttpUtils.getUrl(url + key + "&date=" + date);
            JSONObject temp = JSONObject.parseObject(response);
            int code = temp.getIntValue("code");
            if (code == 200) {
                System.out.println("-==========-> 调用节假日接口成功 <-==========-");
                JSONArray info = temp.getJSONArray("newslist");
                today = info.getJSONObject(0);
            } else {
                System.out.println("-==========-> 调用节假接口失败 <-==========-");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return today;
    }

}
