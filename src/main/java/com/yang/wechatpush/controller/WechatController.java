package com.yang.wechatpush.controller;

import com.yang.wechatpush.constants.WechatConstants;
import com.yang.wechatpush.service.WechatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试号推送控制层
 * @author YangLiCcc
 * @since JDK 1.8 Spring Boot 2.6.10 2022-08-30
 */
@RestController
@Slf4j
public class WechatController {

    @Autowired
    private WechatService wechatService;

    @GetMapping("/push/test")
    public void pushTest() {
        wechatService.pushArticle(WechatConstants.TARGET_ID_MINE);
    }

    @GetMapping("/push")
    public void push() {
        wechatService.pushArticle(WechatConstants.TARGET_ID_WW);
        wechatService.pushArticle(WechatConstants.TARGET_ID_MINE);
    }

}
