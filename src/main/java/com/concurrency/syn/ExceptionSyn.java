package com.concurrency.syn;

import java.util.concurrent.TimeUnit;

/**
 * 程序在执行过程中，如果出现异常，默认情况锁会被释放，所以在并发处理的过程中，有异常要多加小心，不然可能会发生不一致的情况，
 * 比如在一个web app处理过程中，多个servlet线程共同访问同一个资源，这时如果异常处理不合适，在第一个线程中抛出异常，锁会被释放，其他线程就会进入同步代码区，有可能访问到异常产生时的数据
 * 如果不想释放这把锁，那就加上try/catch
 * @author: sjs
 * @date: 2019-11-25 22:09:39 
 * @description:
 **/
public class ExceptionSyn {

    private int count = 0;
    public synchronized void m(){
        while(true){
            System.out.println(Thread.currentThread().getName() + "-count = " + count++  );
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(count == 5){
                int k = 1/0;
            }
        }
    }

    public static void main(String[] args) {
        ExceptionSyn syn  = new ExceptionSyn();
        new Thread(()->syn.m()).start();
        new Thread(()->syn.m()).start();

    }
}
