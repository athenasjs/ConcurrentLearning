package com.concurrency.jucContainers;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentLinkedQueueTest {
    public static void main(String[] args) {
        Queue<String> strs = new ConcurrentLinkedQueue<>();

        for(int i = 0; i < 10; i++){
            strs.offer("a" + i);  //offer和add一样都是向队列中插入元素
        }
        System.out.println(strs);
        System.out.println(strs.size());
        System.out.println(strs.poll()); //拿出来并删除,peek是不删除

        System.out.println(strs.peek());
        System.out.println(strs.size());

        //双端队列Deque

    }
}
