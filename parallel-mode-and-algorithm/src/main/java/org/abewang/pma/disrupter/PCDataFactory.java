package org.abewang.pma.disrupter;

import com.lmax.disruptor.EventFactory;

/**
 * @Author Abe
 * @Date 2018/9/13.
 */
public class PCDataFactory implements EventFactory<PCData> {
    @Override
    public PCData newInstance() {
        return new PCData();
    }
}
