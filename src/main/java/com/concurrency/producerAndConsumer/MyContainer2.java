package com.concurrency.producerAndConsumer;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
使用lock Condition await signalAll来完成
精确指定生产者线程/消费者线程全部等待或者唤醒
 */
public class MyContainer2<T> {
    private LinkedList<T> list = new LinkedList<>();
    private Lock lock = new ReentrantLock();
    private Condition producer = lock.newCondition();
    private Condition consumer = lock.newCondition();
    private int count = 0;
    private final int MAX = 10;

    public void put(T t){
        lock.lock();

            try {
                while(list.size() == MAX){
                producer.await();}
                list.add(t);
                count++;
                consumer.signalAll();  //通知消费者线程进行消费,可以精确通知那些线程被叫醒，哪些不被叫醒

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finally{
                lock.unlock();
        }
    }

    public T get(){
        T t = null;
        lock.lock();
        try{
            while(list.size() == 0){
                consumer.await();
            }
            t = list.removeFirst();
            count--;
            producer.signalAll();  //通知生产者进行生产

        }catch(InterruptedException e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
        return t;
    }


    public static void main(String[] args) {
        MyContainer2<String> con = new MyContainer2<>();
        for(int i = 0; i < 10; i++){
            new Thread(()->{
                for(int j = 0; j < 5; j++){
                    System.out.println(Thread.currentThread().getName() + "消费了" + con.get());
                }
            }, "消费者" + i).start();
        }

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < 2; i++){
            new Thread(()->{
                for(int j = 0; j < 25; j++){
                    con.put(j+"");
                    System.out.println(Thread.currentThread().getName() + "生产了" + j);
                }
            }, "生产者" + i).start();
        }

    }



}
