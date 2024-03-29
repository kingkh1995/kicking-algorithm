package com.kkk.hot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 双指针 <br>
 *
 * @author KaiKoo
 */
public class DoublePointerHot {

  /**
   * 11. 盛最多水的容器 <br>
   * 双指针从两端往中心逼近计算即可，只有移动较短的一边才有可能使得容积增大。
   */
  public int maxArea(int[] height) {
    int ans = 0, l = 0, r = height.length - 1;
    while (l < r) {
      if (height[l] < height[r]) {
        ans = Math.max(ans, height[l] * (r - l));
        ++l;
      } else {
        ans = Math.max(ans, height[r] * (r - l));
        --r;
      }
    }
    return ans;
  }

  /**
   * 15. 三数之和 <br>
   * 排序后，单重循环双指针解法。 <br>
   */
  public List<List<Integer>> threeSum(int[] nums) {
    List<List<Integer>> ans = new ArrayList<>();
    Arrays.sort(nums);
    for (int i = 0; i < nums.length - 2 && nums[i] <= 0; ++i) { // nums[i]>0时可直接终止
      // 不能重复
      if (i > 0 && nums[i] == nums[i - 1]) {
        continue;
      }
      // 双指针查找
      int l = i + 1, r = nums.length - 1;
      while (l < r) {
        // 不能重复，由算法原理可知，只需要判断一边即可。
        if (l > i + 1 && nums[l] == nums[l - 1]) {
          l++;
          continue;
        }
        int sum = nums[i] + nums[l] + nums[r];
        if (sum > 0) {
          r--;
        } else if (sum < 0) {
          l++;
        } else {
          // 找到匹配，均往中心逼近一位，继续查找。
          ans.add(Arrays.asList(nums[i], nums[l], nums[r]));
          l++;
          r--;
        }
      }
    }
    return ans;
  }

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
          while (++lo < hi && nums[lo] == nums[lo - 1]) // 根据算法原理，可以只判断一边即可。
            ;
        }
      }
    }
    return ans;
  }

  /** 26. 删除有序数组中的重复项 <br> */
  public int removeDuplicates(int[] nums) {
    int index = 1; // 双指针，原地修改。
    for (int i = 1; i < nums.length; ++i) {
      if (nums[i] != nums[index - 1]) {
        nums[index++] = nums[i];
      }
    }
    return index;
  }

  /**
   * 88. 合并两个有序数组 <br>
   * 双指针归并，为了不覆盖原数组，从右往左归并。
   */
  public void merge(int[] nums1, int m, int[] nums2, int n) {
    int p1 = m - 1, p2 = n - 1;
    for (int p = nums1.length - 1; p2 >= 0; p--) { // 终止条件p2为0即可
      if (p1 >= 0 && nums1[p1] > nums2[p2]) {
        nums1[p] = nums1[p1--];
      } else {
        nums1[p] = nums2[p2--];
      }
    }
  }

  /**
   * 392. 判断子序列 <br>
   * 【双指针解法】，如果存在大量输入（【524题】），则预处理字符串t后，可加快判断过程。
   */
  public boolean isSubsequence(String s, String t) {
    int m = s.length(), n = t.length(), i = 0;
    for (int j = 0; i < m && j < n; ++j) {
      if (s.charAt(i) == t.charAt(j)) {
        ++i;
      }
    }
    return i == m;
  }

  /**
   * 844. 比较含退格的字符串 <br>
   * 最优解法，双指针，从右往左遍历比较，逻辑处理比较复杂。 <br>
   */
  public boolean backspaceCompare(String s, String t) {
    int i = s.length() - 1, j = t.length() - 1, skip;
    while (i >= 0 || j >= 0) { // 注意：只要有一个字符串非空就要处理
      skip = 0;
      while (i >= 0) {
        if (s.charAt(i) == '#') {
          ++skip;
          --i;
        } else if (skip > 0) {
          --skip;
          --i;
        } else {
          break;
        }
      }
      skip = 0;
      while (j >= 0) {
        if (t.charAt(j) == '#') {
          ++skip;
          --j;
        } else if (skip > 0) {
          --skip;
          --j;
        } else {
          break;
        }
      }
      if (i < 0 || j < 0) {
        break;
      } else if (s.charAt(i--) != t.charAt(j--)) { // 此时必然非'#'一旦不同则表示不匹配
        return false;
      }
    }
    return i == j; // i&j必须都为-1
  }

  // ===============================================================================================

  /**
   * 42. 接雨水 <br>
   * 双指针解法，只需遍历一遍，无需辅助空间。<br>
   * 【判断某个位置能否接雨水要看它两侧是不是都有高度大于等于它的高点存在】，故两个指针更高的一方可以作为另外一方的一侧高点，<br>
   * 遍历过程中，分别记录了两侧的最高高度，那么同一侧的最大高度就是另一侧高点，因此两个指针中矮的一方可以接雨水，<br>
   * 每个位置能接到的雨水也由同一侧的最大高度即可决定，因为接雨水时最大高度总是在指针另一侧，即另一侧一定存在不低于其的高度。 <br>
   * 可知每次都只移动矮的那一侧指针，即扫描过程中一到遇到了当前的最大高度则该侧的指针就不会再移动了，直到另一侧出现更高的高度。
   */
  public int trap(int[] height) {
    int ans = 0, l = 0, r = height.length - 1, lm = 0, rm = 0;
    while (l < r) {
      lm = Math.max(lm, height[l]); // 左侧最大高点（包含左指针）
      rm = Math.max(rm, height[r]); // 右侧最大高点（包含右指针）
      if (height[l] < height[r]) { // 较低的一段可以接雨水
        ans += lm - height[l];
        l++;
      } else {
        ans += rm - height[r];
        r--;
      }
    }
    return ans;
  }

  /**
   * 581. 最短无序连续子数组 <br>
   * 左边升序区间的每一个值都小于该值右边的所有元素，右边升序区间的每一个值都大于该值左边的每一个元素。 <br>
   * 从左遍历扫描每一个元素以确认右窗口的位置，从右遍历扫描每一个元素以确认左窗口的位置，<br>
   * 只需遵守上面原则即可，窗口左右端的确认互不干扰，因此可以在同一个循环内同时处理。
   */
  public int findUnsortedSubarray(int[] nums) {
    int n = nums.length, l = -1, r = -1, lMax = Integer.MIN_VALUE, rMin = Integer.MAX_VALUE;
    for (int i = 0; i < nums.length; ++i) { // 同时处理左右窗口，[left,right]为结果区间。
      if (nums[i] < lMax) { // 从左往右遍历的指针为i
        r = i;
      } else {
        lMax = nums[i];
      }
      if (nums[n - 1 - i] > rMin) { // 从右往左遍历的指针为n - 1 - i
        l = n - 1 - i;
      } else {
        rMin = nums[n - 1 - i];
      }
    }
    return r == -1 ? 0 : r - l + 1;
  }
}
