package com.kkk.acm;

import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class BuySms {

  /*
  短信平台优惠活动
  输入：
  6
  10 20 30 40 60
  输出：
  70
  输入：
  15
  10 20 30 40 60 60 70 80 90 150
  输出：
  210
    */

  public static void main(String[] args) {
    try (Scanner in = new Scanner(System.in)) {
      int m = in.nextInt();
      in.nextLine();
      String[] split = in.nextLine().trim().split(" ");
      int[][] arr = new int[split.length][2]; // 售价-短信数
      for (int i = 0; i < arr.length; ++i) {
        arr[i][0] = i + 1;
        arr[i][1] = Integer.parseInt(split[i]);
      }
      int[] dp = new int[m + 1];
      for (int i = 1; i <= m; ++i) { // 总额从1开始一直顺序计算
        for (int j = 0; j < arr.length; ++j) { // 选择一个能使用的优惠活动
          if (arr[j][0] <= i) { // 更新结果
            dp[i] = Math.max(dp[i], arr[j][1] + dp[i - arr[j][0]]);
          }
        }
      }
      System.out.println(dp[m]);
    }
  }
}
