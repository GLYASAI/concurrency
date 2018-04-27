package org.abewang.concurrentpackages.sync.extension;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CountDownLatch倒计时器
 *
 * @Author Abe
 * @Date 2018/4/28.
 */
public class CountDownLatchDemo implements Runnable {
    private final static CountDownLatch end = new CountDownLatch(10);
    private final static CountDownLatchDemo demo = new CountDownLatchDemo();

    @Override
    public void run() {
        try {
            Thread.sleep((long) (Math.random() * 3000));
            System.out.println("check complete");
            end.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            exec.submit(demo);
        }
        end.await();
        System.out.println("Fire...");
        exec.shutdown();
    }
}
