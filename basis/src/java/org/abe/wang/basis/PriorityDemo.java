package org.abe.wang.basis;

/**
 * 线程优先级
 *
 * @Author Abe
 * @Date 2018/4/19.
 */
public class PriorityDemo {
    public static class HighPriority extends Thread {
        int count = 0;

        public void run() {
            while (true) {
                synchronized (PriorityDemo.class) {
                    count++;
                    if (count > 10000) {
                        System.out.println("HighPriority complete.");
                        break;
                    }
                }
            }
        }
    }

    public static class LowPriority extends Thread {
        int count = 0;

        public void run() {
            while (true) {
                synchronized (PriorityDemo.class) {
                    count++;
                    if (count > 10000) {
                        System.out.println("LowPriority complete.");
                        break;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        HighPriority highPriority = new HighPriority();
        LowPriority lowPriority = new LowPriority();
        highPriority.setPriority(Thread.MAX_PRIORITY);
        lowPriority.setPriority(Thread.MIN_PRIORITY);
        highPriority.start();
        lowPriority.start();
    }
}
