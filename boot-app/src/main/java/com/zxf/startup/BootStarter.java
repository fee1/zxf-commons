package com.zxf.startup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author 朱晓峰
 */
@SpringBootApplication
@EnableSwagger2
public class BootStarter {

    public static void main(String[] args) {
        try {
            SpringApplication.run(BootStarter.class, args);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
