package com.concurrency.jucContainers;

import com.sun.jnlp.JNLPRandomAccessFileNSBImpl;

import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;

public class ConcurrentMapTest {
    public static void main(String[] args) {
        Map<String ,String> map = new ConcurrentHashMap<>() ; //如果追求高并发且需要排序，则使用ConcurrentSkipListMap 跳表数据解构

        Random random = new Random();
        Thread[] ths = new Thread[100];
        CountDownLatch latch = new CountDownLatch(100);
        long start = System.currentTimeMillis();
        for(int i = 0; i < ths.length ;i++){
            ths[i] = new Thread(()->{
               for(int j = 0; j < 10000; j++){
                   map.put("a" + random.nextInt(100000), "a" +random.nextInt(100000));

               }
               latch.countDown();
            });
        }
        Arrays.asList(ths).forEach((o)->o.start());
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println(end-start);

    }
}
