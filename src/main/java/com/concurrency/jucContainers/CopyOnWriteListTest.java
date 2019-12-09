package com.concurrency.jucContainers;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/*
写时复制容器 copyOnWrite
多线程环境下，写时效率低，读时效率高
适合写少读多的环境
 */
public class CopyOnWriteListTest {

    public static void main(String[] args) {
        List<String> list = new CopyOnWriteArrayList<>(); //写时复制
        Random r = new Random();
        Thread[] ths = new Thread[100];

        for(int i = 0; i < ths.length ;i++){
            Runnable task = new Runnable(){

                @Override
                public void run() {
                    for(int i = 0; i < 1000; i++){
                        list.add("a" + r.nextInt(100000));
                    }
                }
            };
        }

        runAndComputeTime(ths);
        System.out.println(list.size());

    }

    private static void runAndComputeTime(Thread[] ths) {
        long s1 = System.currentTimeMillis();
        Arrays.asList(ths).forEach(t->t.start());
        Arrays.asList(ths).forEach(t->{
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long s2  = System.currentTimeMillis();
        System.out.println(s2-s1);
    }
}
