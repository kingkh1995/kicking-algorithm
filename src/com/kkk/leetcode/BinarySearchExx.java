package com.kkk.leetcode;

import com.kkk.supports.ArrayUtils;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 二分查找 <br>
 * 410、719
 *
 * @author KaiKoo
 */
public class BinarySearchExx {

  /**
   * 三个模板： <br>
   * 1、区间允许只有一个元素（不和任何元素对比）；2、区间至少两个元素（要和右边元素对比）；3、区间至少3个元素（要和左右元素对比）。 <br>
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

  // 在旋转排序数组中搜索元素
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
        int mid = lo + ((hi - lo) >> 1);
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
    // 找到任意一个极限值即可，可以认为 nums[-1] = nums[n] = -∞。
    // 故只需要保证区间内元素至少两个即可，使用二分查找，每次往数值大的一半寻找即可，不断逼近最终一定能找到。
    int l = 0, r = nums.length - 1;
    while (l < r) {
      int m = l + ((r - l) >> 1);
      // 比较m和m+1，因为最后一步l=r-1=m，故m+1肯定存在。
      if (nums[m] > nums[m + 1]) {
        r = m;
      } else {
        l = m + 1;
      }
    }
    // 退出循环时，l=r，返回l即可。
    return l;
  }

  // 从排序数组中找到等于target的区间
  public int[] searchRange(int[] nums, int target) {
    // 小于target的元素的个数
    int leftIdx = ArrayUtils.rank(nums, target);
    // 小于target+1的元素的个数
    int rightIdx = ArrayUtils.rank(nums, target + 1);
    return leftIdx < rightIdx ? new int[] {leftIdx, rightIdx - 1} : new int[] {-1, -1};
  }

  // 查找数组中最接近x的k个数 二分查找 + 双指针法
  public static List<Integer> findClosestElements(int[] arr, int k, int x) {
    if (k < 1 || k > arr.length) {
      throw new IllegalArgumentException();
    }
    // 找到第一个大于等于x元素的位置
    int index = ArrayUtils.rank(arr, x);
    if (index == 0) {
      return Arrays.stream(arr).limit(k).boxed().collect(Collectors.toList());
    }
    if (index == arr.length) {
      return Arrays.stream(arr).skip(arr.length - k).boxed().collect(Collectors.toList());
    }
    int low, hi;
    // 设置low hi 取index和index-1之间最接近x的那个元素
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

  // 求pow
  public static double myPow(double x, int n) {
    return n >= 0 ? quickPow(x, n) : 1 / quickPow(x, -(long) n); // 必须要转为long类型 处理-2^31次方情况
  }

  // 迭代实现
  private static double quickPow(double x, long n) {
    double temp = x;
    double ans = 1;
    // n变为2的n次幂相加的结果 则 ans转为x的（2的n次幂）次幂相乘的结果
    while (n != 0) {
      if ((n & 1) == 1) {
        ans *= temp;
      }
      n >>= 1;
      temp *= temp;
    }
    return ans;
  }

  // 从旋转数组中求最小值 （无有重复值） 不同于求极小值 数组分为左右区间且均是有序
  public int findMin0(int[] nums) {
    int lo = 0, hi = nums.length - 1;
    while (lo < hi) {
      int mid = lo + ((hi - lo) >> 1);
      // 如果已经到了右区间或数组无旋转，直接返回lo指针
      if (nums[hi] >= nums[lo]) {
        return nums[lo];
      }
      // 因为最小值位于右区间，故hi不能直接跳到mid左侧。
      if (nums[mid] >= nums[lo]) {
        lo = mid + 1;
      } else {
        hi = mid;
      }
    }
    return nums[lo];
  }

  // 从旋转数组中求最小值 （有重复值） 不同于求极小值 数组分为左右区间且均是有序
  public int findMin(int[] nums) {
    int low = 0;
    int high = nums.length - 1;
    while (low < high) {
      int pivot = low + ((high - low) >> 1);
      if (nums[pivot] < nums[high]) {
        high = pivot; // 小于必然在右区间 忽略右边
      } else if (nums[pivot] > nums[high]) {
        low = pivot + 1; // 大于必然在左区间 忽略左边
      } else {
        high -= 1; // 等于无法判断，左移一格
      }
    }
    return nums[low];
  }

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

  // 找到两个排序数组的中位数
  public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
    // 数组小的放左边 将两个数组都分为两个区间
    if (nums1.length > nums2.length) {
      return findMedianSortedArrays(nums2, nums1);
    }
    int l = 0, h = nums1.length;
    while (l <= h) {
      // 双指针
      int i = (l + h) / 2;
      int j = (nums1.length + nums2.length) / 2 - i;
      // 左边区间为i-1,j-1 右边区间为i,j 左边个数等于右边或比右边小1
      // 要保证左边区间所有值都小于右边区间
      int nums_im1 = (i == 0 ? Integer.MIN_VALUE : nums1[i - 1]); // i-1要小于j
      int nums_i = (i == nums1.length ? Integer.MAX_VALUE : nums1[i]); // i
      int nums_jm1 = (j == 0 ? Integer.MIN_VALUE : nums2[j - 1]); // j-1要小于i
      int nums_j = (j == nums2.length ? Integer.MAX_VALUE : nums2[j]); // j
      if (nums_im1 > nums_j) {
        h = i - 1;
      } else if (nums_jm1 > nums_i) {
        l = i + 1;
      } else {
        // 找到了正确的切分
        int m1 = Math.max(nums_im1, nums_jm1); // 左边区间取大值
        int m2 = Math.min(nums_i, nums_j); // 右边区间取小值
        return ((nums1.length + nums2.length) & 1) == 0 ? (m1 + m2) / 2.0 : m2; // 右边长度大于等于左边
      }
    }
    // 结果一定会匹配，因为数组是有序的，必然能找到切分，此处代码永远不会执行
    return 0;
  }

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
