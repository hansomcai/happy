package com.happy.sms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author ：shenjuncai
 * @description：TODO
 * @date ：2021/9/6 11:52
 */
@Data
@Component
@ConfigurationProperties(prefix = "happy.sms")
public class SmsProperties {
    String accessKeyId;

    String accessKeySecret;

    String signName;

    String verifyCodeTemplate;
}
