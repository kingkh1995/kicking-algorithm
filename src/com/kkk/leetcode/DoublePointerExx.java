package com.kkk.leetcode;

import java.util.Arrays;
import java.util.List;

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

  /**
   * 524. 通过删除字母匹配到字典里最长单词 <br>
   * 先排序，然后使用双指针判断即可，因为字符全为小写字母则可以预处理字符串加快匹配过程。
   */
  public String findLongestWord(String s, List<String> dictionary) {
    int n = s.length();
    // 使用动态规划预处理字符串，dp[i][j]表示从i位置开始字符出现的下一个位置。
    int[][] dp = new int[n + 1][26];
    Arrays.fill(dp[n], n); // 最后一列用于边界处理，设置初始值。
    for (int i = n - 1; i >= 0; --i) { // 从右往左计算
      for (int j = 0; j < 26; ++j) {
        dp[i][j] = s.charAt(i) == 'a' + j ? i : dp[i + 1][j];
      }
    }
    dictionary.sort(
        (s1, s2) -> {
          int cmp = s2.length() - s1.length();
          return cmp == 0 ? s1.compareTo(s2) : cmp;
        });
    for (String word : dictionary) {
      for (int i = 0, j = 0; (i = dp[i][word.charAt(j) - 'a']) < n; ++i) {
        if (++j == word.length()) {
          return word;
        }
      }
    }
    return "";
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
}
