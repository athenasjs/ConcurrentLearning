package com.concurrency.jucContainers;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/*
阻塞队列测试(队列分两种：并发容器和阻塞容器 BlockingQueue提供put和take方法存取元素，这两个方法是阻塞的，在容器慢/空的状况下会阻塞)
 */
public class LinkedBlockingQueueTest {

    static BlockingQueue<String> strs = new LinkedBlockingQueue<>();
    static Random random = new Random();

    public static void main(String[] args) {
        new Thread(()->{ //开一个生产者线程
            for(int i = 0; i < 100; i++){
                try {
                    strs.put("a" + i);  //阻塞方法
                    TimeUnit.MILLISECONDS.sleep(random.nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }, "p1").start();
        for(int i = 0; i < 5; i++){  //开5个消费者线程去消费
            new Thread(()->{
                for(;;){
                    try {
                        System.out.println(Thread.currentThread().getName() + "take-" + strs.take());//队头出队
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "c"+i).start();
        }

    }
}
