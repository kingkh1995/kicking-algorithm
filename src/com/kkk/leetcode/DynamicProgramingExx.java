package com.kkk.leetcode;

/**
 * 动态规划 <br>
 * 三个模板： <br>
 * 1、dp[i][j]: i-j的区间；2、dp[i]：0-i区间；3、dp[i]：以i结尾的最大连续区间。 <br>
 * 确定遍历顺序要看需要查看哪个方向的状态，则提前计算出该方向的状态。<br>
 * 494
 *
 * @author KaiKoo
 */
public class DynamicProgramingExx {

  // ===============================================================================================

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

  /**
   * 给定一个由 0 和 1 组成的矩阵，找出每个元素到最近的 0 的距离 <br>
   * f(i,j)的值肯定是0（元素为0）或其上下左右的结果的最小值加1 问题是如何构建出动态规划数组？ <br>
   * 从四个角落出发构造一遍即可，实际上只需要构造两遍，选择（左上和右下） 或（左下或右上）。
   */
  public int[][] updateMatrix(int[][] matrix) {
    int m = matrix.length, n = matrix[0].length;
    int[][] dist = new int[m][n];
    // 初始化动态规划的数组，元素为 0 则设为 0 否则设置为一个很大的数（m+n）
    for (int i = 0; i < m; ++i) {
      for (int j = 0; j < n; ++j) {
        dist[i][j] = matrix[i][j] == 0 ? 0 : m + n;
      }
    }
    // 只有 水平向左移动 和 竖直向上移动，注意动态规划的计算顺序
    for (int i = 0; i < m; ++i) {
      for (int j = 0; j < n; ++j) {
        if (i - 1 >= 0) {
          dist[i][j] = Math.min(dist[i][j], dist[i - 1][j] + 1);
        }
        if (j - 1 >= 0) {
          dist[i][j] = Math.min(dist[i][j], dist[i][j - 1] + 1);
        }
      }
    }
    // 只有 水平向右移动 和 竖直向下移动，注意动态规划的计算顺序
    for (int i = m - 1; i >= 0; --i) {
      for (int j = n - 1; j >= 0; --j) {
        if (i + 1 < m) {
          dist[i][j] = Math.min(dist[i][j], dist[i + 1][j] + 1);
        }
        if (j + 1 < n) {
          dist[i][j] = Math.min(dist[i][j], dist[i][j + 1] + 1);
        }
      }
    }
    return dist;
  }
}
