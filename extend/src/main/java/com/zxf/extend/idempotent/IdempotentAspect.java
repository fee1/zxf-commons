package com.zxf.extend.idempotent;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 接口幂等性切面
 * 通过Redis实现接口幂等性校验
 */
@Aspect
@Component
@Slf4j
public class IdempotentAspect {

    @Resource
    private RedisTemplate redisTemplate;

    private static final String LOCK_PREFIX = "idempotent_Lock:";

    /**
     * 表达式解析器
     */
    private static final ExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();

    /**
     * 参数名发现器
     */
    private static final ParameterNameDiscoverer PARAMETER_NAME_DISCOVERER = new DefaultParameterNameDiscoverer();

    /**
     * 定义切点：拦截所有带有@Idempotent注解的方法
     */
    @Pointcut("@annotation(com.zxf.extend.idempotent.Idempotent)")
    public void idempotent() {
    }

    /**
     * 环绕通知：在目标方法执行前后织入逻辑
     */
    @Around("idempotent()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取当前方法签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        
        // 获取注解信息
        Idempotent idempotent = method.getAnnotation(Idempotent.class);
        if (idempotent == null) {
            // 没有注解直接执行原方法
            return joinPoint.proceed();
        }
        
        // 获取幂等性Key
        String idempotentKey = getIdempotentKey(joinPoint, idempotent);
        log.debug("幂等性校验Key: {}", idempotentKey);
        
        // Redis加锁，实现幂等
        boolean lockSuccess = setIdempotentLock(LOCK_PREFIX + idempotentKey, idempotent.expireTime(), idempotent.timeUnit());
        
        if (!lockSuccess) {
            log.warn("重复请求被拦截: {}", idempotentKey);
            throw new IdempotentException(idempotent.message());
        }
        
        try {
            // 执行原方法
            return joinPoint.proceed();
        } catch (Throwable e) {
            // 如果方法执行过程中出现异常，释放锁，允许客户端重试
            throw e;
        }finally {
            redisTemplate.delete(idempotentKey);
        }
    }

    /**
     * 生成幂等性校验的Key
     */
    private String getIdempotentKey(JoinPoint joinPoint, Idempotent idempotent) {
        StringBuilder keyBuilder = new StringBuilder();
        keyBuilder.append(idempotent.prefix()).append(":");
        
        // 如果有自定义keys，优先使用自定义keys
        if (idempotent.keys().length > 0) {
            keyBuilder.append(buildKeyBySpEL(joinPoint, idempotent.keys()));
        } else {
            // 否则默认使用请求参数+请求路径作为幂等key
            keyBuilder.append(buildDefaultKey(joinPoint));
        }
        
        // 对最终的key进行MD5处理，避免key过长
        return DigestUtils.md5Hex(keyBuilder.toString());
    }

    /**
     * 使用Spring EL表达式构建幂等Key
     */
    private String buildKeyBySpEL(JoinPoint joinPoint, String[] keyExpressions) {
        StringBuilder keyBuilder = new StringBuilder();
        
        // 获取方法参数名和参数值
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Object[] args = joinPoint.getArgs();
        String[] parameterNames = PARAMETER_NAME_DISCOVERER.getParameterNames(method);
        
        // 创建表达式上下文
        EvaluationContext context = new StandardEvaluationContext();
        if (parameterNames != null) {
            for (int i = 0; i < parameterNames.length; i++) {
                context.setVariable(parameterNames[i], args[i]);
            }
        }
        
        // 解析SpEL表达式，获取key
        List<String> keyList = new ArrayList<>();
        for (String keyExpression : keyExpressions) {
            if (StringUtils.hasText(keyExpression)) {
                Expression expression = EXPRESSION_PARSER.parseExpression(keyExpression);
                Object value = expression.getValue(context);
                if (value != null) {
                    keyList.add(String.valueOf(value));
                }
            }
        }
        
        return String.join(":", keyList);
    }

    /**
     * 构建默认的幂等Key（请求路径+参数）
     */
    private String buildDefaultKey(JoinPoint joinPoint) {
        StringBuilder keyBuilder = new StringBuilder();
        
        // 添加请求路径
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            keyBuilder.append(request.getRequestURI()).append(":");
        } catch (Exception e) {
            // 非Web环境下，使用方法全限定名
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            keyBuilder.append(signature.getDeclaringTypeName())
                    .append(".")
                    .append(signature.getName())
                    .append(":");
        }
        
        // 添加请求参数的哈希值
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            for (Object arg : args) {
                if (arg != null) {
                    keyBuilder.append(arg.hashCode()).append(":");
                }
            }
        }
        
        return keyBuilder.toString();
    }

    /**
     * 设置幂等锁，使用Redis的setnx命令，加上同步方法，保证并发安全
     */
    private synchronized boolean setIdempotentLock(String key, long expireTime, TimeUnit timeUnit) {
        // 将时间单位转换为秒
        String value = "1"; // 锁的值不重要，只要能设置成功即可
        
        // 在Redis中设置key-value，如果key不存在才设置成功，返回true，否则返回false
        Boolean b = redisTemplate.hasKey(key);
        if (b != null && b) {
            return false;
        }
        redisTemplate.opsForValue().set(key, value, expireTime, timeUnit);
        return true;
    }
} 