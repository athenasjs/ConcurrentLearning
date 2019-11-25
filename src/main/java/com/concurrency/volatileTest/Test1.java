package com.concurrency.volatileTest;

import java.util.concurrent.TimeUnit;

public class Test1 {
    private volatile boolean running = true;  //对比一下有无volatile得情况下，整个程序的运行区别
    public void m(){
        System.out.println("m start");
        while(running){

        }
        System.out.println("m end");
    }

    public static void main(String[] args) {
        Test1 test1 = new Test1();
        new Thread(test1::m).start();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        test1.running = false;
    }
}
