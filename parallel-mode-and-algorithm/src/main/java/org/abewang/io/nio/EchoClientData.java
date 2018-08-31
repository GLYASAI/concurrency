package org.abewang.io.nio;

import java.nio.ByteBuffer;
import java.util.LinkedList;

/**
 * @Author Abe
 * @Date 2018/7/29.
 */
public class EchoClientData {
    private LinkedList<ByteBuffer> outQ;

    public EchoClientData() {
        outQ = new LinkedList<>();
    }

    public LinkedList<ByteBuffer> getOutputQueue() {
        return outQ;
    }

    public void enqueue(ByteBuffer bb) {
        outQ.addFirst(bb);
    }
}
