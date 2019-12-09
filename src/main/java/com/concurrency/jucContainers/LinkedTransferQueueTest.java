package com.concurrency.jucContainers;

import java.util.concurrent.LinkedTransferQueue;

/*
LinkedTransferQueue 这种队列主要的应用场景是消息直接丢给消费者，不添加到容器
transfer方法，如果当前没有消费者，会直接阻塞，适用于实时消息处理，不处理就阻塞（netty中用一些）
 */
public class LinkedTransferQueueTest {
    public static void main(String[] args) {
        LinkedTransferQueue<String> strs = new LinkedTransferQueue<>();
        new Thread(()->{
            try {
                System.out.println(strs.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        try {
            strs.transfer("aaa"); //需要先启动消费者，消息直接处理，如果只有生产者，那该方法会阻塞
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
