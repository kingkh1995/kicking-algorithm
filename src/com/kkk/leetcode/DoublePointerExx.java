package com.kkk.leetcode;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * 双指针 <br>
 *
 * @author KaiKoo
 */
public class DoublePointerExx {

  /**
   * 16. 最接近的三数之和 <br>
   * 类似三数之和，排序后使用双指针求解，注意过滤重复组合。
   */
  public int threeSumClosest(int[] nums, int target) {
    int n = nums.length, ans = 1000000;
    Arrays.sort(nums);
    for (int i = 0; i < n - 2; ++i) {
      if (i > 0 && nums[i] == nums[i - 1]) { // 去重
        continue;
      }
      int lo = i + 1, hi = n - 1;
      while (lo < hi) {
        int sum = nums[lo] + nums[hi] + nums[i];
        if (sum == target) {
          return target;
        }
        if (Math.abs(sum - target) < Math.abs(ans - target)) { // 更新结果
          ans = sum;
        }
        if (sum > target) {
          while (--hi > lo && nums[hi] == nums[hi + 1])
            ;
        } else {
          while (++lo < hi && nums[lo] == nums[lo - 1])
            ;
        }
      }
    }
    return ans;
  }

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

  /**
   * 349. 两个数组的交集 <br>
   * 两数组元素个数相近情况下，排序后使用双指针遍历。 <br>
   * 最简单解法为使用哈希表。 <br>
   * 1、如果有序，如何优化算法？ 省略排序步骤 <br>
   * 2、如果 nums1 的大小比 nums2 小很多？ 二分查找 <br>
   * 2、如果num2元素多到无法全部加载？ a.分治 b.如果nums2去重后可以加载到内存，使用散列表 <br>
   */
  public int[] intersection(int[] nums1, int[] nums2) {
    Arrays.sort(nums1);
    Arrays.sort(nums2);
    int[] arr = new int[Math.min(nums1.length, nums2.length)];
    int count = 0;
    for (int i1 = 0, i2 = 0; i1 < nums1.length && i2 < nums2.length; ) { // 双指针 从头开始顺序查找
      int n1 = nums1[i1];
      int cmp = n1 - nums2[i2];
      if (cmp == 0) {
        arr[count++] = n1;
        i1++;
        i2++;
        while (i1 < nums1.length) { // 对i1去重，i2可省略
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

  /**
   * 524. 通过删除字母匹配到字典里最长单词 <br>
   * 子问题为【392题】，对字典单词排序，然后找到第一个匹配的单词即是结果。
   */
  public String findLongestWord(String s, List<String> dictionary) {
    dictionary.sort( // 先按长度倒序，之后按字母序排序。
        Comparator.comparingInt(String::length)
            .reversed()
            .thenComparing(Comparator.naturalOrder()));
    int n = s.length();
    int[][] dp = new int[n + 1][26]; // 使用dp[i][j]记录字符在i位置之后出现的第一个位置
    Arrays.fill(dp[n], n); // 最后一列用于在dp过程中处理边界问题
    for (int i = n - 1; i >= 0; --i) { // 从右往左推导
      for (int j = 0, c = s.charAt(i) - 'a'; j < 26; ++j) {
        dp[i][j] = j == c ? i : dp[i + 1][j];
      }
    }
    for (String word : dictionary) {
      // 在以i位置开头的子串中找到下一个字符的位置，如果为n则表示不存在即不匹配。
      for (int i = 0, j = 0; (i = dp[i][word.charAt(j) - 'a']) < n; ++i) {
        if (++j == word.length()) { // j指针走完则匹配
          return word;
        }
      }
    }
    return "";
  }
}
