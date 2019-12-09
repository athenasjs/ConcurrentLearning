package com.concurrency.volatileTest;

import java.util.concurrent.TimeUnit;

/*
volatile关键字使得一个变量在多个线程之间可见，
AB线程都用到一个变量，java默认是A线程中保留一份copy，这样如果B修改了该变量，则A线程未必知道使用volatile关键字
会让所有线程都读到该变量的修改值

在下面的代码中，running变量存在于堆内存的t对象中，当线程t1开始运行，会把running从内存中读取到t1线程的工作区，在运行过程中使用这个copy，并不会每次都去
读取堆内存，这样当主线程修改running的值后，t1线程感知不到，所以不会停止运行
使用volatile，强制所有线程都去堆内存中读取running的值，volatile并不能保证多个线程共同修改running变量时带来的不一致问题，不能代替synchronized
 */
public class Test1 {
    private volatile boolean running = true;  //对比一下有无volatile得情况下，整个程序的运行区别
    //线程之间的可见性  无锁同步，一个线程更新变量，其他线程可见
    public synchronized void m(){
        System.out.println("m start");
        while(running){
            //当CPU有空闲的时候，有可能去主内存里面刷一下值到缓存中，一般都是在缓存中读取
            //volatile必synchronized并发性高的多
        }
        System.out.println("m end");
    }

    public static void main(String[] args) {
        Test1 test1 = new Test1();
        new Thread(test1::m).start();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        test1.running = false;
    }
}
