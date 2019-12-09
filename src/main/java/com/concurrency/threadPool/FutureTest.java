package com.concurrency.threadPool;

import java.util.concurrent.*;

public class FutureTest {
    //Future<>接口 里包含get()方法拿到未来任务执行完成的返回值
    //FutureTask类实现了改接口和Runnable接口
    //Future的简单用法 拿到未来的一个返回值
    public static void main(String[] args) {
        //FutureTask(Callable<Integer>)
        FutureTask<Integer> task = new FutureTask<>(()->{
            TimeUnit.SECONDS.sleep(3);
            return 1000;
        });
        new Thread(task).start();
        try {
            System.out.println(task.get());  //阻塞
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        ExecutorService service = Executors.newFixedThreadPool(5);
        Future<Integer> future = service.submit(()->{ //submit方法可以直接往里扔一个Callable
            TimeUnit.SECONDS.sleep(5);
            return 1000;
        });
        System.out.println(future.isDone());
        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(future.isDone()); //任务执行完没
        service.shutdown();
    }

}
