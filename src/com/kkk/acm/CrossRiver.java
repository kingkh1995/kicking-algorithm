package com.kkk.acm;

import java.util.Arrays;
import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class CrossRiver {

  /*
  士兵过河
  输入：
  5
  43
  12 13 15 20 50
  输出：
  3 40
  输入：
  5
  130
  50 12 13 15 20
  输出：
  5 128
  输入：
  7
  171
  25 12 13 15 20 35 20
  输出：
  7 171
    */

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    while (in.hasNextInt()) {
      int n = in.nextInt(), t = in.nextInt();
      int[] arr = new int[n];
      for (int i = 0; i < n; ++i) {
        arr[i] = in.nextInt();
      }
      Arrays.sort(arr);
      int[] dp = new int[n];
      dp[0] = arr[0];
      dp[1] = arr[1];
      dp[2] = Math.min(arr[0] * 10, arr[0] + arr[1] + arr[2]);
      for (int i = 3; i < n; ++i) { // 船已经在对岸，需要有人划船回来，人数超过四个时，可以选择两个辅助人划船回来。
        int dp1 = dp[i - 2] + arr[0] + arr[0] * 10; // a0回来，带上ai-1 ai
        int dp2 = dp[i - 1] + arr[0] + arr[i]; // a0回来，带上ai
        int dp3 = dp[i - 2] + arr[1] + arr[i] + arr[0] + arr[1]; // a1回来，ai带ai-1过去，a0回来，a0带a1过去
        dp[i] = Math.min(Math.min(dp1, dp2), dp3);
        if (dp[i] > t) {
          break;
        }
      }
      int count = 0, spend = 0;
      for (int i : dp) {
        if (i > t) {
          break;
        }
        count++;
        spend = i;
      }
      System.out.println(count + " " + spend);
    }
  }
}
