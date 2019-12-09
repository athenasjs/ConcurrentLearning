package com.concurrency.jucContainers;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/*
有界队列
 */
public class ArrayBlockingQueueTest {//BlockingQueue是java提供的一个同步阻塞式容器的实现
    static BlockingQueue<String> strs = new ArrayBlockingQueue<>(10); //有界队列，里面装的元素个数固定
    static Random r = new Random();

    public static void main(String[] args) {
        for(int i = 0; i < 10; i++){
            try {
                strs.put("a" + i);//满了就会等待
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //strs.add("aaa"); //有界队列这里不能用add添加，会报异常
        try {
            strs.offer("bbb", 2, TimeUnit.SECONDS); //按时间段阻塞，隔1秒钟之后添加
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //可以用offer和put
        System.out.println(strs);
    }
}
