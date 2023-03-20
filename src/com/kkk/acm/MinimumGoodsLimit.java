package com.kkk.acm;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class MinimumGoodsLimit {

  /*
  统一限载货物数最小值
  输入：
  4
  3 2 6 3
  0 1 1 0
  2
  输出：
  6
  输入：
  4
  3 2 6 8
  0 1 1 1
  1
  输出：
  16
    */

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    while (in.hasNextInt()) {
      int n = in.nextInt();
      int[] goods = new int[n];
      int dryTotal = 0, dryMax = Integer.MIN_VALUE, wetTotal = 0, wetMax = Integer.MIN_VALUE;
      for (int i = 0; i < n; ++i) {
        goods[i] = in.nextInt();
      }
      List<Integer> dryGoods = new ArrayList<>();
      List<Integer> wetGoods = new ArrayList<>();
      for (int i = 0; i < n; ++i) {
        if (in.nextInt() == 1) {
          wetGoods.add(goods[i]);
          wetTotal += goods[i];
          wetMax = Math.max(wetMax, goods[i]);
        } else {
          dryGoods.add(goods[i]);
          dryTotal += goods[i];
          dryMax = Math.max(dryMax, goods[i]);
        }
      }
      int k = in.nextInt();
      int ans =
          Math.max(
              findMinLimit(k, wetGoods, wetTotal, wetMax),
              findMinLimit(k, dryGoods, dryTotal, dryMax));
      System.out.println(ans);
    }
  }

  public static int findMinLimit(int k, List<Integer> goods, int total, int max) {
    // 最大值即total，最小值是max(max,avg)
    int left = Math.max(max, (total + k - 1) / k), right = total, ans = right + 1;
    while (left <= right) {
      int mid = left + (right - left) / 2;
      if (check(k, goods, mid)) { // 够装可以变小
        ans = mid;
        right = mid - 1;
      } else {
        left = mid + 1;
      }
    }

    return ans;
  }

  public static boolean check(int k, List<Integer> goods, int limit) {
    int count = 0;
    for (int n : goods) {
      if (count + n > limit) { // 换下一辆车
        if (k == 0) { // 没车了，失败
          return false;
        }
        count = n;
        k--;
      } else { // 当前车够装
        count += n;
      }
    }
    return true;
  }
}
