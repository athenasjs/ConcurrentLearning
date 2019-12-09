package com.concurrency.reentrantLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest3 {

    public static void main(String[] args) {
        Lock lock = new ReentrantLock();

        Thread t1 = new Thread(()->{

            try {
                lock.lock();
                System.out.println("t1 start");
                TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
            } catch (InterruptedException e) {
                System.out.println("t1 is interrupted!!!");
            }finally{
                lock.unlock();
            }
        });
        t1.start();
        Thread t2 = new Thread(()->{

            try {
                lock.lockInterruptibly();  //lock.lockInterruptibly()可以对interrupt方法做出响应
                //而lock.lock()是不会被打断的，只能一直等待锁
                //可以无限时申请锁，但是可以被打断
                System.out.println("t2 start");
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                System.out.println("t2 is interrupted!!!");
            }finally{
                lock.unlock(); //这里t2的unlock可以用一个布尔型变量来做判断是否拿到锁，避免没拿到锁而unlock导致异常
            }

        });
        t2.start();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.interrupt();  //打断线程2的等待

    }
}
