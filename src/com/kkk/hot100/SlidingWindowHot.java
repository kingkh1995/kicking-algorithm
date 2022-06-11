package com.kkk.hot100;

/**
 * 滑动窗口 <br>
 * 【滑动窗口属于动态规】
 *
 * @author KaiKoo
 */
public class SlidingWindowHot {

  /**
   * 3. 无重复字符的最长子串 <br>
   * 记录字符出现的最后位置，如果最后位置在滑动窗口内，则窗口左侧需要移动到该位置的右边，才能继续满足条件。
   */
  public int lengthOfLongestSubstring(String s) {
    int ans = 0;
    int[] aux = new int[128]; // 字符为ASCII字符
    for (int l = 0, r = 0; r < s.length(); ++r) {
      char c = s.charAt(r);
      l = Math.max(l, aux[c]); // 移动窗口左侧
      ans = Math.max(ans, r - l + 1); // 更新结果
      aux[c] = r + 1; // 记录字符最后出现的位置+1，即是窗口左侧应该跳转的位置。
    }
    return ans;
  }


  // ===============================================================================================

  /**
   * 76. 最小覆盖子串 <br>
   * 窗口内子串一旦完全覆盖后则每次都尝试移动左窗口以缩小子串长度。
   */
  public String minWindow(String s, String t) {
    String ans = ""; // 无法覆盖时返回结果
    int[] ori = new int['z' - 'A' + 1]; // t中字符统计
    for (char c : t.toCharArray()) {
      ori[c - 'A']++;
    }
    int[] cnt = new int[ori.length]; // 当前窗口内字符统计
    int matchCount = 0; // 已经匹配的t中字符的总数，等于t.length()则表示当前窗口已经完全覆盖。
    char[] charAt = s.toCharArray();
    for (int l = 0, r = 0, index; r < charAt.length; ++r) {
      if (++cnt[index = charAt[r] - 'A'] <= ori[index]) { // 字符匹配次数未超出时增加字符匹配总数
        matchCount++;
      }
      if (matchCount < t.length()) { // 未完全覆盖则移动右窗口
        continue;
      }
      while (l <= r) { // 完全覆盖则移动左窗口，去掉窗口左边统计次数超出的字符。
        if (cnt[index = charAt[l] - 'A'] <= ori[index]) {
          break;
        }
        cnt[index]--;
        l++;
      }
      // 左窗口移动完成后更新结果
      ans = ans.length() == 0 || r - l + 1 < ans.length() ? s.substring(l, r + 1) : ans;
    }
    return ans;
  }
}
