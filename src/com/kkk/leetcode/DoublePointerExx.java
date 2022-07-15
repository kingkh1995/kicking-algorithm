package com.kkk.leetcode;

import java.util.Arrays;

/**
 * 双指针 <br>
 *
 * @author KaiKoo
 */
public class DoublePointerExx {

  /**
   * 26. 删除有序数组中的重复项 <br>
   * 80. 删除有序数组中的重复项 II <br>
   * 双指针法解答即可，需要注意比较选择的元素。
   */
  class removeDuplicatesSolution {
    public int removeDuplicates1(int[] nums) {
      if (nums.length <= 1) {
        return nums.length;
      }
      int ans = 1;
      for (int i = 1; i < nums.length; ++i) {
        if (nums[i] != nums[ans - 1]) {
          nums[ans++] = nums[i];
        }
      }
      return ans;
    }

    public int removeDuplicates2(int[] nums) {
      if (nums.length <= 2) {
        return nums.length;
      }
      int ans = 2;
      for (int i = 2; i < nums.length; ++i) {
        if (nums[i] != nums[ans - 2]) {
          nums[ans++] = nums[i];
        }
      }
      return ans;
    }
  }

  // ===============================================================================================

  // ===============================================================================================
  /** 拔高题 */

  /**
   * 两个数组的交集 <br>
   * 进阶: <br>
   * 1、如果有序，如何优化算法？ 省略排序步骤 <br>
   * 2、如果 nums1 的大小比 nums2 小很多？ 二分查找 <br>
   * 2、如果num2元素多到无法全部加载？ a.分治 b.如果nums2去重后可以加载到内存，使用散列表 <br>
   */
  // 双指针解法，两数组元素个数相近情况下
  public int[] intersection(int[] nums1, int[] nums2) {
    if (nums1.length == 0 || nums2.length == 0) {
      return new int[0];
    }
    // 先排序
    Arrays.sort(nums1);
    Arrays.sort(nums2);
    int[] arr = new int[Math.min(nums1.length, nums2.length)];
    int count = 0;
    // 双指针 从头开始顺序查找
    for (int i1 = 0, i2 = 0; i1 < nums1.length && i2 < nums2.length; ) {
      int n1 = nums1[i1];
      int cmp = n1 - nums2[i2];
      if (cmp == 0) {
        arr[count++] = n1;
        i1++;
        i2++;
        // 找到第一个不同的n1  如果不用去重省略下面的判断即可
        while (i1 < nums1.length) {
          if (nums1[i1] > n1) {
            break;
          }
          i1++;
        }
      } else if (cmp > 0) {
        i2++;
      } else {
        i1++;
      }
    }
    return Arrays.copyOfRange(arr, 0, count);
  }

  // ===============================================================================================
  /** 困难题 */
}
