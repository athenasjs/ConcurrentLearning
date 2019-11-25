package com.concurrency.syn;

public class Test2 implements Runnable{

    private int count = 10;
    @Override
    public synchronized void run() { //这两句话执行过程加把锁，执行代码块不可能被打断，原子操作不可分
        count --;
        System.out.println(Thread.currentThread().getName() + "count= " + count);
    }

    public static void main(String[] args) {
        Runnable runnable = new Test2();
        for(int i = 0; i <5;i++){
            new Thread(runnable, "thread" + i).start();
        }
    }
}
