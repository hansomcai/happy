package com.happy.user.service.impl;

import com.happy.common.util.NumberUtils;
import com.happy.user.mapper.UserMapper;
import com.happy.user.pojo.User;
import com.happy.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author ：shenjuncai
 * @description：TODO
 * @date ：2021/9/7 10:08
 */
@Service
public class UserServiceImpl implements UserService {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String KEY_PREFIX = "user:code:phone:";
    public Boolean checkUserData(String data, Integer type) {
        User user = new User();
        switch (type){
            case 1:
                user.setUsername(data);
                break;
            case 2:
                user.setPhone(data);
                break;
            default:
                return null;
        }
        return userMapper.selectCount(user) == 0;
    }

    public Boolean sendVerifyCode(String phone) {
        //生成验证码
        String code = NumberUtils.generateCode(6);
        Map<String, String> msg = new HashMap(2);
        msg.put("phone", phone);
        msg.put("code", code);
        try {
            redisTemplate.opsForValue().set(KEY_PREFIX + phone,code,5, TimeUnit.MINUTES);
            amqpTemplate.convertAndSend("HAPPY.SMS.EXCHANGE", "sms.verify.code", msg);
        }catch (AmqpException e){
            LOGGER.error("给{}发送验证码失败", phone, e);
            return null;
        }

        return true;
    }
}
