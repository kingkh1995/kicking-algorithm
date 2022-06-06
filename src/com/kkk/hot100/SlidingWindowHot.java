package com.kkk.hot100;

/**
 * 滑动窗口 <br>
 *
 * @author KaiKoo
 */
public class SlidingWindowHot {

  /**
   * 3. 无重复字符的最长子串 <br>
   * 记录字符出现的最后位置，如果最后位置在滑动窗口内，则窗口左侧需要移动到该位置的右边，才能继续满足条件。
   */
  public int lengthOfLongestSubstring(String s) {
    int[] aux = new int[128]; // 字符为ASCII字符
    int ans = 0;
    for (int l = 0, r = 0; r < s.length(); ++r) {
      char c = s.charAt(r);
      l = Math.max(l, aux[c]); // 移动窗口左侧
      ans = Math.max(ans, r - l + 1); // 更新结果
      aux[c] = r + 1; // 记录字符最后出现的位置+1，即是窗口左侧应该跳转的位置。
    }
    return ans;
  }
}
