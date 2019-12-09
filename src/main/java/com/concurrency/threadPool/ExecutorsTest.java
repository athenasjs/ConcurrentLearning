package com.concurrency.threadPool;

import java.util.concurrent.*;

//Executors类是一个工具类，有一些针对Executor的工厂方法和工具方法，类似于Arrays Collections
public class ExecutorsTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        Future<String> future =  service.submit(()->{
            //execute方法只能执行没返回值的，而submit方法可以执行有返回值和没有返回值的
            System.out.println(Thread.currentThread().getName() + "starts...");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "aaa";
        });

        service.shutdown();
        System.out.println(future.get());

    }

}
