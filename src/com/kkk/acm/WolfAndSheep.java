package com.kkk.acm;

import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class WolfAndSheep {

  /*
  狼羊过河
  输入：
  5 3 3
  输出：
  3
    */

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    while (in.hasNextInt()) {
      System.out.println(transfer(in.nextInt(), in.nextInt(), in.nextInt()));
    }
  }

  public static int transfer(int m, int n, int x) {
    int m1 = m, m2 = 0, n1 = n, n2 = 0; // m羊 n狼
    int count = 1;
    circle:
    while (m1 + n1 > x) { // 边界条件，处理最后一轮。
      count++;
      for (int i = Math.min(x, m1); i >= 0; --i) {
        for (int j = Math.min(x - i, n1); j >= 0; --j) {
          // 运输完成后，岸两边羊都大于狼（包含狼为0），或羊为0，也不会损失羊。
          if ((m1 - i > n1 - j || m1 - i == 0)
              && (m2 + i > n2 + j || m2 + i == 0)
              && (i > 0 || j > 0)) {
            m1 -= i;
            m2 += i;
            n1 -= j;
            n2 += j;
            continue circle; // 跳到最外层循环
          }
        }
      }
      return 0;
    }
    return count;
  }
}
