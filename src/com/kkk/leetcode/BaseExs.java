package com.kkk.leetcode;

/**
 * 基础入门
 *
 * @author KaiKoo
 */
public class BaseExs {
  // =============================================================================================
  /** 二分查找 */

  // 求 x 的平方根，其中 x 是非负整数，结果只保留整数的部分，小数部分将被舍去。
  public int mySqrt(int x) {
    if (x == 1) return 1;
    // 排除掉1的情况后，hi可直接取x/2，可减少一次循环
    int lo = 0, hi = x >> 1;
    while (lo <= hi) {
      int mid = lo + ((hi - lo) >> 1); // 可防止计算溢出
      // 要转为long因为可能会超出int范围
      long cmp = x - (long) mid * mid; // 只需将一个mid强制转为long即可，剩余的会自动强制转换
      if (cmp < 0) {
        hi = mid - 1;
      } else if (cmp > 0) {
        lo = mid + 1;
      } else {
        return mid;
      }
    }
    // 为什么返回hi?
    // 如果循环结束于cmp<0，表示mid次方大于x，故需要减1，取hi即可
    // 如果循环结束于cmp>0，表示mid次方小于x，故取mid即可，而结束的时候lo=hi=mid，还是取hi
    return hi;
  }

  // ===============================================================================================
}
