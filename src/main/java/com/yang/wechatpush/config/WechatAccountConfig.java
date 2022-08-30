package com.yang.wechatpush.config;

import com.yang.wechatpush.constants.WechatConstants;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author YangLiCcc
 */
@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatAccountConfig {

    // 公众平台id
    private String appId = WechatConstants.APP_ID;

    // 公众平台秘钥
    private String appSecret = WechatConstants.APP_SECRET;

}
