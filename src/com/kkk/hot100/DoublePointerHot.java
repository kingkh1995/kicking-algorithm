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

  // ===============================================================================================

  /**
   * 42. 接雨水 <br>
   * 双指针解法，只需遍历一遍，无需辅助空间。<br>
   * 判断指针位置能否接雨水，要看它两侧是不是都有高度大于等于它的高点存在，故两个指针更高的一方可以作为另外一方的一侧高点，<br>
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
}
