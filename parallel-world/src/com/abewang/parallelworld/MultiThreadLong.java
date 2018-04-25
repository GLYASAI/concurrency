package com.abewang.parallelworld;

/**
 * 在32位系统上，long（64位）型数据的读写不是原子性的
 *
 * @Author Abe wang
 * @Date 4/19/2018.
 */
public class MultiThreadLong {
    public static long t = 0;

    public static class ChangT implements Runnable {
        private long to;

        public ChangT(long to) {
            this.to = to;
        }

        @Override
        public void run() {
            while (true) {
                t = to;
                Thread.yield();
            }
        }
    }

    public static class ReadT implements Runnable {
        @Override
        public void run() {
            while (true) {
                long tmp = t;
                if (tmp != 111L && tmp != -999L && tmp != 333L && tmp != -444L) {
                    System.out.println(tmp);
                }
                Thread.yield();
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new ChangT(111L)).start();
        new Thread(new ChangT(-999L)).start();
        new Thread(new ChangT(333L)).start();
        new Thread(new ChangT(-444L)).start();
        new Thread(new ReadT());
    }
}
