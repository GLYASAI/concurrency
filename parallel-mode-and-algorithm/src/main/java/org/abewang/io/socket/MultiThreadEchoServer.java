package org.abewang.io.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author Abe
 * @Date 2018/7/19.
 */
public class MultiThreadEchoServer {
    public static ExecutorService pools = Executors.newCachedThreadPool();

    static class HandleMsg implements Runnable {
        Socket socket;

        public HandleMsg(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter os = new PrintWriter(socket.getOutputStream(), true)) {
                String line;
                long b = System.currentTimeMillis();
                while ((line = is.readLine()) != null) {
                    System.out.println(line);
                    os.println(line);
                }
                long e = System.currentTimeMillis();
                System.out.println("spend: " + (e - b) + "ms");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8000);

        while (true) {
            Socket client = server.accept();
            System.out.println(client.getRemoteSocketAddress() + " connect!");
            pools.execute(new HandleMsg(client));
        }
    }
}
