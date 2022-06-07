package com.kkk.hot100;

import java.util.LinkedList;
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
    LinkedList<Character> stack = new LinkedList<>();
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
    LinkedList<Integer> stack = new LinkedList<>();
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

  // ===============================================================================================

  /**
   * 42. 接雨水 <br>
   * 单调栈，保证栈内元素的高度是单调递减的，如果非递减了则计算雨水。 <br>
   * 根据木桶效应，左边高度递减仍然是在桶内，一旦非递减了就可以确定出一个左右端高度最高的木桶，可以接雨水。
   */
  public int trap(int[] height) {
    int ans = 0;
    LinkedList<Integer> stack = new LinkedList<>();
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
}
