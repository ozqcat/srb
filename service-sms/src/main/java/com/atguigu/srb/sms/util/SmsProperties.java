package com.atguigu.srb.sms.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author ozq
 * @Date 2021/12/3 12:47
 * @Description
 */

@Getter
@Setter
@ConfigurationProperties(prefix = "yuntongxun.sms")
@Component
public class SmsProperties implements InitializingBean {

    private String accountSid;
    private String accountToken;
    private String AppId;
    private String templateId;


    public  static String ACCOUNTS_ID;
    public static String ACCOUNT_TOKEN;
    public static String APP_ID;
    public static String TEMPLATE_ID;

    //当私有成员被赋值后，此方法自动被调用，从而初始化常量
    @Override
    public void afterPropertiesSet() throws Exception {
        ACCOUNTS_ID = accountSid;
        ACCOUNT_TOKEN = accountToken;
        APP_ID = AppId;
        TEMPLATE_ID = templateId;
    }
}
