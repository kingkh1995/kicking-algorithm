package com.kkk.leetcode;

/**
 * 动态规划
 *
 * @author KaiKoo
 */
public class DynamicProgramingExx {

  // ===============================================================================================
  /** 拔高题 */

  // 分割数组的最大值 将非负整数数组分为非空且连续的m组 使得各数组各自的和的最大值最小 求出该最小值
  public static int splitArray(int[] nums, int m) {
    int n = nums.length;
    int[][] dp = new int[n + 1][m + 1]; // 将前n个元素分为m组
    int[] sum = new int[n + 1];
    for (int i = 1, s = 0; i <= n; i++) {
      s += nums[i - 1];
      sum[i] = s;
    }
    // dp[i][1]=sum[i]
    for (int i = 1; i <= n; i++) {
      for (int j = 1; j <= i && j <= m; j++) { // j不能大于i且不能大于m
        if (j == 1) {
          dp[i][1] = sum[i];
        } else {
          // 将前k个元素分为j-1组 之后的元素单独分为一组 k不能小于j-1 且不能大于i-1
          // 循环求解，得出最小值
          dp[i][j] = Integer.MAX_VALUE;
          for (int k = Math.max(j - 1, 1); k < i; k++) {
            dp[i][j] = Math.min(dp[i][j], Math.max(dp[k][j - 1], sum[i] - sum[k]));
          }
        }
      }
    }
    return dp[n][m];
  }
}
