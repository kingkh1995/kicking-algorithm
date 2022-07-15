package com.kkk.leetcode;

import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Phaser;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.IntConsumer;

/**
 * 多线程 <br>
 * 三种方式：<br>
 * 1、无锁化，自旋 + volatile；<br>
 * 2、锁，等待/通知模式。<br>
 * 3、通信工具类
 *
 * @author KaiKoo
 */
public class ThreadExx {

  /** 1114. 按序打印 <br> */
  class Foo {
    class Foo1 {
      private volatile int phase = 0;

      public void first(Runnable printFirst) throws InterruptedException {
        printFirst.run();
        phase = 1;
      }

      public void second(Runnable printSecond) throws InterruptedException {
        while (phase != 1) {
          Thread.yield();
        }
        printSecond.run();
        phase = 2;
      }

      public void third(Runnable printThird) throws InterruptedException {
        while (phase != 2) {
          Thread.yield();
        }
        printThird.run();
      }
    }

    // 使用锁
    class Foo2 {
      private final Object lock = new Object(); // 使用锁的通信机制
      private int phase = 0; // 标识任务阶段

      public void first(Runnable printFirst) throws InterruptedException {
        synchronized (lock) {
          printFirst.run();
          ++phase;
          lock.notifyAll();
        }
      }

      public void second(Runnable printSecond) throws InterruptedException {
        synchronized (lock) {
          while (phase != 1) {
            lock.wait();
          }
          printSecond.run();
          ++phase;
          lock.notifyAll();
        }
      }

      public void third(Runnable printThird) throws InterruptedException {
        synchronized (lock) {
          while (phase != 2) {
            lock.wait();
          }
          printThird.run();
        }
      }
    }

    // 使用通信工具类
    class Foo3 {
      private final Phaser phaser = new Phaser(1);

      public void first(Runnable printFirst) throws InterruptedException {
        printFirst.run();
        phaser.arrive();
      }

      public void second(Runnable printSecond) throws InterruptedException {
        phaser.awaitAdvance(0);
        printSecond.run();
        phaser.arrive();
      }

      public void third(Runnable printThird) throws InterruptedException {
        phaser.awaitAdvance(0); // 阶段为0时等待
        phaser.awaitAdvance(1); // 阶段为1时等待
        printThird.run();
      }
    }
  }

  /** 1116. 打印零与奇偶数 <br> */
  class ZeroEvenOdd {

    class ZeroEvenOdd1 {
      private int n;
      private volatile int state = -1;

      public ZeroEvenOdd1(int n) {
        this.n = n;
      }

      public void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; ++i) {
          while (state >= 0) {
            Thread.yield();
          }
          printNumber.accept(0);
          state = i & 1;
        }
      }

      public void even(IntConsumer printNumber) throws InterruptedException {
        for (int i = 2; i <= n; i += 2) {
          while (state != 0) {
            Thread.yield();
          }
          printNumber.accept(i);
          state = -1;
        }
      }

