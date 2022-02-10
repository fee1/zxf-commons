package com.zxf.cache.config;

import com.zxf.cache.CacheFactory;
import com.zxf.cache.CacheService;
import com.zxf.cache.CacheServiceImpl;
import com.zxf.cache.guava.GuavaCacheFactory;
import com.zxf.cache.redis.RedisCacheFactory;
import com.zxf.cache.redis.RedisConfig;
import com.zxf.cache.redis.RedisProperties;
import com.zxf.cache.redis.serializer.AutoTypeValueSerializer;
import com.zxf.common.utils.Base64Util;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
import java.util.List;
import java.util.Objects;

/**
 * 缓存配置类
 *
 * @author zhuxiaofeng
 * @date 2022/2/9
 */
@Configuration(proxyBeanMethods = false)
public class CacheAutoConfiguration {

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnProperty(value = "cache.type", havingValue = "guava")
    public static class GuavaCacheConfiguration{
        @Bean
        public GuavaCacheFactory guavaCacheFactory(){
            return new GuavaCacheFactory();
        }
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnProperty(value = "cache.type", havingValue = "redis")
    public static class RedisCacheConfiguration{

        @Bean
        public RedisProperties redisProperties(){
            return new RedisProperties();
        }

        /**
         * LettuceConnectionFactory
         *
         * @return LettuceConnectionFactory
         */
        @Bean
        public LettuceConnectionFactory lettuceConnectionFactory(RedisProperties properties) {
            List<RedisNode> nodes = RedisConfig.getRedisNodes(properties.getHosts());
            Assert.isTrue(!nodes.isEmpty(), "未配置redis的hosts");
            // todo 可以使用sm2 加解密和base64双重加解密方式
            String password = Base64Util.decrypt(properties.getPassword());
            //连接池配置
            GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
            poolConfig.setMaxIdle(properties.getMaxIdle());
            poolConfig.setMinIdle(properties.getMinIdle());
            poolConfig.setMaxTotal(properties.getMaxTotal());
            poolConfig.setMaxWaitMillis(properties.getMaxWait());

            ClientOptions clientOptions = ClientOptions.builder()
                    .autoReconnect(true).socketOptions(
                            SocketOptions.builder()
                                    .connectTimeout(Duration.ofMillis(properties.getConnectTimeout()))
                                    .keepAlive(true)
                                    .tcpNoDelay(true)
                                    .build()).build();

            LettucePoolingClientConfiguration lettucePoolingClientConfiguration =
                    LettucePoolingClientConfiguration.builder()
                            .poolConfig(poolConfig)
                            .clientOptions(clientOptions)
                            .commandTimeout(Duration.ofMillis(properties.getConnectTimeout()))
                            .build();

            LettuceConnectionFactory connectionFactory;

            //单机
            if (nodes.size() == 1) {
                RedisNode node = nodes.get(0);
                RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
                redisStandaloneConfiguration.setHostName(Objects.requireNonNull(node.getHost()));
                redisStandaloneConfiguration.setPort(node.getPort().intValue());
                if (StringUtils.isNotBlank(password)) {
                    redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
                }
                connectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration, lettucePoolingClientConfiguration);
                //设置非共享连接，允许多个连接公用一个物理连接。如果设置false ,每一个连接的操作都会开启和关闭socket连接。 true适合单机环境
                connectionFactory.setShareNativeConnection(true);
            } else {
                //集群
                RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
                redisClusterConfiguration.setClusterNodes(nodes);
                redisClusterConfiguration.setMaxRedirects(properties.getMaxRedirects());
                if (StringUtils.isNotBlank(password)) {
                    redisClusterConfiguration.setPassword(RedisPassword.of(password));
                }
                connectionFactory = new LettuceConnectionFactory(redisClusterConfiguration, lettucePoolingClientConfiguration);
                //设置非共享连接，允许多个连接公用一个物理连接。如果设置false ,每一个连接的操作都会开启和关闭socket连接。 false适合集群环境
                connectionFactory.setShareNativeConnection(false);
            }
            return connectionFactory;
        }

        /**
         * 缓存管理器
         *
         * @param lettuceConnectionFactory 连接
         * @return CacheManager
         */
        @Bean
        public CacheManager cacheManager(LettuceConnectionFactory lettuceConnectionFactory, RedisProperties properties) {
            StringRedisSerializer keySerializer = new StringRedisSerializer();

            AutoTypeValueSerializer autoTypeValueSerializer = AutoTypeValueSerializer.getInstance();
            org.springframework.data.redis.cache.RedisCacheConfiguration configuration = org.springframework.data.redis.cache.RedisCacheConfiguration.defaultCacheConfig()
                    .computePrefixWith(key -> RedisConfig.formatFullKey(properties.getPrefixKey(), key))
                    .entryTtl(Duration.ofSeconds(properties.getTimeout()))
                    .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer))
                    .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(autoTypeValueSerializer))
                    .disableCachingNullValues();
            return RedisCacheManager.builder(lettuceConnectionFactory).cacheDefaults(configuration).build();
        }

        @Bean
        public RedisCacheFactory redisCacheFactory(RedisProperties redisProperties, LettuceConnectionFactory lettuceConnectionFactory){
            return new RedisCacheFactory(redisProperties.getPrefixKey(), redisProperties.getTimeout(), lettuceConnectionFactory);
        }

    }
    
    @Bean
    public CacheService cacheService(CacheFactory[] cacheFactories){
        return new CacheServiceImpl(cacheFactories);
    }

}
