package com.zxf.startup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author 朱晓峰
 */
@SpringBootApplication
@EnableSwagger2 ////表示打开swagger功能
@EnableFeignClients //表示打开Feign功能
public class BootStarter {

    public static void main(String[] args) {
        try {
            SpringApplication.run(BootStarter.class, args);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
