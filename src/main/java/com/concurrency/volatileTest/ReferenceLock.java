package com.concurrency.volatileTest;

import com.concurrency.syn.ReenterTest;

import java.util.concurrent.TimeUnit;

/*
synchronized锁的是堆内存中的对象，而不是栈内存中的引用
锁的信息是记录在堆内存中
 */
public class ReferenceLock {
    Object lock = new Object();
    void m(){
        synchronized(lock){
            while(true){

                System.out.println(Thread.currentThread().getName());
            }
        }
    }

    public static void main(String[] args) {
        ReferenceLock test = new ReferenceLock();
        new Thread(test::m, "Thread-1").start();
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread t = new Thread(test::m, "Thread-2");
        test.lock = new Object(); //锁对象发生改变，因此线程2得以执行
        t.start();
    }
}
