package com.kkk.hot100;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * 栈和队列和优先队列 【DFS & BFS】<br>
 * 【回溯法回退时需要重置状态，而DFS不需要】
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

  /**
   * 200. 岛屿数量 <br>
   * DFS & BFS都可以，并将给定数组作为标记数组。
   */
  class numIslandsSolution {
    static final int[][] dirs = new int[][] {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public int numIslands(char[][] grid) {
      int count = 0;
      for (int i = 0; i < grid.length; ++i) {
        for (int j = 0; j < grid[0].length; ++j) {
          if (grid[i][j] == '1') {
            dfs(grid, i, j);
            count++;
          }
        }
      }
      return count;
    }

    private void dfs(char[][] grid, int i, int j) {
      grid[i][j] = '0';
      for (int[] dir : dirs) {
        int ni = i + dir[0], nj = j + dir[1];
        if (ni >= 0 && ni < grid.length && nj >= 0 && nj < grid[0].length && grid[ni][nj] == '1') {
          dfs(grid, ni, nj);
        }
      }
    }

    public int numIslands0(char[][] grid) {
      int m = grid.length, n = grid[0].length, count = 0;
      Queue<Integer> queue = new ArrayDeque<>(m * n); // 使用BFS，需要借助队列。
      for (int i = 0; i < m; ++i) {
        for (int j = 0; j < n; ++j) {
          if (grid[i][j] == '1') {
            count++;
            grid[i][j] = '0';
            queue.offer(i + j * m);
            while (!queue.isEmpty()) {
              int code = queue.poll(), row = code % m, col = code / m;
              for (int[] dir : dirs) {
                int nr = row + dir[0], nc = col + dir[1];
                if (nr >= 0
                    && nr < grid.length
                    && nc >= 0
                    && nc < grid[0].length
                    && grid[nr][nc] == '1') {
                  grid[nr][nc] = '0';
                  queue.offer(nr + nc * m);
                }
              }
            }
          }
        }
      }
      return count;
    }
  }

  /** 207. 课程表 <br> */
  class canFinishSolution {
    List<List<Integer>> adj; // 有向图邻接表

    private void build(int numCourses, int[][] prerequisites) {
      adj = new ArrayList<>(numCourses);
      for (int i = 0; i < numCourses; ++i) {
        adj.add(new LinkedList<>());
      }
      inDegree = new int[numCourses];
      for (int[] p : prerequisites) {
        adj.get(p[1]).add(p[0]); // 前置为p[1]，则边为p[1]->p[0]。
        ++inDegree[p[0]]; // p[0]入度加一
      }
    }

    // DFS：拓扑排序即是DFS的逆后续排列
    private int[] visited; // 1表示在DFS路径上、2表示在拓扑排序中。
    private boolean hasCycle; // 是否存在环，有环则表示无法完成。

    public boolean canFinish1(int numCourses, int[][] prerequisites) {
      build(numCourses, prerequisites);
      visited = new int[numCourses];
      hasCycle = false;
      for (int i = 0; i < numCourses; ++i) {
        if (hasCycle) {
          return false;
        } else if (visited[i] == 0) {
          dfs(i); // dfs求拓扑排序
        }
      }
      return true;
    }

    private void dfs(int i) {
      visited[i] = 1; // 先标记为在DFS路径上
      for (int v : adj.get(i)) {
        if (visited[v] == 0) {
          dfs(v);
          if (hasCycle) {
            return;
          }
        } else if (visited[v] == 1) {
          hasCycle = true;
          return;
        }
      }
      visited[i] = 2; // 因为拓扑排序是逆后续排列，故dfs回退后才标记为在拓扑排序中。
    }

    // BFS：计算每个顶点的入度，如果为0则可以加入拓扑排序，之后删去其作为起点的边。
    int[] inDegree; // 表示每个点的入度

    public boolean canFinish2(int numCourses, int[][] prerequisites) {
      build(numCourses, prerequisites);
      int count = 0; // 当前拓扑排序中的点个数，最终如果个数小于顶点总数则表示无法完成。
      Queue<Integer> queue = new ArrayDeque<>(numCourses);
      for (int i = 0; i < numCourses; ++i) {
        if (inDegree[i] == 0) { // 入度为0的顶点入队列
          queue.offer(i);
        }
      }
      while (!queue.isEmpty()) {
        int poll = queue.poll();
        count++; // 入度为0的顶点可以加入拓扑排序，因为已经不存在其前置顶点了。
        for (int v : adj.get(poll)) {
          if (--inDegree[v] == 0) { // 删去边终点的入度减一
            queue.offer(v); // 如果终点入度为0了则也可以加入拓扑排序
          }
        }
      }
      return count == numCourses;
    }
  }

  /**
   * 279. 完全平方数 <br>
   * 最优解法：【BFS】从0出发，下一节点为当前节点值加上任一平方数，bfs搜索直到找到。 <br>
   */
  public int numSquares(int n) {
    Queue<Integer> queue = new ArrayDeque<>(n);
    boolean[] marked = new boolean[n]; // 标记数组，长度n即可，最多只到n-1节点
    int count = 0; // bfs层数，即完全平方数的最少数量
    queue.offer(0);
    while (!queue.isEmpty()) {
      ++count;
      int size = queue.size(); // 本层节点数量
      while (size-- > 0) {
        int curr = queue.poll(); // 为当前节点寻找下层节点，加上任一平方数即可。
        for (int i = 1; i <= n; ++i) {
          int next = curr + i * i;
          if (next == n) { // 找到结果直接返回
            return count;
          } else if (next > n) { // 超出范围则退出寻找
            break;
          } else if (!marked[next]) { // 节点加入下一层
            marked[next] = true; // 标记
            queue.offer(next);
          }
        }
      }
    }
    return count;
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

  /**
   * 301. 删除无效的括号 <br>
   * 【BFS】，每轮删除一个括号，直到该轮出现有效的字符串为止。
   */
  public List<String> removeInvalidParentheses(String s) {
    List<String> ans = new ArrayList<>();
    Set<String> curr = new HashSet<>(); // 为了去重使用两个Set替代Queue
    curr.add(s);
    while (!curr.isEmpty()) {
      if (ans.size() > 0) {
        break;
      }
      Set<String> next = new HashSet<>();
      for (String poll : curr) {
        // 判断当前字符串是否有效
        int count = 0;
        for (int i = 0; i < poll.length() && count >= 0; ++i) {
          char c = poll.charAt(i);
          if (c == '(') {
            count++;
          } else if (c == ')') {
            count--;
          }
        }
        if (count == 0) { // 有效则添加结果
          ans.add(poll);
        } else { // 字符串无效则删除任一字符
          for (int i = 0; i < poll.length(); ++i) {
            char c = poll.charAt(i);
            if (c == '(' || c == ')') {
              next.add(poll.substring(0, i) + poll.substring(i + 1));
            }
          }
        }
      }
      curr = next; // 转到下一层
    }
    return ans;
  }
}
