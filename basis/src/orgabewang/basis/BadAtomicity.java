package orgabewang.basis;

import java.util.ArrayList;
import java.util.List;

/**
 * volatile无法保证复合操作的原子性
 *
 * @Author Abe
 * @Date 2018/4/18.
 */
public class BadAtomicity {
    public static volatile int i;

    public static class PlusTask implements Runnable {
        @Override
        public void run() {
            for (int j = 0; j < 10000; j++) {
                i++;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        List<Thread> threadList = new ArrayList<>();

        for (int j = 0; j < 10; j++) {
            Thread thread = new Thread(new PlusTask());
            threadList.add(thread);
            thread.start();
        }

        for (Thread thread : threadList) {
            thread.join();  // 等待threadList中的线程跑完
        }


        System.out.println(i);  // i小于100000，说明volatile无法保证i++的原子性
    }
}