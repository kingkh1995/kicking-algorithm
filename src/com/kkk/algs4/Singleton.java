package com.kkk.algs4;

/**
 * volatile+双重检查实现懒汉式的单例模式 <br>
 *
 * @author KaiKoo
 */
public class Singleton {

  // 使用volatile解决双重检查问题，防止指令重排，初始化对象完成后，instance变量才对其他线程可见。
  private static volatile Singleton instance;

  // 构造方法设置为私有
  private Singleton() {}

  public static Singleton getInstance() {
    if (instance == null) {
      synchronized (Singleton.class) {
        if (instance == null)
          // 如果instance非volatile，赋值语句执行过程可能会被重排，可能赋值引用先于初始化对象发生。
          instance = new Singleton();
      }
    }
    return instance;
  }
}
