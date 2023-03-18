package com.kkk.acm;

import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class CentralLocation {

  /*
  服务中心选址
  */

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    while (in.hasNextInt()) {
      int n = in.nextInt();
      int[][] dist = new int[n][2];
      int left = Integer.MIN_VALUE, right = Integer.MIN_VALUE; // 最左和最右
      for (int i = 0; i < n; ++i) {
        dist[i][0] = in.nextInt();
        left = Math.min(left, dist[i][0]);
        dist[i][1] = in.nextInt();
        right = Math.max(right, dist[i][1]);
      }
      int res = 0;
      while (left < right) {
        int mid = left + (right - left) / 2;
        int d0 = getDistance(mid, dist);
        int d1 = getDistance(mid + 1, dist);
        res = Math.min(d0, d1);
        if (d0 == d1) { // 等于则直接找到了
          break;
        } else if (d0 < d1) { // 将小值留在区间内
          right = mid;
        } else {
          left = mid + 1;
        }
      }
      System.out.println(res);
    }
  }

  public static int getDistance(int location, int[][] dist) {
    int ans = 0;
    for (int[] arr : dist) {
      if (arr[0] > location) {
        ans += arr[0] - location;
      } else if (arr[1] < location) {
        ans += location - arr[1];
      }
    }
    return ans;
  }
}
