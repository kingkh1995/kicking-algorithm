package com.kkk.hot100;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
      List<String> list = map.getOrDefault(key, new ArrayList<>());
      list.add(s);
      map.put(key, list);
    }
    return new ArrayList<>(map.values());
  }

  /** 146. LRU 缓存 <br> */
  class LRUCache {

    class Node {
      int key;
      int val;
      Node prev;
      Node next;

      public Node(int key, int val) {
        this.key = key;
        this.val = val;
      }
    }

    private Map<Integer, Node> map;
    private int capacity, size;
    private Node head, tail;

    public LRUCache(int capacity) {
      this.map = new HashMap<>(capacity);
      this.capacity = capacity;
      head = tail = new Node(0, 0); // 可以使用同一个虚拟节点
      head.next = tail;
      tail.prev = head;
    }

    public int get(int key) {
      Node node = map.get(key);
      if (node == null) {
        return -1;
      }
      moveToHead(node);
      return node.val;
    }

    public void put(int key, int value) {
      Node node = map.get(key);
      if (node == null) {
        node = new Node(key, value);
        map.put(key, node);
        addToHead(node);
        if (++size > capacity) {
          size--;
          map.remove(removeTail().key);
        }
      } else {
        node.val = value;
        moveToHead(node);
      }
    }

    private void addToHead(Node node) {
      node.prev = head;
      node.next = head.next;
      head.next.prev = node;
      head.next = node;
    }

    private void removeNode(Node node) {
      node.prev.next = node.next;
      node.next.prev = node.prev;
    }

    private void moveToHead(Node node) {
      removeNode(node);
      addToHead(node);
    }

    private Node removeTail() {
      Node last = tail.prev;
      removeNode(last);
      return last;
    }
  }

  // ===============================================================================================

  /**
   * 128. 最长连续序列 <br>
   * 简单解法：遍历数组统计即可，需要选择连续序列的最小值作为起点，使用哈希表判断是否能作为起点。 <br>
   * 最佳解法：只需遍历数组一遍，每个不重复的数字都会将其左右区间（或空）连接为大区间，每次只需要查询数字的左边和右边即可。
   */
  class longestConsecutiveSolution {

    public int longestConsecutive(int[] nums) {
      int ans = 0;
      Map<Integer, Integer> map = new HashMap<>(nums.length); // 使用map记录数字和其所在连续区间的长度
      for (int n : nums) {
        if (!map.containsKey(n)) { // 数组中存在重复数字，保证只被访问一次。
          // 查看n-1和n+1，确定当前数字要连接的左边和右边区间。
          int left = map.getOrDefault(n - 1, 0), right = map.getOrDefault(n + 1, 0);
          int sum = left + right + 1; // 左右区间连接后新的区间长度
          // 更新数字所在区间的长度，且只需要更新区间的左右端即可，因为只有区间两端才会被查看，即只需把区间的长度更新到区间两端即可。
          if (left > 0) {
            map.put(n - left, sum);
          }
          if (right > 0) {
            map.put(n + right, sum);
          }
          map.put(n, sum);
          ans = Math.max(sum, ans); // 更新结果
        }
      }
      return ans;
    }

    public int longestConsecutive0(int[] nums) {
      int ans = 0;
      Set<Integer> set = new HashSet<>(nums.length);
      for (int n : nums) {
        set.add(n);
      }
      for (int n : nums) {
        if (!set.contains(n - 1)) { // 数组中不存在n-1，则n可以作为起点
          int count = 1;
          while (set.contains(n + 1)) { // 查找后续数字
            n++;
            count++;
          }
          ans = Math.max(ans, count);
        }
      }
      return ans;
    }
  }
}
