package org.abewang.concurrentpackages.sync.extension;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 循环栅栏
 *
 * @Author Abe
 * @Date 2018/4/28.
 */
public class CyclicBarrierDemo {
    public static class Soldier implements Runnable {
        private final CyclicBarrier cyclic;
        private String soldier;

        public Soldier(CyclicBarrier cyclic, String soldier) {
            this.cyclic = cyclic;
            this.soldier = soldier;
        }

        @Override
        public void run() {
            try {
                cyclic.await();
                Thread.sleep(new Random().nextInt(10) * 1000);
                System.out.println(soldier + " : 任务完成");
                cyclic.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    public static class BarrierRun implements Runnable {
        private boolean flag;
        final int N;

        public BarrierRun(boolean flag, int n) {
            this.flag = flag;
            N = n;
        }

        @Override
        public void run() {
            if (flag) {
                System.out.println("司令: [士兵" + N + "个，任务完成！]");
            } else {
                System.out.println("司令: [士兵" + N + "个，集合完毕！]");
                flag = true;
            }
        }
    }

    public static void main(String[] args) {
        final int N = 10;
        CyclicBarrier cyclic = new CyclicBarrier(N, new BarrierRun(false, N));

        System.out.println("集合队伍！");
        for (int i = 0; i < N; i++) {
            System.out.println("士兵" + N + "报道");
            new Thread(new Soldier(cyclic, "士兵" + i)).start();
        }
    }
}
