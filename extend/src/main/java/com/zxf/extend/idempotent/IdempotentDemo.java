package com.zxf.extend.idempotent;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 幂等性注解使用示例
 * 
 * 此类仅作为示例，实际项目中可以删除
 */
@RestController
@RequestMapping("/demo/idempotent")
public class IdempotentDemo {

    /**
     * 使用默认配置的幂等性注解
     * 按照请求参数自动生成幂等性标识
     * 默认过期时间为60秒
     */
    @PostMapping("/default")
    @Idempotent
    public Map<String, Object> defaultDemo(@RequestBody Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "处理成功");
        return result;
    }

    /**
     * 自定义过期时间的幂等性注解
     * 设置幂等性标识过期时间为5分钟
     */
    @PostMapping("/custom-expire")
    @Idempotent(expireTime = 5, timeUnit = TimeUnit.MINUTES)
    public Map<String, Object> customExpireDemo(@RequestBody Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "处理成功");
        return result;
    }

    /**
     * 使用自定义key的幂等性注解
     * 基于请求参数中的特定字段作为幂等性标识
     */
    @PostMapping("/custom-key")
    @Idempotent(prefix = "order", keys = {"#params['orderId']"})
    public Map<String, Object> customKeyDemo(@RequestBody Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "处理成功");
        return result;
    }

    /**
     * 多字段组合作为幂等性标识
     * 同时自定义幂等性标识前缀和错误提示信息
     */
    @PostMapping("/multi-key")
    @Idempotent(
            prefix = "payment",
            keys = {"#params['userId']", "#params['orderNo']"},
            expireTime = 30,
            message = "订单正在支付中，请勿重复提交"
    )
    public Map<String, Object> multiKeyDemo(@RequestBody Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "支付成功");
        return result;
    }
} 