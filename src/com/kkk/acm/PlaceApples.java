package com.kkk.acm;

import java.util.Scanner;

public class PlaceApples {

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    while (in.hasNextInt()) { // 注意 while 处理多个 case
      int m = in.nextInt();
      int n = in.nextInt();
      System.out.println(dp(m, n));
    }
  }

  private static int dp(int m, int n) {
    int[][] dp = new int[m + 1][n + 1];
    for (int i = 0; i <= m; ++i) {
      for (int j = 1; j <= n; ++j) { // j从1开始，盘子数至少为1。
        if (i == 0) { // 无苹果只一种摆法，用于处理边界问题。
          dp[i][j] = 1;
          continue;
        }
        // 分为留空盘和不留空盘两种情况
        if (i < j) { // 苹果少于盘子，必然有空盘，只摆放一个空盘。
          dp[i][j] = dp[i][j - 1]; // 有多个空盘的情况在上一个状态中已经包含。
        } else { // 苹果多余盘子，则两种情况都能覆盖。
          dp[i][j] = dp[i][j - 1] + dp[i - j][j]; // 每个盘子都放一个苹果即可。
        }
      }
    }
    return dp[m][n];
  }
}
