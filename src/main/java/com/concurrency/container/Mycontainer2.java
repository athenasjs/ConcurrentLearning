package com.concurrency.container;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/*
进一步优化 wait notify  只有wait方法释放锁，sleep和notify不释放锁
不能指定notify的唤醒目标线程，由CPU来管理
 */
public class Mycontainer2 {

    List<Integer> list = new ArrayList<>();
    void add(int i){
        list.add(i);
    }

    int size(){
        return list.size();
    }

    public static void main(String[] args) {

        Mycontainer2 con = new Mycontainer2();
        Object lock = new Object();
        Thread t1 = new Thread(()->{
            synchronized(lock){
                System.out.println("t1 start...");
                for(int i = 0; i < 10; i++){
                    con.add(i);
                    System.out.println("t1 add" + i);
                    if(con.size() == 5){
                        lock.notify();
                    }
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        });

        Thread t2 = new Thread(()->{

            System.out.println("t2 start...");
            if(con.size() != 5){
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("t2 end...");
        });
        t1.start();
        t2.start();

    }
}
