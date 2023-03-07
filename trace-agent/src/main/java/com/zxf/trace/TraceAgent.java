package com.zxf.trace;

import com.zxf.trace.interceptor.MethodCostTime;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;

/**
 * @author zhuxiaofeng
 * @date 2022/12/5
 */
@Slf4j
public class TraceAgent {

    //JVM 首先尝试在代理类上调用以下方法
    public static void premain(String agentArgs, Instrumentation inst) {
        log.debug("Trace agent is start");
        //代理线程
        agentThread(inst);
        agentComponent(inst);
        log.debug("Trace agent is end");
    }

    //如果代理类没有实现上面的方法，那么 JVM 将尝试调用该方法
    public static void premain(String agentArgs) {
    }

    private static void agentThread(Instrumentation inst){
        AgentBuilder.Transformer transformer = (builder, typeDescription, classLoader, javaModule) -> {
            return builder
                    .method(ElementMatchers.any()) // 测试
//                    .method(ElementMatchers.isPublic().and(ElementMatchers.named("run"))) //拦截run方法
                    .intercept(MethodDelegation.to(MethodCostTime.class)); // 委托
        };

        AgentBuilder.Listener listener = new AgentBuilder.Listener() {
            @Override
            public void onDiscovery(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {
            }

            @Override
            public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b, DynamicType dynamicType) {
                log.debug("AgentThread - Complete transfor class, name:{}", typeDescription.getName());
            }

            @Override
            public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b) {
            }

            @Override
            public void onError(String s, ClassLoader classLoader, JavaModule javaModule, boolean b, Throwable throwable) {
                log.debug("AgentThread - Error transfor class, name:{}", s);
            }

            @Override
            public void onComplete(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {
            }

        };

        new AgentBuilder
                .Default()
                 //指定需要拦截的类
                .type(ElementMatchers.nameStartsWith("com.zxf.trace"))// 测试
//                .type(ElementMatchers.isSubTypeOf(Runnable.class)
//                        .and(ElementMatchers.not(ElementMatchers.nameStartsWith("com.taobao")))) //拦截run方法
                .transform(transformer)
                .with(listener)
                .installOn(inst);
    }

    private static void agentComponent(Instrumentation inst){

    }

}
