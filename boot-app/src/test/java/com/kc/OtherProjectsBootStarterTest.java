package com.kc;

import com.zxf.startup.BootStarter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * 其他项目启动类，需要加入其他项目的包名和自己公司项目的包名
 *
 * @author zhuxiaofeng
 * @date 2022/9/5
 */
@SpringBootApplication
@Import(BootStarter.class)
//@SpringBootApplication(scanBasePackages = {"com.zxf", "com.kc"}) //这种也可以
public class OtherProjectsBootStarterTest {

    public static void main(String[] args) {
        try {
            SpringApplication.run(OtherProjectsBootStarterTest.class, args);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
