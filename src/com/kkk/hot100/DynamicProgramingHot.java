package com.kkk.hot100;

import java.util.List;

/**
 * 动态规划 <br>
 * 【隐式动态规划：如果不需要查看所有之前状态则可以直接使用变量保存状态】
 *
 * @author KaiKoo
 */
public class DynamicProgramingHot {

  /**
   * 5. 最长回文子串 <br>
   * 动态规划求解，相比于中心扩散法，空间复杂度为 O(n^2)。
   */
  public String longestPalindrome(String s) {
    int length = s.length();
    boolean[][] dp = new boolean[length][length]; // dp[i][j]表示从i至j的子串是否为回文字符串
    String res = "";
    // dp遍历方向为从下至上，从左至右。
    for (int i = length - 1; i >= 0; i--) {
      for (int j = i; j < length; j++) {
        if (s.charAt(i) == s.charAt(j)) {
          if (j - i > 2) {
            dp[i][j] = dp[i + 1][j - 1];
          } else {
            dp[i][j] = true; // 区间长度小于等于3则直接为true
          }
          // 更新结果
          if (dp[i][j] && j - i + 1 > res.length()) {
            res = s.substring(i, j + 1);
          }
        }
      }
    }
    return res;
  }

  /**
   * 53.最大子数组和 <br>
   * 隐式动态规划，只使用一个变量即可。
   */
  public int maxSubArray(int[] nums) {
    int ans = nums[0], max = nums[0];
    for (int i = 1; i < nums.length; ++i) {
      ans = Math.max(ans, max = nums[i] + Math.max(max, 0));
    }
    return ans;
  }

  /** 62. 不同路径 <br> */
  public int uniquePaths(int m, int n) {
    int[][] dp = new int[m][n]; // dp[i][j]表示可到达该位置的路径数量
    for (int i = 0; i < m; ++i) {
      for (int j = 0; j < n; ++j) {
        if (i == 0 || j == 0) {
          dp[i][j] = 1; // 因为只能下移和右移，故第一行和第一列结果为1。
        } else {
          dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
        }
      }
    }
    return dp[m - 1][n - 1];
  }

  /**
   * 64. 最小路径和 <br>
   * 直接原地DP，不需要辅助空间。
   */
  public int minPathSum(int[][] grid) {
    int m = grid.length, n = grid[0].length;
    // 因为只能下移和右移，故第一行和第一列只能沿着直线移动。
    for (int i = 1; i < m; ++i) {
      grid[i][0] = grid[i - 1][0] + grid[i][0];
    }
    for (int i = 1; i < n; ++i) {
      grid[0][i] = grid[0][i - 1] + grid[0][i];
    }
    for (int i = 1; i < m; ++i) {
      for (int j = 1; j < n; ++j) {
        grid[i][j] = Math.min(grid[i - 1][j], grid[i][j - 1]) + grid[i][j];
      }
    }
    return grid[m - 1][n - 1];
  }

  /**
   * 70. 爬楼梯 <br>
   * 实际上就是计算斐波那契数列，不使用递归，会超出时间限制，使用隐式动态规划。
   */
  public int climbStairs(int n) {
    int p, q = 0, r = 1;
    for (int i = 1; i <= n; ++i) {
      p = q;
      q = r;
      r = p + q;
    }
    return r;
  }

  /**
   * 139. 单词拆分 <br>
   * 可以使用哈希表优化，用单词最后一个字符作为键收集单词，这样可以减少不必要的比对。
   */
  public boolean wordBreak(String s, List<String> wordDict) {
    boolean[] dp = new boolean[s.length() + 1];
    dp[0] = true;
    for (int i = 1, index; i <= s.length(); ++i) {
      for (String word : wordDict) {
        if ((index = i - word.length()) >= 0 // index为单词首字母在字符串中的索引
            && word.equals(s.substring(index, i)) // 当前子串尾部匹配了单词
            && (dp[i] = dp[index])) { // 状态转移
          break;
        }
      }
    }
    return dp[s.length()];
  }

  /**
   * 152. 乘积最大子数组 <br>
   * 隐式动态规划，记录乘积最大值和最小值。nums[i]、nums[i]*max[i-1]、nums[i]*min[i-1]三者最大为max[i]最小为min[i]。
   */
  public int maxProduct(int[] nums) {
    int ans = nums[0], max = ans, min = ans, maxN, minN;
    for (int i = 1; i < nums.length; ++i) { // 注意从索引1位置开始
      max = Math.max(nums[i], Math.max(maxN = max * nums[i], minN = min * nums[i]));
      min = Math.min(nums[i], Math.min(maxN, minN));
      ans = Math.max(ans, max);
    }
    return ans;
  }

  /**
   * 198. 打家劫舍 <br>
   * 因为只需要查看上个状态和上上个状态，则可以使用变量保存以节约空间。
   */
  public int rob(int[] nums) {
    int m2 = 0, m1 = nums[0], max = m1;
    for (int i = 1; i < nums.length; ++i) {
      max = Math.max(nums[i] + m2, m1);
      m2 = m1; // 上上个状态
      m1 = max; // 上个状态
    }
    return max;
  }

