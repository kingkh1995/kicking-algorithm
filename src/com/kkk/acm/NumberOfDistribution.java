package com.kkk.acm;

import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class NumberOfDistribution {

  /*
  linux发行版的数量
  输入：
  4
  1 1 0 0
  1 1 1 0
  0 1 1 0
  0 0 0 1
  输出：
  3
    */

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    while (in.hasNextInt()) {
      int n = in.nextInt();
      int[] id = new int[n]; // 联通分量
      int[] count = new int[n]; // 个数
      for (int i = 0; i < n; ++i) {
        id[i] = i;
        count[i] = 1;
      }
      for (int i = 0; i < n; ++i) {
        for (int j = 0; j < n; ++j) {
          boolean connected = in.nextInt() == 1;
          if (i > j && connected) {
            int p = id[i], q = id[j];
            if (p != q) { // p全部改成q
              for (int k = 0; k < n; ++k) {
                if (id[k] == p) {
                  id[k] = q;
                }
              }
              count[q] += count[p];
              count[p] = 0;
            }
          }
        }
      }
      int ans = 0;
      for (int k : count) {
        ans = Math.max(ans, k);
      }
      System.out.println(ans);
    }
  }
}
