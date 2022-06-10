package com.kkk.hot100;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 递归和迭代 【回溯 & 贪心】 <br>
 * 【回溯属于递归、贪心属于迭代】
 *
 * @author KaiKoo
 */
public class RecursionAndIterationHot {

  /**
   * 5. 最长回文子串 <br>
   * 中心扩散法，使用迭代，从每一个位置往两边扩散比较字符求解。
   */
  class longestPalindromeSolution {
    char[] charAt;
    int ansL; // 最长回文子串起点索引
    int ansH; // 最长回文子串终点索引

    public String longestPalindrome(String s) {
      if (s.length() < 2) {
        return s;
      }
      charAt = s.toCharArray();
      ansL = 0;
      ansH = 0;
      for (int i = 0; i < s.length(); i++) {
        expandAroundCenter(i, i); // 奇数回文串
        expandAroundCenter(i, i + 1); // 偶数回文串
      }
      return s.substring(ansL, ansH + 1);
    }

    private void expandAroundCenter(int l, int h) {
      while (l >= 0 && h < charAt.length && charAt[l] == charAt[h]) {
        --l;
        ++h;
      }
      // 为开区间需要内缩一格，更新结果。
      if (--h - ++l > ansH - ansL) {
        ansH = h;
        ansL = l;
      }
    }
  }

  /**
   * 17. 电话号码的字母组合 <br>
   * 回溯法
   */
  class letterCombinationsSolution {
    Map<Character, char[]> MAP = new HashMap<>(); // 字符集
    String digits;
    StringBuilder sb;
    List<String> ans;

    public List<String> letterCombinations(String digits) {
      if (digits.length() == 0) {
        return Collections.emptyList();
      }
      this.digits = digits;
      this.sb = new StringBuilder();
      this.ans = new ArrayList<>();
      backTrack(0);
      return this.ans;
    }

    private void backTrack(int i) {
      if (i == digits.length()) {
        ans.add(sb.toString());
        return;
      }
      for (char c : MAP.get(digits.charAt(i))) {
        sb.append(c);
        backTrack(i + 1);
        sb.deleteCharAt(i);
      }
    }
  }

  /**
   * 22. 括号生成 <br>
   * 回溯法
   */
  class generateParenthesisSolution {
    StringBuilder sb;
    List<String> ans;

    public List<String> generateParenthesis(int n) {
      sb = new StringBuilder();
      ans = new ArrayList<>();
      backTrack(0, 0, n);
      return ans;
    }

    private void backTrack(int openCount, int closeCount, int n) {
      if (openCount == n && closeCount == n) {
        ans.add(sb.toString());
        return;
      }
      if (openCount < n) {
        sb.append('(');
        backTrack(openCount + 1, closeCount, n);
        sb.deleteCharAt(openCount + closeCount);
      }
      // 闭括号个数小于开括号个数则可以追加闭括号
      if (closeCount < openCount) {
        sb.append(')');
        backTrack(openCount, closeCount + 1, n);
        sb.deleteCharAt(openCount + closeCount);
      }
    }
  }

  /**
   * 39. 组合总和 <br>
   * 回溯法，要求不能重复，因为candidate各不相同，故排序后，按顺序添加，则可以避免重复。
   */
  class combinationSumSolution {
    LinkedList<Integer> deque;
    List<List<Integer>> ans;

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
      Arrays.sort(candidates);
      deque = new LinkedList<>();
      ans = new ArrayList<>();
      backTrack(candidates, target);
      return ans;
    }

