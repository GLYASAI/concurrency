package org.abewang.concurrentpackages.sync.extension;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 简单地演示Condition的功能
 *
 * @Author Abe
 * @Date 2018/4/27.
 */
public class ReentrantLockCondition implements Runnable {
    private static ReentrantLock lock = new ReentrantLock();
    private static Condition condition  = lock.newCondition();

    @Override
    public void run() {
        try {
            lock.lock();
            try {
                condition.await();  // 需要线程持有相关的重入锁
                System.out.println("test");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("going on...");
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new ReentrantLockCondition());
        t.start();
        Thread.sleep(1000);
//        lock.lock();
        condition.signal();  // 需要线程持有相关的重入锁
//        lock.unlock();  // 释放锁，谦让给被唤醒的线程
    }
}
