package com.happy.upload.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ：shenjuncai
 * @description：TODO
 * @date ：2021/8/31 10:35
 */
@Data
@Component
@ConfigurationProperties(prefix = "happy.upload")
public class UploadProperties {
    private String BaseUrl;
    private List<String> allowTypes;
}
