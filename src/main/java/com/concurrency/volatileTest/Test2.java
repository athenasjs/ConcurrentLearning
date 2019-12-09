package com.concurrency.volatileTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
/**
 * volatile保证可见性，但不保证原子性，保证其他线程读到最新的值，但是不保证写会的时候检查值
 * 因此本程序中count的值远小于预期
 * @author: sjs
 * @date: 2019-11-26 14:45:50
 * @description:
 **/
public class Test2 {
    /*volatile*/ int count = 0; //如果不加volatile，可以用synchronized保证可见性和原子性
    synchronized void m(){
        for(int i = 0; i < 10000; i++){
            count ++;
        }
    }

    public static void main(String[] args) {
        Test2 test2 = new Test2();
        List<Thread> list = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            list.add(new Thread(test2::m));
        }
        //forEach遍历集合，forEach是Iterable<T>中的方法，传入一个消费型函数式接口Consumer ，里面有一个void accept(T t)方法对集合元素操作
        list.forEach((o)-> o.start());
        list.forEach((o)->{
            try {
                o.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


        System.out.println(test2.count);
    }
}
