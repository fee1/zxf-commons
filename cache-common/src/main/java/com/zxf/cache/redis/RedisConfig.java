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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * redis配置类
 *
 * @author 朱晓峰
 */
@Configuration
@ConditionalOnProperty(value = "spring.cache.redis.enable", matchIfMissing = true)
@ConfigurationProperties(prefix = "spring.cache.redis")
@Data
@Slf4j
public class RedisConfig {

    /**
     * hosts
     */
    @Value("${hosts:}")
    private String[] hosts;

    /**
     * 连接超时时间,5s
     */
    @Value("${connect-timeout:5000}")
    private long connectTimeout;

    /**
     * 命令超时时间10s
     */
    @Value("${command-timeout:10000}")
    private long commandTimeout;

    /**
     * 密码
     */
    @Value("${password:}")
    private String password;

    /**
     * 缓存超时时间
     */
    @Value("${timeout:86400}")
    private int timeout;

    /**
     * 连接池最大连接数，负数无限制
     */
    @Value("${max-total:8}")
    private int maxTotal;

    /**
     * 连接池最大空闲连接
     */
    @Value("${max-idle:8}")
    private int maxIdle;

    /**
     * 连接池最小空闲连接
     */
    @Value("${min-idle:1}")
    private int minIdle;

    /**
     * 连接池最大等待阻塞时间,负数无限制
     */
    @Value("${max-wait:5000}")
    private long maxWait;

    /**
     * 集群配置，最大重定向。第一台挂了，连第二台，第二台挂了连第三台
     */
    @Value("${max-redirects:3}")
    private int maxRedirects;

    /**
     * key前缀
     */
    @Value("${prefix-key:}")
    private String prefixKey;

    /**
     * 获取redisNode结点
     *
     * @return List
     */
    private List<RedisNode> getRedisNodes() {
        List<RedisNode> redisNodes = new ArrayList<>();
        for (String host : this.hosts) {
            String[] items = host.split(":");
            Assert.isTrue(StringUtils.isNotBlank(items[0]) && StringUtils.isNotBlank(items[1]),
                    "host与port未配置正确，格式: host:port");
            redisNodes.add(new RedisNode(items[0], Integer.parseInt(items[1])));
        }
        return redisNodes;
    }

    /**
     * LettuceConnectionFactory
     *
     * @return LettuceConnectionFactory
     */
    @Bean
    LettuceConnectionFactory lettuceConnectionFactory() {
        List<RedisNode> nodes = getRedisNodes();
        Assert.isTrue(!nodes.isEmpty(), "未配置redis的hosts");
        // todo 可以使用sm2 加解密和base64双重加解密方式
        this.password = Base64Util.decrypt(this.password);
        //连接池配置
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxIdle(this.maxIdle);
        poolConfig.setMinIdle(this.minIdle);
        poolConfig.setMaxTotal(this.maxTotal);
        poolConfig.setMaxWaitMillis(this.maxWait);

        ClientOptions clientOptions = ClientOptions.builder()
                .autoReconnect(true).socketOptions(
                        SocketOptions.builder()
                                .connectTimeout(Duration.ofMillis(this.connectTimeout))
                                .keepAlive(true)
                                .tcpNoDelay(true)
                                .build()).build();

        LettucePoolingClientConfiguration lettucePoolingClientConfiguration =
                LettucePoolingClientConfiguration.builder()
                        .poolConfig(poolConfig)
                        .clientOptions(clientOptions)
                        .commandTimeout(Duration.ofMillis(this.connectTimeout))
                        .build();

        LettuceConnectionFactory connectionFactory;

        //单机
        if (nodes.size() == 1) {
            RedisNode node = nodes.get(0);
            RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
            redisStandaloneConfiguration.setHostName(Objects.requireNonNull(node.getHost()));
            redisStandaloneConfiguration.setPort(node.getPort().intValue());
            if (StringUtils.isNotBlank(this.password)) {
                redisStandaloneConfiguration.setPassword(RedisPassword.of(this.password));
            }
            connectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration, lettucePoolingClientConfiguration);
        } else {
            //集群
            RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
            redisClusterConfiguration.setClusterNodes(nodes);
            redisClusterConfiguration.setMaxRedirects(this.maxRedirects);
            if (StringUtils.isNotBlank(this.password)) {
                redisClusterConfiguration.setPassword(RedisPassword.of(this.password));
            }
            connectionFactory = new LettuceConnectionFactory(redisClusterConfiguration, lettucePoolingClientConfiguration);
        }
        //是否共享本地连接
        connectionFactory.setShareNativeConnection(false);
        return connectionFactory;
    }

    /**
     * 缓存管理器
     *
     * @param connectionFactory 连接
     * @return CacheManager
     */
    @Bean
    CacheManager cacheManager(@Autowired LettuceConnectionFactory connectionFactory) {
        StringRedisSerializer keySerializer = new StringRedisSerializer();

        AutoTypeValueSerializer autoTypeValueSerializer = AutoTypeValueSerializer.getInstance();
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig()
                .computePrefixWith(key -> formatFullKey(this.prefixKey, key))
                .entryTtl(Duration.ofSeconds(this.timeout))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(autoTypeValueSerializer))
                .disableCachingNullValues();
        return RedisCacheManager.builder(connectionFactory).cacheDefaults(configuration).build();
    }

    /**
     * 设置项目key前缀 缓存类key前缀
     *
     * @param prefixKey 项目key前缀
     * @param key       缓存类key前缀
     * @return String
     */
    static String formatFullKey(String prefixKey, String key) {
        return String.format("%s:%s:", prefixKey, key);
    }

    /**
     * 获取redis缓存产品工厂
     *
     * @return RedisCacheFactory
     */
    @Bean
    RedisCacheFactory getRedisCacheFactory() {
        return new RedisCacheFactory(this, this.prefixKey);
    }

}
