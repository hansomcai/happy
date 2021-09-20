package com.happy.sms.util;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.happy.sms.config.SmsProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author ：shenjuncai
 * @description：TODO
 * @date ：2021/9/6 13:43
 */
@Component
@EnableConfigurationProperties(SmsProperties.class)
public class SmsUtils {
    @Autowired
    private SmsProperties smsProperties;

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    /**
     * 发送短信
     */
    public CommonResponse sendSms(String telephone, String param, String signName, String templateId) throws ClientException {
        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou",
                prop.getAccessKeyId(), prop.getAccessKeySecret());
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.setMethod(MethodType.POST);
        // 接收短信的手机号码
        request.putQueryParameter("PhoneNumbers", telephone);
        // 短信签名名称。请在控制台签名管理页面签名名称一列查看（必须是已添加、并通过审核的短信签名）。
        request.putQueryParameter("SignName", signName);
        // 短信模板ID
        request.putQueryParameter("TemplateCode", templateId);
        // 短信模板变量对应的实际值，JSON格式。
        request.putQueryParameter("TemplateParam", param);
        CommonResponse commonResponse = client.getCommonResponse(request);
        LOGGER.info("发送短信结果：{}", commonResponse.getData());

        return commonResponse;
    }

}
