package com.kkk.aim2offer;

/**
 * 设计模式
 *
 * @author KaiKoo
 * @date 2020/2/7 17:03
 */
public class DesignPattern {

    /**
     * 单例模式（懒汉模式，只有在调用时才加载）
     */
    public static class Singleton {

        //使用volatile解决双重检查问题
        private static volatile Singleton instance;

        //构造方法设置为私有
        private Singleton() {
        }

        public static Singleton getInstance() {
            if (instance == null) {
                synchronized (Singleton.class) {
                    if (instance == null) {
                        instance = new Singleton();
                    }
                }
            }
            return instance;
        }
    }
}
