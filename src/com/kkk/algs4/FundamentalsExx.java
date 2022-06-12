package com.kkk.algs4;

import com.kkk.supports.ArrayUtils;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 第一章 基础
 *
 * @author KaiKoo
 */
public class FundamentalsExx {

  // ===============================================================================================

  /** 1.1.9 将一个整数（可正可负，正数省去前面的0）用二进制表示并转换为字符串 Integer.toBinaryString(int i) */
  private static String toBinaryString(int i) {
    // 特殊情况 0
    if (i == 0) {
      return "0";
    }
    // 使用 char 数组保存二进制表示
    char[] chars = new char[32];
    int index = 32;
    // 终止条件 i==0 即不再存在任何一个 1
    while (i != 0) {
      // 从最低开始使用 & 运算
      chars[--index] = (char) ('0' + (i & 1));
      // 无符号左移一位 去除掉低位
      i = i >>> 1;
    }
    // String(char value[], int offset, int count) 包含offset
    return new String(chars, index, 32 - index);
  }

  public static void toBinaryStringTest() {
    int i = ThreadLocalRandom.current().nextInt();
    System.out.println(i);
    System.out.println(Integer.toBinaryString(i));
    System.out.println(FundamentalsExx.toBinaryString(i));
    System.out.println(Integer.toBinaryString(-i));
    System.out.println(FundamentalsExx.toBinaryString(-i));
  }

  // ===============================================================================================

  /** 1.1.27 求二项分布 */
  private static double binomial(int N, int k, double p) {
    if (N == 0 && k == 0) {
      return 1.0;
    }
    if (N < 0 || k < 0) {
      return 0.0;
    }
    // 递归次数太多，有严重的效率问题
    return (1.0 - p) * binomial(N - 1, k, p) + p * binomial(N - 1, k - 1, p);
  }

  // 循环求解，空间换时间
  private static double betterBinomial(int n, int k, double p) {
    // 使用二维数组保存之前的值
    double[][] matrix = new double[n + 1][k + 1];
    matrix[0][0] = 1.0D;
    // 先求出特殊情况 n=0 和 k=0
    for (int i = 1; i <= k; i++) {
      matrix[0][i] = 0;
    }
    for (int i = 1; i <= n; i++) {
      matrix[i][0] = (1.0 - p) * matrix[i - 1][0];
    }
    // 循环求解
    for (int i = 1; i <= n; i++) {
      for (int j = 1; j <= k; j++) {
        matrix[i][j] = (1.0 - p) * matrix[i - 1][j] + p * matrix[i - 1][j - 1];
      }
    }
    return matrix[n][k];
  }

  public static void binomialTest() {
    System.out.println(binomial(10, 5, 0.25D));
    System.out.println(betterBinomial(10, 5, 0.25D));
    // 递归次数太多无法求解
    //        System.out.println(binomial(100, 50, 0.25D));
    System.out.println(betterBinomial(100, 50, 0.25D));
  }

  // ===============================================================================================

  /** 1.1.29 基于二分查找，对一个整型有序数组（可能存在重复值），添加一个rank()方法返回小于key值的元素个数，一个count()方法返回等于key值的元素个数。 */
  private static int rank(int key, int[] a) {
    int lo = 0, hi = a.length - 1;
    // 等于情况下，向左遍历直到第一个不等于（小于）key的元素
    // 其余情况下等同于二分查找
    while (lo <= hi) {
      int mid = lo + ((hi - lo) >> 1);
      if (a[mid] == key) {
        while (--mid >= 0 && a[mid] == key) {}
        return ++mid;
      } else if (a[mid] < key) {
        lo = mid + 1;
      } else {
        hi = mid - 1;
      }
    }
    // 根据算法原理可知low就是小于key的个数
    return lo;
  }

  private static int count(int key, int[] a) {
    int num = 0;
    for (int i = rank(key, a); i < a.length && a[i++] == key; num++) {}
    return num;
  }

  public static void rankTest() {
    int key = 20;
    int[] arr = ArrayUtils.randomSortedArr(50, 0, 30);
    ArrayUtils.printArray(arr);
    int rank = rank(key, arr);
    int count = count(key, arr);
    System.out.println(rank);
    System.out.println(count);
    // 打印出等于key的元素
    ArrayUtils.printArray(Arrays.copyOfRange(arr, rank, rank + count));
  }

  // ===============================================================================================

  /** 1.2.6 回环变位 字符串s循环移动任意位置后得到t 判断两个字符串是否互为回环变位 */
  // 仅使用 length() indexOf() 方法
  private static boolean circularRotation(String s, String t) {
    // 互为回环变换，则拼接两个t必然包含s
    return s.length() == t.length() && (t + t).contains(s);
  }

