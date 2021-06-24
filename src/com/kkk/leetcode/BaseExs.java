package com.kkk.leetcode;

/**
 * 基础入门
 *
 * @author KaiKoo
 */
public class BaseExs {
  // =============================================================================================

  /**
   * 二分查找 <br>
   * 三个模板：1、区间允许只有一个元素；2、区间至少两个元素；3、区间至少3个元素。 <br>
   * <br>
   */

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
    // 为什么返回hi?最后一轮循环lo=hi=mid
    // 如果循环结束于cmp<0，表示mid次方大于x，故需要减1，最后一轮循环结束后，hi=mid-1，所以取hi
    // 如果循环结束于cmp>0，表示mid次方小于x，故取mid即可，而最后一轮循环后，hi未变，还是等于mid
    return hi;
  }

  // 搜索旋转排序数组
  public static int search(int[] nums, int target) {
    int lo = 0, hi = nums.length - 1;
    int left = nums[lo], right = nums[hi];
    // 先和左右对比，这样之后的对比可以去掉=判断
    if (target == left) {
      return lo;
    } else if (target == right) {
      return hi;
    } else {
      while (lo <= hi) {
        int mid = lo + (hi - lo) / 2;
        int cmp = target - nums[mid];
        if (cmp == 0) {
          return mid;
        }
        // 找到有序的一半数组，必然至少有一半数组是有序的，不需要取lo hi处的元素，只需查两端即可
        if (nums[mid] >= left) {
          // 如果左边有序 判断是否在该区间（小于mid且大于left）则在该区间查找 否则去另一半区间查找
          if (cmp < 0 && target > left) {
            hi = mid - 1;
          } else {
            lo = mid + 1;
          }
        } else {
          // 如果右边有序 也判断是否在该区间（大于mid小于right）则在该区间查找 否则去另一半区间查找
          if (cmp > 0 && target < right) {
            lo = mid + 1;
          } else {
            hi = mid - 1;
          }
        }
      }
      return -1;
    }
  }

  // 求区间极小值
  public int findPeakElement(int[] nums) {
    if (nums.length == 1) {
      return 0;
    }
    // 找到任意一个极限值 保证区间元素至少有两个
    int l = 0, r = nums.length - 1;
    while (l < r) {
      int m = l + (r - l) / 2;
      // 最后一步l=m=r-1 所以先判断右边 并保证右边一定是小值
      if (nums[m] > nums[m + 1]) {
        r = m;
      } else {
        l = m + 1;
      }
    }
    return l;
  }

  // 求最小值，不同于求极小值
  public static int findMin(int[] nums) {
    // 保证区间元素至少有两个 区间长度为1时特殊判断
    if (nums.length == 1) {
      return nums[0];
    }
    int l = 0, r = nums.length - 1;
    while (l < r) {
      int m = l + (r - l) / 2;
      if (nums[m] > nums[r]) { // 中点大于说明最小值在mid右边 否则在左边 保证右边是右区间的小值
        l = m + 1;
      } else {
        r = m;
      }
    }
    return nums[l];
  }

  // ===============================================================================================
}
