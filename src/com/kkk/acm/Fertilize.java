package com.kkk.acm;

import java.util.Arrays;
import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class Fertilize {

  /*
  农场施肥、不爱施肥的小布
  输入：
  6 7
  1 2 2 2 2 6
  输出：
  3
     */

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int m = in.nextInt(), n = in.nextInt();
    if (m > n) { // 每天最多浇一块地，则永远无法完成。
      System.out.println(-1);
      return;
    }
    int[] fields = new int[m];
    for (int i = 0; i < m; ++i) {
      fields[i] = in.nextInt();
    }
    Arrays.sort(fields);
    // 二分查找确定值的区间位置，值肯定在数组最小值和最大之间。
    int lo = 0, hi = m - 1, rank = hi + 1;
    while (lo <= hi) {
      int mid = lo + (hi - lo) / 2;
      if (fertilize(n, fields[mid], fields)) { // 如果能施肥完成，则施肥机效能还可以减少
        hi = mid - 1;
        rank = mid; // rank位置为数组内最小能满足施肥完成的元素
      } else {
        lo = mid + 1;
      }
    }
    // rank位置为数组内最小能满足施肥完成的元素，但是最终结果可能不等于数组内元素。
    for (int i = fields[rank - 1] + 1; i <= fields[rank]; ++i) {
      if (fertilize(n, i, fields)) { // 找到第一个满足条件的结果即可
        System.out.println(i);
        return;
      }
    }
  }

  public static boolean fertilize(int n, int k, int[] fields) { // 判断能否施肥完成
    int count = 0;
    for (int f : fields) {
      count += (f - 1) / k + 1;
      if (count > n) {
        return false;
      }
    }
    return true;
  }
}
