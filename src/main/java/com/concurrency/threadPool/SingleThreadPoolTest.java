package com.concurrency.threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingleThreadPoolTest {
    public static void main(String[] args) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        for(int i = 0; i < 5; i++){
            int j = i;
            service.execute(()->{
                System.out.println(j + Thread.currentThread().getName());
            });

        }
    }
}
