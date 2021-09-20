package com.happy.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringCloudApplication
public class HappyGateway {
    public static void main(String[] args) {
        SpringApplication.run(HappyGateway.class,args);
    }
}
