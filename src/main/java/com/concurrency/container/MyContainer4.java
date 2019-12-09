package com.concurrency.container;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/*
最优方法，使用Latch(门栓)替代wait notify来进行通知
好处是通信方式简单，同时也可以指定等待时间
使用await和countdown方法替代wait和notify
CountDownLatch不涉及锁定，当count的值为0时当前线程继续运行
当不涉及同步，只是涉及线程通信的时候，用synchronized+wait/notify就显得太重了
这时应该考虑countdownlatch/cyclibarrier/semaphore(信号量)
属性加volatile，线程更新该属性之后会通知其他线程更新CPU中的缓存值，CPU中保存的是对象具体的属性数据
 */
public class MyContainer4 {
    volatile List<Integer> list = new ArrayList<>();
    void add(int i){
        list.add(i);
    }

    int size(){
        return list.size();
    }

    public static void main(String[] args) {
        MyContainer4 con = new MyContainer4();
        //创建一个门栓对象 当count减少到0，则门栓打开，锁住的线程得以继续执行，不影响其他线程
        //并发度比较高
        CountDownLatch latch = new CountDownLatch(1);

        new Thread(()->{
            System.out.println("t2 start...");
            if(con.size() != 5){
                try {
                    //锁住，也可以指定时间
                    latch.await();
//                    latch.await(20, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("t2 end...");
        }).start();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(()->{
            System.out.println("t1 start");
            for(int i = 0; i < 10; i++){
                con.add(i);
                System.out.println("t1 add" + i);
                if(con.size() == 5){
                    //减少计数到0，门栓打开，线程2继续运行
                    //线程1也继续运行
                    latch.countDown();

                }
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            System.out.println("t1 end...");
        }).start();
    }

}
