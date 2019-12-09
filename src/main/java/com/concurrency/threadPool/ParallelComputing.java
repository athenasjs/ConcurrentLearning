package com.concurrency.threadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ParallelComputing {

    static class MyTask implements Callable<List<Integer>>{

        int startPos, endPos;
        public MyTask(int startPos, int endPos){
            this.startPos = startPos;
            this.endPos = endPos;
        }
        @Override
        public List<Integer> call() throws Exception {

            return getPrime(startPos, endPos);
        }
    }


    static boolean isPrime(int num){
        for(int i = 2; i <= num/2 ; i++){
            if(num % i == 0){
                return false;
            }
        }
        return true;
    }

    static List<Integer> getPrime(int start, int end){
        List<Integer> list = new ArrayList<>();
        for(int i = start; i <= end; i++){
            if(isPrime(i)){
                list.add(i);
            }
        }
        return list;
    }

    //线程池作并行计算
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long start1 = System.currentTimeMillis();
        getPrime(1,200000);
        long end1 = System.currentTimeMillis();
        System.out.println("单线程执行时间:" + (end1-start1));

        ExecutorService service = Executors.newFixedThreadPool(4);
        MyTask task1 = new MyTask(1, 50000);
        MyTask task2 = new MyTask(50001, 80000);
        MyTask task3 = new MyTask(80001, 130000);
        MyTask task4 = new MyTask(130001, 200000);
        Future<List<Integer>> f1 = service.submit(task1);
        Future<List<Integer>> f2 = service.submit(task2);
        Future<List<Integer>> f3 = service.submit(task3);
        Future<List<Integer>> f4 = service.submit(task4);
        long start2 = System.currentTimeMillis();
        f1.get(); //在这里阻塞
        f2.get();
        f3.get();
        f4.get();
        long end2 = System.currentTimeMillis();
        System.out.println("线程池运行时间:" + (end2-start2));
    }
}
