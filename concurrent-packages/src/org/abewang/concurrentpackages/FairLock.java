package org.abewang.concurrentpackages;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 公平锁
 *
 * @Author Abe
 * @Date 2018/4/25.
 */
public class FairLock implements Runnable {
    private static ReentrantLock fairLock = new ReentrantLock(true);

    @Override
    public void run() {
        while (true) {
            try {
                fairLock.lock();
                System.out.println(Thread.currentThread().getName() + " 获得锁");
            } finally {
                fairLock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        FairLock r = new FairLock();
        Thread t1 = new Thread(r, "thread-1");
        Thread t2 = new Thread(r, "thread-2");
        t1.start();
        t2.start();
    }
}
