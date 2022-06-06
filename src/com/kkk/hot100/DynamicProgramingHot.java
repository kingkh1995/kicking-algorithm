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
    int l = s.length();
    boolean[][] dp = new boolean[l][l]; // dp[i][j]表示从i至j的子串是否为回文字符串
    String res = "";
    // dp遍历方向为从下至上，从左至右。
    for (int i = l - 1; i >= 0; i--) {
      for (int j = i; j < l; j++) {
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
      int sl = sCharAt.length;
      int pl = pCharAt.length;
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
}
