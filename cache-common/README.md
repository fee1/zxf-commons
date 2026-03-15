# 缓存框架使用文档

## 概述

本包提供了一套完整的缓存框架，支持Redis等多种缓存类型，提供了统一的缓存操作接口和自动加载机制。

## 核心组件

### 1. 接口层
- **LoadingCache<K, V>**: 缓存操作的核心接口，定义了get、set、remove等基本操作
- **ICache**: 底层缓存实现接口，支持多种缓存产品

### 2. 抽象层
- **AbstractLoadingCache<K, V>**: 抽象缓存实现类，提供了缓存的基础功能
- **BaseLoadingCache<V>**: 简化的抽象类，Key固定为String类型

### 3. 服务层
- **CacheService**: 缓存服务接口
- **CacheServiceImpl**: 缓存服务实现类
- **CacheFactory**: 缓存工厂，用于创建不同类型的缓存实例

## 主要特性

1. **自动加载**: 当缓存中没有数据时，自动从数据源加载
2. **多缓存支持**: 支持Redis、Guava等多种缓存类型
3. **批量操作**: 支持批量获取和设置缓存
4. **过期时间控制**: 支持自定义缓存过期时间
5. **线程安全**: 内置同步机制，防止缓存击穿
6. **序列化支持**: 提供多种序列化方式

## 使用方法

### 1. 创建缓存类

继承`BaseLoadingCache<V>`类，实现必要的抽象方法：

```java
@Component
public class ShopShowSettingCache extends BaseLoadingCache<ShopShowSetting> {

    @Resource
    private ShopShowSettingRepository shopShowSettingRepository;

    /**
     * 设置缓存过期时间（秒）
     * 默认为3600秒（1小时）
     */
    @Override
    public int getTimeout() {
        return 3600; // 1小时
    }

    /**
     * 设置缓存类型（可选）
     * 默认为Redis缓存
     */
    @Override
    public String getCacheType() {
        return super.getCacheType(); // 默认Redis
    }

    /**
     * 设置缓存名称
     * 用于区分不同的缓存实例
     */
    @Override
    public String getCacheName() {
        return "shop-show-setting";
    }

    /**
     * 数据加载逻辑
     * 当缓存中没有数据时，会调用此方法从数据源加载
     */
    @Override
    protected ShopShowSetting doLoad(String brandId) throws Exception {
        ShopShowSetting showSetting = shopShowSettingRepository.findByBrandId(brandId);
        if (showSetting == null) {
            // 如果找不到指定品牌的设置，返回默认设置
            showSetting = shopShowSettingRepository.findByBrandId("0");
            showSetting.setId(null);
        }
        return showSetting;
    }
}
```

### 2. 使用缓存

在需要使用缓存的地方注入缓存实例：

```java
@Service
public class ShopService {
    
    @Resource
    private ShopShowSettingCache shopShowSettingCache;
    
    public ShopShowSetting getShopShowSetting(String brandId) {
        // 自动从缓存获取，如果缓存中没有则自动加载
        return shopShowSettingCache.get(brandId);
    }
    
    public void updateShopShowSetting(String brandId, ShopShowSetting setting) {
        // 更新数据库
        shopShowSettingRepository.save(setting);
        
        // 手动更新缓存
        shopShowSettingCache.set(brandId, setting);
        
        // 或者删除缓存，下次访问时自动重新加载
        // shopShowSettingCache.remove(brandId);
    }
}
```

### 3. 批量操作

```java
// 批量获取
List<String> brandIds = Arrays.asList("1", "2", "3");
List<ShopShowSetting> settings = shopShowSettingCache.multiGet(brandIds);

// 批量设置
Map<String, ShopShowSetting> settingMap = new HashMap<>();
settingMap.put("1", setting1);
settingMap.put("2", setting2);
shopShowSettingCache.multiSet(settingMap);
```

## 配置说明

### 缓存类型
- **Redis**: 分布式缓存，支持集群部署（默认）
- **Guava**: 本地缓存，支持过期时间控制

### 过期时间配置
- 重写`getTimeout()`方法设置过期时间（单位：秒）
- 默认过期时间为3600秒（1小时）
- 设置为0表示永不过期

### 缓存命名规范
- 缓存名称应该具有描述性，便于识别和管理
- 建议使用小写字母和连字符，如：`user-info`、`shop-setting`

## 注意事项

1. **空值处理**: `doLoad`方法返回null时，不会缓存该值
2. **异常处理**: `doLoad`方法抛出异常时，会传播给调用方
3. **线程安全**: 框架内置了同步机制，防止缓存击穿
4. **内存管理**: 使用本地缓存时注意内存使用量
5. **序列化**: 缓存的对象必须支持序列化

## 最佳实践

1. **合理设置过期时间**: 根据数据更新频率设置合适的过期时间
2. **避免缓存穿透**: 对于可能不存在的数据，考虑缓存空对象或使用布隆过滤器
3. **监控缓存命中率**: 定期检查缓存的命中率，优化缓存策略
4. **及时清理缓存**: 数据更新时及时清理或更新相关缓存
5. **异常处理**: 在`doLoad`方法中妥善处理异常情况

## 扩展开发

如需支持新的缓存类型，可以：

1. 实现`ICache`接口
2. 在`CacheFactory`中注册新的缓存类型
3. 重写缓存类的`getCacheType()`方法返回新类型标识

---
