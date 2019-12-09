package com.concurrency.threadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ParallelStreamAPI {
    public static void main(String[] args) {
        List<Integer> nums = new ArrayList<>();
        Random random = new Random();
        for(int i = 0; i < 10000; i++){
            nums.add(1000000 + random.nextInt(1000000));
        }

        long start  = System.currentTimeMillis();
        nums.forEach(o-> isPrime(o));
        long end = System.currentTimeMillis();
        System.out.println(end - start);

        //使用Parallel stream api  1302 214 运行时间
        start = System.currentTimeMillis();
        nums.parallelStream().forEach(ParallelStreamAPI::isPrime);
        end = System.currentTimeMillis();
        System.out.println(end-start);
    }

    static boolean isPrime(int num){
        for(int i = 2; i<= num/2; i++){
            if(num % i==0){
                return false;
            }
        }
        return true;
    }
}
