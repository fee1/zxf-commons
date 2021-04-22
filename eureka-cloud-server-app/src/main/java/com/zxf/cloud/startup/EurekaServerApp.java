package com.zxf.cloud.startup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author 朱晓峰
 */
@EnableEurekaServer //作为eureka 注册中心服务
@SpringBootApplication
public class EurekaServerApp {

    public static void main(String[] args) {
        try {
            SpringApplication.run(EurekaServerApp.class, args);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
