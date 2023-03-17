package com.kkk.acm;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class BuySms {

  /*
  短信平台优惠活动
  输入：
  6
  10 20 30 40 60
  输出：
  70
  输入：
  15
  10 20 30 40 60 60 70 80 90 150
  输出：
  210
    */

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    while (in.hasNextInt()) {
      int m = in.nextInt();
      in.nextLine();
      String[] split = in.nextLine().trim().split(" ");
      int[][] arr = new int[split.length][2]; // 售价-短信数
      for (int i = 0; i < arr.length; ++i) {
        arr[i][0] = i + 1;
        arr[i][1] = Integer.parseInt(split[i]);
      }
      // 按性价比、售价从高到低排序
      Arrays.sort(
          arr,
          Comparator.<int[]>comparingDouble(array -> -array[1] / (double) array[0])
              .thenComparingInt(array -> -array[0]));
      int ans = 0;
      while (m > 0) {
        for (int i = 0; i < arr.length; ++i) { // 选择能选的第一种
          if (m >= arr[i][0]) {
            m -= arr[i][0];
            ans += arr[i][1];
            break;
          }
        }
      }

      System.out.println(ans);
    }
  }
}
