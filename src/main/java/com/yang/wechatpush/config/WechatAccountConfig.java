package com.yang.wechatpush.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatAccountConfig {

    // 公众平台id
    @Value("${wechat.appId}")
    private String appId;

    // 公众平台秘钥
    @Value("${wechat.appSecret}")
    private String appSecret;

    // 模板id
    @Value("${wechat.templateId}")
    private String templateId;

}
