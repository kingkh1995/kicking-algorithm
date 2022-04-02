package com.kkk.leetcode;

import java.util.HashMap;

/**
 * 滑动窗口 <br>
 *
 * @author KaiKoo
 */
public class SlidingWindowExx {

  // ===============================================================================================
  /** 基础题 */

  // ===============================================================================================
  /** 拔高题 */

  // 请你判断数组中是否存在两个不同下标 i 和 j，使得abs(nums[i] - nums[j]) <= t ，同时又满足 abs(i -j) <= k
  public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
    // 使用桶排序算法，按数字范围t+1收集数组中的数字，同时维护长度为k的滑动窗口。
    if (t < 0) {
      return false;
    }
    // 使用map，因为一个桶内最多只会有一个数字。
    HashMap<Long, Long> map = new HashMap<>();
    int n = nums.length;
    long w = t + 1;
    for (int i = 0; i < n; i++) {
      // 窗口长度超出后，删除窗口中最前端的数。
      if (i > k) {
        map.remove(getId(nums[i - k - 1], w));
      }
      // 得到当前数的桶编号
      long id = getId(nums[i], w);
      // 因为桶中的数字都是窗口内的，且桶的数字范围为t+1，如果两个数字落在一个桶内，就肯定符合。
      if (map.containsKey(id)) {
        return true;
      }
      // 只需要查看相邻桶即可，非相邻桶内的数字范围肯定超过了t，分别从相邻桶中取出数字比对判断。
      if (map.containsKey(id + 1) && map.get(id + 1) - nums[i] < w) {
        return true;
      }

      if (map.containsKey(id - 1) && nums[i] - map.get(id - 1) < w) {
        return true;
      }
      // 放置数字到桶中
      map.put(id, (long) nums[i]);
    }
    return false;
  }

  // 获取当前数字的桶编号
  private long getId(long num, long w) {
    if (num >= 0) {
      // 非负数直接取模
      return num / w;
    } else {
      // 负数处理
      return (num + 1) / w - 1;
    }
  }

  // ===============================================================================================
  /** 困难题 */
}
