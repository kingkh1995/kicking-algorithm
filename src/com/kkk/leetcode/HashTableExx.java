package com.kkk.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * 哈希表 <br>
 * 140
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
}
