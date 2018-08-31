package org.abewang.io.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;

/**
 * @Author Abe
 * @Date 2018/7/29.
 */
public class EchoClient {
    private Selector selector;

    public void init(String ip, int port) throws IOException {
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);
        this.selector = SelectorProvider.provider().openSelector();
        channel.connect(new InetSocketAddress(ip, port));
        channel.register(selector, SelectionKey.OP_CONNECT);
    }

    public void working() throws IOException {
        while (true) {
            if (!selector.isOpen()) {
                break;
            }
            selector.select();
            Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                iter.remove();
                // 连接事件发生
                if (key.isConnectable()) {
                    connect(key);
                } else {
                    read(key);
                }
            }
        }
    }

    public void connect(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        // 如果正在连接, 则完成连接
        if (channel.isConnectionPending()) {
            channel.finishConnect();
        }
        channel.configureBlocking(false);
        channel.write(ByteBuffer.wrap("hello server\r\n".getBytes()));
        channel.register(selector, SelectionKey.OP_READ);
    }

    public void read(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        // 创建读取的缓冲区
        ByteBuffer bb = ByteBuffer.allocate(100);
        channel.read(bb);
        byte[] data = bb.array();
        String msg = new String(data).trim();
        System.out.println("客户端收到信息: " + msg);
        channel.close();
        key.selector().close();
    }

    public static void main(String[] args) throws IOException {
        EchoClient echoClient = new EchoClient();
        echoClient.init("192.168.189.1", 8000);
        echoClient.working();
    }
}
