# 有关注解

## @Nullable
```text
用 @Nullable 声明注释的元素 null 值对于返回 (方法)、传递给 (参数) 和保持 (对于局部变量和字段) 是完全有效的。
```
## @NotNull
```text
用 @NotNull 声明注释的元素不允许返回 null 值 （对于方法），传递给（对于参数）和保持（对于局部变量和字段）。
```
## @ConfigurationProperties
```text
需要
```
# 有关类

## RedisSerializer
```text
    spring boot简单引入redis依赖，并使用RedisTemplate进行对象存储时，需要使存储对象实现Serializable接口，这样才能够成功将对象进行序列化。
RedisTemplate默认使用的序列化机制是JdkSerializationRedisSerializer，但实际开发中，往往会以json的形式来保存数据。那么可以通过两种方式来实
现这种要求，第一就是将保存的对象通过一些方法先转换成JsonString的形式，然后再通过redis保存；第二种方式，就是RedisSerializer.
```
## DefaultValueOperations
```text

```
## DefaultListOperations
## DefaultSetOperations
## DefaultStreamOperations
## DefaultZSetOperations
## DefaultGeoOperations
## DefaultHyperLogLogOperations
## DefaultClusterOperations

# 封装的方法
## 设置值
## 获取过期时间
### 获取过期时间(long)，单位秒
### 获取过期时间，单位自己设置
## 