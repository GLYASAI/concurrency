package com.abewang.basis;

/**
 * 线程中断
 *
 * @Author Abe
 * @Date 2018/4/17.
 */
public class InterruptThread {
    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Thread interrupt.");
                    break;
                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    System.out.println("InterruptedException");
                    // Thread.sleep()由于中断而抛出异常，清除中断标记
                    // 重新设置中断状态
                    Thread.currentThread().interrupt();
                }
            }
        });

        t.start();
        try {
            Thread.sleep(80);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.interrupt();
    }
}
