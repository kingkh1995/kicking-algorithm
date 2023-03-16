package com.kkk.acm;

import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class ElegantArray {

  /*
  优雅数组
  输入：
  7 2
  1 2 3 1 2 3 1
  输出：
  10
    */

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    while (in.hasNextInt()) {
      int n = in.nextInt(), k = in.nextInt(), ans = 0;
      int[] arr = new int[n];
      for (int i = 0; i < n; ++i) {
        arr[i] = in.nextInt();
      }
      for (int i = 0; i < n; ++i) {
        int[] aux = new int[n + 1]; // 统计区间内数字的频次
        int count = 0; // count为频次大于等于k的数字的个数
        for (int j = i; j < n; ++j) {
          if (++aux[arr[j]] == k) {
            count++;
          }
        }
        for (int j = n - 1; j >= i && count > 0; --j) { // count大于0时才会缩小区间
          ans++;
          // 移除当前位置，进行下一轮内循环
          if (aux[arr[j]]-- == k) {
            count--;
          }
        }
      }
      System.out.println(ans);
    }
  }
}
