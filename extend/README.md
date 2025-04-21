```text
idempotent: 幂等

介绍：
    为了防止重复提交，可以采用幂等性处理，即在方法执行前，先判断是否已经执行过，如果已经执行过，则直接返回结果，而不再执行方法。
    默认使用：用户ID+请求参数+请求路径作为幂等key，区分是否为相同请求，支持自定义，具体查看示例。
    支持HTTP接口，针对特定方法，MQ消费场景使用

在方法上怎增加@Idempotent注解
示例：cn.mezeron.jianzan.extend.idempotent.IdempotentDemo
```
```text
ratelimit: 限流
```