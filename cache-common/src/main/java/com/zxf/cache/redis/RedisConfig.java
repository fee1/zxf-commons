package com.zxf.cache.redis;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * redis配置类工具类
 *
 * @author 朱晓峰
 */
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

}
