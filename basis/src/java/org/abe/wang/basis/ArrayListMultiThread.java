package org.abe.wang.basis;

import java.util.ArrayList;

/**
 * @Author Abe
 * @Date 2018/4/19.
 */
public class ArrayListMultiThread implements Runnable {
    private static ArrayList<Integer> list = new ArrayList<>();

    @Override
    public void run() {
        for (int i = 0; i < 1000000; i++) {
            list.add(i);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new ArrayListMultiThread());
        Thread t2 = new Thread(new ArrayListMultiThread());
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println(list.size());
    }
}
