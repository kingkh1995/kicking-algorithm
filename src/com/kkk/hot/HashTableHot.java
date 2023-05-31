package com.kkk.hot;

import com.kkk.supports.TreeNode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 哈希表 【前缀和】<br>
 * 【前缀和的思想是使用哈希表缓存以优化查找效率】
 *
 * @author KaiKoo
 */
public class HashTableHot {

  /**
   * 1. 两数之和 <br>
   * 【哈希表】，遍历，使用哈希表查找匹配元素，不存在则将元素添加到哈希表。 <br>
   * 如果元素值范围区间较小，则可以使用数组实现哈希表。 <br>
   * 如果是有序数组可以使用二分查找（O(NlogN)）或双指针（O(N)） <br>
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

  /** 13. 罗马数字转整数 <br> */
  public int romanToInt(String s) {
    Map<Character, Integer> map =
        Map.of('I', 1, 'V', 5, 'X', 10, 'L', 50, 'C', 100, 'D', 500, 'M', 1000);
    int ans = 0;
    int n = s.length();
    for (int i = 0; i < n; ++i) {
      int num = map.get(s.charAt(i));
      if (i < n - 1 && num < map.get(s.charAt(i + 1))) { // 如果小的数字在大的数字左边了则要减去
        ans -= num;
      } else {
        ans += num;
      }
    }
    return ans;
  }

