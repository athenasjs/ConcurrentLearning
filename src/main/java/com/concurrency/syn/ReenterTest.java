package com.concurrency.syn;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

/**
 * 一个同步方法可以调用另外一个同步方法，一个线程已经拥有某个对象的锁，再次申请的时候仍然会得到该对象的锁，也就是说synchronized获得锁是可重入的
 * @author: sjs
 * @date: 2019-11-25 21:38:16
 * @description:
 **/
public class ReenterTest {

    public synchronized void m1(){
        System.out.println("m1 start...");
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        m2();
        System.out.println("m1 end ...");
    }

    public synchronized void m2(){
        System.out.println("m2 start...");
        System.out.println("m2 end...");
    }

    public static void main(String[] args) {
        ReenterTest test = new ReenterTest();
        new Thread(()->test.m1()).start();
    }
}
