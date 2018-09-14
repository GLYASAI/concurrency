package org.abewang.pma.disrupter;

import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * @Author Abe
 * @Date 2018/9/13.
 */
public class Producer {
    private final RingBuffer<PCData> ringBuffer;

    public Producer(RingBuffer<PCData> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void pushData(ByteBuffer bb) {
        long sequence = ringBuffer.next();  // Grab the next sequence.
        try {
            // Get the entry int the Disruptor for the sequence.
            PCData event = ringBuffer.get(sequence);

            event.set(bb.getLong(0));  // Fill with data.
        } finally {
            ringBuffer.publish(sequence);
        }
    }
}
