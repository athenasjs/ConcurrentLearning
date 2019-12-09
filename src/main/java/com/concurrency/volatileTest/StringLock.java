package com.concurrency.volatileTest;

/*
不要以字符串常量作为锁定对象，在下面的例子中，m1和m2其实锁定的是同一个对象
这种情况还会发生比较诡异的现象，比如你用到一个类库，在该类库中代码锁定了字符串Hello，
但是你读不到源码，所以你在自己代码中也锁定hello，这时候有可能发生死锁阻塞，代码跑不动，因为程序和类库使用了同一把锁
jetty曾经出现这样一个bug
 */
public class StringLock {
    String s1 = "hello";
    String s2 = "hello";
    void m1(){
        synchronized (s1){

        }
    }
    void m2(){
        synchronized (s2){

        }
    }
}
