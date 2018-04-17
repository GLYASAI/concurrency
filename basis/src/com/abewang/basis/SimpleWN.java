package com.abewang.basis;

/**
 * wait, notify
 *
 * @Author Abe
 * @Date 2018/4/17.
 */
public class SimpleWN {
    private static final SimpleWN obj = new SimpleWN();

    public static class T1 extends Thread {
        @Override
        public void run() {
            System.out.println(System.currentTimeMillis() + ": T1 start!");
            synchronized (obj) {
                try {
                    System.out.println(System.currentTimeMillis() + ": T1 wait for object!");
                    obj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(System.currentTimeMillis() + ": T1 end!");
        }
    }

    public static class T2 extends Thread {
        @Override
        public void run() {
            System.out.println(System.currentTimeMillis() + ": T2 start! notify one thread");
            synchronized (obj) {
                obj.notify();
            }
            System.out.println(System.currentTimeMillis() + ": T2 end!");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new T1();
        Thread t2 = new T2();
        t1.start();
        Thread.sleep(100);
        t2.start();
    }
}
