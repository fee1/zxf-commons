package com.zxf.common.utils;

import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.CallbackFilter;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.cglib.proxy.NoOp;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author zhuxiaofeng
 * @date 2022/12/4
 */
public class SpringEnhancerUtilTest {

    @SneakyThrows
    @Test
    public void simpleEnhancer() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(TargetService.class);
//      enhancer.setCallbackType(TestMethodInterceptor.class);
        enhancer.setCallbackFilter(new TestCallbackFilter());//filter要比callbacks先设置
        enhancer.setCallbackTypes(new Class[]{TestMethodInterceptor.class, NoOp.class});

        Class<?> subclass = enhancer.createClass();
        Enhancer.registerCallbacks(subclass, new Callback[] {
                new TestMethodInterceptor(),NoOp.INSTANCE
        });
        System.out.println(subclass);
        TargetService obj = (TargetService) subclass.newInstance();
        obj.doService();
        obj.testService();
    }


    //先定义一个target class
    public static class TargetService {
        public void doService(){
            System.out.println("doService() TargetService");
        }

        public void testService(){
            System.out.println("testService() TargetService");
        }
    }

    public static class TestCallbackFilter implements CallbackFilter {

        @Override
        public int accept(Method method) {
            int result = 1;
            if (method.getName().equals("doService")) {
                result = 0;
            }
            return result;
        }

    }

    //创建interceptor和filter
    //org.springframework.cglib.proxy.MethodInterceptor
    public static class TestMethodInterceptor implements MethodInterceptor {

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
            System.out.println("before");
            Object res = methodProxy.invokeSuper(obj, args);
            System.out.println("after");
            return res;
        }

    }

}