  // ===============================================================================================

  /** 1.3.3 将 0-9 顺序入栈，判断出栈序列是否可能 */
  private static boolean isPopSequence(int[] arr) {
    Stack stack = new Stack();
    int i = 0;
    for (int n : arr) {
      // 如果栈为空 或 当前出栈不匹配 则入栈
      while (stack.isEmpty() || stack.top() != n) {
        if (i > 9) {
          return false;
        }
        stack.push(i++);
      }
      // 匹配则出栈
      stack.pop();
    }
    return true;
  }

  public static void isPopSequenceTest() {
    System.out.println(isPopSequence(new int[] {4, 3, 2, 1, 0, 9, 8, 7, 6, 5}));
    System.out.println(isPopSequence(new int[] {4, 6, 8, 7, 5, 3, 2, 9, 0, 1}));
    System.out.println(isPopSequence(new int[] {2, 5, 6, 7, 4, 8, 9, 3, 1, 0}));
    System.out.println(isPopSequence(new int[] {4, 3, 2, 1, 0, 5, 6, 7, 8, 9}));
    System.out.println(isPopSequence(new int[] {1, 2, 3, 4, 5, 6, 9, 8, 7, 0}));
    System.out.println(isPopSequence(new int[] {0, 4, 6, 5, 3, 8, 1, 7, 2, 9}));
    System.out.println(isPopSequence(new int[] {1, 4, 7, 9, 8, 6, 5, 3, 0, 2}));
    System.out.println(isPopSequence(new int[] {2, 1, 4, 3, 6, 5, 8, 7, 9, 0}));
  }

  // ===============================================================================================

  /** 1.3.37 约瑟夫问题 使用队列 （41个人到3则出列，最后剩16、31） */
  private static int[] josephus(int n, int m) {
    int[] arr = new int[n];
    Queue queue = new Queue();
    // 从 1 开始
    for (int i = 1; i <= n; i++) {
      queue.enqueue(i);
    }
    for (int count = 0, i = 0; i < n; ) {
      int out = queue.dequeue();
      count++;
      if (count == m) {
        count = 0;
        arr[i++] = out;
      } else {
        queue.enqueue(out);
      }
    }
    return arr;
  }

  public static void josephusTest() {
    ArrayUtils.printArray(josephus(7, 2));
    ArrayUtils.printArray(josephus(41, 3));
  }

  // ===============================================================================================

  /** 1.4.15 平方级别算法实现 3-sum 双指针 */
  private static int count3Sum(int[] arr) {
    Arrays.sort(arr);
    int count = 0;
    for (int i = 0; i < arr.length - 2; i++) {
      int lo = i + 1;
      int hi = arr.length - 1;
      while (lo < hi) {
        int sum = arr[i] + arr[lo] + arr[hi];
        if (sum > 0) {
          hi--;
        } else if (sum < 0) {
          lo++;
        } else {
          System.out.println(arr[i] + "+" + arr[lo] + "+" + arr[hi]);
          count++;
          lo++;
          hi--;
        }
      }
    }
    return count;
  }

  // ===============================================================================================

  /** 1.4.30 三个栈实现一个双向队列 */
  private static class DequeBy3Stacks {

    private final Stack left = new Stack();
    private final Stack right = new Stack();
    // 辅助栈不存元素，只用于辅助转移
    private final Stack temp = new Stack();

    public void pushLeft(int i) {
      left.push(i);
    }

    public void pushRight(int i) {
      right.push(i);
    }

    public boolean isEmpty() {
      return left.isEmpty() && right.isEmpty();
    }

    public int popLeft() {
      if (this.isEmpty()) {
        throw new NoSuchElementException();
      }
      if (left.isEmpty()) {
        moveHalf(right, left);
      }
      return left.pop();
    }

    public int popRight() {
      if (this.isEmpty()) {
        throw new NoSuchElementException();
      }
      if (right.isEmpty()) {
        moveHalf(left, right);
      }
      return right.pop();
    }

    // 转移时只把一半放过去，确保左右弹栈时不用一直来回倒
    private void moveHalf(Stack from, Stack to) {
      int n = from.size();
      for (int i = 0; i < n >> 1; i++) {
        temp.push(from.pop());
      }
      while (!from.isEmpty()) {
        to.push(from.pop());
      }
      while (!temp.isEmpty()) {
        from.push(temp.pop());
      }
    }
  }

  // ===============================================================================================

}
