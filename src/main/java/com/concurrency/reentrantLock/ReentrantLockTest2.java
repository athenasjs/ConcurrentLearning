package com.concurrency.reentrantLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest2 {

    Lock lock = new ReentrantLock();
    void m1(){
        try{
            lock.lock();
            for(int i = 0; i < 10; i++){
                System.out.println("m1 " +i);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }finally{
            lock.unlock();
        }

    }

    /*
    lock与synchronized不同的一点在于
    使用tryLock进行尝试锁定，不管锁定与否，方法都将继续执行
    可以根据tryLock的返回值来判定是否锁定
    也可以指定tryLock的时间，由于tryLock(time)抛出异常，所以要注意unlock的处理，必须放到finally 中，可以根据返回值判断是否成功锁定

     */
    void m2(){
        boolean locked = false;
        try {
            locked = lock.tryLock(5, TimeUnit.SECONDS);
            if(locked){
                System.out.println("m2 locked...");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            if(locked){
                lock.unlock();
            }
        }

    }

    public static void main(String[] args) {
        ReentrantLockTest2 test = new ReentrantLockTest2();
        new Thread(test::m1).start();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(test::m2).start();
    }
}
