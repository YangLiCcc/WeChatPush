package com.yang.wechatpush.util;

import com.alibaba.fastjson2.JSONObject;
import com.yang.wechatpush.constants.ApiConstants;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 句子生成工具
 * @author YangLiCcc
 * @since jdk8 2022-08-30
 * @version 1.0.1
 */
public class PhraseUtils {

    private static List<String> quoteList = new ArrayList<>();

    /**
     * 彩虹屁获取 API
     * @param key API-key
     * @return default or Phrase got from API
     */
    public static String getRainbowFrats(String key) {
        // 默认
        String defaultFrat = "阳光落在屋里，爱你藏在心里";
        try {
            JSONObject jsonObject = JSONObject.parseObject(HttpUtils.getUrl(ApiConstants.RAINBOW_BASE_URL + key));
            if (jsonObject.getIntValue("code") == 200) {
                defaultFrat = jsonObject.getJSONArray("newslist").getJSONObject(0).getString("content");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return defaultFrat;
    }

    /**
     * 晚安句获取 API
     * @param key API-key
     * @return default or Phrase got from API
     */
    public static String getNightPhrase(String key) {
        String defaultPhrase = "用最孤独的时光塑造出最好的自己，才能笑着对别人说那些云淡风轻的过去。晚安！";

        try {
            JSONObject jsonObject = JSONObject.parseObject(HttpUtils.getUrl(ApiConstants.NIGHT_BASE_URL + key));
            if (jsonObject.getIntValue("code") == 200) {
                defaultPhrase = jsonObject.getJSONArray("newslist").getJSONObject(0).getString("content");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return defaultPhrase;
    }

    /**
     * 载入句子库
     * 与 getQuote 搭配
     * 先 载入 -> 再 随机读取
     * 从 static/quotes.txt 文件内随机读取句子
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

}
