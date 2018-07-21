package org.abewang.nio.socket;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.LockSupport;

/**
 * @Author Abe
 * @Date 2018/7/20.
 */
public class HeavySocketClient {
    private static ExecutorService pools = Executors.newFixedThreadPool(10);
    private static long sleepTime = 1000 * 1000 * 1000;  // 1 nanosecond = 1.0 × 10^(-9) seconds

    public static class EchoClient implements Runnable {
        @Override
        public void run() {
            PrintWriter writer = null;
            BufferedReader reader = null;
            try (Socket client = new Socket()) {
                client.connect(new InetSocketAddress("localhost", 8000));
                writer = new PrintWriter(client.getOutputStream(), true);
                writer.print("H");
                LockSupport.parkNanos(sleepTime);
                writer.print("e");
                LockSupport.parkNanos(sleepTime);
                writer.print("l");
                LockSupport.parkNanos(sleepTime);
                writer.print("l");
                LockSupport.parkNanos(sleepTime);
                writer.print("o");
                LockSupport.parkNanos(sleepTime);
                writer.print("!");
                LockSupport.parkNanos(sleepTime);
                writer.println();

                reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                System.out.println("from server: " + reader.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (writer != null) {
                        writer.close();
                    }
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        EchoClient ec = new EchoClient();
        for (int i = 0; i < 10; i++) {
            pools.execute(ec);
        }
    }
}
