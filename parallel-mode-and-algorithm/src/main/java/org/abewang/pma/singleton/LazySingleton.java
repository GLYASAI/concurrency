package org.abewang.pma.singleton;

/**
 * 单例模式-延迟加载
 */
public class LazySingleton {
    private static LazySingleton instance = null;

    private LazySingleton() {
        System.out.println("LazySingleton is create");
    }

    public static synchronized LazySingleton getInstance() {
        if (instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }
}
