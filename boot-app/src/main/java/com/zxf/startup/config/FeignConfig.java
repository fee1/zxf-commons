//package com.zxf.startup.config;
//
//import feign.Feign;
//import feign.Logger;
//import org.springframework.cloud.openfeign.FeignLoggerFactory;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @author zhuxiaofeng
// * @date 2022/1/4
// */
//@Configuration
//public class FeignConfig {
//
//    public void feignConfig(){
//        Feign.builder()
//                .client()//http客户端接口, apache httpclient 或者使用 okhttp
//                .errorDecoder()//错误编码解释器
//                .retryer()//重试机制
//                .logger()//日志
//                .logLevel()//日志级别
//                .requestInterceptors()//请求拦截器
//                .contract()//处理feign接口注解，Spring Cloud Feign 使用SpringMvcContract 实现，处理Spring mvc 注解 ，所以可以使用springmvc的注解
//                .encoder()//编码器
//                .decoder();//解码器
//    }
//
//}
