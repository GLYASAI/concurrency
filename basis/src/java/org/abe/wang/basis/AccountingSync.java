package org.abe.wang.basis;

/**
 * @Author Abe
 * @Date 2018/4/19.
 */
public class AccountingSync implements Runnable {
    private static AccountingSync instance = new AccountingSync();
    private static int i;

    public void run() {
        for (int j = 0; j < 10000000; j++) {
            synchronized (instance) {
                i++;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(instance);
        t1.setName("t1");
        Thread t2 = new Thread(instance);
        t1.setName("t2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println(i);
    }
}
