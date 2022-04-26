package com.kkk.aim2offer;

/**
 * 滑动窗口
 *
 * @author KaiKoo
 * @date 2020/3/8 19:40
 */
public class SlidingWindow {

  private static final SlidingWindow SLIDING_WINDOW = new SlidingWindow();

  /*
  找出一个字符串其中不含有重复字符的 最长子串 的长度
   */
  /*
  解题思路：构建一个滑动窗口，遍历整个字符串，如果第一次发现了重复的字符，则直接跳过整个子字符串
  时间复杂度：只需要遍历一遍，即为o(n)
   */
  public int lengthOfLongestUnduplicatedSubString(String s) {
    if (s == null || s.isBlank()) {
      return 0;
    }
    // 用ans记录下窗口滑动过程中窗口长度的最大值
    int ans = 0;
    // 假设字符集为ASCII码(0-127)，这个数组用来代替map
    int[] index = new int[128];
    // i为窗口的前端，j为窗口的后端
    for (int i = 0, j = 0; j < s.length(); j++) {
      /// 用数组记录下该字符上一次出现的索引加1，如果为0表示该字符串未出现过
      int charindex = index[s.charAt(j)];
      // 如果该字符出现过，需要将窗口前端设为该字符上次出现位置的右边和当前窗口前端的最大值，因为字符串是不允许重复的。
      i = Math.max(charindex, i);
      // 结果为当前窗口长度和遍历中的结果的大值
      ans = Math.max(ans, j - i + 1);
      // 更新或者设置字符在s中的位置
      index[s.charAt(j)] = j + 1;
    }
    return ans;
  }

  // 测试用例

  public static void lengthOfLongestUnduplicatedSubStringTest() {
    System.out.println(SLIDING_WINDOW.lengthOfLongestUnduplicatedSubString(null));
    System.out.println(SLIDING_WINDOW.lengthOfLongestUnduplicatedSubString(""));
    System.out.println(SLIDING_WINDOW.lengthOfLongestUnduplicatedSubString("a"));
    System.out.println(SLIDING_WINDOW.lengthOfLongestUnduplicatedSubString("aaaaa"));
    System.out.println(SLIDING_WINDOW.lengthOfLongestUnduplicatedSubString("abcabcdea"));
    System.out.println(SLIDING_WINDOW.lengthOfLongestUnduplicatedSubString("wabcawbc"));
  }
}
