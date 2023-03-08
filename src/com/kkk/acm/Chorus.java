package com.kkk.acm;

import java.util.Scanner;

public class Chorus {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    while (in.hasNextInt()) {
      int n = in.nextInt();
      int[] arr = new int[n];
      for (int i = 0; i < n; ++i) {
        arr[i] = in.nextInt();
      }
      System.out.println(chorus(arr));
    }
  }

  private static int chorus(int[] arr) {
    // 子问题为求最长递增子序列
    int[] left = leftDP(arr), right = rightDP(arr);
    int max = 0;
    for (int i = 1; i < arr.length - 1; ++i) { // 0和n-1直接忽略
      max = Math.max(max, 1 + left[i] + right[i]);
    }
    return arr.length - max;
  }

  private static int[] leftDP(int[] arr) { // 左边最长递增子序列
    int[] dp = new int[arr.length]; // 每个位置作为最高同学，左边的最长递增子序列的长度。
    for (int i = 1; i < arr.length - 1; ++i) {
      for (int j = 0; j < i; ++j) {
        if (arr[j] < arr[i]) {
          dp[i] = Math.max(dp[i], dp[j] + 1);
        }
      }
    }
    return dp;
  }

  private static int[] rightDP(int[] arr) { // 右边最长递减子序列
    int[] dp = new int[arr.length]; // 每个位置作为最高同学，右边的最长递减子序列的长度。
    for (int i = arr.length - 2; i > 0; --i) {
      for (int j = arr.length - 1; j > i; --j) {
        if (arr[j] < arr[i]) {
          dp[i] = Math.max(dp[i], dp[j] + 1);
        }
      }
    }
    return dp;
  }
}
