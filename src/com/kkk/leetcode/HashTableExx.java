package com.kkk.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * 哈希表 <br>
 *
 * @author KaiKoo
 */
public class HashTableExx {

  // ===============================================================================================
  /** 基础题 */

  // 常数时间插入、删除和获取随机元素，保证每个函数的平均时间复杂度为O(1)
  static class RandomizedSet {

    private final HashMap<Integer, Integer> map; // 使用map保存元素和其在list中索引
    private final ArrayList<Integer> list; // 使用list保存元素以在常数时间内获取随机元素
    private final Random random;

    public RandomizedSet() {
      this.map = new HashMap<>();
      this.list = new ArrayList<>();
      this.random = new Random();
    }

    public boolean insert(int val) {
      if (map.containsKey(val)) {
        return false;
      }
      map.put(val, list.size());
      list.add(val);
      return true;
    }

    public boolean remove(int val) {
      Integer index = map.remove(val);
      if (index == null) {
        return false;
      }
      int lastIndex = list.size() - 1;
      // 取出lastIndex位置元素进行替换
      if (index != lastIndex) {
        Integer last = list.get(lastIndex);
        map.put(last, index);
        list.set(index, last);
      }
      // 移除lastIndex位置元素
      list.remove(lastIndex);
      return true;
    }

    public int getRandom() {
      return list.get(random.nextInt(list.size()));
    }
  }

  // ===============================================================================================
  /** 拔高题 */

  // 一个未排序的整数数组，找出数字连续的最长序列，实现时间复杂度为 O(n)。
  static class longestConsecutiveSolution {

    // 解法一：从连续序列最小的元素开始往后查找，使用Set将查找复杂度降为 O(1)，查找次数会小于2n，故时间复杂度为 O(n)。
    public int longestConsecutive1(int[] nums) {
      int res = 0;
      var set = Arrays.stream(nums).boxed().collect(Collectors.toSet());
      for (int n : nums) {
        // 因为序列最小值，故n-1不能存在。
        if (!set.contains(n - 1)) {
          int count = 1;
          while (set.contains(n + 1)) {
            n++;
            count++;
          }
          res = Math.max(count, res);
        }
      }
      return res;
    }

    // 解法二：只判断左右。
    public int longestConsecutive2(int[] nums) {
      int res = 0;
      HashMap<Integer, Integer> map = new HashMap<>(nums.length);
      for (int n : nums) {
        if (!map.containsKey(n)) {
          // 确定左右区间
          int left = map.getOrDefault(n - 1, 0);
          int right = map.getOrDefault(n + 1, 0);
          // 计算出新的区间长度
          int sum = left + right + 1;
          // 插入当前值，保证重复值只能处理一次
          map.put(n, sum);
          // 更新区间长度，且只需要更新区间的左右端即可。
          // 因为新插入的值必然是连接两个不同的区间，所以只有区间两端才会被查看，即只需把区间的长度更新到区间两端即可。
          if (left > 0) {
            map.put(n - left, sum);
          }
          if (right > 0) {
            map.put(n + right, sum);
          }
          // 更新最大值
          res = Math.max(sum, res);
        }
      }
      return res;
    }
  }
}
