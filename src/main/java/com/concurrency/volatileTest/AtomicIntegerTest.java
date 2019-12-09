package com.concurrency.volatileTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/*
解决同样的问题更高效的做法，使用AtomXXX类，本身方法都是原子性的，内部实现不是加锁，是用相当底层的方法，保证方法不会被其他线程打断，
但是不能保证多个方法连续调用是原子性的，由于是用相当底层的方法，效率比syn高很多
多个方法之间，不加锁的话还是可能被其他线程打断
 */
public class AtomicIntegerTest {
    AtomicInteger count = new AtomicInteger(0);
    void m(){
        for(int i = 0; i < 10000; i++){
            //if(count.get() < 1000)    //如果这两个方法一起，那就不保证原子性了，虽然get()和incrementAndGet都是原子性
            count.incrementAndGet();// 比如线程A加到999，被B打断，B加到1000，此时count还会被A加到1001，有原子性问题
        }

    }

    public static void main(String[] args) {
        AtomicIntegerTest test = new AtomicIntegerTest();
        List<Thread> list = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            list.add(new Thread(test::m));
        }
        list.forEach((o)-> o.start());
        list.forEach((o)-> {
            try {
                o.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(test.count.get());
    }
}
