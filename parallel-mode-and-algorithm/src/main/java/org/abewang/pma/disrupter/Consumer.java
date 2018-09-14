package org.abewang.pma.disrupter;

import com.lmax.disruptor.WorkHandler;

/**
 * @Author Abe
 * @Date 2018/9/13.
 */
public class Consumer implements WorkHandler<PCData> {
    @Override
    public void onEvent(PCData pcData) throws Exception {
        System.out.println(Thread.currentThread().getName() + ": Event: --" + pcData.get() * pcData.get() + "--");
    }
}
