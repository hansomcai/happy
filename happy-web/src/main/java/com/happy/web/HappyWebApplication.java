package com.happy.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author ：shenjuncai
 * @description：TODO
 * @date ：2021/9/2 10:24
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class HappyWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(HappyWebApplication.class);
    }
}