      public void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i += 2) {
          while (state != 1) {
            Thread.yield();
          }
          printNumber.accept(i);
          state = -1;
        }
      }
    }

    class ZeroEvenOdd2 {
      private int n;
      private final Object lock = new Object();
      private int state = -1;

      public ZeroEvenOdd2(int n) {
        this.n = n;
      }

      public void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; ++i) {
          synchronized (lock) {
            while (state >= 0) {
              lock.wait();
            }
            printNumber.accept(0);
            state = i & 1;
            lock.notifyAll();
          }
        }
      }

      public void even(IntConsumer printNumber) throws InterruptedException {
        for (int i = 2; i <= n; i += 2) {
          synchronized (lock) {
            while (state != 0) {
              lock.wait();
            }
            printNumber.accept(i);
            state = -1;
            lock.notifyAll();
          }
        }
      }

      public void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i += 2) {
          synchronized (lock) {
            while (state != 1) {
              lock.wait();
            }
            printNumber.accept(i);
            state = -1;
            lock.notifyAll();
          }
        }
      }
    }

    class ZeroEvenOdd3 {
      private int n;
      private final Semaphore semaphore0 = new Semaphore(1);
      private final Semaphore semaphore1 = new Semaphore(0); // 初始为0
      private final Semaphore semaphore2 = new Semaphore(0); // 初始为0

      public ZeroEvenOdd3(int n) {
        this.n = n;
      }

      public void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; ++i) {
          semaphore0.acquire();
          printNumber.accept(0);
          if ((i & 1) == 1) {
            semaphore1.release();
          } else {
            semaphore2.release();
          }
        }
      }

      public void even(IntConsumer printNumber) throws InterruptedException {
        for (int i = 2; i <= n; i += 2) {
          semaphore2.acquire();
          printNumber.accept(i);
          semaphore0.release();
        }
      }

      public void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i += 2) {
          semaphore1.acquire();
          printNumber.accept(i);
          semaphore0.release();
        }
      }
    }
  }

  /** 1117. H2O 生成 <br> */
  class H2O {
    private final CyclicBarrier p = new CyclicBarrier(3); // 满了三个线程则放行
    private final Semaphore h = new Semaphore(2);
    private final Semaphore o = new Semaphore(1);

    public H2O() {}

    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
      h.acquire();
      try {
        p.await();
      } catch (BrokenBarrierException e) {
        throw new RuntimeException(e);
      }
      releaseHydrogen.run();
      h.release();
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
      o.acquire();
      try {
        p.await();
      } catch (BrokenBarrierException e) {
        throw new RuntimeException(e);
      }
      releaseOxygen.run();
      o.release();
    }
  }

  /** 1195. 交替打印字符串 <br> */
  class FizzBuzz {
    private int n;
    private volatile int state = 1;

    public FizzBuzz(int n) {
      this.n = n;
    }

    public void fizz(Runnable printFizz) throws InterruptedException {
      for (int s; (s = state) <= n; ) {
        if (s % 3 == 0 && s % 5 != 0) {
          printFizz.run();
          state = s + 1;
        } else {
          Thread.yield();
        }
      }
    }

    public void buzz(Runnable printBuzz) throws InterruptedException {
      for (int s; (s = state) <= n; ) {
        if (s % 5 == 0 && s % 3 != 0) {
          printBuzz.run();
          state = s + 1;
        } else {
          Thread.yield();
        }
      }
    }

    public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
      for (int s; (s = state) <= n; ) {
        if (s % 3 == 0 && s % 5 == 0) {
          printFizzBuzz.run();
          state = s + 1;
        } else {
          Thread.yield();
        }
      }
    }

    public void number(IntConsumer printNumber) throws InterruptedException {
      for (int s; (s = state) <= n; ) {
        if (s % 3 != 0 && s % 5 != 0) {
          printNumber.accept(s);
          state = s + 1;
        } else {
          Thread.yield();
        }
      }
    }
  }

  /**
   * 1226. 哲学家进餐 <br>
   * 解法一：限制每次进餐人数为一，使用一个锁。 <br>
   * 解法二：最多只允许四个人持有叉子，需要使用一个信号量和五个锁。<br>
   * 解法三：确定策略，奇数编号先拿左边，偶数编号先拿右边，需要五个锁。
   */
  class DiningPhilosophers { // 解法三
    private ReentrantLock[] forks;

    public DiningPhilosophers() {
      forks = new ReentrantLock[5];
      Arrays.setAll(forks, i -> new ReentrantLock());
    }

    public void wantsToEat(
        int philosopher,
        Runnable pickLeftFork,
        Runnable pickRightFork,
        Runnable eat,
        Runnable putLeftFork,
        Runnable putRightFork)
        throws InterruptedException {
      int left = (philosopher + 1) % 5, right = philosopher;
      if ((philosopher & 1) == 0) {
        forks[left].lock();
        forks[right].lock();
      } else {
        forks[right].lock();
        forks[left].lock();
      }
      pickLeftFork.run();
      pickRightFork.run();
      eat.run();
      putLeftFork.run();
      putRightFork.run();
      forks[left].unlock();
      forks[right].unlock();
    }
  }
}
