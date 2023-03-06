package com.kkk.acm;

import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class DecomposePrimes {

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int n = in.nextInt();
    // 从小到大遍历即可，不可能输出非质数，因为非质数都是质数的整数倍，在前面处理过程已经被除尽。
    // 不超时的关键，质数因子不可能超过n的平方根，之后直接输出剩余除数即可。
    for (int i = 2; i <= Math.sqrt(n); ++i) {
      if (n % i == 0) {
        System.out.print(i + " ");
        n /= i;
        i--;
      }
    }
    System.out.print(n);
  }
}
