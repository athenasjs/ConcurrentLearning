package com.concurrency.threadPool;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class ForkJoinPoolTest {

    static int[] nums = new int[1000000];
    static final int MAX_NUM = 50000;
    static Random random = new Random();

    static{
        for(int i = 0; i < nums.length ;i++){
            nums[i] = random.nextInt(100);
        }
        System.out.println(Arrays.stream(nums).sum());  //stream api

    }

    static class AddTask extends RecursiveTask<Long> {  //继承RecursiveAction类的方式是没有返回值的
        //RecursiveTask是有返回值的
        static int sum = 0;
        int start, end;

        public AddTask(int start, int end){
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            if(end - start <= MAX_NUM){
                long sum = 0L;
                for(int i = start; i < end; i++){
                    sum += nums[i];
                }
                //System.out.println("from" + start + "to" + end + "=" + sum );
                return sum;
            }else{
                int middle = start + (end-start)/2;
                AddTask task1 = new AddTask(start, middle);
                AddTask task2 = new AddTask(middle , end);
                task1.fork(); //开新线程执行
                task2.fork();
                return task1.join() + task2.join(); //合并结果，总的任务完成
                //task1.fork();  //fork表示分叉，任务拆分
                //task2.fork();
            }

        }
    }

    public static void main(String[] args) throws IOException {
        ForkJoinPool service = new ForkJoinPool();
        AddTask task = new AddTask(0, nums.length);
        service.execute(task);
        System.out.println(task.join()); //join本身就是阻塞的
        System.in.read();
    }



}
