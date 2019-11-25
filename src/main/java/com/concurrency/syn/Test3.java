package com.concurrency.syn;

public class Test3 {

    public synchronized void m1(){
        System.out.println(Thread.currentThread().getName() + "m1.start...");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "m1.end...");

    }

    public synchronized void m2(){
        System.out.println(Thread.currentThread().getName() + "m2.start...");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "m2.end...");
    }

    public static void main(String[] args) {
        Test3 test3 = new Test3();
        new Thread(()->test3.m1(), "t1").start();
        new Thread(()->test3.m2(), "t2").start();
    }
}
