package com.kkk.acm;

import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class PerfectMove {

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    String s = in.next();
    int length = s.length();
    int[] count = new int[4];
    for (char c : s.toCharArray()) {
      count[getIndex(c)]++;
    }
    boolean flag = true;
    for (int c : count) {
      if (c != length / 4) {
        flag = false;
      }
    }
    if (flag) {
      System.out.println(0);
      return;
    }
    for (int n = 1; n < length; ++n) { // 子串长度从1开始，每次增加一，直到匹配。
      for (int i = 0; i + n <= length; ++i) {
        if (match(s, i, i + n - 1)) { // 子串区间为[i, i+n-1]
          System.out.println(n);
          return;
        }
      }
    }
  }

  public static boolean match(String s, int lo, int hi) { // 如果子串之外的区间，每个方向的次数不超过length/4，则当前子串符合条件。
    int[] count = new int[4];
    // 统计区间外的字母
    for (int j = 0; j < s.length(); ++j) {
      if (j == lo) { // 跳过中间区间
        j = hi;
        continue;
      }
      if (++count[getIndex(s.charAt(j))] > s.length() / 4) {
        return false;
      }
    }
    return true;
  }

  public static int getIndex(char c) {
    if ('W' == c) {
      return 0;
    } else if ('A' == c) {
      return 1;
    } else if ('S' == c) {
      return 2;
    } else {
      return 3;
    }
  }
}
