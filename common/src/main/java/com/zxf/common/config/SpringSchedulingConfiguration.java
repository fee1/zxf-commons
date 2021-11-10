package com.zxf.common.config;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * spring定时任务线程池配置
 *
 * @author zhuxiaofeng
 * @date 2021/10/24
 */
@ConditionalOnProperty(value = "spring.scheduling.enable", matchIfMissing = true)
@Configuration
@EnableScheduling
@ConfigurationProperties(prefix = "spring.scheduling")
@Slf4j
public class SpringSchedulingConfiguration implements SchedulingConfigurer {

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

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskScheduler());
    }

    public ScheduledExecutorService taskScheduler(){
        ThreadFactory threadFactory = ThreadFactoryBuilder.create().setNamePrefix("scheduling-thread-id-").build();


        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor =
                new ScheduledThreadPoolExecutor(this.corePoolSize, threadFactory, new ThreadPoolExecutor.AbortPolicy());
        scheduledThreadPoolExecutor.setMaximumPoolSize(this.maxPoolSize);
        scheduledThreadPoolExecutor.setKeepAliveTime(this.keepAliveSeconds, TimeUnit.SECONDS);
        return scheduledThreadPoolExecutor;
    }

}
