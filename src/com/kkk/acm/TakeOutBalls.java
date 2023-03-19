package com.kkk.acm;

import java.util.Arrays;
import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class TakeOutBalls {

  /*
  开放日活动、取出尽量少的球
  输入：
  14 7
  2 3 2 5 5 1 4
  输出：
  [0,1,0,3,3,0,2]
     */

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int sum = in.nextInt(), n = in.nextInt();
    int[] arr = new int[n];
    int total = 0, max = 0; // 记录最大值作为cap的上限
    for (int i = 0; i < n; ++i) {
      arr[i] = in.nextInt();
      max = Math.max(max, arr[i]);
      total += arr[i];
    }
    if (total <= sum) {
      System.out.println("[]");
      return;
    }
    int lo = 0, hi = max, cap = max; // 二分查找确定cap
    while (lo <= hi) {
      int mid = lo + (hi - lo) / 2; // 最大容量
      int t = 0;
      for (int i : arr) {
        t += Math.min(i, mid);
      }
      t -= sum;
      if (t > 0) { // 需要变小
        hi = mid - 1;
        cap = mid;
      } else {
        lo = mid + 1;
      }
    }
    cap--; // cap为总和大于sum的最小值，需要减1才是maxCapacity。
    int[] ans = new int[n];
    for (int i = 0; i < n; ++i) {
      ans[i] = Math.max(0, arr[i] - cap);
    }
    System.out.println(Arrays.toString(ans).replace(" ", ""));
  }
}
