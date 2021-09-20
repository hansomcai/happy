package com.happy.upload.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author ：shenjuncai
 * @description：cors解决跨域
 * @date ：2021/8/26 16:56
 */
@Configuration
public class GlobalCorsConfig {
    public CorsFilter corsFilter(){
        //1.添加CORS配置信息
        CorsConfiguration configuration = new CorsConfiguration();
        //1) 允许的域,不要写*，否则cookie就无法使用了
        configuration.addAllowedOrigin("http://manage.leyou.com");
        //2) 是否发送Cookie信息
        configuration.setAllowCredentials(false);
        //3) 允许的请求方式
        configuration.addAllowedMethod("OPTIONS");
        configuration.addAllowedMethod("POST");
        configuration.addAllowedHeader("*");

        //2.添加映射路径，我们拦截一切请求
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", configuration);

        //3.返回新的CorsFilter.
        return new CorsFilter(configSource);

    }
}
