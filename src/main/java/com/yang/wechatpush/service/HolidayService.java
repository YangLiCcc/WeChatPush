package com.yang.wechatpush.service;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.yang.wechatpush.constants.ApiConstants;
import com.yang.wechatpush.util.HttpUtils;
import org.springframework.stereotype.Service;

/**
 * 节假日服务层
 * 调用 Holiday API
 * 参数 String date 直接从天气中的 fxDate 获取
 */
@Service
public class HolidayService {

    public JSONObject holiday(String date) {

        JSONObject today = null;
        try {
            String response = HttpUtils.getUrl(ApiConstants.HOLIDAY_BASE_URL + ApiConstants.COMMON_API_KEY + "&date=" + date);
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
