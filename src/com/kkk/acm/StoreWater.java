package com.kkk.acm;

import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class StoreWater {

  /*
  能储水多少？
  输入：
  12
  0 1 0 2 1 0 1 3 2 1 2 1
  输出：
  17
   */

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    while (in.hasNextInt()) {
      int n = in.nextInt();
      int[] arr = new int[n];
      for (int i = 0; i < n; ++i) {
        arr[i] = in.nextInt();
      }
      int ans = 0, left = 0, right = n - 1, lm = 0, rm = 0;
      while (left < right) { // 左侧的最高点和右侧最高两者取低。
        // left左侧最高点，已经确定但右侧最高点无法确定，只要保证当前左侧最高点低于目前确定的右侧最高点，则不需要再考虑右边。
        // 如果lm<=rm，则可以确定左边，因为右边必然存在比它高的木板了，且左边的最高点已经确定出来了，同理相反则确定右边。
        lm = Math.max(lm, arr[left]);
        rm = Math.max(rm, arr[right]);
        if (lm <= rm) {
          ans += lm; // 计算left右边一格
          left++;
        } else {
          ans += rm; // 计算right左边一格
          right--;
        }
      }
      System.out.println(ans);
    }
  }
}
