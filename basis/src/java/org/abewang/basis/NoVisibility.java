package org.abewang.basis;

/**
 * volatile保证数据的可见性
 *
 * @Author Abe
 * @Date 2018/4/18.
 */
public class NoVisibility {
    public static int number;
    public static volatile boolean ready;

    public static class ReaderThread extends Thread {

        public void run() {
            while (!ready);
            System.out.println(number);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread readerThread = new ReaderThread();
        readerThread.setName("ReaderThread");
        readerThread.start();
        Thread.sleep(1000);  // ?? 为什么加了sleep，ReaderThread就看不到ready的变化(没加volatile的前提)
        number = 300;
        ready = true;
    }
}
