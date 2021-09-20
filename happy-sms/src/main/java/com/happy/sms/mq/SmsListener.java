package com.happy.sms.mq;

/**
 * @author ：shenjuncai
 * @description：TODO
 * @date ：2021/9/6 14:02
 */
@Component
@EnableConfigurationProperties(SmsProperties.class)
public class SmsListener {

    @Autowired
    private SmsUtils smsUtils;

    @Autowired
    private SmsProperties prop;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "happy.sms.queue", durable = "true"),
            exchange = @Exchange(value = "happy.sms.exchange",
                    ignoreDeclarationExceptions = "true"),
            key = {"sms.verify.code"}))
    public void listenSms(Map<String, String> msg) throws Exception {
        if (msg == null || msg.size() <= 0) {
            // 放弃处理
            return;
        }
        String phone = msg.get("phone");
        String code = msg.get("code");

        if (StringUtils.isBlank(phone) || StringUtils.isBlank(code)) {
            // 放弃处理
            return;
        }
        // 发送消息
        SendSmsResponse resp = this.smsUtils.sendSms(phone, code,
                prop.getSignName(),
                prop.getVerifyCodeTemplate());
        // 发送失败
        throw new RuntimeException();
    }
}
