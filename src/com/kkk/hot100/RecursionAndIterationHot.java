package com.kkk.hot100;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 递归和迭代 <br>
 * 回溯法属于递归
 *
 * @author KaiKoo
 */
public class RecursionAndIterationHot {

  /**
   * 5. 最长回文子串 <br>
   * 中心扩散法，使用迭代，从每一个位置往两边扩散比较字符求解。
   */
  class longestPalindromeSolution {

    char[] charAt;
    int ansL; // 最长回文子串起点索引
    int ansH; // 最长回文子串终点索引

    public String longestPalindrome(String s) {
      if (s.length() < 2) {
        return s;
      }
      charAt = s.toCharArray();
      ansL = 0;
      ansH = 0;
      for (int i = 0; i < s.length(); i++) {
        expandAroundCenter(i, i); // 奇数回文串
        expandAroundCenter(i, i + 1); // 偶数回文串
      }
      return s.substring(ansL, ansH + 1);
    }

    private void expandAroundCenter(int l, int h) {
      while (l >= 0 && h < charAt.length && charAt[l] == charAt[h]) {
        --l;
        ++h;
      }
      // 为开区间需要内缩一格。
      h--;
      l++;
      // 更新结果
      if (h - l > ansH - ansL) {
        ansH = h;
        ansL = l;
      }
    }
  }

  /**
   * 17. 电话号码的字母组合 <br>
   * 回溯法求解
   */
  class letterCombinationsSolution {

    Map<Character, char[]> MAP = new HashMap<>(); // 字符集
    String digits;
    StringBuilder sb;
    List<String> ans;

    public List<String> letterCombinations(String digits) {
      if (digits.length() == 0) {
        return Collections.emptyList();
      }
      this.digits = digits;
      this.sb = new StringBuilder();
      this.ans = new ArrayList<>();
      backTrack(0);
      return this.ans;
    }

    private void backTrack(int i) {
      if (i == digits.length()) {
        ans.add(sb.toString());
        return;
      }
      for (char c : MAP.get(digits.charAt(i))) {
        sb.append(c);
        backTrack(i + 1);
        sb.deleteCharAt(i);
      }
    }
  }

  /**
   * 22. 括号生成 <br>
   * 不使用暴力解法，而是回溯法。
   */
  class generateParenthesisSolution {

    StringBuilder sb;
    List<String> ans;

    public List<String> generateParenthesis(int n) {
      sb = new StringBuilder();
      ans = new ArrayList<>();
      backTrack(0, 0, n);
      return ans;
    }

    private void backTrack(int openCount, int closeCount, int n) {
      if (openCount == n && closeCount == n) {
        ans.add(sb.toString());
        return;
      }
      if (openCount < n) {
        sb.append('(');
        backTrack(openCount + 1, closeCount, n);
        sb.deleteCharAt(openCount + closeCount);
      }
      // 闭括号个数小于开括号个数则可以追加闭括号
      if (closeCount < openCount) {
        sb.append(')');
        backTrack(openCount, closeCount + 1, n);
        sb.deleteCharAt(openCount + closeCount);
      }
    }
  }
}
