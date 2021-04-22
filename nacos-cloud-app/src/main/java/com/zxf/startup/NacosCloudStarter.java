package com.zxf.startup;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 朱晓峰
 */
@SpringBootApplication
@EnableDiscoveryClient//表示为客户端，非一定需要才能注册到nacos
@EnableFeignClients //表示打开Feign功能
public class NacosCloudStarter {

    public static void main(String[] args) {
        try {
            SpringApplication.run(NacosCloudStarter.class, args);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
