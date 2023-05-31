package com.kkk.hot;

import com.kkk.supports.ArrayUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 二分查找 <br>
 *
 * @author KaiKoo
 */
public class BinarySearchHot {

  /**
   * 15. 三数之和 <br>
   * 排序后，双重循环使用二分查找。 <br>
   */
  public List<List<Integer>> threeSum(int[] nums) {
    List<List<Integer>> ans = new ArrayList<>();
    Arrays.sort(nums);
    for (int i = 0; i < nums.length - 2; ++i) {
      // 不能重复
      if (i > 0 && nums[i] == nums[i - 1]) {
        continue;
      }
      for (int j = i + 1; j < nums.length - 1; ++j) {
        // 不能重复
        if (j > i + 1 && nums[j] == nums[j - 1]) {
          continue;
        }
        // 二分查找
        int lo = j + 1, hi = nums.length - 1;
        int t = -nums[i] - nums[j];
        while (lo <= hi) {
          int mid = lo + ((hi - lo) >> 1);
          if (nums[mid] < t) {
            lo = mid + 1;
          } else if (nums[mid] > t) {
            hi = mid - 1;
          } else {
            ans.add(Arrays.asList(nums[i], nums[j], t));
            break;
          }
        }
      }
    }
    return ans;
  }

  /**
   * 33. 搜索旋转排序数组 <br>
   * mid的左区间或右区间一定至少有一个是有序的，未旋转的情景下则都是有序的。
   */
  public int search(int[] nums, int target) {
    int n = nums.length, lo = 0, hi = n - 1;
    while (lo <= hi) {
      int mid = lo + ((hi - lo) >> 1);
      int cmp = nums[mid] - target;
      if (cmp == 0) {
        return mid;
      } else if (nums[mid] >= nums[0]) { // 确定出必定是有序的半区后，判断target是否位于该半区。
        // mid左半边区间为有序的，且target位于该半区，则移动hi，否则移动lo。
        if (target >= nums[0] && cmp > 0) {
          hi = mid - 1;
        } else {
          lo = mid + 1;
        }
      } else {
        // mid右半边区间为有序的，且target位于该半区，则移动lo，否则移动hi。
        if (target <= nums[n - 1] && cmp < 0) {
          lo = mid + 1;
        } else {
          hi = mid - 1;
        }
      }
    }
    return -1;
  }

  /**
   * 34. 在排序数组中查找元素的第一个和最后一个位置 <br>
   * 使用rank方法查找 target 和 target+1 即可，处理 target 不存在的情况。
   */
  public int[] searchRange(int[] nums, int target) {
    int i1 = ArrayUtils.rank(nums, target), i2 = ArrayUtils.rank(nums, target + 1);
    return i1 == i2 ? new int[] {-1, -1} : new int[] {i1, i2 - 1};
  }

  /**
   * 162. 寻找峰值 <br>
   * 模板二解法，mid与mid+1对比，将大值保留在区间内，不断的缩小区间，直到退出循环。
   */
  public int findPeakElement(int[] nums) {
    int lo = 0, hi = nums.length - 1;
    while (lo < hi) {
      int mid = lo + ((hi - lo) >> 1);
      if (nums[mid] < nums[mid + 1]) {
        lo = mid + 1;
      } else {
        hi = mid;
      }
    }
    return lo; // 退出时lo=hi
  }

  // ===============================================================================================

  /**
   * 4. 寻找两个正序数组的中位数 <br>
   * 使用二分查找确定出两个数组的划分，使得划分左侧元素全部小于右侧元素。
   */
  public double findMedianSortedArrays(int[] nums1, int[] nums2) {
    // 数组小的放左边，在小数组上二分查找。
    if (nums1.length > nums2.length) {
      return findMedianSortedArrays(nums2, nums1);
    }
    int lo = 0, hi = nums1.length; // hi可以为nums1.length
    int ml = 0, mh = 0; // 左侧区间的最大值和右侧区间的最小值
    while (lo <= hi) {
      int mid1 = lo + ((hi - lo) >> 1);
      // mid位于右侧区间，左侧区间元素总数等于或比右侧区间元素总数小1
      int mid2 = ((nums1.length + nums2.length) >> 1) - mid1;
      int n1m1 = mid1 == 0 ? Integer.MIN_VALUE : nums1[mid1 - 1]; // mid1-1
      int n1 = mid1 == nums1.length ? Integer.MAX_VALUE : nums1[mid1]; // mid1
      int n2m1 = mid2 == 0 ? Integer.MIN_VALUE : nums2[mid2 - 1]; // mid2-1
      int n2 = mid2 == nums2.length ? Integer.MAX_VALUE : nums2[mid2]; // mid2
      if (n1m1 <= n2) {
        // mid1-1小于mid2则更新值，另一条件mid2-1小于mid1会在后续缩小区间中满足。
        ml = Math.max(n1m1, n2m1);
        mh = Math.min(n1, n2);
        lo = mid1 + 1;
      } else {
        hi = mid1 - 1;
      }
    }
    // 元素总数为奇数则直接返回右侧值，因为右侧元素多1。
    return ((nums1.length + nums2.length) & 1) == 1 ? mh : (ml + mh) / 2D;
  }

  /**
   * 287. 寻找重复数 <br>
   * todo... 二分查找解法 <br>
   */
}
