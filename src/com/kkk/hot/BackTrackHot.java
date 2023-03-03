package com.kkk.hot;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * 回溯 <br>
 * 【回溯属于递归，回退时需要重置状态，而DFS不需要】
 *
 * @author KaiKoo
 */
public class BackTrackHot {

  static final int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

  /** 17. 电话号码的字母组合 <br> */
  class letterCombinationsSolution {
    char[][] dict =
        new char[][] {
          {'a', 'b', 'c'},
          {'d', 'e', 'f'},
          {'g', 'h', 'i'},
          {'j', 'k', 'l'},
          {'m', 'n', 'o'},
          {'p', 'q', 'r', 's'},
          {'t', 'u', 'v'},
          {'w', 'x', 'y', 'z'}
        };
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
      for (char c : dict[digits.charAt(i) - '2']) {
        sb.append(c);
        backTrack(i + 1);
        sb.deleteCharAt(i);
      }
    }
  }

  /** 22. 括号生成 <br> */
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
   * 要求不能重复，因为candidate各不相同，故排序后，按顺序添加，则可以避免重复。
   */
  class combinationSumSolution {
    int[] candidates;
    Deque<Integer> deque;
    List<List<Integer>> ans;

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
      Arrays.sort(candidates);
      this.candidates = candidates;
      this.deque = new ArrayDeque<>();
      this.ans = new ArrayList<>();
      backTrack(0, target);
      return this.ans;
    }

    private void backTrack(int index, int target) { // index作为candidate的起点，这样能避免重复添加。
      if (target == 0) {
        ans.add(new ArrayList<>(deque));
      }
      for (int i = index, n; i < candidates.length && (n = candidates[i]) <= target; ++i) {
        deque.push(n);
        backTrack(i, target - n);
        deque.pop();
      }
    }
  }

  /**
   * 46. 全排列 <br>
   * 与求字符串中字符的所有排列组合相同，使用哈希表或者数组标记已被选中的数字。
   */
  class permuteSolution {
    int[] nums;
    List<List<Integer>> ans;
    Deque<Integer> deque;
    boolean[] marked; // 标记数字是否可用，数字不重复且范围为-10到10，则使用长度21的boolean数组。

    public List<List<Integer>> permute(int[] nums) {
      this.nums = nums;
      this.ans = new ArrayList<>();
      this.deque = new ArrayDeque<>(nums.length); // list最大容量固定
      this.marked = new boolean[21];
      for (int n : nums) {
        this.marked[n + 10] = true;
      }
      backTrack(0);
      return this.ans;
    }

    private void backTrack(int i) {
      if (i == nums.length) {
        ans.add(new ArrayList<>(deque));
      }
      for (int n : nums) {
        if (marked[n + 10]) { // 要求数字可用
          marked[n + 10] = false; // 标记为不可用
          deque.push(n);
          backTrack(i + 1);
          deque.pop();
          marked[n + 10] = true; // 重新标记为不可用
        }
      }
    }
  }

  /**
   * 78. 子集 <br>
   * 每个元素有两种情况，被选择或不被选择。
   */
  class subsetsSolution {
    int[] nums;
    List<List<Integer>> ans;
    Deque<Integer> stack;

    public List<List<Integer>> subsets(int[] nums) {
      this.nums = nums;
      ans = new ArrayList<>();
      stack = new ArrayDeque<>(nums.length);
      backTrack(0);
      return ans;
    }

    private void backTrack(int i) {
      if (i == nums.length) {
        ans.add(new ArrayList<>(stack));
        return;
      }
      stack.push(nums[i]);
      backTrack(i + 1); // 选择当前
      stack.pop();
      backTrack(i + 1); // 不选择当前
    }
  }

  /** 79. 单词搜索 <br> */
  class existSolution {
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
      for (int[] dir : dirs) {
        int ni = i + dir[0], nj = j + dir[1];
        if (ni >= 0
            && ni < board.length
            && nj >= 0
            && nj < board[0].length
            && !marked[ni][nj]
            && backTrack(ni, nj, index + 1)) {
          return true;
        }
      }
      marked[i][j] = false; // 回退过程中会重置marked数组
      return false;
    }
  }

  // ===============================================================================================

  /**
   * 301. 删除无效的括号 <br>
   * 预先统计出需要删除的左括号和右括号个数，遍历过程中左括号默认需要删除，除非后面碰到了一个配对的右括号。
   */
  class removeInvalidParenthesesSolution {
    List<String> ans = new LinkedList<>();

    public List<String> removeInvalidParentheses(String s) {
      int lr = 0, rr = 0;
      for (char c : s.toCharArray()) {
        if (c == '(') {
          ++lr;
        } else if (c == ')') { // 右括号前面有匹配的左括号则左右括号均不需要删除，否则需要删除右括号
          if (lr > 0) {
            --lr;
          } else {
            ++rr;
          }
        }
      }
      backTrack(s, 0, lr, rr);
      return ans;
    }

    private void backTrack(String s, int start, int lr, int rr) { // start表示允许处理索引起点，目的是去重。
      if (lr == 0 && rr == 0) {
        if (isValid(s)) {
          ans.add(s);
        }
        return;
      }
      for (int i = start; i <= s.length() - lr - rr; ++i) {
        int c = s.charAt(i);
        if (i != start && c == s.charAt(i - 1)) { // 去重，连续的相同符号只用处理一次。
          continue;
        }
        // 删除当前位置的字符，并且下一轮处理的起点仍然是i，因为i+1位置现在变成了i。
        if (lr > 0 && c == '(') {
          backTrack(s.substring(0, i) + s.substring(i + 1), i, lr - 1, rr);
        } else if (rr > 0 && c == ')') {
          backTrack(s.substring(0, i) + s.substring(i + 1), i, lr, rr - 1);
        }
      }
    }

    private boolean isValid(String s) { // 判断当前字符串是否有效
      int count = 0;
      for (int i = 0; i < s.length() && count >= 0; ++i) {
        char c = s.charAt(i);
        if (c == '(') {
          ++count;
        } else if (c == ')') {
          --count;
        }
      }
      return count == 0;
    }
  }
}
