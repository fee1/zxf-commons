package com.zxf.startup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 朱晓峰
 */
@SpringBootApplication(scanBasePackages = "com.zxf")
@EnableEurekaClient // 注册客户端
@EnableDiscoveryClient //表示eureka客户端
@EnableFeignClients(basePackages = "com.zxf") //表示打开Feign功能
public class EurekaClientApp {

    public static void main(String[] args) {
        try {
            SpringApplication.run(EurekaClientApp.class, args);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
