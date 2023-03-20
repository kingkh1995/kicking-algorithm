package com.kkk.acm;

import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class ArrangeJobs {

  /*
  最大报酬、安排工作
  输入：
  40 3
  20 10
  20 20
  20 5
  输出：
  30
    */

  public static void main(String[] args) {
    try (Scanner in = new Scanner(System.in)) {
      int ts = in.nextInt(); // 总时间
      int n = in.nextInt();
      int[][] jobs = new int[n][2];
      for (int i = 0; i < n; ++i) {
        jobs[i][0] = in.nextInt(); // 时长
        jobs[i][1] = in.nextInt(); // 报酬
      }
      int[][] dp = new int[n][ts + 1]; // 0-i的数组，j时长内能获得的最大报酬
      for (int i = 0; i < n; ++i) {
        int time = jobs[i][0];
        if (i == 0) {
          dp[i][time] = jobs[i][1];
          continue;
        }
        for (int j = ts; j - time >= 0; --j) {
          dp[i][j] = Math.max(dp[i - 1][j], jobs[i][1] + dp[i - 1][j - time]);
        }
      }
      System.out.println(dp[n - 1][ts]);
    }
  }
}
