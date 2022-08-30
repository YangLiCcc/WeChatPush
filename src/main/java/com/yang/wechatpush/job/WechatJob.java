package com.yang.wechatpush.job;

import com.yang.wechatpush.constants.WechatConstants;
import com.yang.wechatpush.service.WechatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务
 * @author YangLiCcc
 */
@Component
public class WechatJob {

    @Autowired
    WechatService wechatService;

    /**
     * @param @Scheduled 开启定时
     * @param cron 秒 分 时 日 月 周 年 七个参数 前六个必填 年可省略
     *             0 0 7,22 * * ?
     *             表示每个月每一天的7点和22点各执行一次
     */
    @Scheduled(cron = "0 0 7,22 * * ?")
    public void push() {
        wechatService.pushArticle(WechatConstants.TARGET_ID_WW);
        wechatService.pushArticle(WechatConstants.TARGET_ID_MINE);
    }

}
