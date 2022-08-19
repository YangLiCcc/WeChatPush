package com.yang.wechatpush.util;

import com.alibaba.fastjson2.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PhraseUtils {

    private static List<String> quoteList = new ArrayList<>();

    public static String getRainbowFrats(String key) {
        // 默认
        String defaultFrat = "阳光落在屋里，爱你藏在心里";
        try {
            JSONObject jsonObject = JSONObject.parseObject(HttpUtils.getUrl("http://api.tianapi.com/caihongpi/index?key=" + key));
            if (jsonObject.getIntValue("code") == 200) {
                defaultFrat = jsonObject.getJSONArray("newslist").getJSONObject(0).getString("content");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return defaultFrat;
    }

    public static String getNightPhrase(String key) {
        String defaultPhrase = "用最孤独的时光塑造出最好的自己，才能笑着对别人说那些云淡风轻的过去。晚安！";

        try {
            JSONObject jsonObject = JSONObject.parseObject(HttpUtils.getUrl("http://api.tianapi.com/wanan/index?key=" + key));
            if (jsonObject.getIntValue("code") == 200) {
                defaultPhrase = jsonObject.getJSONArray("neslist").getJSONObject(0).getString("content");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return defaultPhrase;
    }

    /**
     * 载入句子库
     */
    static {
        InputStream inputStream = PhraseUtils.class.getClassLoader().getResourceAsStream("static/quotes.txt");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String str = "";
            String temp = "";
            while ((temp = br.readLine()) != null) {
                if (!StringUtils.isEmpty(temp)) {
                    str = str + "\r\n" + temp;
                } else {
                    quoteList.add(str);
                    str = "";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getQuote() {
        Random random = new Random();
        return quoteList.get(random.nextInt(quoteList.size()));
    }

    public static void main(String[] args) {
        System.out.println(getQuote());
    }

}
