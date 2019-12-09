package com.concurrency.tickerSeller;

import java.util.Vector;
import java.util.concurrent.TimeUnit;

public class TicketSeller2 {
    static Vector<String> tickets = new Vector<>();//换成vector还是会出现重复销售
    //因为两个原子方法之间会被打断
    static{
        for(int i = 0; i < 1000; i++){
            tickets.add("票编号" + i);
        }
    }

    public static void main(String[] args) {
        for(int i = 0; i < 10; i++){
            new Thread(()->{
                while(tickets.size() > 0){
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("销售了" + tickets.remove(0));
                }
            }).start();
        }
    }
}
