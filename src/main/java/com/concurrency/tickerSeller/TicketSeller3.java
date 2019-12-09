package com.concurrency.tickerSeller;

import java.util.ArrayList;
import java.util.List;

public class TicketSeller3 {
    static List<String> list = new ArrayList<>();
    static{
        for(int i = 0; i < 1000; i++){
            list.add("票编号" + i);
        }
    }

    public static void main(String[] args) {

        for(int i = 0; i < 10; i++){
            new Thread(()->{
               while(true){
                   synchronized (list){  //加锁，把判断和销售加到一个原子操作中，一定不会出问题
                       if(list.size() <= 0){
                           break;
                       }
                       System.out.println("销售了" +list.remove(0));
                   }
               }
            }).start();
        }
    }
}
