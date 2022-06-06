package com.kkk.hot100;

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
        int l = j + 1, h = nums.length - 1;
        int t = -nums[i] - nums[j];
        while (l <= h) {
          int mid = l + ((h - l) >> 1);
          if (nums[mid] < t) {
            l = mid + 1;
          } else if (nums[mid] > t) {
            h = mid - 1;
          } else {
            ans.add(Arrays.asList(nums[i], nums[j], t));
            break;
          }
        }
      }
    }
    return ans;
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
    int l = 0, r = nums1.length;
    int ml = 0, mh = 0; // 左侧区间的最大值和右侧区间的最小值
    while (l <= r) {
      int mid1 = l + ((r - l) >> 1);
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
        l = mid1 + 1;
      } else {
        r = mid1 - 1;
      }
    }
    // 元素总数为奇数则直接返回右侧值，因为右侧元素多1。
    return ((nums1.length + nums2.length) & 1) == 1 ? mh : (ml + mh) / 2D;
  }

}
