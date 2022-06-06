package com.kkk.hot100;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class DoublePointerHot {

  /**
   * 11. 盛最多水的容器 <br>
   * 双指针从两端往中心逼近计算即可，为了使容积变大，每次需要移动短的一边。
   */
  public int maxArea(int[] height) {
    int ans = 0;
    int l = 0, r = height.length - 1;
    while (l < r) {
      ans = Math.max(ans, Math.min(height[l], height[r]) * (r - l));
      if (height[l] < height[r]) {
        l++;
      } else {
        r--;
      }
    }
    return ans;
  }

  /**
   * 15. 三数之和 <br>
   * 排序后，单重循环，使用双指针查找。 <br>
   */
  public List<List<Integer>> threeSum(int[] nums) {
    List<List<Integer>> ans = new ArrayList<>();
    Arrays.sort(nums);
    for (int i = 0; i < nums.length - 2; ++i) {
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
}
