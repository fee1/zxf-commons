package com.zxf;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 朱晓峰
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class NacosCloudStarter {

    public static void main(String[] args) {
        try {
            SpringApplication.run(NacosCloudStarter.class, args);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
