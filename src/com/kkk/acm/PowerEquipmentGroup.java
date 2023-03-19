package com.kkk.acm;

import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class PowerEquipmentGroup {

  /*
  查找充电设备组合
  输入：
  4
  50 20 20 60
  90
  输出：
  90
  输入：
  2
  50 40
  30
  输出：
  0
    */

  public static void main(String[] args) {
    try (Scanner in = new Scanner(System.in)) {
      int n = in.nextInt();
      int[] arr = new int[n];
      for (int i = 0; i < n; ++i) {
        arr[i] = in.nextInt();
      }
      int p = in.nextInt();
      boolean[] dp = new boolean[p + 1]; // 背包问题
      dp[0] = true;
      for (int i = 0; i < n; ++i) {
        int num = arr[i];
        for (int j = p; j >= num; --j) {
          dp[j] = dp[j] || dp[j - num];
        }
      }
      for (int i = p; i >= 0; --i) { // 从最大值开始判断，子数组和是否能等于该值。
        if (dp[i]) {
          System.out.println(i);
          return;
        }
      }
    }
  }
}
