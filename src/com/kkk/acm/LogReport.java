package com.kkk.acm;

import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class LogReport {

  /*
  日志采集系统、日志首次上报最多积分
  输入：
  1 98 1
  输出：
  98
  输入：
  50 60 1
  输出：
  50
   */

  public static void main(String[] args) {
    try (Scanner in = new Scanner(System.in)) {
      String[] arr = in.nextLine().split(" ");
      int max = Integer.MIN_VALUE, dp = 0, sum = 0;
      for (int i = 0; i < arr.length; ++i) { // 遍历计算
        int num = Integer.parseInt(arr[i]);
        // dp[i] = dp[i-1] + arr[i] - sum[i-1]
        dp = dp + Math.min(num, 100 - sum) - sum;
        max = Integer.max(max, dp); // 更新结果
        if (num + sum >= 100) { // 不能超过100条
          break;
        }
        sum += num; // sum[i]
      }
      System.out.println(max);
    }
  }
}
