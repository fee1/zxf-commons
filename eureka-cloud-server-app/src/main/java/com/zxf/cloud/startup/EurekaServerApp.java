package com.zxf.cloud.startup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 朱晓峰
 */
@EnableEurekaServer //作为eureka 注册中心服务
@SpringBootApplication
@EnableFeignClients //表示打开Feign功能
public class EurekaServerApp {

    public static void main(String[] args) {
        try {
            SpringApplication.run(EurekaServerApp.class, args);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
