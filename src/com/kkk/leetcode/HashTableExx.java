package com.kkk.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

/**
 * 哈希表 <br>
 * 140
 *
 * @author KaiKoo
 */
public class HashTableExx {

  /**
   * 205. 同构字符串 <br>
   * 数组实现哈希表
   */
  public boolean isIsomorphic(String s, String t) {
    int n = s.length();
    if (n != t.length()) {
      return false;
    }
    int[] s2t = new int[128]; // 哈希表记录字符映射
    boolean[] t2s = new boolean[128]; // 不保存映射关系只做标记
    for (int i = 0; i < n; ++i) {
      char sc = s.charAt(i), tc = t.charAt(i);
      if (s2t[sc] == 0 && !t2s[tc]) {
        s2t[sc] = tc + 1;
        t2s[tc] = true;
      } else if (s2t[sc] == 0 || !t2s[tc] || s2t[sc] != tc + 1) {
        return false;
      }
    }
    return true;
  }

  /** 219. 存在重复元素 II <br> */
  public boolean containsNearbyDuplicate(int[] nums, int k) {
    HashMap<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < nums.length; ++i) {
      if (map.containsKey(nums[i]) && i - map.get(nums[i]) <= k) {
        return true;
      }
      map.put(nums[i], i);
    }
    return false;
  }

  /**
   * 380. O(1) 时间插入、删除和获取随机元素 <br>
   * 使用list保存元素以在常数时间内获取随机元素，使用map保存元素和其在list中索引以实现O(1)时间复杂度。
   */
  class RandomizedSet {

    private final ArrayList<Integer> list;
    private final HashMap<Integer, Integer> map;
    private final Random random;

    public RandomizedSet() {
      list = new ArrayList<>();
      map = new HashMap<>();
      random = new Random();
    }

    public boolean insert(int val) {
      if (map.putIfAbsent(val, list.size()) != null) {
        return false;
      }
      return list.add(val);
    }

    public boolean remove(int val) { // 注意list移除操作不能修改其他元素的位置。
      Integer index = map.remove(val);
      if (index == null) {
        return false;
      }
      // 取出最后一个加入的元素进行替换，并移除最后一个元素。
      int lastIndex = list.size() - 1;
      if (index != lastIndex) {
        Integer last = list.get(lastIndex);
        map.put(last, index);
        list.set(index, last);
      }
      list.remove(lastIndex);
      return true;
    }

    public int getRandom() {
      return list.get(random.nextInt(list.size()));
    }
  }

  /**
   * 381. O(1) 时间插入、删除和获取随机元素 - 允许重复 <br>
   * 相比于380，使用Set收集重复元素的索引。
   */
  class RandomizedCollection {

    private final ArrayList<Integer> list;
    private final HashMap<Integer, HashSet<Integer>> map;
    private final Random random;

    public RandomizedCollection() {
      list = new ArrayList<>();
      map = new HashMap<>();
      random = new Random();
    }

    public boolean insert(int val) { // 永远添加元素且如果不存在则返回true
      boolean containsKey = map.containsKey(val);
      map.compute(
          val,
          (k, v) -> {
            if (v == null) {
              v = new HashSet<>();
            }
            v.add(list.size());
            return v;
          });
      list.add(val);
      return !containsKey;
    }

    public boolean remove(int val) { // 注意list移除操作不能修改其他元素的位置。
      HashSet<Integer> set = map.get(val);
      if (set == null) {
        return false;
      }
      // 移除随机一个重复元素
      Iterator<Integer> it = set.iterator();
      int index = it.next();
      it.remove();
      if (!it.hasNext()) {
        map.remove(val);
      }
      int lastIndex = list.size() - 1;
      if (index != lastIndex) {
        Integer last = list.get(lastIndex);
        list.set(index, last);
        HashSet<Integer> lastSet = map.get(last);
        lastSet.remove(lastIndex);
        lastSet.add(index);
      }
      list.remove(lastIndex);
      return true;
    }

    public int getRandom() {
      return list.get(random.nextInt(list.size()));
    }
  }

  /**
   * 387. 字符串中的第一个唯一字符 <br>
   * 【哈希表】，因为全为小写字母，则可以使用数组实现哈希表。<br>
   * 解法一：双指针查找，并记录字符是否唯一，虽然需要多次遍历，但是每次并不是遍历整个数组。<br>
   * 解法二：只需要两次遍历 ，第一次遍历记录所有唯一字符的索引，第二次遍历时利用索引找到第一个唯一字符。
   */
  class firstUniqCharSolution {
    public int firstUniqChar1(String s) {
      boolean[] aux = new boolean[26];
      for (int i = 0; i < s.length(); ++i) {
        char c = s.charAt(i);
        if (!aux[c - 'a']) {
          aux[c - 'a'] = true;
          if (s.lastIndexOf(c) == i) { // 直接使用api
            return i;
          }
        }
      }
      return -1;
    }

    public int firstUniqChar2(String s) {
      int[] aux = new int[26];
      for (int i = 0; i < s.length(); ++i) {
        int index = s.charAt(i) - 'a';
        if (aux[index] == 0) {
          aux[index] = i + 1; // 记录索引+1
        } else if (aux[index] > 0) {
          aux[index] = -1; // -1表示非唯一
        }
      }
      int min = Integer.MAX_VALUE;
      for (int n : aux) {
        if (n > 0) {
          min = Math.min(min, n);
        }
      }
      return min == Integer.MAX_VALUE ? -1 : min - 1;
    }
  }

  // ===============================================================================================

  /**
   * 220. 存在重复元素 III <br>
   * 使用桶排序算法，并维护长度为k的滑动窗口。
   */
}
