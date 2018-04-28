package org.abewang.concurrentpackages.sync.extension;

import java.util.concurrent.locks.LockSupport;

/**
 * 线程阻塞工具类
 *
 * @Author Abe
 * @Date 2018/4/28.
 */
public class LockSupportDemo {
    private static Object u = new Object();
    private final static Thread t1 = new Foo("t1");
    private final static Thread t2 = new Foo("t2");

    public static class Foo extends Thread {
        public Foo(String name) {
            super.setName(name);
        }

        @Override
        public void run() {
            synchronized (u) {
                System.out.println("in " + Thread.currentThread().getName());
                LockSupport.park();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        t1.start();
        Thread.sleep(100);
        t2.start();
//        LockSupport.unpark(t1);
//        LockSupport.unpark(t2);
        t1.join();
        t2.join();
    }
}
