package com.concurrency.container;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/*
进一步优化
运用wait 和notify的方法，必须保证t2先执行，首先让t2监听才可以
notify之后，t1必须释放锁，t2退出后，也必须notify，通知t1继续执行
整个通信过程比较繁琐

 */
public class MyContainer3 {

    volatile List<Integer> list = new ArrayList<>();
    void add(int i){
        list.add(i);
    }

    int size(){
        return list.size();
    }

    public static void main(String[] args) {
        final Object lock  = new Object();
        MyContainer3 con = new MyContainer3();
        new Thread(()->{
            synchronized (lock){
                System.out.println("t2启动");
                if(con.size() != 5){
                    try {
                        //释放锁
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("t2结束");
                //通知t1继续执行
                lock.notify();
            }
        }).start();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(()->{
            synchronized (lock){
                System.out.println("t1启动");
                for(int i = 0; i < 10; i++){
                    con.add(i);
                    System.out.println("t1 add "+i);
                    if(con.size() == 5){
                        //先唤醒t2，这个过程不会释放锁
                        lock.notify();
                        try {
                            //释放锁
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }
}
