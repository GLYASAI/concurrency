package orgabewang.basis;

/**
 * suspend, resume的错误使用
 *
 * @Author Abe
 * @Date 2018/4/18.
 */
public class BadSuspend {
    private static final Object u = new Object();
    private static ChangeObjectThread t1 = new ChangeObjectThread("t1");
    private static ChangeObjectThread t2 = new ChangeObjectThread("t2");

    public static class ChangeObjectThread extends Thread {
        public ChangeObjectThread(String name) {
            super.setName(name);
        }

        @Override
        public void run() {
            synchronized (u) {
                System.out.println("in " + Thread.currentThread().getName());
                Thread.currentThread().suspend();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        t1.start();
        Thread.sleep(100);  // 为什么？？？
        t2.start();
        t1.resume();
        t2.resume();
        t1.join();
        t2.join();
    }
}
