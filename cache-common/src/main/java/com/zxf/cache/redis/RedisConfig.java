package com.zxf.cache.redis;

import com.zxf.cache.redis.serializer.AutoTypeValueSerializer;
import com.zxf.common.utils.Base64Util;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.Assert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * redis配置类工具类
 *
 * @author 朱晓峰
 */
//@Configuration
//@ConditionalOnProperty(value = "cache.type", havingValue = "redis")
//@ConfigurationProperties(prefix = "cache.redis")
//@Data
@Slf4j
public class RedisConfig {

    /**
     * 获取redisNode结点
     *
     * @return List
     */
    public static List<RedisNode> getRedisNodes(String[] hosts) {
        List<RedisNode> redisNodes = new ArrayList<>();
        for (String host : hosts) {
            String[] items = host.split(":");
            Assert.isTrue(StringUtils.isNotBlank(items[0]) && StringUtils.isNotBlank(items[1]),
                    "host与port未配置正确，格式: host:port");
            redisNodes.add(new RedisNode(items[0], Integer.parseInt(items[1])));
        }
        return redisNodes;
    }

    /**
     * 设置项目key前缀 缓存类key前缀
     *
     * @param prefixKey 项目key前缀
     * @param key       缓存类key前缀
     * @return String
     */
    public static String formatFullKey(String prefixKey, String key) {
        return String.format("%s:%s:", prefixKey, key);
    }

//    /**
//     * 获取redis缓存产品工厂
//     *
//     * @return RedisCacheFactory
//     */
//    @Bean
//    RedisCacheFactory getRedisCacheFactory() {
//        return new RedisCacheFactory(this, this.prefixKey);
//    }

}
