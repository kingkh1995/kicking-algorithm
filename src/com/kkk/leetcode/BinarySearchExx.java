package com.kkk.leetcode;

import com.kkk.supports.ArrayUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 二分查找 <br>
 * 三个模板： <br>
 * 1、区间至少一个元素（和给定值对比）；2、区间至少两个元素（和右边元素对比）；3、区间至少3个元素（和左右元素对比）。 <br>
 * <br>
 * 410、719
 *
 * @author KaiKoo
 */
public class BinarySearchExx {

  /**
   * 69. x 的平方根 <br>
   * 模板一解法，需要考虑溢出问题。
   */
  public int mySqrt(int x) {
    if (x == 1) {
      return 1;
    }
    int lo = 1, hi = x >> 1;
    while (lo <= hi) {
      int mid = lo + ((hi - lo) >> 1);
      int cmp = Long.compare(x, (long) mid * mid); // 处理溢出
      if (cmp == 0) {
        return mid;
      } else if (cmp > 0) {
        lo = mid + 1;
      } else {
        hi = mid - 1;
      }
    }
    return hi; // 退出循环时lo=hi+1，因为返回结果是FLOOR，故返回hi。
  }

  /**
   * 74. 搜索二维矩阵 <br>
   * 一次二分查找，将矩阵视作升序的一维数组，index = i * n + j;
   */
  public boolean searchMatrix(int[][] matrix, int target) {
    int m = matrix.length, n = matrix[0].length, lo = 0, hi = m * n - 1;
    while (lo <= hi) {
      int mid = lo + (hi - lo) / 2;
      int cmp = target - matrix[mid / n][mid % n];
      if (cmp == 0) {
        return true;
      } else if (cmp > 0) {
        lo = mid + 1;
      } else {
        hi = mid - 1;
      }
    }
    return false;
  }

  /**
   * 162. 寻找峰值 <br>
   * 模板二解法，将大值保留在区间内，不断的缩小区间，直到退出循环。
   */
  public int findPeakElement(int[] nums) {
    int lo = 0, hi = nums.length - 1;
    while (lo < hi) {
      int mid = lo + ((hi - lo) >> 1);
      if (nums[mid] > nums[mid + 1]) {
        hi = mid;
      } else {
        lo = mid + 1;
      }
    }
    return lo; // 退出时lo=hi
  }

  // ===============================================================================================

  /**
   * 153. 寻找旋转排序数组中的最小值（无重复值） <br>
   * 154. 寻找旋转排序数组中的最小值 II（有重复值） <br>
   * 模板二解法，存在重复值时还需要增加等于情况的处理。
   */
  class findMinSolution {
    public int findMin1(int[] nums) {
      int lo = 0, hi = nums.length - 1;
      while (lo < hi) {
        if (nums[lo] < nums[hi]) { // 如果当前区间已经是单调了则可以直接返回
          return nums[lo];
        }
        int mid = lo + ((hi - lo) >> 1);
        if (nums[mid] < nums[hi]) { // 最小值是位于右边区间
          hi = mid;
        } else {
          lo = mid + 1;
        }
      }
      return nums[lo];
    }

    public int findMin2(int[] nums) {
      int lo = 0, hi = nums.length - 1;
      while (lo < hi) {
        if (nums[lo] < nums[hi]) {
          return nums[lo];
        }
        int mid = lo + ((hi - lo) >> 1);
        int cmp = nums[mid] - nums[hi];
        if (cmp < 0) {
          hi = mid;
        } else if (cmp > 0) {
          lo = mid + 1;
        } else { // 相等情况无法判断，只能hi左移动一位，因为最小值位于hi左边。
          hi--;
        }
      }
      return nums[lo];
    }
  }

  /** 315. 计算右侧小于当前元素的个数 <br> */
  public int[] countSmaller(int[] nums) {
    int n = nums.length;
    int[] ans = new int[n];
    int[] sorted = new int[n];
    // 从右往左插入排序。
    for (int i = n - 1; i >= 0; --i) {
      // 当前排序数组的长度
      int bound = n - 1 - i;
      // 使用二分查找获取元素应该插入的位置
      int rank = ArrayUtils.rank(sorted, nums[i], 0, bound - 1);
      // rank即为有序数组中比当前元素小的元素的个数，因为是从右往左依次添加，即为元素右边比其小的元素的个数。
      ans[i] = rank;
      // 数组后部分元素右移一位，然后插入元素。
      System.arraycopy(sorted, rank, sorted, rank + 1, bound - rank);
      sorted[rank] = nums[i];
    }
    return ans;
  }

  /**
   * 350. 两个数组的交集 II <br>
   * 在nums1大小远小于num2情况下，使用二分查找
   */
  public int[] intersection(int[] nums1, int[] nums2) {
    Arrays.sort(nums1);
    Arrays.sort(nums2);
    int[] arr = new int[nums1.length];
    int index = 0; // 记录元素个数
    for (int i = 0; i < nums1.length; ) {
      int count = 1;
      int n = nums1[i];
      for (; ++i < nums1.length; count++) { // 统计相同元素个数
        if (nums1[i] != n) {
          break;
        }
      }
      for (int j = ArrayUtils.rank(nums2, n); // 找到小于key值的元素个数 并顺序查找个数
          j < nums2.length && nums2[j++] == n && count > 0;
          count--) {
        arr[index++] = n;
      }
    }
    return Arrays.copyOfRange(arr, 0, index);
  }

  /**
   * 378. 有序矩阵中第 K 小的元素 <br>
   * 沿着一段矩阵内锯齿线，可以保证锯齿线左上方的所有值均小于锯齿线对应的值。 <br>
   * 数组最小值为左上角，最大值为右下角，使用二分查找，不断确定中间值，统计出小于该值的总数直到满足条件。<br>
   */
  public int kthSmallest(int[][] matrix, int k) {
    int n = matrix.length, left = matrix[0][0], right = matrix[n - 1][n - 1];
    while (left < right) {
      int mid = left + ((right - left) >> 1); // 确定中间值
      int i = n - 1, j = 0, count = 0;
      while (i >= 0 && j < n) { // 从左下角出发，向右（增大）和向上（减小）确定出锯齿线，并统计左上角元素总数是否大于等于k
        if (matrix[i][j] <= mid) { // 小于等于则右移，大于则上移。
          j++;
          count += i + 1; // 增加统计值
        } else {
          i--;
        }
      }
      if (count >= k) { // 判断以缩小数据范围
        right = mid;
      } else {
        left = mid + 1;
      }
    }
    return left;
  }

  /**
   * 658. 找到 K 个最接近的元素 <br>
   * 模板二变种解法，确定出区间的最左端，判断mid是否位于目标区间内，并不断缩进。
   */
  public List<Integer> findClosestElements(int[] arr, int k, int x) {
    int lo = 0, hi = arr.length - k;
    while (lo < hi) {
      int mid = lo + ((hi - lo) >> 1);
      if (x - arr[mid] > arr[mid + k] - x) { // 目标区间左端肯定小于x，右端肯定大于x。
        lo = mid + 1;
      } else {
        hi = mid;
      }
    }
    List<Integer> ans = new ArrayList<>(k);
    for (int i = 0; i < k; i++) {
      ans.add(arr[i + lo]);
    }
    return ans;
  }
}
