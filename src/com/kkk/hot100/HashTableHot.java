package com.kkk.hot100;

import java.util.HashMap;

/**
 * 哈希表 <br>
 *
 * @author KaiKoo
 */
public class HashTableHot {

  /**
   * 1. 两数之和 <br>
   * 最优解法：哈希，顺序遍历，首先查询是否存在匹配元素，否则将元素添加到哈希表。 <br>
   * 思路一：如果元素值范围区间较小，则可以使用数组实现哈希表。 <br>
   * 思路二：如果是排序数组，则使用二分查找或双指针查找。<br>
   */
  public int[] twoSum(int[] nums, int target) {
    HashMap<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < nums.length; ++i) {
      if (map.containsKey(target - nums[i])) {
        return new int[] {i, map.get(target - nums[i])};
      }
      map.put(nums[i], i);
    }
    return new int[0];
  }
}
