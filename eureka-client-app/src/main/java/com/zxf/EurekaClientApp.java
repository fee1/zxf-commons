package com.zxf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author 朱晓峰
 */
@SpringBootApplication
@EnableEurekaClient // 注册客户端
@EnableDiscoveryClient //发现客户端
public class EurekaClientApp {

    public static void main(String[] args) {
        try {
            SpringApplication.run(EurekaClientApp.class, args);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
