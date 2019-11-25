package com.concurrency.syn;

/**
 * synchronized关键字 对某个对象加锁
 * @author: sjs
 * @date: 2019-11-25 20:13:20
 * @description:
 **/
public class Test1 {

    private volatile int count = 10;
    private Object lock = new Object();
    public void m(){
        while(count > 0){
            synchronized(this){ //任何线程要执行下面的代码，必须先拿到lock的锁
                if(count > 0){
                    count -- ;
                    System.out.println(Thread.currentThread().getName() + " count = " + count);
                }

            }
        }

    }
    //等同于synchronized(this) 这段代码从一开始就锁定自身，锁定的是this对象
    public synchronized void m1(){

    }
    //静态方法锁定的是class对象
    public static synchronized void m2(){

    }

    public static void main(String[] args) {
        Test1 test1 = new Test1();
        new Thread(test1::m, "线程1").start();
        new Thread(test1::m, "线程2").start();
        new Thread(test1::m, "线程3").start();
    }
}
