package com.kkk.hot100;

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
   * 双指针从两端往中心逼近计算即可，为了使容积变大，每次需要移动短的一边。
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
   * 解法：排序后，单重循环，再使用两数之和的解法查找。 <br>
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

  // ===============================================================================================

  /**
   * 42. 接雨水 <br>
   * 双指针解法，只需遍历一遍，无需辅助空间。<br>
   * 【判断某个位置能否接雨水要看它两侧是不是都有高度大于等于它的高点存在】，故两个指针更高的一方可以作为另外一方的一侧高点，<br>
   * 遍历过程中，分别记录了两侧的最高高度，那么同一侧的最大高度就是另一侧高点，因此两个指针中矮的一方可以接雨水，<br>
   * 每个位置能接到的雨水也由同一侧的最大高度决定，因为另一侧一定存在不低于其的高度，可知每次都只移动矮的那一侧指针， <br>
   * 那么当任一指针移动到最大高度位置后就再也不会移动了，因为接雨水时最大高度总是在指针另一侧。
   */
  public int trap(int[] height) {
    int ans = 0, l = 0, r = height.length - 1, lm = 0, rm = 0;
    while (l < r) {
      lm = Math.max(lm, height[l]);
      rm = Math.max(rm, height[r]);
      if (height[l] < height[r]) {
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
