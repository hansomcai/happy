package com.happy.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author ：shenjuncai
 * @description：TODO
 * @date ：2021/9/6 15:58
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.happy.user.mapper")
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

}
