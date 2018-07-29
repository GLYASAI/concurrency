package org.abewang.io.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author Abe
 * @Date 2018/7/29.
 */
public class EchoServer {
    private Selector selector;
    private ExecutorService pools = Executors.newCachedThreadPool();

    private static Map<Socket, Long> timeStat = new HashMap<>(10240);

    private void startServer() throws Exception {
        selector = SelectorProvider.provider().openSelector();
        ServerSocketChannel ssc = ServerSocketChannel.open();  // 服务器端的SocketChannel
        ssc.configureBlocking(false);  // 将socketChannel设置为非阻塞模式  TODO: 什么意思
        InetSocketAddress isa = new InetSocketAddress(InetAddress.getLocalHost(), 8000);
        ssc.socket().bind(isa);

        // 注册感兴趣的事件为Accept
        // SelectionKey一对selector代表和channel的对应关系
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        for (; ; ) {
            selector.select();  // 阻塞, 直到有数据
            Set readyKeys = selector.selectedKeys();
            Iterator iter = readyKeys.iterator();
            long e = 0;
            while (iter.hasNext()) {
                SelectionKey sk = (SelectionKey) iter.next();
                iter.remove();

                if (sk.isAcceptable()) {
                    doAccept(sk);  // 与客户端建立连接
                } else if (sk.isValid() && sk.isReadable()) {
                    timeStat.putIfAbsent(((SocketChannel) sk.channel()).socket(), System.currentTimeMillis());
                    doRead(sk);
                } else if (sk.isValid() && sk.isWritable()) {
                    doWrite(sk);
                    e = System.currentTimeMillis();
                    long b = timeStat.get(((SocketChannel) sk.channel()).socket());
                    System.out.println("spend: " + (e - b) + "ms");
                }
            }

        }
    }

    public void doAccept(SelectionKey sk) {
        ServerSocketChannel server = (ServerSocketChannel) sk.channel();
        SocketChannel clientChannel;
        try {
            clientChannel = server.accept();
            clientChannel.configureBlocking(false);

            // Register this channel for reading.
            SelectionKey clientKey = clientChannel.register(selector, SelectionKey.OP_READ);
            // Allocate an EchoClient instance and attach it to this selection key.
            EchoClient echoClient = new EchoClient();
            clientKey.attach(echoClient);

            InetAddress clientAddress = clientChannel.socket().getInetAddress();
            System.out.println("Accepted connection from " + clientAddress.getHostAddress() + ".");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void doRead(SelectionKey sk) {
        SocketChannel channel = (SocketChannel) sk.channel();  // 为什么不是ServerSocketChannel
        ByteBuffer bb = ByteBuffer.allocate(8196);
        int len;

        try {
            len = channel.read(bb);
            if (len < 0) {
//                disconnect(sk);
                sk.cancel();
                return;
            }
        } catch (IOException e) {
            System.out.println("Failed to read from client.");
            e.printStackTrace();
//            disconnect(sk);
            sk.cancel();
            return;
        }

        bb.flip();
        pools.execute(new HandleMsg(sk, bb));
    }

    public void doWrite(SelectionKey sk) {
        SocketChannel channel = (SocketChannel) sk.channel();
        EchoClient echoClient = (EchoClient) sk.attachment();
        LinkedList<ByteBuffer> outQ = echoClient.getOutputQueue();

        ByteBuffer bb = outQ.getLast();
        try {
            int len = channel.write(bb);
            if (len == -1) {
                sk.cancel();
                return;
            }

            if (bb.remaining() == 0) {
                // The buffer was completely written, remove it
                outQ.removeLast();
            }
        } catch (IOException e) {
            System.out.println("Failed to write to client");
            e.printStackTrace();
            sk.cancel();
        }

        if (outQ.size() == 0) {
            sk.interestOps(SelectionKey.OP_READ);
        }
    }

    class HandleMsg implements Runnable {
        SelectionKey sk;
        ByteBuffer bb;

        public HandleMsg(SelectionKey sk, ByteBuffer bb) {
            this.sk = sk;
            this.bb = bb;
        }

        @Override
        public void run() {
            EchoClient echoClient = (EchoClient) sk.attachment();
            echoClient.enqueue(bb);
            // 重新注册感兴趣的消息
            sk.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
            selector.wakeup();
        }
    }

    public static void main(String[] args) throws Exception {
        EchoServer echoServer = new EchoServer();
        echoServer.startServer();
    }
}
