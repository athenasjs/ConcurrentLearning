package com.concurrency.syn;

/**
 * 重入锁的另外一种情形，子类的同步方法调用父类的同步方法，也是可以获得锁的
 * @author: Lenovo
 * @date: 2019-11-25 21:56:51
 * @description:
 **/
public class ReenterTest2 {

    public synchronized  void m(){
        System.out.println("ReenterTest2.m");
    }

    public static void main(String[] args) {
        TT t = new TT();
        new Thread(()->t.m()).start();
    }

}

class TT extends ReenterTest2{
    @Override
    public synchronized void m() {
        System.out.println("TT.m");
        super.m();

    }

}