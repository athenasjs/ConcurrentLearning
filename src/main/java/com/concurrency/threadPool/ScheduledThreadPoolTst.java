package com.concurrency.threadPool;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolTst {
    public static void main(String[] args) {
        //定时执行任务 指定第一个任务延时和任务间隔，有空闲线程就执行，没有就等待，保证当前任务执行完成
        ScheduledExecutorService service = Executors.newScheduledThreadPool(4);//只有4个线程
        service.scheduleAtFixedRate(()->{
            try {
                TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName());
        }, 0, 500, TimeUnit.MILLISECONDS);

    }
}
