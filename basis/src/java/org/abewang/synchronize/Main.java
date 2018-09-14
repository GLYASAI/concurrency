package org.abewang.synchronize;

/**
 * @Author Abe
 * @Date 2018/8/31.
 */
public class Main {
    public void syncReentrantLock() {
        synchronized (this) {
            synchronized (this) {

            }
        }
    }
}
