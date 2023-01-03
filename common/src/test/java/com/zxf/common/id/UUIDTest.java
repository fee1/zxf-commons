package com.zxf.common.id;


import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;


/**
 * @author zhuxiaofeng
 * @date 2023/1/2
 */
public class UUIDTest {

    @Test
    public void simpleUse(){
        UUID uuid = new UUID();
        System.out.println(uuid.builder());
    }

    @Test
    public void concurrencyTest() {
        UUID uuid = new UUID();

        ForkJoinPool forkJoinPool =new ForkJoinPool(Runtime.getRuntime().availableProcessors() / 2);
        List<String> ids = new ArrayList<>();
        List<Callable<String>> tasks = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            tasks.add(() -> {
                String l = uuid.builder();
                ids.add(l);
                return l;
            });
        }
        Long startTime = System.currentTimeMillis();
        forkJoinPool.invokeAll(tasks);
        Long endTime = System.currentTimeMillis();
        System.out.println("耗时: "+ (endTime.longValue() - startTime.longValue())/1000);
        System.out.println(ids);


    }
}