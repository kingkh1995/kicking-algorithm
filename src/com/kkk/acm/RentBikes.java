package com.kkk.acm;

import java.util.Arrays;
import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class RentBikes {

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int m = in.nextInt(), n = in.nextInt();
    int[] arr = new int[n];
    for (int i = 0; i < n; ++i) {
      arr[i] = in.nextInt();
    }
    Arrays.sort(arr);
    int count = 0;
    for (int lo = 0, hi = n - 1; lo <= hi; count++) { // 边界lo=hi也是count++
      if (arr[lo] + arr[hi] > m) {
        hi--;
      } else {
        hi--;
        lo++;
      }
    }
    System.out.println(count);
  }
}
