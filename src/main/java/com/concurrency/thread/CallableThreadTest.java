package com.concurrency.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CallableThreadTest  implements Callable<Integer>{


    /*
    创建线程三种方式对比：
    1.采用实现Runnable\Callable接口方式创建多线程，
    优势：线程类还可以继承其他类，多个线程可以共享同一个target对象，非常适合多个相同线程处理同一份资源的情况
    从而可以将CPU、代码和数据分开，形成清晰的模型，较好体现面向对象的思想
    劣势：编程略复杂，如果要访问当前线程，必须使用Thread.currentThread()方法
    2.继承Thread类方式创建线程，
    优势：编写简单，this就可获得当前线程
    劣势：不能继承其他父类
    3.Runnable和Callable区别
    （1）Callable规定重写方法是call(),Runnable规定重写方法run()
     (2)Callable任务执行后可以返回值，而Runnable的任务是不能有返回值的
     (3)call方法可以抛出异常，而run方法不可以
     (4)运行Callable任务可以拿到一个Future对象，表示异步计算的结果，提供了检查计算是否完成的方法，以等待计算的完成，并检索计算的结果，
     通过Future对象可以了解任务执行情况，可取消任务的执行，还可以获取执行结果
     */
    public Integer call() throws Exception {
        int i = 0;
        for(; i < 100; i++){
            System.out.println(Thread.currentThread().getName() + " " + i);

        }
        return i;
    }

    public static void main(String[] args) {
        CallableThreadTest ctt = new CallableThreadTest();
        FutureTask<Integer> task = new FutureTask<Integer>(ctt);
        for(int i = 0; i < 20; i++){
            System.out.println(Thread.currentThread().getName() + " " + i);
            if(10 == i){
                new Thread(task, "有返回值的线程").start();
            }
        }

        try {
            //task.cancel(true);
            System.out.println("子线程的返回值" + task.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}
