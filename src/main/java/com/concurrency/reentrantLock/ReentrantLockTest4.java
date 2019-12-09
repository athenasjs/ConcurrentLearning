package com.concurrency.reentrantLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
ReentrantLock还可以指定为公平锁
而synchronized是非公平锁，哪个等待线程获得锁是不确定的，不会因为等待时间长而获得锁


 */
public class ReentrantLockTest4 implements Runnable{

    private static Lock lock = new ReentrantLock(true); //参数true指定公平锁，公平锁效率比较低，但是公平

    @Override
    public void run() {
        for(int i = 0; i < 100; i++){

            lock.lock();
            try{

                System.out.println(Thread.currentThread().getName() + "获得锁");
                //这里如果指定公平锁，两个线程会依次拿到锁，因为另一个线程是等待时间更久的
            }finally{

                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        ReentrantLockTest4 test = new ReentrantLockTest4();
        new Thread(test, "Thread-1").start();
        new Thread(test, "Thread-2").start();


    }


}
