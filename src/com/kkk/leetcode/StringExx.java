package com.kkk.leetcode;

/**
 * 字符串 <br>
 * 140
 *
 * @author KaiKoo
 */
public class StringExx {

  // ===============================================================================================
  /** 基础题 */

  // 最长回文子串，中心扩散法。
  public String longestPalindrome(String s) {
    if (s == null || s.isBlank()) {
      return "";
    }
    String res = "";
    for (int i = 0; i < s.length(); i++) {
      // 奇数回文串
      res = expandAroundCenter(s, res, i, i);
      // 偶数回文串
      res = expandAroundCenter(s, res, i, i + 1);
    }
    return res;
  }

  private String expandAroundCenter(String s, String res, int left, int right) {
    while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
      --left;
      ++right;
    }
    // right、left为开区间，均需要内缩一格。
    return right - left - 1 > res.length() ? s.substring(left + 1, right) : res;
  }

  // ===============================================================================================
  /** 拔高题 */
}
