package com.zxf;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * JDK 1.8 方式实现异步方法
 *
 * @author zhuxiaofeng
 * @date 2021/10/12
 */
public class AsyncCompletableFutureTest {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("异步线程在执行" + Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("异步线程返回前");
            return "异步线程执行完毕了！";
        }, executorService).thenApplyAsync( ( a ) -> {
            return "ffff" + a;
        });

//        stringCompletableFuture.get()//这个方法会阻塞线程
        System.out.println("异步线程之后呢" + stringCompletableFuture.getNow(null));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("主线程执行完毕" + stringCompletableFuture.getNow(null));

        executorService.shutdown();
    }

}
