package com.happy.upload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author ：shenjuncai
 * @description：TODO
 * @date ：2021/8/25 14:54
 */
@EnableDiscoveryClient
@SpringBootApplication
public class UploadApplication {
    public static void main(String[] args) {
        SpringApplication.run(UploadApplication.class,args);
    }
}
