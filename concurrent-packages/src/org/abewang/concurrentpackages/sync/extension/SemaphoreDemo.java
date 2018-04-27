package org.abewang.concurrentpackages.sync.extension;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 信号量示例
 *
 * @Author Abe
 * @Date 2018/4/27.
 */
public class SemaphoreDemo implements Runnable {
    final Semaphore semaphore = new Semaphore(5);


    @Override
    public void run() {
        try {
            semaphore.acquire();
            // 模拟耗时操作
            Thread.sleep(5000);
            System.out.println(Thread.currentThread().getId() + ": done.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(20);
        SemaphoreDemo demo = new SemaphoreDemo();
        for (int i = 0; i < 20; i++) {
            pool.submit(demo);
        }
    }
}
