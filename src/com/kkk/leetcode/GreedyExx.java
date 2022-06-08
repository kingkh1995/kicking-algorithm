package com.kkk.leetcode;

/**
 * 贪心算法 <br>
 * 不从整体最优上加以考虑，而是考虑某种意义上的局部最优解。
 *
 * @author KaiKoo
 */
public class GreedyExx {

  // ===============================================================================================
  /** 基础题 */

  // 判断这个数组中是否存在长度为 3 的递增子序列
  public boolean increasingTriplet(int[] nums) {
    if (nums.length < 3) {
      return false;
    }
    // 贪心算法，维护前两个数。
    int first = nums[0], second = Integer.MAX_VALUE;
    // 如果遍历过程中遇到小于first 的元素，则会用该元素更新first，虽然更新后的first出现在second的后面，
    // 但是在second的前面一定存在一个元素first小于second，因此当遇到大于second的元素时，即存在递增的三元子序列。
    for (int i = 1; i < nums.length; ++i) {
      int n = nums[i];
      if (n > second) {
        return true;
      } else if (n > first) {
        second = n;
      } else {
        first = n;
      }
    }
    return false;
  }
}

// ===============================================================================================
/** 拔高题 */