  /** 221. 最大正方形 <br> */
  public int maximalSquare(char[][] matrix) {
    int ans = 0, m = matrix.length, n = matrix[0].length;
    int[][] dp = new int[m + 1][n + 1];
    for (int i = 1; i <= m; ++i) {
      for (int j = 1; j <= n; ++j) {
        if (matrix[i - 1][j - 1] == '1') {
          dp[i][j] = 1 + Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]);
          ans = Math.max(ans, dp[i][j] * dp[i][j]);
        }
      }
    }
    return ans;
  }

  // ===============================================================================================

  /**
   * 10. 正则表达式匹配 <br>
   * '.' 匹配任意单个字符 '*' 匹配零个或多个前面的那一个元素 <br>
   * 关键是边界条件的处理。
   */
  class isMatchSolution {
    char[] sCharAt;
    char[] pCharAt;

    public boolean isMatch(String s, String p) {
      sCharAt = s.toCharArray();
      pCharAt = p.toCharArray();
      int sl = sCharAt.length, pl = pCharAt.length;
      // dp[i][j]表示s前i位匹配p前j位
      boolean[][] dp = new boolean[sl + 1][pl + 1]; // 长度加一则不需要考虑数组越位情况。
      dp[0][0] = true;
      for (int i = 0; i <= sl; ++i) { // 从空字符串开始，因为空字符串也可能匹配模式串。
        for (int j = 1; j <= pl; ++j) {
          if (matches(i, j)) {
            dp[i][j] = dp[i - 1][j - 1];
          } else if ('*' == pCharAt[j - 1]) {
            // *不需要匹配最后一位元素，则p最后两位可以忽略；*匹配了最后一位元素，则s最后一位可以忽略。
            dp[i][j] = dp[i][j - 2] || (matches(i, j - 1) && dp[i - 1][j]);
          }
        }
      }
      return dp[sl][pl];
    }

    // 判断当前给定位置字符是否匹配
    public boolean matches(int i, int j) {
      return i > 0 && ('.' == pCharAt[j - 1] || sCharAt[i - 1] == pCharAt[j - 1]);
    }
  }

  /** 32. 最长有效括号 <br> */
  public int longestValidParentheses(String s) {
    int ans = 0;
    char[] charAt = s.toCharArray();
    // dp[i]为以i结尾的最长满足区间
    int[] dp = new int[s.length()];
    for (int i = 1; i < s.length(); ++i) {
      // 以闭括号结束才可能有效
      if (charAt[i] == ')') {
        if (charAt[i - 1] == '(') {
          // 最后的一对括号可以和dp[i-2]组合后有效。
          dp[i] = (i > 2 ? dp[i - 2] : 0) + 2;
        } else {
          // 为dp[i - 1]区间左端元素的索引
          int k = i - dp[i - 1];
          // 要求dp[i-1]为有效区间，且dp[i-1]不是[0,i-1]区间，且dp[i-1]区间左侧为开括号，dp[i]才有效。
          if (k > 0 && k < i - 1 && charAt[k - 1] == '(') {
            // [k-1,i]区间都有效了，则还需要加上dp[k-2]，因为与dp[k-2]组合后也有效。
            dp[i] = (k > 2 ? dp[k - 2] : 0) + dp[i - 1] + 2;
          }
        }
        ans = Math.max(ans, dp[i]);
      }
    }
    return ans;
  }

  /**
   * 42. 接雨水 <br>
   * todo... 动态规划解法
   */

  /**
   * 72. 编辑距离 <br>
   * 注意处理空字符串
   */
  public int minDistance(String word1, String word2) {
    int m = word1.length(), n = word2.length();
    // dp[i][j]表示将0-(i-1)位置的word1子串转换为0-(j-1)位置的word2子串需要的最小操作数
    int[][] dp = new int[m + 1][n + 1];
    for (int i = 1; i <= m; ++i) {
      dp[i][0] = i; // word2为空串，需要i次删除操作。
    }
    for (int i = 1; i <= n; ++i) {
      dp[0][i] = i; // word1为空串，需要i次插入操作。
    }
    for (int i = 1; i <= m; ++i) {
      for (int j = 1; j <= n; ++j) {
        // 对比上一状态，im1需要一次删除；jm1需要一次插入；im1jm1如果最后一位字符不匹配则要一次替换。
        int im1 = dp[i - 1][j] + 1, jm1 = dp[i][j - 1] + 1;
        int im1jm1 = dp[i - 1][j - 1] + (word1.charAt(i - 1) == word2.charAt(j - 1) ? 0 : 1);
        // 取im1、jm1、im1jm1三者最小
        dp[i][j] = Math.min(Math.min(im1, jm1), im1jm1);
      }
    }
    return dp[m][n];
  }
}
