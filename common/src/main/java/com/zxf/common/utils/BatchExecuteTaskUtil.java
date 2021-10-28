package com.zxf.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 批量执行任务
 *
 * @author zhuxiaofeng
 * @date 2021/10/20
 */
@Slf4j
public class BatchExecuteTaskUtil {

    private static final ForkJoinPool FORK_JOIN_POOL =new ForkJoinPool(4);

    /**
     * 执行并获取返回类型，组装到list后，返回指定类型list。推荐用于入参类型和所需要返回list类型不一致的情况使用
     * @param datas 入参数据
     * @param action 执行逻辑动作
     * @param <T> 数据类型
     * @param <R> 返回数据类型
     * @return list
     */
    public static <T,R> List<R> executeAndReturn(Collection<? extends T> datas, Function<T, ? extends R> action) {
        List<Future<R>> futureList = execute(datas, action);
        List<R> rList = new ArrayList<>(futureList.size());
        futureList.forEach(future -> {
            R r = null;
            try {
                r = future.get();
            } catch (Exception e) {
                e.printStackTrace();
                log.error("BatchExecuteTaskUtil.executeAndReturn execute fail in future.get(): {}", e.getMessage());
            }
            rList.add(r);
        });
        return rList;
    }

    /**
     * 执行并返回List<Future<R>>。 推荐用于常规操作数据对象不需要返回的情况
     * @param datas 入参数据
     * @param action 执行逻辑动作
     * @param <T> 数据类型
     * @param <R> 返回数据类型
     * @return list
     */
    public static <T,R> List<Future<R>> execute(Collection<? extends T> datas, Function<T, ? extends R> action) {
        Collection<? extends Callable<R>> tasks= datas.stream()
                .map(input -> (Callable<R>) () -> action.apply(input)).collect(Collectors.toList());
        return FORK_JOIN_POOL.invokeAll(tasks);
    }

}
