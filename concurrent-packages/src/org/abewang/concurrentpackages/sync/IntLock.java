package org.abewang.concurrentpackages.sync;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 中断响应
 *
 * @Author Abe
 * @Date 2018/4/25.
 */
public class IntLock implements Runnable {
    private static ReentrantLock lock1 = new ReentrantLock();
    private static ReentrantLock lock2 = new ReentrantLock();
    private int lock;

    /**
     * 控制加锁顺序，方便构造死锁
     *
     * @param lock
     */
    public IntLock(int lock) {
        this.lock = lock;
    }

    public void run() {
        try {
            if (lock == 1) {
                lock1.lockInterruptibly();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {}
                lock2.lockInterruptibly();
            } else {
                lock2.lockInterruptibly();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {}
                lock1.lockInterruptibly();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (lock1.isHeldByCurrentThread()) {
                lock1.unlock();
            }
            if (lock2.isHeldByCurrentThread()) {
                lock2.unlock();
            }
            System.out.println("线程" + Thread.currentThread().getId() + "结束");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new IntLock(1));
        Thread t2 = new Thread(new IntLock(2));
        t1.start();
        t2.start();
        Thread.sleep(1000);
        t2.interrupt();
    }
}
