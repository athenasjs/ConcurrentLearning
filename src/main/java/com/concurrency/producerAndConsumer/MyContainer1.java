package com.concurrency.producerAndConsumer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/*
写一个固定容量同步容器，拥有put和get方法，以及getCount方法
能够支持2个生产者线程以及10个消费者线程的阻塞调用
这里使用wait/notify实现
 */
public class MyContainer1<T>{

    final private LinkedList<T> list = new LinkedList<>();
    final private int MAX = 10;
    private int count = 0;

    public synchronized void put(T t){
        while(list.size() == MAX){  //这里为什么用while而不是用if
            //如果用If只判断一次，在被唤醒之后，在放入之前可能有另一个线程先放入一个，此时再放入就会出问题
            //因为放入的线程会先后拿到锁执行放入动作，用if的话后面的put动作不再进行判断，会继续执行
            //在effective java中，wait有99%的可能是和while一起使用，notifyAll可以叫醒所有生产者线程
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //醒了之后代码继续运行
        list.add(t);
        count++;
        this.notifyAll(); //通知消费者进行消费，这里不是用Notify，因为有可能又叫醒一个消费者，继续wait，程序执行不懂
    }

    public synchronized T get(){
        while(list.size() == 0){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        T t = list.removeFirst();
        count--;
        this.notifyAll(); //通知生产者进行生产
        return t;
    }

    public static void main(String[] args) {
        MyContainer1<String> con = new MyContainer1<>();
        //启动消费者线程
        for(int i =0; i < 10; i++){
            new Thread(()->{
                for(int j = 0; j < 5;j++){
                    System.out.println(Thread.currentThread().getName() +"消费了"+ con.get());
                }
            }, "消费者"+i).start();
        }

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //启动生产者线程
        for(int i = 0; i < 2;i++){
            new Thread(()->{
                for(int j = 0; j < 25; j++){
                    con.put(j+"");
                    System.out.println(Thread.currentThread().getName() +"生产了"+j);
                }
            }, "生产者"+i).start();
        }

    }


}