  /**
   * 49. 字母异位词分组 <br>
   * 将单词拆分为字符并排序，也统计单词中的字符及其出现次数，最后使用哈希表收集即可。
   */
  public List<List<String>> groupAnagrams(String[] strs) {
    Map<String, List<String>> map = new HashMap<>();
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

  /** 146. LRU 缓存 <br> */
  class LRUCache {
    private Map<Integer, Node> map;
    private int capacity;
    private Node head, tail;

    public LRUCache(int capacity) {
      this.map = new HashMap<>(capacity);
      this.capacity = capacity;
      head = tail = new Node(0, 0); // 可以使用同一个虚拟节点
      head.next = tail;
      tail.prev = head;
    }

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
        if (map.size() > capacity) {
          removeTail();
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

    private void removeTail() {
      map.remove(tail.prev.key);
      removeNode(tail.prev);
    }
  }

  /**
   * 205. 同构字符串 <br>
   * 【哈希表】，实现数组实现哈希表。
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
      if (s2t[sc] == 0) {
        if (t2s[tc]) { // 只允许一对一关系
          return false;
        }
        s2t[sc] = tc + 1; // 用于处理0
        t2s[tc] = true;
      } else if (s2t[sc] != tc + 1) {
        return false;
      }
    }
    return true;
  }

  /**
   * 560. 和为 K 的子数组 <br>
   * 【前缀和】，因为是找子数组，遍历数组以每个元素为子数组最后一个元素然后向前查找即可，记录前缀和优化查找效率。
   */
  public int subarraySum(int[] nums, int k) {
    int count = 0;
    HashMap<Integer, Integer> prefix = new HashMap<>(); // 统计前缀和的数量
    prefix.put(0, 1);
    for (int i = 0, sum = 0; i < nums.length; i++) {
      sum += nums[i]; // 更新前缀和
      count += prefix.getOrDefault(sum - k, 0); // 查找满足条件的前缀和
      prefix.compute(sum, (key, value) -> value == null ? 1 : ++value);
    }
    return count;
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
        if (map.containsKey(n)) { // 数组中存在重复数字，防止重复处理。
          continue;
        }
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
        map.put(n, sum); // 将当前元素插入，防止重复处理。
        ans = Math.max(sum, ans); // 更新结果
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

  /**
   * 149. 直线上最多的点数 <br>
   * 哈希表解法，类似【两数之和】，最重要的是计算斜率，要将斜率分数x/y转化为最简分数记录；<br>
   * 需要找出x和y的最大公约数，由于题设斜率绝对值最大为20000，可以转换为x*20001+y，且考虑到存在负数，要保证负数只能是x。
   */
  class maxPointsSolution {
    public int maxPoints(int[][] points) {
      int len = points.length;
      if (len < 2) {
        return len;
      }
      int ans = 0;
      for (int i = 0; i < len - 1; ++i) {
        if (ans > len / 2 || ans + i >= len) { // 提前终止条件
          break;
        }
        Map<Integer, Integer> map = new HashMap<>();
        for (int j = i + 1; j < len; ++j) {
          int x = points[i][0] - points[j][0], y = points[i][1] - points[j][1];
          if (x == 0) { // 处理0的场景
            y = 1;
          } else if (y == 0) {
            x = 1;
          } else { // 求最大公约数，简化分数。
            int gcd = gcd(Math.abs(x), Math.abs(y));
            x = x / gcd;
            y = y / gcd;
          }
          if (y < 0) { // y始终为正数
            x = -x;
            y = -y;
          }
          int key = x * 20001 + y;
          int count = map.getOrDefault(key, 1) + 1; // 初始值是1
          map.put(key, count);
          ans = Math.max(ans, count); // 更新结果，则后续不需要再次遍历求最大值。
        }
      }
      return ans;
    }

    // 求最大公约数，迭代解法。
    public int gcd(int a, int b) {
      while (b != 0) {
        int t = a % b;
        a = b;
        b = t;
      }
      return a;
    }

    // 求最大公约数，递归解法。
    public int gcd0(int a, int b) {
      return b != 0 ? gcd(b, a % b) : a;
    }
  }

  /**
   * 166. 分数到小数 <br>
   * 模拟除法，使用【哈希表】查找循环小数。
   */
  public String fractionToDecimal(int numerator, int denominator) {
    long n = numerator;
    long d = denominator;
    StringBuilder sb = new StringBuilder();
    // 处理符号
    if (n * d < 0L) {
      sb.append("-");
    }
    n = Math.abs(n);
    d = Math.abs(d);
    // 处理整数部分
    sb.append(n / d);
    n %= d;
    // 判断是否已经除尽
    if (n == 0L) {
      return sb.toString();
    }
    // 处理小数部分
    sb.append(".");
    // 使用哈希表记录余数和index
    Map<Long, Integer> map = new HashMap<>();
    while (n != 0L) {
      // 余数已经出现过，则表示是循环小数。
      if (map.containsKey(n)) {
        sb.insert(map.get(n), "(");
        sb.append(")");
        break;
      }
      map.put(n, sb.length());
      n *= 10L;
      sb.append(n / d);
      n %= d;
    }
    return sb.toString();
  }

  /**
   * 437. 路径总和 III <br>
   * 最佳解法：回溯法，并记录前缀和进行优化，【前缀和是从根节点出发到任一节点的路径和】。
   */
  class pathSumSolution {
    private final Map<Long, Integer> prefix = new HashMap<>(); // 统计前缀和的数量

    public int pathSum(TreeNode root, int targetSum) {
      prefix.put(0L, 1); // 添加初始值
      return backTrack(root, 0, targetSum);
    }

    private int backTrack(TreeNode root, long sum, int targetSum) { // sum为上一个前缀和
      if (root == null) {
        return 0;
      }
      sum += root.val; // 添加当前节点更新当前前缀和
      // 先统计【以当前节点为最后一个节点的满足条件的路径】的数量，等于前缀和为【sum-targetSum】的数量。
      // sum为从根节点出发到当前节点的路径和，而前缀和是路径前半段，满足条件的路径为后半段，故【prefix+targetSum=sum】。
      int count = prefix.getOrDefault(sum - targetSum, 0);
      prefix.compute(sum, (k, v) -> v == null ? 1 : ++v); // 前进，添加当前前缀和。
      count += backTrack(root.left, sum, targetSum); // 统计左子树
      count += backTrack(root.right, sum, targetSum); // 统计右子树
      prefix.computeIfPresent(sum, (k, v) -> --v); // 回退，删除当前前缀和。
      return count;
    }
  }
}
