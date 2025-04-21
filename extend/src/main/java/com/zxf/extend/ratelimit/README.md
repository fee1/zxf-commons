# 接口限流功能

基于Spring拦截器、自定义注解和Guava RateLimiter实现的接口限流功能。

## 功能特点

1. 基于注解的声明式限流，使用简单
2. 支持按接口或自定义key进行限流
3. 支持自定义QPS限流速率
4. 支持自定义限流提示信息
5. 使用Guava的RateLimiter实现平滑限流

## 使用方法

### 1. 在接口上添加@RateLimit注解

```java
@RestController
@RequestMapping("/api/example")
public class ExampleController {

    // 默认配置：QPS=10
    @RateLimit
    @GetMapping("/default")
    public Object defaultLimit() {
        // 业务逻辑
        return result;
    }
    
    // 自定义QPS：QPS=5
    @RateLimit(qps = 5)
    @GetMapping("/custom-qps")
    public Object customQpsLimit() {
        // 业务逻辑
        return result;
    }
    
    // 自定义key，相同key的接口共享限流配额
    @RateLimit(key = "shared-limit", qps = 2)
    @GetMapping("/shared")
    public Object sharedLimit() {
        // 业务逻辑
        return result;
    }
    
    // 自定义错误信息
    @RateLimit(qps = 1, message = "操作太频繁了，请休息一下再试")
    @GetMapping("/custom-message")
    public Object customMessage() {
        // 业务逻辑
        return result;
    }
}
```

### 2. @RateLimit注解参数说明

| 参数 | 类型 | 默认值 | 说明 |
| --- | --- | --- | --- |
| qps | double | 10 | 限流的QPS(每秒查询率) |
| key | String | "" | 限流的key，为空则使用方法完整路径 |
| message | String | "请求过于频繁，请稍后再试" | 超过限流时的提示信息 |

### 3. 全局异常处理

当请求被限流时，会抛出`RateLimitException`异常，由全局异常处理器返回HTTP 429状态码(Too Many Requests)，响应格式为：

```json
{
  "code": 429,
  "message": "请求过于频繁，请稍后再试"
}
```

## 原理说明

1. 通过Spring的拦截器拦截所有请求
2. 检查接口方法是否带有@RateLimit注解
3. 根据注解配置，获取对应的Guava RateLimiter
4. 尝试获取令牌，如果成功则放行请求，失败则抛出限流异常
5. 限流异常由全局异常处理器处理，返回友好提示

## 配置说明

RateLimiter实例会缓存在内存中，默认配置：
- 过期时间：1小时
- 最大缓存数量：1000个

## 注意事项

1. 使用相同key的接口会共享限流配额
2. 默认使用类全名+方法名作为限流key
3. 可以根据需要自定义错误信息 