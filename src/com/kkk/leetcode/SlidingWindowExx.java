package com.kkk.leetcode;

import java.util.Arrays;
import java.util.HashMap;

/**
 * 滑动窗口 <br>
 * 遍历过程中更新结果，窗口右端永远在当前位置，每次移动窗口左端。
 *
 * @author KaiKoo
 */
public class SlidingWindowExx {

  // ===============================================================================================

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

  // 仅有小写字母组成的字符串，找出最长子串，要求该子串中的每一字符出现次数都不少于k。
  public int longestSubstring(String s, int k) {
    int ans = 0;
    // 辅助数组，记录当前窗口内，字符出现的频次。
    int[] aux = new int[26];
    // 求出子串中字符种类1-26下的答案，取最大值。
    for (int total = 1; total <= 26; ++total) {
      Arrays.fill(aux, 0);
      // 左右窗口
      int l = 0, r = 0;
      // 当前窗口内出现的频次小于k的字符的个数
      int less = 0;
      // 当前窗口内字符的种类
      int cnt = 0;
      while (r < s.length()) {
        int i = s.charAt(r++) - 'a';
        aux[i]++;
        // 新增字符种类
        if (aux[i] == 1) {
          cnt++;
          less++;
        }
        // 字符频次达到k
        if (aux[i] == k) {
          less--;
        }
        // 字符种类超过了限制则需要左移窗口使字符种类减少
        while (cnt > total) {
          i = s.charAt(l++) - 'a';
          aux[i]--;
          // 字符种类减少
          if (aux[i] == 0) {
            cnt--;
            less--;
          }
          // 字符频次少于了k
          if (aux[i] == k - 1) {
            less++;
          }
        }
        // 判断是否符合
        if (less == 0) {
          ans = Math.max(ans, r - l);
        }
      }
    }
    return ans;
  }
}
