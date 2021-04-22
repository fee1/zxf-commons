package com.zxf.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.zxf")
@EnableFeignClients(basePackages = "com.zxf")
public class BootStarterTest {

    public static void main(String[] args) {
        try {
            SpringApplication.run(BootStarterTest.class, args);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
