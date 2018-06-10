package org.abewang.concurrentpackages.sync.extension;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReadWriteLock读写锁
 *
 * @Author Abe
 * @Date 2018/4/27.
 */
public class ReadWriteLockDemo {
    private static ReentrantLock lock = new ReentrantLock();
    private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private static ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
    private static ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();

    private static int value;

    /**
     * 模拟读操作
     */
    public static int handleRead(Lock lock) {
        try {
            lock.lock();
            try {
                // 耗时越长，读写锁的高效率越明显
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            lock.unlock();
        }

        return value;
    }

    /**
     * 模拟写操作
     */
    public static int handleWrite(Lock lock, int value) {
        try {
            lock.lock();
            try {
                ReadWriteLockDemo.value = value;
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            lock.unlock();
        }

        return ReadWriteLockDemo.value;
    }

    public static class ReadLock implements Runnable {
        @Override
        public void run() {
            handleRead(readLock);
//            handleRead(lock);
        }
    }

    public static class WriteLock implements Runnable {
        @Override
        public void run() {
            handleWrite(writeLock, new Random().nextInt());
//            handleWrite(lock, new Random().nextInt());
        }
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < 18; i++) {
            Thread t = new Thread(new ReadLock());
            threadList.add(t);
            t.start();
        }

        for (int i = 18; i < 20; i++) {
            Thread t = new Thread(new WriteLock());
            threadList.add(t);
            t.start();
        }

        for (Thread thread : threadList) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        System.out.println("Time: " + (System.currentTimeMillis() - start));
    }
}
