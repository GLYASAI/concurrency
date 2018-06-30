package org.abewang.pma.singleton;

/**
 * 单例模式
 * 正确并且良好的实现
 */
public class Singleton {
    private Singleton() {
        System.out.println("Singleton is create");
    }

    private  static Singleton instance = new Singleton();

    public static Singleton getInstance() {
        return instance;
    }
}
