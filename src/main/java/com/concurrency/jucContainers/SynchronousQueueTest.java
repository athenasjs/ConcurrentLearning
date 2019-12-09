package com.concurrency.jucContainers;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

/*
容量为0的队列，消息来了必须处理
 */
public class SynchronousQueueTest {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> strs = new SynchronousQueue<>(); //容量为0

        new Thread(()->{
            try {
                System.out.println(strs.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

//        strs.add("aaa");
        strs.put("aaa"); //阻塞等待消费者消费,这种队列不能用add方法，会报异常
        //里面放的任何东西都必须直接给消费者消费，是一种特殊的transfer，内部调用transfer
        //如果没有消费者，则调用put方法阻塞，add方法报错
        System.out.println(strs.size());
    }
}