    private void backTrack(int[] candidates, int target) {
      if (target == 0) {
        ans.add(new ArrayList<>(deque));
      }
      for (int n : candidates) {
        if (n > target) {
          break; // 元素均大于0，超过了target则可以直接退出循环。
        } else if (!deque.isEmpty() && n < deque.peek()) {
          continue; // 按大小顺序添加，不添加比当前元素小的元素。
        }
        deque.push(n);
        backTrack(candidates, target - n);
        deque.pop();
      }
    }
  }

  /**
   * 46. 全排列 <br>
   * 回溯法，与求字符串中字符的所有排列组合相同，使用哈希表或者数组标记已被选中的数字。
   */
  class permuteSolution {
    List<List<Integer>> ans;
    List<Integer> list;
    boolean[] marked; // 标记数字是否可用，数字不重复且范围为-10到10，则使用长度21的boolean数组。

    public List<List<Integer>> permute(int[] nums) {
      ans = new ArrayList<>();
      list = new ArrayList<>(nums.length); // list最大容量固定
      marked = new boolean[21];
      for (int n : nums) {
        marked[n + 10] = true;
      }
      backTrack(nums, 0);
      return ans;
    }

    private void backTrack(int[] nums, int i) {
      if (i == nums.length) {
        ans.add(new ArrayList<>(list));
      }
      for (int n : nums) {
        if (marked[n + 10]) { // 要求数字可用
          marked[n + 10] = false; // 标记为不可用
          list.add(i, n);
          backTrack(nums, i + 1);
          list.remove(i);
          marked[n + 10] = true; // 重新标记为不可用
        }
      }
    }
  }

  /**
   * 55. 跳跃游戏 <br>
   * 贪心算法，遍历数组，记录下可以达到的最远距离max，因为从0位置到max的所有位置都一定可达，判断max能否到达最后一个位置。
   */
  public boolean canJump(int[] nums) {
    for (int i = 0, max = nums[0]; i <= max; ++i) {
      if (max >= nums.length - 1) {
        return true;
      }
      max = Math.max(max, i + nums[i]); // 遍历过程中更新可达的最远位置
    }
    return false; // 如果最终循环结束表示最后一个位置不可达
  }

  /**
   * 78. 子集 <br>
   * 每个元素有两种情况，被选择或不被选择，然后使用回溯法收集即可。
   */
  class subsetsSolution {
    List<List<Integer>> ans;
    Deque<Integer> stack;

    public List<List<Integer>> subsets(int[] nums) {
      ans = new ArrayList<>();
      stack = new ArrayDeque<>(nums.length);
      backTrack(nums, 0);
      return ans;
    }

    private void backTrack(int[] nums, int i) {
      if (i == nums.length) {
        ans.add(new ArrayList<>(stack));
        return;
      }
      stack.push(nums[i]);
      backTrack(nums, i + 1);
      stack.pop();
      backTrack(nums, i + 1);
    }
  }

  /** 79. 单词搜索 <br> */
  class existSolution {
    static final int[][] dirs = new int[][] {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    char[][] board;
    boolean[][] marked;
    char[] charAt;

    public boolean exist(char[][] board, String word) {
      int m = board.length, n = board[0].length;
      this.board = board;
      this.marked = new boolean[m][n];
      this.charAt = word.toCharArray();
      for (int i = 0; i < m; ++i) {
        for (int j = 0; j < n; ++j) {
          if (backTrack(i, j, 0)) {
            return true;
          }
        }
      }
      return false;
    }

    private boolean backTrack(int i, int j, int index) {
      // 终止条件包括当前位置字符是否匹配以及指针位置
      if (board[i][j] != charAt[index]) {
        return false;
      } else if (index == charAt.length - 1) {
        return true;
      }
      marked[i][j] = true;
      boolean result = false;
      for (int[] dir : dirs) {
        int ni = i + dir[0], nj = j + dir[1];
        if (ni >= 0
            && ni < board.length
            && nj >= 0
            && nj < board[0].length
            && !marked[ni][nj]
            && backTrack(ni, nj, index + 1)) {
          result = true;
          break;
        }
      }
      marked[i][j] = false; // 回退过程中会重置marked数组
      return result;
    }
  }

  /** 121. 买卖股票的最佳时机 <br> */
  public int maxProfit(int[] prices) {
    int ans = 0, min = Integer.MAX_VALUE; // min为遍历过程中的最低价格
    for (int n : prices) {
      ans = Math.max(ans, n - (min = Math.min(min, n)));
    }
    return ans;
  }

  /**
   * 238. 除自身以外数组的乘积 <br>
   * 先从左往右遍历，求出前缀元素乘积，再从右往左遍历，累乘上所有后缀元素。
   */
  public int[] productExceptSelf(int[] nums) {
    int[] ans = new int[nums.length];
    ans[0] = 1;
    for (int i = 1; i < nums.length; ++i) {
      ans[i] = ans[i - 1] * nums[i - 1];
    }
    int m = 1; // 从右往左遍历使用辅助变量
    for (int i = nums.length - 1; i >= 0; --i) {
      ans[i] *= m;
      m *= nums[i];
    }
    return ans;
  }

  // ===============================================================================================

  /**
   * 32. 最长有效括号 <br>
   * 遍历查找，从左往右，记录左右括号的个数，当两者相等时则表示当前区间是有效的，一旦右括号数量大于左括号则当前区间永远是无效的了， <br>
   * 需要重置区间从当前位置开始，不需要记录区间位置，因为计数总和就是区间的长度。<br>
   * 如果左括号永远大于右括号个数则无法求解，因此需要从右往左再次遍历一遍，左括号数量大于右括号则判断无效。
   */
  public int longestValidParentheses(String s) {
    int ans = 0, left = 0, right = 0;
    for (int i = 0; i < s.length(); i++) {
      if (s.charAt(i) == '(') {
        left++;
      } else {
        right++;
      }
      if (left == right) {
        ans = Math.max(ans, left << 1);
      } else if (right > left) {
        left = right = 0;
      }
    }
    left = right = 0;
    for (int i = s.length() - 1; i >= 0; i--) {
      if (s.charAt(i) == '(') {
        left++;
      } else {
        right++;
      }
      if (left == right) {
        ans = Math.max(ans, left << 1);
      } else if (left > right) {
        left = right = 0;
      }
    }
    return ans;
  }
}
