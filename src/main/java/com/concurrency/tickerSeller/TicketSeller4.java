package com.concurrency.tickerSeller;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TicketSeller4 {
    //用并发容器ConcurrentLinkedQueue实现
    static Queue<String> tickets = new ConcurrentLinkedQueue<>();
    static {
        for(int i = 0; i < 1000; i++){
            tickets.add("票编号" +i);
        }
    }

    public static void main(String[] args) {
        for(int i = 0; i < 10; i++){
            new Thread(()->{
                while(true){
                    String s = tickets.poll(); //这里也没有加锁，也有判断和出售
                    //
                    if(s == null){//做了判断之后再也没有对容器有任何修改操作
                        //不用加锁效率高很多
                        //poll底层实现是CAS，不是加锁实现，效率高很多
                        break;
                    }else{
                        System.out.println("卖出了" + s);
                    }
                }
            }).start();
        }
    }
}
