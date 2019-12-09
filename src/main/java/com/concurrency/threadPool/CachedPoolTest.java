package com.concurrency.threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CachedPoolTest {
    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();//只是new出来，poolsize为0，没有线程
        System.out.println(service);

        for(int i = 0; i < 8; i++){
            service.execute(()->{ //有工作需要就会开启线程
                try {
                    TimeUnit.MILLISECONDS.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() );
            });
        }
        System.out.println(service);
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(service);
    }
}
