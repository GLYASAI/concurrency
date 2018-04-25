package orgabewang.basis;

/**
 * 守护线程
 *
 * @Author Abe
 * @Date 2018/4/19.
 */
public class DaemonT implements Runnable {
    @Override
    public void run() {
        while (true) {
            System.out.println("I am alive.");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread dt = new Thread(new DaemonT());
        dt.setName("Daemon Thread");
        // 设置为守护线程后, dt会在main线程结合后结束
        // 否则会一直执行下去
        dt.setDaemon(true);
        dt.start();

        Thread.sleep(2000);
    }
}
