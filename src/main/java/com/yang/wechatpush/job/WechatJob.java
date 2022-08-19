package com.yang.wechatpush.job;

import com.yang.wechatpush.controller.WechatController;
import com.yang.wechatpush.service.WechatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WechatJob {

    @Value("${push.target.test.openId}")
    private String testOpenId;

    @Value("${push.target.openId}")
    public String openId;

    @Autowired
    WechatService wechatService;

    public void pushTest() {
        wechatService.pushArticle(testOpenId);
    }

    @Scheduled(cron = "0 15 9 * * ?")
    public void push() {
        wechatService.pushArticle(openId);
        wechatService.pushArticle(testOpenId);
    }

}
