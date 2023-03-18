package com.kkk.acm;

import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class SubArrayWithMaxGM {

  /*
  【未通过】
  几何平均值最大子数组
  输入：
  10 2
  0.2
  0.1
  0.2
  0.2
  0.2
  0.1
  0.2
  0.2
  0.2
  0.2
  输出:
  2 2
    */

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    while (in.hasNextInt()) {
      int n = in.nextInt(), l = in.nextInt();
      double[] arr = new double[n];
      double min = Double.MAX_VALUE, max = Double.MIN_VALUE;
      for (int i = 0; i < n; ++i) {
        arr[i] = in.nextDouble();
        min = Math.min(min, arr[i]);
        max = Math.max(max, arr[i]);
      }
      int[] ans = {0, l};
      // 二分查找，逼近最大值，区间为min-max
      double left = min, right = max;
      while (left + 1e-5 < right) {
        double mid = left + (right - left) / 2;
        int[] check = check(arr, l, mid);
        if (check != null) {
          ans = check;
          left = mid;
        } else {
          right = mid;
        }
      }
      System.out.println(ans[0] + " " + ans[1]);
    }
  }

  public static int[] check(double[] arr, int k, double avg) {
    // 新数组内找到一个长度至少为k的子数组的乘积大于1
    // 求出前缀乘积，[i,j]范围内乘积 = t[j]/t[i]，记录左边最小的乘积即可，返回第一个满足条件的结果即可。
    double rt = 1, lt = 1, mlt = Double.MAX_VALUE;
    int mli = -1; // 左边最小乘积
    for (int i = 0; i < arr.length; ++i) {
      rt *= arr[i] / avg;
      if (i < k - 1) {
        continue;
      } else if (i == k - 1) {
        if (rt >= 1) {
          return new int[] {0, k};
        } else {
          lt = arr[0] / avg;
          mlt = lt;
          mli = 0;
          continue;
        }
      }
      // 区间长度满足了，(mli, i] 长度i-mli
      if (rt / mlt >= 1) {
        return new int[] {mli + 1, i - mli};
      }
      lt *= arr[i - k] / avg;
      if (lt < mlt) {
        mlt = lt;
        mli = i - k;
      }
    }
    return null;
  }
}
