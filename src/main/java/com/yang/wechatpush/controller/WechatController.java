package com.yang.wechatpush.controller;

import com.yang.wechatpush.service.WechatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class WechatController {

    @Value("${push.target.test.openId}")
    private String target_test;

    @Value("${push.target.openId}")
    private String targetId;

    @Autowired
    private WechatService wechatService;

    @GetMapping("/push/test")
    public void pushTest() {
        wechatService.pushArticle(target_test);
    }

    @GetMapping("/push")
    public void push() {
        wechatService.pushArticle(targetId);
        wechatService.pushArticle(target_test);
    }

}
