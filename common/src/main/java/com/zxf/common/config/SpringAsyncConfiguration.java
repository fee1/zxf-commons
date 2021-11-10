package com.zxf.common.config;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * spring线程异步配置
 *
 * @author zhuxiaofeng
 * @date 2021/9/29
 */
@ConditionalOnProperty(value = "spring.async.enable", matchIfMissing = true)
@Configuration
@EnableAsync
@ConfigurationProperties(prefix = "spring.async")
@Slf4j
public class SpringAsyncConfiguration implements AsyncConfigurer {

    /**
     * 核心线程数
     */
    @Value("${core-pool-size:2}")
    private int corePoolSize;

    /**
     * 最大线程数
     */
    @Value("${max-pool-size:5}")
    private int maxPoolSize;

    /**
     * 非核心线程，活跃线程存活时间
     */
    @Value("${keep-alive-seconds:60}")
    private int keepAliveSeconds;

    /**
     * 任务队列长度容量
     */
    @Value("${queue-capacity:10}")
    private int queueCapacity;

    //@Bean(name = "") ---> @Async(value = "") 执行线程池配置
    //@ConditionalOnMissingBean
//    @Bean("defaultAsyncThreadPool")
    private ThreadPoolExecutor executor(){
        ThreadFactory threadFactory = ThreadFactoryBuilder.create().setNamePrefix("async-thread-id-").build();

        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(this.corePoolSize);
        taskExecutor.setMaxPoolSize(this.maxPoolSize);
        taskExecutor.setKeepAliveSeconds(this.keepAliveSeconds);
        taskExecutor.setQueueCapacity(this.queueCapacity);
        //拒绝策略
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        taskExecutor.setThreadFactory(threadFactory);
        return taskExecutor.getThreadPoolExecutor();
    }

    /**
     * 获取异步任务线程池
     * @return Executor
     */
    @Override
    public Executor getAsyncExecutor() {
        return executor();
    }

    /**
     * 异步线程报错处理
     * @return AsyncUncaughtExceptionHandler
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (ex, method, params) -> {
            ex.printStackTrace();
            log.error("异步线程出错, 方法: {}, 参数: {}, 原因: {}", method, params, ex.getMessage());
        };
    }
}
