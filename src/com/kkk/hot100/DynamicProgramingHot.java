package com.kkk.hot100;

/**
 * 动态规划 <br>
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
}
