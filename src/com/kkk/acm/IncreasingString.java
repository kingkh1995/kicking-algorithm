package com.kkk.acm;

import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class IncreasingString {

  /*
  递增字符串
  输入：
  AABBABA
  输出：
  2
    */

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    while (in.hasNext()) {
      String s = in.next();
      int[][] dp = new int[s.length()][2]; // 0以A结尾，1以B结尾
      if (s.charAt(0) == 'A') {
        dp[0][1] = 1; // 变成B要加一次修改次数
      } else {
        dp[0][0] = 1; // 变成A要加一次修改次数
      }
      for (int i = 1; i < s.length(); ++i) {
        if (s.charAt(i) == 'A') {
          dp[i][0] = dp[i - 1][0]; // 维持A不变，上一位只能是A
          dp[i][1] = Math.min(dp[i - 1][0], dp[i - 1][1]) + 1; // A改成B，上一位是A,B均可，修改次数要加一
        } else {
          dp[i][0] = dp[i - 1][0] + 1; // B改成A，上一位只能是A，修改次数要加一
          dp[i][1] = Math.min(dp[i - 1][0], dp[i - 1][1]); // 维持B不变，上一位是A,B均可
        }
      }
      System.out.println(Math.min(dp[s.length() - 1][0], dp[s.length() - 1][1])); // 两种情况的小值即可
    }
  }
}
