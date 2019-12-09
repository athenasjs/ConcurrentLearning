package com.concurrency.reentrantLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
ReentrantLock用于替代synchronized
复习synchronized最原始的语义
使用ReentrantLock可以完成同样的功能
需要注意的是，必须手动释放锁
使用syn锁定的话如果遇到异常，JVM会自动释放锁，但是lock必须手动释放，因此经常在finally中进行锁的释放
手动锁定，手动释放
互斥锁
 */
public class ReentrantLockTest1 {

    Lock lock = new ReentrantLock();

    void m1(){

        try {
            lock.lock();
            for(int i = 0; i < 10; i++){
                TimeUnit.SECONDS.sleep(1);
                System.out.println(i);
            }
            }catch (InterruptedException e) {
            e.printStackTrace();
            }finally{
            //在finally中手动释放锁
                lock.unlock();

            }
    }

    void m2(){
        //这两个方法互斥
        lock.lock();
        System.out.println("m2 start...");
        lock.unlock();

    }

    public static void main(String[] args) {
        ReentrantLockTest1 test = new ReentrantLockTest1();
        new Thread(test::m1, "Thread-1").start();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(test::m2, "Thread-2").start();


    }


}
