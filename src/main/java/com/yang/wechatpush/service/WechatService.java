package com.yang.wechatpush.service;

import com.alibaba.fastjson2.JSONObject;
import com.yang.wechatpush.config.WechatMpConfig;
import com.yang.wechatpush.constants.ApiConstants;
import com.yang.wechatpush.constants.WechatConstants;
import com.yang.wechatpush.util.AnniversaryUtils;
import com.yang.wechatpush.util.PhraseUtils;
import com.yang.wechatpush.util.TimeUtils;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 测试号推送服务层
 * 需要测试号的 templateId
 * 以及推送对象的 openId
 * @author YangLiCcc
 * @since JDK 1.8 Spring Boot 2.6.10 2022-08-30
 */
@Service
public class WechatService {

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private HolidayService holidayService;

    @Autowired
    private WechatMpConfig mpConfig;

    /**
     * 文章推送
     * 根据时间 进行早上和晚上不同内容的推送
     * 早上和晚上使用两个不同的模板
     * 注入 WeatherService 和 HolidayService 以获取今日、明日天气 即对应的节假日or工作日
     * @param openId 推送对象
     */
    public void pushArticle(String openId) {

        WxMpService wxMpService = mpConfig.wxMpService();

        String templateId = WechatConstants.TEMPLATE_ID_MORNING;
        if (TimeUtils.isNight()) {
            templateId = WechatConstants.TEMPLATE_ID_NIGHT;
        }

        // 推送信息
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .toUser(openId)
                .templateId(templateId)
                .build();
        JSONObject weather = weatherService.weather();
        JSONObject holiday = holidayService.holiday(weather.getString("fxDate"));

        if (!TimeUtils.isNight()) {
            // 早上推送的内容
            // 情话
            templateMessage.addData(new WxMpTemplateData("lovePhrase", PhraseUtils.getRainbowFrats(ApiConstants.COMMON_API_KEY), "#FF69B4"));
            // 日期
            templateMessage.addData(new WxMpTemplateData("date", holiday.getString("date") + " " + holiday.getString("cnweekday") + " 农历" + holiday.getString("lunarmonth") + holiday.getString("lunarday"), "#00BFFF"));
            // 纪念日
            templateMessage.addData(new WxMpTemplateData("loveDay", AnniversaryUtils.getRelationship(WechatConstants.LOVE_DATE) + "", "#FF1493"));

            //节假日
            if (holiday.getString("isnotwork").equals("1")) {
                // 休息日
                if (holiday.getString("info").equals("节假日") || holiday.getString("info").equals("节日")) {
                    templateMessage.addData(new WxMpTemplateData("holiday", holiday.getString("name") + " 快乐! 好好享受假期吧~", "#aaff00"));
                } else {
                    templateMessage.addData(new WxMpTemplateData("holiday", "一周的工作辛苦了, " + holiday.getString("info") + " 就好好休息吧~", "#aaff00"));
                }
            } else {
                if (holiday.getString("info").equals("节日")) {
                    templateMessage.addData(new WxMpTemplateData("holiday", holiday.getString("holiday") + " 快乐! 但也要加油搬砖哟~", "#aaff00"));
                } else {
                    templateMessage.addData(new WxMpTemplateData("holiday", "又是搬砖的一天orz 多多摸鱼吧~", "#00ccff"));
                }
            }

            // 生日
            templateMessage.addData(new WxMpTemplateData("fBirthday", AnniversaryUtils.getBirthday(WechatConstants.BIRTHDAY_WW) + "", "#FFA500"));
            templateMessage.addData(new WxMpTemplateData("mBirthday", AnniversaryUtils.getBirthday(WechatConstants.BIRTHDAY_MINE) + "", "#FFA500"));

            // 金句
            templateMessage.addData(new WxMpTemplateData("quote", PhraseUtils.getQuote() + "", "#C71585"));
        } else {
            // 晚上推送的内容
            // 晚安句子
            templateMessage.addData(new WxMpTemplateData("night", PhraseUtils.getNightPhrase(ApiConstants.COMMON_API_KEY), "#FF69B4"));

            //节假日
            if (holiday.getString("isnotwork").equals("1")) {
                // 休息日
                if (holiday.getString("info").equals("节假日") || holiday.getString("info").equals("节日")) {
                    templateMessage.addData(new WxMpTemplateData("holiday", "明天就是 " + holiday.getString("name") + " 咯~再拼一天，就能快乐玩耍了！", "#aaff00"));
                } else {
                    templateMessage.addData(new WxMpTemplateData("holiday", "明天就是 " + holiday.getString("info") + " 咯~再拼一天，就能快乐玩耍了！", "#aaff00"));
                }
            } else {
                if (holiday.getString("info").equals("节日")) {
                    templateMessage.addData(new WxMpTemplateData("holiday", "虽然明天是 " + holiday.getString("holiday") + " 但还得搬砖 orz", "#aaff00"));
                } else {
                    templateMessage.addData(new WxMpTemplateData("holiday", "明天还要搬砖呢，早点休息！干巴得！^v-", "#00ccff"));
                }
            }
        }

        // 天气
        templateMessage.addData(new WxMpTemplateData("weather", weather.getString("textDay"), "#00FFFF"));
        // 最低温度
        templateMessage.addData(new WxMpTemplateData("low", weather.getString("tempMin") + "", "#173177"));
        // 最高温度
        templateMessage.addData(new WxMpTemplateData("high", weather.getString("tempMax") + "", "#FF6347"));
        // 紫外线强度
        templateMessage.addData(new WxMpTemplateData("uv", weather.getString("uvIndex") + "", "#FF6347"));
        // 相对湿度
        templateMessage.addData(new WxMpTemplateData("humidity", weather.getString("humidity") + "", "#FF6347"));

        try {
            System.out.println(templateMessage.toJson());
            System.out.println(wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage));
        } catch (Exception e) {
            System.out.println("********* 推送失败 *********");
            e.printStackTrace();
        }

    }

}
