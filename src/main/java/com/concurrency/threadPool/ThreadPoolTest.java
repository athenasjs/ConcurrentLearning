package com.concurrency.threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {
    public static void main(String[] args) {
        //java中所有的线程池都实现了ExecutorService接口
        //线程池的概念：池子里有一些线程，来一个任务开启一个，线程池维护一个任务队列放置等待的任务，
        //线程池的好处：当一个线程执行完毕后还在池子里，来一个任务可以继续执行，如果线程和任务的数量安排的比较好，线程池可以执行很多很多任务，不用新建线程，效率比较高
        //新启动线程需要操作系统用户态->内核态 消耗资源 所以线程能重用就重用
        ExecutorService service = Executors.newFixedThreadPool(5);
        for(int i = 0; i < 6; i++){
            service.execute(()->{
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "ends");
            });
        }
        System.out.println(service);
        service.shutdown(); //shutdown方法是正常的关闭，等所有任务执行完才关闭
        //shutdownNow方法是直接关闭，不管任务有没有执行完
        System.out.println(service.isTerminated());//isTerminate表示所有任务都执行完
        //线程池打印的状态：isrunning - > isshuttingdown -> terminated
        System.out.println(service.isShutdown());
        System.out.println(service);

        //线程池维护2个队列： queue tasks和completed tasks，分别是排位的任务和完成的任务
        //一堆线程维护两个队列
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(service.isTerminated());
        System.out.println(service.isShutdown());
        System.out.println(service);
    }
}
