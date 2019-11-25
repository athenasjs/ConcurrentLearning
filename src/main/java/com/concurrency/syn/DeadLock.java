package com.concurrency.syn;

import java.util.concurrent.TimeUnit;
//死锁情况
//实际项目中的死锁： 线程1锁定A等着B，线程2锁定B等着C，线程3锁定C等着A，循环死锁
public class DeadLock {

    private Object lock1 = new Object();
    private Object lock2 = new Object();

    public void m1(){
        synchronized (lock1){
            System.out.println(Thread.currentThread().getName() + " m1.start");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lock2){

            }
        }
    }

    public void m2(){
        synchronized (lock2){
            System.out.println(Thread.currentThread().getName() + " m2.start");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lock1){

            }
        }
    }

    public static void main(String[] args) {
        DeadLock d = new DeadLock();
        new Thread(()-> d.m1()).start();
        new Thread(()-> d.m2()).start();
    }
}
