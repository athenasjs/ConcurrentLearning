package com.concurrency.container;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/*
需求是实现一个容器，给出add和size方法，线程1加入元素，线程2监控个数，当到5个，结束监控
 */
public class Mycontainer1 {
    volatile boolean running = true;
    volatile List<Integer> list = new ArrayList<>();//添加volatile使得t2能够得到通知
    //但是这样的优化还是不妥，1是因为没有做同步，当线程2检测到size为5，线程1可能又加了1个，不精确；
    //2是线程2中的死循环太浪费CPU，一直在检测
    void add(int num){list.add(num);}
    int size(){
        return list.size();
    }

    public static void main(String[] args) {
        Mycontainer1 con = new Mycontainer1();

        new Thread(()->{
            for(int i = 0; i < 10; i++){
                if(con.running){

                    con.add(i);
                    System.out.println("当前容器元素个数：" + con.size());
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        new Thread(()->{
            while(true){

                if(con.size() == 5){
                    /*con.running = false;*/
                    System.out.println("t2结束");
                    return;
                }
            }
        }).start();
    }
}
