package com.kkk.leetcode;

/**
 * 数组和矩阵
 *
 * @author KaiKoo
 */
public class ArrayAndMatrixExx {

  // ===============================================================================================
  /** 基础题 */

  // 将0全部移到末位，原地稳定排序
  // 解法1：类似冒泡排序 为0则交换
  public void moveZeroes1(int[] nums) {
    boolean flag = true;
    for (int i = nums.length - 1; i > 0 && flag; i--) {
      flag = false;
      for (int j = 0; j < i; j++) {
        if (nums[j] == 0 && nums[j + 1] != 0) {
          int temp = nums[j + 1];
          nums[j + 1] = nums[j];
          nums[j] = temp;
          flag = true;
        }
      }
    }
  }

  // 解法2：遍历 如果不为0则移动到前端 然后后端全设为0
  public void moveZeroes2(int[] nums) {
    int index = 0;
    for (int i = 0; i < nums.length; i++) {
      if (nums[i] != 0) {
        nums[index++] = nums[i];
      }
    }
    while (index < nums.length) {
      nums[index++] = 0;
    }
  }

  // 给定一个有序的数组 删除重复的元素，将不重复的元素全部移到数组前端
  public int removeDuplicates(int[] nums) {
    if (nums.length <= 1) {
      return nums.length;
    }
    // 从第二个元素开始，如果不等于前一个元素则移到前端
    int index = 1;
    for (int i = 1; i < nums.length; i++) {
      if (nums[i] != nums[index - 1]) {
        nums[index++] = nums[i];
      }
    }
    return index;
  }

  // 给定一个有序的数组 删除重复的元素，使每个元素最多出现两次 将不重复的元素全部移到数组前端
  public int removeDuplicates2(int[] nums) {
    if (nums.length <= 2) {
      return nums.length;
    }
    // 从第三个元素开始 直接和前面第二个元素对比即可
    int index = 2;
    for (int i = 2; i < nums.length; i++) {
      if (nums[i] != nums[index - 2]) {
        nums[index++] = nums[i];
      }
    }
    return index;
  }

  // ===============================================================================================
  /** 拔高题 */

  // n+1大小的数组，元素在[1,n]之间，只有一个元素重复出现了多次，求出该值且不能修改数组
  // 转换为求链表的环，使用快慢指针法
  // 证明至少存在一个重复的数字：快慢指针相遇就能证明
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
}
