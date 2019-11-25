package com.concurrency.thread;

public class RunnableThreadTest implements Runnable{

    int i = 0;
    public void run() { //run方法执行完，线程消亡
        for(; i<100; i++){
            System.out.println(Thread.currentThread().getName() + " " + i);
        }
    }

    public static void main(String[] args) {
        for(int i = 0; i < 20; i++){
            System.out.println(Thread.currentThread().getName() + " " + i);
            if(10 == i){
                //开启一个线程
                new Thread(new RunnableThreadTest(), "新线程1").start();
                new Thread(new RunnableThreadTest(), "新线程2").start();
            }
        }
    }

}
