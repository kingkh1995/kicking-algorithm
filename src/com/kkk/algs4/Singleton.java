package com.kkk.algs4;

/**
 * volatile+双重检查实现懒汉式的单例模式 <br>
 *
 * @author KaiKoo
 */
public class Singleton {
  // 使用volatile解决双重检查问题
  private static volatile Singleton instance;

  // 构造方法设置为私有
  private Singleton() {}

  public static Singleton getInstance() {
    if (instance == null) {
      synchronized (Singleton.class) {
        if (instance == null) {
          // 使用volatile防止指令重排，初始化对象完成后，instance变量才对其他线程可见。
          instance = new Singleton();
        }
      }
    }
    return instance;
  }
}
