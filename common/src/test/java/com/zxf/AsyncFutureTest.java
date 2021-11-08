package com.zxf;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 自定义异步实现
 *
 * @author zhuxiaofeng
 * @date 2021/10/12
 */
public class AsyncFutureTest {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        FutureTask futureTask = new FutureTask<>(() -> {
            Thread.sleep(1000);
            return "异步任务的返回";
        });

        executorService.execute(futureTask);
        Object o = null;

        try {
            o = futureTask.get(0, TimeUnit.SECONDS);//设置超时时间的方法不会等待任务是否执行完成,没有执行完毕前get()方法会一直阻塞等待执行完毕
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        System.out.println(o);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            o = futureTask.get(0, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        System.out.println(o);
        executorService.shutdown();
    }

}
