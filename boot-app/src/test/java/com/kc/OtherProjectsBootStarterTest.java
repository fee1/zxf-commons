package com.kc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 其他项目启动类，需要加入其他项目的包名和自己公司项目的包名
 *
 * @author zhuxiaofeng
 * @date 2022/9/5
 */
@SpringBootApplication(scanBasePackages = {"com.zxf", "com.kc"})
public class OtherProjectsBootStarterTest {

    public static void main(String[] args) {
        try {
            SpringApplication.run(OtherProjectsBootStarterTest.class, args);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
