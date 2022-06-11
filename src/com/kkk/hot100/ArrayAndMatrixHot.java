package com.kkk.hot100;

import com.kkk.supports.ArrayUtils;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;

/**
 * 数组和矩阵 <br>
 *
 * @author KaiKoo
 */
public class ArrayAndMatrixHot {

  /**
   * 48. 旋转图像 <br>
   * 先沿着对角线对折，然后上下对折，即能将矩阵顺时针旋转90度。
   */
  public void rotate(int[][] matrix) {
    int n = matrix.length;
    // 先沿着对角线对折，[i][j]与[n-1-j][n-1-i]交换
    for (int i = 0; i < n; ++i) {
      for (int j = 0; j < n - i; ++j) {
        int temp = matrix[i][j];
        matrix[i][j] = matrix[n - 1 - j][n - 1 - i];
        matrix[n - 1 - j][n - 1 - i] = temp;
      }
    }
    // 再沿着中线对折，[i][j]与[n-1-i][j]交换
    for (int i = 0; i < n >> 1; ++i) {
      for (int j = 0; j < n; ++j) {
        int temp = matrix[i][j];
        matrix[i][j] = matrix[n - 1 - i][j];
        matrix[n - 1 - i][j] = temp;
      }
    }
  }

  /**
   * 56. 合并区间 <br>
   * 按区间左端点排序数组之后合并区间
   */
  public int[][] merge(int[][] intervals) {
    Arrays.sort(intervals, Comparator.comparingInt(i -> i[0]));
    Deque<int[]> ans = new LinkedList<>();
    int[] last;
    for (int[] i : intervals) {
      if (!ans.isEmpty() && (last = ans.peekLast())[1] >= i[0]) {
        last[1] = Math.max(last[1], i[1]);
      } else {
        ans.offer(i);
      }
    }
    return ans.toArray(new int[0][0]);
  }

  /**
   * 75. 颜色分类 <br>
   * 实际上就是三向切分快速排序，选择1作为切分，同时指针从0位置出发，只需要排序一次即可。
   */
  public void sortColors(int[] nums) {
    int lt = 0, i = 0, gt = nums.length - 1, cmp;
    while (i <= gt) { // 未排序区间为[i,gt]
      if ((cmp = nums[i] - 1) < 0) {
        ArrayUtils.swap(nums, lt++, i++);
      } else if (cmp > 0) {
        ArrayUtils.swap(nums, gt--, i);
      } else {
        i++;
      }
    }
  }

  /**
   * 169. 多数元素 <br>
   * 【投票算法】，如果存在多数元素，等于多数元素则为1，否则为-1，遍历数组累加完结果肯定大于0。 <br>
   * 随机选取一个元素作为多数元素，如果累加结果小0了，则抛弃上一段区间，在下一段区间内重新选择多数元素并累加结果， <br>
   * 遍历完成后，最后选择的一个多数元素肯定是数组的多数元素，因为之前的统计区间内真正多数元素的个数没超过区间的一半 <br>
   * 故最后一段统计区间内多数元素的个数肯定超过最后一段区间的一半，即最后一段统计区间内的多数元素肯定是数组的多数元素。 <br>
   * 【则如果确定存在多数元素遍历一遍即可找出，否则需要再遍历一遍判断最后一段统计区间内多数元素是不是整个数组的多数元素。】
   */
  public int majorityElement(int[] nums) {
    int ans = 0, count = 0;
    for (int n : nums) {
      count += n == (ans = count == 0 ? n : ans) ? 1 : -1; // count为0了则重新选择多数元素
    }
    return ans;
  }

  /** 208. 实现 Trie (前缀树) <br> */
  class Trie {
    private Trie[] children = new Trie[26]; // 字符集只包含小写字母
    private boolean existed = false; // 是否存在单词

    public void insert(String word) {
      Trie node = this;
      for (int i = 0; i < word.length(); i++) {
        int index = word.charAt(i) - 'a';
        node =
            node.children[index] = null == node.children[index] ? new Trie() : node.children[index];
      }
      node.existed = true;
    }

    public boolean search(String word) {
      Trie node = searchPrefix(word);
      return node != null && node.existed;
    }

