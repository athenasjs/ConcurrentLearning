package com.concurrency.jucContainers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SynchronizedList {
    public static void main(String[] args) {
        List<String> strs = new ArrayList<>();
        List<String> strsSync = Collections.synchronizedList(strs);//返回一个加锁的容器
    }
}
