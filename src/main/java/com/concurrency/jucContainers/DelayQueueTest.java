package com.concurrency.jucContainers;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/*
DelayQueue 容器中每一个元素维护一个可以被消费者拿出的时间
 */
public class DelayQueueTest {

    static BlockingQueue<Delayed> queue = new DelayQueue<>();//DelayQueue可以用来做执行定时任务

    static Random r = new Random();

    static class MyTask implements Delayed{ //任务继承Delayed接口，Delayed接口又是从Comparable接口继承的
        private long runningTime;

        public MyTask(long rt){
            this.runningTime = rt;
        }

        @Override
        public long getDelay(TimeUnit unit) {//返回值代表还有多长时间可以往外拿
            return unit.convert(runningTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            if(this.getDelay(TimeUnit.MILLISECONDS) < o.getDelay(TimeUnit.MILLISECONDS)){
                return -1;
            }else if(this.getDelay(TimeUnit.MILLISECONDS) > o.getDelay(TimeUnit.MILLISECONDS)){
                return 1;
            }
            return 0;
        }

        @Override
        public String toString() {

            return ""+runningTime;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        long now = System.currentTimeMillis();
        MyTask t1 = new MyTask(now + 1000);
        MyTask t2 = new MyTask(now + 2000);
        MyTask t3 = new MyTask(now + 1500);
        MyTask t4 = new MyTask(now + 2500);
        MyTask t5 = new MyTask(now + 500);

        queue.put(t1);
        queue.put(t2);
        queue.put(t3);
        queue.put(t4);
        queue.put(t5);

        System.out.println(queue);
        for(int i = 0; i < 5; i++){
            System.out.println(queue.take());
        }
    }

}
