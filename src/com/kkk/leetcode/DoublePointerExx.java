package com.kkk.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 双指针 <br>
 *
 * @author KaiKoo
 */
public class DoublePointerExx {

  // ===============================================================================================
  /** 基础题 */

  // 字符串中的第一个唯一字符
  public int firstUniqChar(String s) {
    for (int i = 0; i < s.length(); i++) {
      // 双指针从前后查找当前字符，如果最终位置相同则表示唯一。
      char c = s.charAt(i);
      if (s.indexOf(c) == s.lastIndexOf(c)) {
        return i;
      }
    }
    return -1;
  }

  // 三数之和为0，且组合不能重复。
  public List<List<Integer>> threeSum(int[] arr) {
    List<List<Integer>> res = new ArrayList<>();
    Arrays.sort(arr);
    for (int i = 0; i < arr.length - 2; i++) {
      // 不能重复
      if (i > 0 && arr[i] == arr[i - 1]) {
        continue;
      }
      int lo = i + 1;
      int hi = arr.length - 1;
      while (lo < hi) {
        int sum = arr[i] + arr[lo] + arr[hi];
        // 不能重复，由算法原理只需判断lo即可。
        if (lo > i + 1 && arr[lo] == arr[lo - 1]) {
          lo++;
          continue;
        }
        if (sum > 0) {
          hi--;
        } else if (sum < 0) {
          lo++;
        } else {
          res.add(Arrays.asList(arr[i], arr[lo], arr[hi]));
          lo++;
          hi--;
        }
      }
    }
    return res;
  }

  // 将所有0移动到数组的末尾，同时保持非零元素的相对顺序
  public void moveZeroes(int[] nums) {
    // 双指针，左指针指向当前已经处理好的序列的尾部，右指针指向待处理序列的头部。
    int index = 0;
    for (int i = 0; i < nums.length; i++) {
      if (nums[i] != 0) {
        nums[index++] = nums[i];
      }
    }
    // 移动完成后剩余位置置为0
    while (index < nums.length) {
      nums[index++] = 0;
    }
  }

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
