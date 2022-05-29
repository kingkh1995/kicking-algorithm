package com.kkk.leetcode;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 动态规划 <br>
 * 494
 *
 * @author KaiKoo
 */
public class DynamicProgramingExx {

  // ===============================================================================================
  /** 基础题 */

  // 打家劫舍
  public int rob(int[] nums) {
    int l = nums.length;
    if (l < 2) {
      return nums[0];
    }
    int[] dp = new int[l];
    dp[0] = nums[0];
    dp[1] = Math.max(nums[0], nums[1]);
    for (int i = 2; i < l; ++i) {
      dp[i] = Math.max(dp[i - 1], nums[i] + dp[i - 2]);
    }
    return dp[l - 1];
  }

  // 完全平方数
  public int numSquares(int n) {
    int[] dp = new int[n + 1];
    dp[0] = 0;
    dp[1] = 1;
    for (int i = 2; i <= n; i++) {
      int count = dp[i - 1] + 1;
      for (int j = 2; j < i; j++) {
        int p = j * j;
        if (p > i) {
          break;
        }
        count = Math.min(dp[i - p] + 1, count);
      }
      dp[i] = count;
    }
    return dp[n];
  }

  // 最长上升子序列
  public int lengthOfLIS(int[] nums) {
    int l = nums.length;
    int[] dp = new int[l];
    dp[0] = 1;
    int ans = 1;
    for (int i = 1; i < l; ++i) {
      dp[i] = 1;
      for (int j = i - 1; j >= 0; j--) {
        if (nums[i] > nums[j]) {
          dp[i] = Math.max(1 + dp[j], dp[i]);
        }
      }
      ans = Math.max(ans, dp[i]);
    }
    return ans;
  }

  // 零钱兑换
  public int coinChange(int[] coins, int amount) {
    int[] dp = new int[amount + 1];
    Arrays.fill(dp, amount + 1);
    dp[0] = 0;
    for (int i = 1; i <= amount; ++i) {
      for (int n : coins) {
        if (n <= i) {
          dp[i] = Math.min(dp[i], dp[i - n] + 1);
        }
      }
    }
    return dp[amount] > amount ? -1 : dp[amount];
  }

  // 最长回文子串
  public static String longestPalindrome(String s) {
    int l = s.length();
    // dp[i][j]表示索引位置从i至j的子串是否为回文字符串
    boolean[][] dp = new boolean[l][l];
    String res = "";
    // 遍历方向为从下至上，从左至右。
    for (int i = l - 1; i >= 0; i--) {
      for (int j = i; j < l; j++) {
        if (s.charAt(i) == s.charAt(j)) {
          if (j - i > 1) {
            dp[i][j] = dp[i + 1][j - 1];
          } else {
            dp[i][j] = true;
          }
          if (dp[i][j] && j - i + 1 > res.length()) {
            res = s.substring(i, j + 1);
          }
        }
      }
    }
    return res;
  }

  // 单词拆分，判断由给定字典是否可拼凑出给定单词。
  public boolean wordBreak(String s, List<String> wordDict) {
    // 以字典中单词最后一个字符构造符号表
    var lastCWordMap =
        wordDict.stream().collect(Collectors.groupingBy(word -> word.charAt(word.length() - 1)));
    boolean[] dp = new boolean[s.length() + 1];
    dp[0] = true;
    for (int i = 1; i <= s.length(); i++) {
      var words = lastCWordMap.get(s.charAt(i - 1));
      if (words != null) {
        for (String word : words) {
          int start = i - word.length();
          if (start >= 0 && dp[start] && word.equals(s.substring(start, i))) {
            dp[i] = true;
            break;
          }
        }
      }
    }
    return dp[s.length()];
  }

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
