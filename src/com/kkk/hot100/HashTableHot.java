package com.kkk.hot100;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

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

  /**
   * 49. 字母异位词分组 <br>
   * 将单词拆分为字符并排序，也统计单词中的字符及其出现次数，最后使用哈希表收集即可。
   */
  public List<List<String>> groupAnagrams(String[] strs) {
    HashMap<String, List<String>> map = new HashMap<>();
    for (String s : strs) {
      char[] arr = s.toCharArray();
      Arrays.sort(arr); // 字母异位词将字符排序后必然都相同
      String key = new String(arr);
      List<String> list = map.getOrDefault(key, new LinkedList<>());
      list.add(s);
      map.put(key, list);
    }
    return new ArrayList<>(map.values());
  }
}
