package com.zxf.startup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 朱晓峰
 */
@SpringBootApplication(scanBasePackages = "com.zxf")
@EnableFeignClients(basePackages = "com.zxf") //表示打开Feign功能
public class BootStarter {

    public static void main(String[] args) {
        try {
            SpringApplication.run(BootStarter.class, args);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
