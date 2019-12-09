package com.concurrency.threadPool;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class WorkStealingPoolTest {
    public static void main(String[] args) throws IOException {
        ExecutorService service = Executors.newWorkStealingPool();
        System.out.println(Runtime.getRuntime().availableProcessors());//核心数

        service.execute(new R(1000));
        for(int i = 0; i < 12; i++){

            service.execute(new R(2000));
        }
        //service.execute(new R(2000)); //daemon
        //service.execute(new R(2000));
        //service.execute(new R(2000)); //开启5个线程
        //第一个线程最先执行完，因此会拿到第五个任务
        System.in.read();

    }

    static class R implements Runnable{

        int time;
        public R(int time){
            this.time = time;
        }
        @Override
        public void run() {
            try {
                TimeUnit.MILLISECONDS.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName());
        }
    }
}
