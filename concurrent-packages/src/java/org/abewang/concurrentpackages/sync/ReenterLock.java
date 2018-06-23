package org.abewang.concurrentpackages.sync;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 简单的重用锁使用案例
 *
 * @Author Abe
 * @Date 2018/4/24.
 */
public class ReenterLock implements Runnable {
    private static ReentrantLock lock = new ReentrantLock();
    private static int i;

    @Override
    public void run() {
        for (int j = 0; j < 10000; j++) {
            try {
                lock.lock();
                i++;
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new ReenterLock());
        Thread t2 = new Thread(new ReenterLock());
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(i);
    }
}