    public boolean startsWith(String prefix) {
      return searchPrefix(prefix) != null;
    }

    private Trie searchPrefix(String prefix) {
      Trie node = this;
      for (int i = 0; i < prefix.length(); i++) {
        int index = prefix.charAt(i) - 'a';
        if (node.children[index] == null) {
          return null;
        }
        node = node.children[index];
      }
      return node;
    }
  }

  /**
   * 215. 数组中的第K个最大元素 <br>
   * 可以使用快速排序（递归）、三向切分排序（迭代）、快速三向切分排序（迭代）。
   */
  class findKthLargestSolution { // 普通的快速排序，使用三取样切分。
    public int findKthLargest(int[] nums, int k) {
      return quickSelect(nums, 0, nums.length - 1, nums.length - k);
    }

    private int quickSelect(int[] nums, int lo, int hi, int index) { // 快速选择排序后在index位置的值
      if (lo >= hi) {
        return nums[lo];
      }
      int p = partition(nums, lo, hi);
      if (p == index) {
        return nums[p];
      }
      return p > index ? quickSelect(nums, lo, p - 1, index) : quickSelect(nums, p + 1, hi, index);
    }

    private int partition(int[] nums, int lo, int hi) {
      int mid = (lo + hi) / 2, a = nums[lo], b = nums[mid], c = nums[hi]; // 三取样
      if ((a - b) * (b - c) > 0) {
        ArrayUtils.swap(nums, lo, mid);
      } else if ((a - c) * (c - b) > 0) {
        ArrayUtils.swap(nums, lo, hi);
      }
      int pivot = nums[lo], i = hi + 1; // [i,hi]区间为大于等于pivot的区间
      for (int j = hi; j > lo; --j) {
        if (nums[j] >= pivot) { // 从右边开始找到大于等于pivot的元素则交换到区间最右边
          ArrayUtils.swap(nums, --i, j);
        }
      }
      ArrayUtils.swap(nums, --i, lo); // 最后把pivot放到[i,hi]区间的左边
      return i;
    }
  }

  /**
   * 240. 搜索二维矩阵 II <br>
   * 从左下角或右上角出发查找即可。
   */
  public boolean searchMatrix(int[][] matrix, int target) {
    int m = matrix.length, n = matrix[0].length;
    for (int i = 0, j = n - 1; i < m && j >= 0; ) {
      int cmp = target - matrix[i][j];
      if (cmp == 0) {
        return true;
      } else if (cmp > 0) {
        i++;
      } else {
        j--;
      }
    }
    return false;
  }

  // ===============================================================================================

  /**
   * 31. 下一个排列 <br>
   * 从右侧找到连续的非严格递减序列，将递减序列左边第一个元素交换到递减序列中，使得序列仍然是递减，然后反转递减序列即可。
   */
  public void nextPermutation(int[] nums) {
    // 确定出非严格递减序列的左侧
    int i = nums.length - 2; // 从倒数第二位开始，将单个元素视为递减序列。
    while (i >= 0 && nums[i] >= nums[i + 1]) {
      i--;
    }
    int j = nums.length - 1;
    // 如果需要交换，则从找到刚好大于待交换元素的元素。
    if (i >= 0) {
      while (j > i && nums[j] <= nums[i]) {
        j--;
      }
      ArrayUtils.swap(nums, i, j);
    }
    // 反转递减序列，如果i=-1则会重置。
    ArrayUtils.reverse(nums, i + 1, nums.length - 1);
  }

  /**
   * 287. 寻找重复数 <br>
   * 最佳解法：转换为求有环链表的交点问题，链表头节点为0，同时交点不可能为0。 <br>
   * 如何证明数组中存在了重复数？只需要将数字交换到索引值等于它的位置，如果发现位置上的值已经等于它了，则表示存在重复数。
   */
  public int findDuplicate(int[] nums) {
    int slow = 0, fast = 0;
    while ((slow = nums[slow]) != (fast = nums[nums[fast]])) {}
    slow = 0;
    while ((slow = nums[slow]) != (fast = nums[fast])) {}
    return slow;
  }
}
