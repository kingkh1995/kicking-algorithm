package com.kkk.leetcode;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 二分查找 <br>
 *
 * @author KaiKoo
 */
public class BinarySearchExs {

  /**
   * 三个模板：1、区间允许只有一个元素；2、区间至少两个元素；3、区间至少3个元素。 <br>
   * <br>
   */

  // ===============================================================================================
  /** 基础题 */

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

  // 求最小值，不同于求极小值 分为左右区间均是有序
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

  // 从排序数组中找到等于target的区间
  public int[] searchRange(int[] nums, int target) {
    // 找到大于target-1的第一个位置
    int leftIdx = binarySearch(nums, target - 1); // 必然大于target-1
    // 找到大于target的第一个位置左移一位（不存在则为右端点）//必然大于等于target
    int rightIdx = binarySearch(nums, target) - 1;
    // 首先判断区间存在，特殊情况是不存在target，left&right则均指向第一个大于target的元素，所以需要判断是否为target
    if (leftIdx <= rightIdx && nums[leftIdx] == target) {
      return new int[] {leftIdx, rightIdx};
    }
    return new int[] {-1, -1};
  }

  // 第一个大于 target 的数的下标
  public static int binarySearch(int[] nums, int target) {
    int left = 0, right = nums.length - 1, ans = nums.length; // 默认为右边界加一
    // 使用二分查找 去查找target并且每次将大值设为ans
    while (left <= right) {
      int mid = (left + right) / 2;
      if (nums[mid] > target) {
        right = mid - 1;
        ans = mid;
      } else {
        left = mid + 1;
      }
    }
    return ans;
  }

  // 查找数组中最解决x的k个数
  public static List<Integer> findClosestElements(int[] arr, int k, int x) {
    if (k < 1 || k > arr.length) {
      throw new IllegalArgumentException();
    }
    // 找到第一个大于x的元素
    int index = binarySearch(arr, x);
    if (index == 0) {
      return Arrays.stream(arr).limit(k).boxed().collect(Collectors.toList());
    }
    if (index == arr.length) {
      return Arrays.stream(arr).skip(arr.length - k).boxed().collect(Collectors.toList());
    }
    int low, hi;
    // 设置low hi 取index index-1最接近的那个
    if (Math.abs(arr[index] - x) <= Math.abs(arr[index - 1] - x)) {
      low = hi = index;
    } else {
      low = hi = index - 1;
    }
    while (hi - low < k - 1) {
      if (low == 0) {
        hi = k - 1;
      } else if (hi == arr.length - 1) {
        low = arr.length - k;
      } else if (Math.abs(arr[low - 1] - x) <= Math.abs(arr[hi + 1] - x)) {
        low--;
      } else {
        hi++;
      }
    }
    return Arrays.stream(arr).skip(low).limit(k).boxed().collect(Collectors.toList());
  }

  // ===============================================================================================
  /** 拔高题 */
}
