package com.concurrency.singleton;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;

import java.util.Arrays;

/*
多线程安全的单例模式，静态内部类来实现，也可以实现懒加载，同时不用加锁
 */
public class Singleton {

    private Singleton(){
        System.out.println("single");
    }
    private static class Inner{
        private static Singleton instance = new Singleton();

    }
    public static Singleton getInstance(){
        //只有执行这句话的时候才会加载内部类，实现懒加载
        return Inner.instance;
    }

    public static void main(String[] args) {
        Thread[] ths = new Thread[200];
        for(int i = 0; i < ths.length ;i++){
            ths[i] = new Thread(()->{
                getInstance();
            });
        }
        Arrays.asList(ths).forEach((o)->{o.start();});
    }


}
