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

  // ===============================================================================================
  /** 基础题 */

  // 计算右侧小于当前元素的个数
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

  // ===============================================================================================
  /** 拔高题 */

  // 两个数组的交集，元素不去重， 在nums1大小远小于num2情况下，使用二分查找
  public static int[] intersection2(int[] nums1, int[] nums2) {
    if (nums1.length == 0 || nums2.length == 0) {
      return new int[0];
    }
    // 先排序
    Arrays.sort(nums1);
    Arrays.sort(nums2);
    int[] arr = new int[nums1.length];
    int index = 0; // 记录元素个数
    for (int i = 0; i < nums1.length; ) {
      int count = 1;
      int n = nums1[i];
      // 统计相同元素个数
      for (; ++i < nums1.length; count++) {
        if (nums1[i] != n) {
          break;
        }
      }
      // 找到小于key值的元素个数 并顺序查找个数
      for (int j = ArrayUtils.rank(nums2, n);
          j < nums2.length && nums2[j++] == n && count > 0;
          count--) {
        arr[index++] = n;
      }
    }
    return Arrays.copyOfRange(arr, 0, index);
  }

  // ===============================================================================================
  /** 困难题 */

  // 有序矩阵中第K小的元素，其中每行和每列元素均按升序排序，即左上角为最小值，右下角为最大值。
  public static int kthSmallest(int[][] matrix, int k) {
    // 沿着一段锯齿线，可以保证锯齿线左上角的所有值均小于锯齿线上的值。
    // 对最小值和最大值，使用二分查找，不断确定中间值，统计出小于该值的总数。
    int n = matrix.length;
    int left = matrix[0][0];
    int right = matrix[n - 1][n - 1];
    while (left < right) {
      int mid = left + ((right - left) >> 1);
      // 从左下角出发，向右和向下确定出锯齿线，并统计左上角元素总数是否大于等于k
      int i = n - 1;
      int j = 0;
      int num = 0;
      while (i >= 0 && j < n) {
        if (matrix[i][j] <= mid) {
          // 小于等于则当前元素及其上方的所有元素均小于等于mid
          num += i + 1;
          // 移动到下一列，继续计算。
          j++;
        } else {
          // 大于则向上移动一行
          i--;
        }
      }
      if (num >= k) {
        right = mid;
      } else {
        left = mid + 1;
      }
    }
    return left;
  }
}
