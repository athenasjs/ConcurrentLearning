package com.concurrency.threadPool;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

//Executor接口是java.uti.concurrent包下的顶层接口，只有一个execute(Runnable)方法，执行任务
public class ExecutorTest implements Executor {

    public static void main(String[] args) {
        new ExecutorTest().execute(()->{
            System.out.println(Thread.currentThread().getName() + "starts...");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "ends...");
        });
        System.out.println("main ends...");
    }
    @Override
    public void execute(Runnable command) {
        new Thread(command).start(); //如果直接调用run()方法那就是简单的方法调用
    }
}
