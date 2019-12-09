package com.concurrency.threadLocal;

import java.util.concurrent.TimeUnit;


/*
ThreadLocal是空间换时间，每个线程保存自己的数据副本，synchronized是时间换空间
比如在hibernate中session就存在于ThreadLocal中，避免synchronized的使用
如果线程要保存自己的数据，不用通知其他线程可以用ThreadLocal
spring 等框架大量使用ThreadLocal，提高效率
 */
public class ThreadLocal1 {

    static ThreadLocal<Person> tl = new ThreadLocal<>();

    public static void main(String[] args) {

        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(tl.get());
        }).start();

        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tl.set(new Person());
        }).start();
    }
}


class Person{
    String name = "zjamhsam";
}