package org.abe.wang.basis;

/**
 * join()方法的使用
 *
 * @Author Abe
 * @Date 2018/4/18.
 */
public class JoinMain {
    public static int i;

    public static class AddThread extends Thread {
        public void run() {
            for (int j = 0; j < 1000; j++) {
                i++;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread addThread = new AddThread();
        addThread.start();
        addThread.join();  // 主线程会等待addThread执行完了再继续执行，如果没有join，i的值会很小
        System.out.println(i);
    }
}
