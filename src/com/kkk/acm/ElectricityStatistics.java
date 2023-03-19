package com.kkk.acm;

import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class ElectricityStatistics {

  /*
  区域发电量统计
  输入：
  2 5 2 6
  1 3 4 5 8
  2 3 6 7 1
  输出：
  4
  输入：
  2 5 1 6
  1 3 4 5 8
  2 3 6 7 1
  输出：
  3
  输入：
  2 5 1 0
  1 3 4 5 8
  2 3 6 7 1
  输出：
  10
    */

  public static void main(String[] args) {
    try (Scanner in = new Scanner(System.in)) {
      int n = in.nextInt(), m = in.nextInt(), c = in.nextInt(), k = in.nextInt();
      int[][] arr = new int[n][m];
      for (int i = 0; i < n; ++i) {
        for (int j = 0; j < m; ++j) {
          arr[i][j] = in.nextInt();
        }
      }
      int[][] dp = new int[n + 1][m + 1]; // 添加一行一列用于处理边界
      for (int i = 1; i <= n; ++i) {
        for (int j = 1; j <= m; ++j) {
          dp[i][j] = arr[i - 1][j - 1] + dp[i - 1][j] + dp[i][j - 1] - dp[i - 1][j - 1];
        }
      }
      int ans = 0;
      // 利用前缀和快速求解，sum = dp[i][j] - dp[i-c][j] - dp[i][j-c] + dp[i-c][j-c]
      for (int i = c; i <= n; ++i) {
        for (int j = c; j <= m; ++j) {
          // 确定一个左上角
          int sum = dp[i][j] - dp[i - c][j] - dp[i][j - c] + dp[i - c][j - c];
          if (sum >= k) {
            ans++;
          }
        }
      }
      System.out.println(ans);
    }
  }
}
