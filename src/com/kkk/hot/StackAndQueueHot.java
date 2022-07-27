package com.kkk.hot;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 栈 & 队列 & 优先队列 <br>
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

  /** 155. 最小栈 <br> */
  class MinStack {
    private Deque<Integer> stack = new LinkedList<>();
    private Deque<Integer> min = new LinkedList<>();

    public MinStack() {}

    public void push(int val) {
      stack.push(val);
      min.push(min.isEmpty() || val <= min.peek() ? val : min.peek()); // push时将push后最小值push到min
    }

    public void pop() {
      stack.pop();
      min.pop();
    }

    public int top() {
      return stack.peek();
    }

    public int getMin() {
      return min.peek();
    }
  }

  /** 394. 字符串解码 <br> */
  class decodeStringSolution {
    public String decodeString(String s) {
      Deque<String> stack = new LinkedList<>();
      for (int i = 0, j, n = s.length(); i < n;) {
        char c = s.charAt(i++);
        if (Character.isLetter(c)) { // 找出一个字符串
          for (j = i; j < n && Character.isLetter(s.charAt(j)); ++j) {}
          pushString(stack, s.substring(i - 1, i = j));
        } else if (Character.isDigit(c)) { // 找出一个数字
          for (j = i; j < n && Character.isDigit(s.charAt(j)); ++j) {}
          stack.push(s.substring(i - 1, i = j));
        } else if (c == '[') {
          stack.push("[");
        } else { // == ']'
          String string = stack.pop(); // pop一个字符串
          stack.pop(); // pop一个[
          int count = Integer.parseInt(stack.pop()); // pop一个数字
          pushString(stack, string.repeat(count)); // 构建字符串并入栈
        }
      }
      return stack.pop(); // 最终栈内只有一个字符串为结果
    }

    private void pushString(Deque<String> stack, String s) {
      if (!stack.isEmpty() && !"[".equals(stack.peek())) { // 栈顶只能为字符串或[，字符串则追加，[入栈。
        stack.push(stack.pop() + s);
      } else {
        stack.push(s);
      }
    }
  }

  /**
   * 692. 前K个高频单词 <br/>
   * 最优解法，先使用哈希表统计频次，然后使用【最小堆】收集即可。
   */
  public List<String> topKFrequent(String[] words, int k) {
    PriorityQueue<Entry<String, Long>> pq = new PriorityQueue<>(
            Entry.<String, Long>comparingByValue()
                    .thenComparing(Entry.comparingByKey(Comparator.reverseOrder())));
    Arrays.stream(words).collect(
            Collectors.groupingBy(Function.identity(), Collectors.counting()))
            .entrySet()
            .forEach(e -> {
              pq.offer(e);
              if (pq.size() > k) {
                pq.poll();
              }
            });
    List<String> ans = new ArrayList<>(k);
    while (!pq.isEmpty()) {
      ans.add(pq.poll().getKey());
    }
    Collections.reverse(ans);
    return ans;
  }

  /**
   * 739. 每日温度 <br>
   * 【单调栈】
   */
  public int[] dailyTemperatures(int[] temperatures) {
    int[] ans = new int[temperatures.length];
    Deque<Integer> stack = new ArrayDeque<>(temperatures.length);
    for (int i = 0; i < temperatures.length; ++i) {
      while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
        int pop = stack.pop();
        ans[pop] = i - pop;
      }
      stack.push(i);
    }
    return ans;
  }

  /**
   * 844. 比较含退格的字符串 <br/>
   * 最简单解法，使用栈重构字符串，然后比较即可，时间复杂度会高于双指针解法。
   */
  public boolean backspaceCompare(String s, String t) {
      Deque<Character> stackS = new ArrayDeque<>(s.length()), stackT = new ArrayDeque<>(t.length());
      for (char c : s.toCharArray()) {
        if (c != '#') {
          stackS.push(c);
        } else if (!stackS.isEmpty()) {
          stackS.pop();
        }
      }
    for (char c : t.toCharArray()) {
      if (c != '#') {
        stackT.push(c);
      } else if (!stackT.isEmpty()) {
        stackT.pop();
      }
    }
    if (stackS.size() != stackT.size()) {
      return false;
    }
    while (!stackS.isEmpty()) {
      if (!Objects.equals(stackS.pop(), stackT.pop())) {
        return false;
      }
    }
    return true;
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
        int left = stack.peek(); // 左挡板
        // 桶宽度为左右挡板间的距离，桶高度为左右挡板中矮的一方减去底的高度
        ans += (i - left - 1) * (Math.min(height[left], height[i]) - height[pop]);
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

  /**
   * 239. 滑动窗口最大值 <br>
   * 【单调双端队列】，使队内元素大小为严格单调递减，将元素加入队尾并维护单调性，同时从队首获取最大值。
   */
  public int[] maxSlidingWindow(int[] nums, int k) {
    int[] ans = new int[nums.length - k + 1];
    Deque<Integer> deque = new ArrayDeque<>(nums.length);
    for (int i = 0; i < nums.length; ) {
      while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[i]) { // 维护队列单调性
        deque.pollLast();
      }
      deque.offer(i); // 元素入单调队列
      while (!deque.isEmpty() && deque.peek() + k <= i) { // 清除队首过期元素（索引小于i-k+1）
        deque.poll();
      }
      if (++i >= k) { // 取最大值
        ans[i - k] = nums[deque.peek()];
      }
    }
    return ans;
  }

  /**
   * 253. 会议室 II <br>
   * 【优先队列】，使用最小堆模拟会议室，在每次会议开始时判断会议室的状态。
   */
  public int minMeetingRooms(int[][] intervals) {
    Arrays.sort(intervals, Comparator.comparingInt(i -> i[0])); // 按会议开始时间排序
    PriorityQueue<Integer> pq = new PriorityQueue<>(intervals.length); // 维护会议结束时间的最小堆
    pq.offer(intervals[0][1]); // 第一场会议开始，开启一间会议室。
    for (int i = 1; i < intervals.length; ++i) {
      // 本场会议开始了，如果堆顶的会议室里的会议已经结束了，则直接使用该会议室即可，否则需要再开一间。
      if (intervals[i][0] >= pq.peek()) {
        pq.poll(); // 因为一场会议只能使用一个会议室，故只需要poll出一个。
      }
      pq.offer(intervals[i][1]); // 会议开始，加入会议结束时间。
    }
    return pq.size(); // 遍历完成，即所有会议都开始了，此时堆的大小即是总共开启的会议室的个数。
  }
}
