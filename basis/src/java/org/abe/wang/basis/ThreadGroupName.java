package org.abe.wang.basis;

/**
 * 线程组
 *
 * @Author Abe
 * @Date 2018/4/19.
 */
public class ThreadGroupName implements Runnable {
    public void run() {
        String groupName = Thread.currentThread().getThreadGroup().getName();
        String name = Thread.currentThread().getName();
        System.out.println("I am " + groupName + "-" + name);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadGroup tg = new ThreadGroup("Java并发程序设计");
        Thread foo = new Thread(tg, new ThreadGroupName(), "foo");
        Thread bar = new Thread(tg, new ThreadGroupName(), "bar");
        foo.start();
        bar.start();
        System.out.println(tg.activeCount());
        tg.list();
    }
}
