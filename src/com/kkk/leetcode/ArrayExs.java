package com.kkk.leetcode;

/**
 * 数组和矩阵
 *
 * @author KaiKoo
 */
public class ArrayExs {
  // ===============================================================================================
  /** 基础题 */

  // n+1大小的数组，元素在[1,n]之间，只有一个元素重复出现了多次，求该值且不能改变数组，转换为求链表的环，快慢指针法
  // 证明至少存在一个重复的数字，快慢指针相遇就能证明
  public int findDuplicate(int[] nums) {
    int slow = 0, fast = 0;
    do {
      slow = nums[slow];
      fast = nums[nums[fast]];
    } while (slow != fast);
    slow = 0;
    do {
      slow = nums[slow];
      fast = nums[fast];
    } while (slow != fast);
    return slow;
  }

  // ===============================================================================================
  /** 拔高题 */
}
