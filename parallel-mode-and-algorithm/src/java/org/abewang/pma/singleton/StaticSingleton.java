package org.abewang.pma.singleton;

/**
 * StaticSingleton结合了Singleton和LazySingleton的优点
 */
public class StaticSingleton {
    private StaticSingleton() {
        System.out.println("StaticSingleton is create");
    }

    public static StaticSingleton getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private static StaticSingleton instance = new StaticSingleton();
    }
}
