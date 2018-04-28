package org.abewang.concurrentpackages.sync.extension;

import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport.park()还能支持中断影响
 *
 * @Author Abe
 * @Date 2018/4/29.
 */
public class LockSupportIntDemo {
    private static Thread t1 = new ChangeObjectThread("t1");
    private static Thread t2 = new ChangeObjectThread("t2");

    public static class ChangeObjectThread extends Thread {
        public ChangeObjectThread(String name) {
            super.setName(name);
        }

        public void run() {
            synchronized (this) {
                System.out.println("In " + Thread.currentThread().getName());
                LockSupport.park();
                if (Thread.interrupted()) {
                    System.out.println(Thread.currentThread().getName() + " 被中断了");
                }
                System.out.println(Thread.currentThread().getName() + " 执行结束");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        t1.start();
        Thread.sleep(100);
        t2.start();
        t1.interrupt();
        LockSupport.unpark(t2);
        t1.join();
        t2.join();
    }
}
