package com.concurrency.syn;

import java.util.concurrent.TimeUnit;

/**
 *  业务写方法加锁，对业务读方法不加锁
 *  容易产生脏读问题（syn方法会被非同步方法打断，其他线程可以在本线程加锁期间调用非同步方法）
 *  实际业务要谨慎考虑，哪些业务方法需要加锁，可以允许部分脏读，性能会好很多
 * @author: sjs
 * @date: 2019-11-25 21:23:16
 * @description:
 **/
public class Account {

    private String name;
    private double balance;

    public synchronized void set(String name, double balance){
        this.name = name;
        //这个睡眠的过程模拟被其他业务代码打断的过程，脏读
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.balance = balance;
    }

    public double get(){
        return balance;
    }

    public static void main(String[] args) {
        Account account = new Account();
        new Thread(()-> account.set("zhangsan", 200)).start();
        System.out.println(account.get());
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(account.get());
    }

}
