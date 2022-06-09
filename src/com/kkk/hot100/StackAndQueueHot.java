package com.kkk.hot100;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Map;

/**
 * 栈和队列 【DFS & BFS】<br>
 *
 * @author KaiKoo
 */
public class StackAndQueueHot {

  /** 20. 有效的括号 <br> */
  public boolean isValid(String s) {
    Map<Character, Character> map = Map.of(')', '(', '}', '{', ']', '[');
    Deque<Character> stack = new ArrayDeque<>(s.length());
    for (char c : s.toCharArray()) {
      if (!map.containsKey(c)) {
        stack.push(c);
      } else if (stack.isEmpty() || stack.pop() != map.get(c)) { // ASCII字符可以直接使用==比对
        return false;
      }
    }
    return stack.isEmpty(); // 结束时栈为空则表示有效
  }

  // ===============================================================================================

  /**
   * 32. 最长有效括号 <br>
   * 左括号下标入栈，并且始终保持栈底元素为当前已经遍历过的元素中【最后一个没有被匹配的右括号的下标】 <br>
   * 因为0到【最后一个没有被匹配的右括号的下标】之间的区间永远是无效的。 <br>
   */
  public int longestValidParentheses(String s) {
    int ans = 0;
    Deque<Integer> stack = new ArrayDeque<>(s.length());
    stack.push(-1); // 处理边界条件，默认最后一个没有被匹配的右括号的下标为-1。
    for (int i = 0; i < s.length(); i++) {
      if (s.charAt(i) == '(') {
        stack.push(i);
      } else {
        stack.pop(); // 直接pop出一个左括号，也可能是【最后一个没有被匹配的右括号的下标】
        if (stack.isEmpty()) {
          // 如果为空了，即当前右括号找不到可以匹配的左括号，需要更新当前下标为【最后一个没有被匹配的右括号的下标】
          stack.push(i);
        } else {
          // 栈非空则表示右括号匹配到左括号，计算结果，当前位置到上一个未匹配的左括号或右括号之间的区间是有效的。
          ans = Math.max(ans, i - stack.peek());
        }
      }
    }
    return ans;
  }

  /**
   * 42. 接雨水 <br>
   * 【单调栈】，保证栈内元素的高度是单调递减的，如果非递减了则计算雨水。 <br>
   * 根据木桶效应，左边高度递减仍然是在桶内，一旦非递减了就可以确定出一个左右端高度最高的木桶，可以接雨水。
   */
  public int trap(int[] height) {
    int ans = 0;
    Deque<Integer> stack = new ArrayDeque<>(height.length);
    for (int i = 0; i < height.length; ++i) {
      // 一旦高度高于栈顶了则可以计算雨水，边pop边计算。
      while (!stack.isEmpty() && height[stack.peek()] < height[i]) {
        // 需要确定出一个木桶，pop为当前木桶的底，栈顶为左挡板，i位置为右挡板。
        int pop = stack.pop();
        if (stack.isEmpty()) { // 栈为空了，则不存在左挡板，无法接雨水。
          break;
        }
        int left = stack.peek();
        int curWidth = i - left - 1; // 木桶宽度
        int curHeight = Math.min(height[left], height[i]) - height[pop]; // 木桶高度为左右挡板矮的一方减去底的高度
        ans += curWidth * curHeight;
      }
      stack.push(i); // 最终都要入栈
    }
    return ans;
  }

  /**
   * 84. 柱状图中最大的矩形 <br>
   * 单调栈，与接雨水类似，找到一个反向桶。维护严格单调递增的栈，遍历过程中确定每个位置的左右边界。
   */
  public int largestRectangleArea(int[] heights) {
    int ans = 0, len = heights.length;
    int[] left = new int[len], right = new int[len]; // 左右边界均不包含
    Arrays.fill(right, len); // 设置默认右边界，否则最后还需要出栈设置。
    Deque<Integer> deque = new ArrayDeque<>(len);
    for (int i = 0; i < heights.length; ++i) {
      while (!deque.isEmpty() && heights[deque.peek()] >= heights[i]) {
        right[deque.pop()] = i; // 栈内的右边界为当前
      }
      left[i] = deque.isEmpty() ? -1 : deque.peek(); // 递增则当前位置左边界为栈顶
      deque.push(i);
    }
    for (int i = 0; i < len; ++i) { // 计算并确定最大值
      ans = Math.max(ans, (right[i] - left[i] - 1) * heights[i]);
    }
    return ans;
  }

  /**
   * 85. 最大矩形 <br>
   * 该矩阵每一行至第一行可以看成一个柱状图，那么可以逐行使用84题的解法求出每层柱状图的最大值，最后汇总即可。
   */
  public int maximalRectangle(char[][] matrix) {
    int ans = 0, m = matrix.length, n = matrix[0].length;
    int[][] heights = new int[m][n]; // 每个位置柱状图的高度
    for (int i = 0; i < m; ++i) {
      for (int j = 0; j < n; j++) {
        if (matrix[i][j] == '1') { // 为1表示该位置有柱状图
          heights[i][j] = i == 0 ? 1 : heights[i - 1][j] + 1; // 当前位置高度为上一行柱状图的高度加1
        }
      }
    }
    for (int i = 0; i < m; i++) { // 开始逐行计算并汇总结果
      ans = Math.max(ans, largestRectangleArea(heights[i]));
    }
    return ans;
  }
}
