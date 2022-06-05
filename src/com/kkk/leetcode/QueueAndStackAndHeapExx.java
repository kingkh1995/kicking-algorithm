package com.kkk.leetcode;

import com.kkk.supports.Queue;
import com.kkk.supports.Stack;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.IntStream;

/**
 * 队列（BFS）& 栈 (DFS) & 堆 <br>
 *
 * @author KaiKoo
 */
public class QueueAndStackAndHeapExx {

  // ===============================================================================================
  /** 基础题 */

  // 岛屿问题
  // 解法：遍历所有格子 找到第一个陆地 通过遍历相邻的陆地，并将探索过的陆地标记为已探索
  static class numIslandsSolution {

    static final int[][] dirs = new int[][] {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public int numIslands(char[][] grid) {
      int count = 0;
      for (int i = 0; i < grid.length; i++) {
        for (int j = 0; j < grid[0].length; j++) {
          if (grid[i][j] == '1') {
            count++;
            // 使用BFS或DFS
            bfs(grid, i, j);
          }
        }
      }
      return count;
    }

    // 解法1：BFS 使用队列
    private void bfs(char[][] grid, int i, int j) {
      // 起始点先置为0
      grid[i][j] = '0';
      Queue queue = new Queue();
      // 只使用一个数字表示坐标 i必然小于grid.length
      queue.enqueue(i + j * grid.length);
      while (!queue.isEmpty()) {
        // 出队列
        int code = queue.dequeue();
        int co = code % grid.length;
        int ro = code / grid.length;
        for (int[] dir : dirs) {
          int column = co + dir[0];
          int row = ro + dir[1];
          if (column >= 0
              && column < grid.length
              && row >= 0
              && row < grid[0].length
              && grid[column][row] == '1') {
            grid[column][row] = '0';
            queue.enqueue(column + row * grid.length);
          }
        }
      }
    }

    // 解法2：DFS 使用递归（即不显示使用栈，而是使用系统的方法栈）
    private void dfs(char[][] grid, int co, int ro) {
      // 当前点置为0
      grid[co][ro] = '0';
      for (int[] dir : dirs) {
        int column = co + dir[0];
        int row = ro + dir[1];
        if (column >= 0
            && column < grid.length
            && row >= 0
            && row < grid[0].length
            && grid[column][row] == '1') {
          dfs(grid, column, row);
        }
      }
    }
  }

  // 完全平方数
  public int numSquares(int n) {
    Queue queue = new Queue();
    queue.enqueue(0);
    int count = 1;
    // 标记数组
    boolean[] visited = new boolean[n];
    while (!queue.isEmpty()) {
      int size = queue.size();
      while (size-- > 0) {
        int sum = queue.dequeue();
        // 标记
        visited[sum] = true;
        // 添加子节点
        for (int i = 1; i <= n; i++) {
          int t = sum + i * i;
          if (t == n) {
            return count; // 等于则表示找到
          } else if (t < n) {
            if (!visited[t]) {
              queue.enqueue(t); // 小于且未标记过则加入
            }
          } else {
            break; // 大于了则退出循环，后面的结点已经没意义了
          }
        }
      }
      count++;
    }
    return count;
  }

  // ===============================================================================================
  /** 拔高题 */

  // 打开转盘锁 四个圆形拨轮的转盘锁
  public int openLock(String[] deadends, String target) {
    // 共四位则构建长度10000的标记数组
    boolean[] visited = new boolean[10000];
    // 将死亡数字加入
    for (String s : deadends) {
      visited[Integer.parseInt(s)] = true;
    }
    if (visited[0]) {
      return -1;
    }
    int t = Integer.parseInt(target);
    // 先记录下10的幂
    int[] pows = new int[] {1, 10, 100, 1000, 10000};
    Queue queue = new Queue();
    queue.enqueue(0);
    int count = 0;
    // BFS遍历 看成8叉树 根节点为0000 同时记录下访问过的数字 沿着层级遍历
    while (!queue.isEmpty()) {
      int n = queue.size();
      // 每次旋转次数加一次 则每轮循环count加1 循环次数为该层的结点总数
      while (n-- > 0) {
        int cur = queue.dequeue();
        if (visited[cur]) continue;
        // 如果找到则直接返回，必然为最小次数 why?
        // 因为BFS遍历了当前旋转次数下所有的可能情况，故第一次找到，则必然是最小值
        if (cur == t) return count;
        visited[cur] = true;
        // 计算出8个子节点 并加入队列
        for (int i = 0; i < 4; i++) {
          int p = cur % pows[i + 1] / pows[i];
          int add = p == 9 ? cur - 9 * pows[i] : cur + pows[i];
          int sub = p == 0 ? cur + 9 * pows[i] : cur - pows[i];
          if (!visited[add]) queue.enqueue(add);
          if (!visited[sub]) queue.enqueue(sub);
        }
      }
      count++;
    }
    return -1;
  }

  // 给定一个由 0 和 1 组成的矩阵，找出每个元素到最近的 0 的距离
  public int[][] updateMatrix(int[][] matrix) {
    int m = matrix.length, n = matrix[0].length;
    int[][] res = new int[m][n];
    boolean[][] visited = new boolean[m][n];
    int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    Queue queue = new Queue();
    // 从任意一个0出发BFS整个数组可以计算出所有的1到这个0的距离，那么为了取到最小值，需要从所有的0出发BFS一遍
    // 可以把所有的0全部加入数组，当作一个超级0，做BFS即可，1被访问到的最小次数则是最小距离
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        // 把所有的0加入队列，并标记为已访问
        if (matrix[i][j] == 0 && !visited[i][j]) {
          queue.enqueue(i * n + j);
          visited[i][j] = true;
        }
      }
    }
    while (!queue.isEmpty()) { // 不需要计算层级，故不需要双重循环
      int code = queue.dequeue();
      int i = code / n;
      int j = code % n;

      for (int k = 0; k < 4; k++) {
        int x = i + dirs[k][0], y = j + dirs[k][1];
        if (x >= 0 && x < m && y >= 0 && y < n && !visited[x][y]) {
          // 未被访问过的入队列 （必然全是1，前面已将0全部标记为已访问）
          queue.enqueue(x * n + y);
          // 标记
          visited[x][y] = true;
          // 沿着1访问，距离为上一个结点的距离加1
          res[x][y] = res[i][j] + 1;
        }
      }
    }
    return res;
  }

  // 每日气温 要想观测到更高的气温，至少需要等待的天数
  public int[] dailyTemperatures(int[] temperatures) {
    Stack stack = new Stack();
    int[] res = new int[temperatures.length];
    for (int i = 0; i < temperatures.length; ) {
      // 为空或当前元素小于等于栈顶元素 将元素下标入栈
      if (stack.isEmpty() || temperatures[i] <= temperatures[stack.top()]) {
        stack.push(i++);
      } else {
        int pop = stack.pop();
        res[pop] = i - pop;
      }
    }
    return res;
  }

  // 字符串解码  形式为 k[s]  ex: 3[z]2[2[y]pq4[2[jk]e1[f]]]ef
  public String decodeString(String s) {
    Deque<StringBuilder> stack = new LinkedList<>();
    for (char c : s.toCharArray()) {
      if (Character.isDigit(c)) {
        if (!stack.isEmpty() && Character.isDigit(stack.peek().charAt(0))) { // 当前栈顶为数字直接追加
          stack.push(stack.pop().append(c));
        } else {
          stack.push(new StringBuilder().append(c)); // 否则push
        }
      } else if (Character.isLetter(c)) {
        if (!stack.isEmpty() && Character.isLetter(stack.peek().charAt(0))) { // 当前栈顶为字符直接追加
          stack.push(stack.pop().append(c));
        } else {
          stack.push(new StringBuilder().append(c)); // 否则push
        }
      } else if (c == '[') {
        stack.push(new StringBuilder().append('[')); // 左括号直接push
      } else {
        // 右括号 取出一个字符串一个数字
        String string = stack.pop().toString(); // 取出一个字符串
        stack.pop(); // 取出一个左括号
        int i = Integer.parseInt(stack.pop().toString()); // 取出一个数字
        String repeat = string.repeat(i);
        if (!stack.isEmpty() && Character.isLetter(stack.peek().charAt(0))) {
          stack.push(stack.pop().append(repeat)); // 栈顶仍然为字符追加
        } else {
          stack.push(new StringBuilder(repeat));
        }
      }
    }
    return stack.pop().toString();
  }

  // 滑动窗口最大值，使用双端队列。
  public int[] maxSlidingWindow(int[] nums, int k) {
    int l = nums.length;
    int[] ans = new int[Math.max(l + 1 - k, 1)];
    // 双端队列保存元素的索引，队首保存窗口的最大值，队尾保存比最大值小的值。
    Deque<Integer> deque = new LinkedList<>();
    for (int i = 0; i < l; i++) {
      // 去除所有队首过期元素
      while (!deque.isEmpty() && deque.peekFirst() + k <= i) {
        deque.pollFirst();
      }
      // 入队，如果是当前最大值则入队首，否则移除队尾所有小的元素并入队尾。
      if (deque.isEmpty() || nums[i] > nums[deque.peekFirst()]) {
        deque.addFirst(i);
      } else {
        // 等于的元素仍然留在队列中
        while (nums[i] > nums[deque.peekLast()]) {
          deque.pollLast();
        }
        deque.addLast(i);
      }
      // 取最大值
      if (i >= k - 1) {
        ans[i + 1 - k] = nums[deque.peekFirst()];
      }
    }
    return ans;
  }

  // 矩阵中的最长递增路径
  static class longestIncreasingPathPathSolution {
    static final int[][] dirs = new int[][] {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    int[][] matrix;
    int rows;
    int columns;
    int[][] cache; // 缓存结果，从当前位置出发的最长递增路径长度。
    int ans;

    public int longestIncreasingPath(int[][] matrix) {
      if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
        return 0;
      }
      this.matrix = matrix;
      this.rows = matrix.length;
      this.columns = matrix[0].length;
      this.cache = new int[rows][columns];
      // dfs
      for (int i = 0; i < rows; ++i) {
        for (int j = 0; j < columns; ++j) {
          ans = Math.max(ans, dfs(i, j));
        }
      }
      return ans;
    }

    private int dfs(int i, int j) {
      if (cache[i][j] != 0) {
        return cache[i][j];
      }
      cache[i][j] = 1;
      for (int[] dir : dirs) {
        int row = i + dir[0];
        int colum = j + dir[1];
        if (row >= 0
            && row < rows
            && colum >= 0
            && colum < columns
            && matrix[row][colum] > matrix[i][j]) {
          cache[i][j] = Math.max(cache[i][j], 1 + dfs(row, colum));
        }
      }
      return cache[i][j];
    }
  }

  // 课程表，返回学习顺序，即求有向图的拓扑排序。
  static class findOrderSolution {
    List<Integer>[] adj; // 有向图邻接表
    private boolean[] marked; // 标记数组
    private boolean[] onStack; // 记录DFS路径
    private boolean hasCycle; // 是否存在环
    private int p; // 指针
    private int[] ans; // 顶点的逆后序排列，即拓扑序列

    public int[] findOrder(int numCourses, int[][] prerequisites) {
      marked = new boolean[numCourses];
      onStack = new boolean[numCourses];
      hasCycle = false;
      p = numCourses;
      ans = new int[numCourses];
      //  构建有向图
      adj = (List<Integer>[]) new List[numCourses];
      for (int[] edge : prerequisites) {
        // 添加第二个元素指向第一个元素的边
        (adj[edge[1]] == null ? (adj[edge[1]] = new ArrayList<>()) : adj[edge[1]]).add(edge[0]);
      }
      // dfs求拓扑序列
      for (int i = 0; i < numCourses; ++i) {
        // 存在环则无拓扑序列
        if (hasCycle) {
          return new int[0];
        } else if (!marked[i]) {
          dfs(i);
        }
      }
      return ans;
    }

    private void dfs(int i) {
      marked[i] = true;
      onStack[i] = true;
      if (adj[i] != null) {
        for (int v : adj[i]) {
          if (onStack[v]) {
            hasCycle = true;
            return;
          } else if (!marked[v]) {
            dfs(v);
          }
        }
      }
      onStack[i] = false;
      ans[--p] = i;
    }
  }

  // ===============================================================================================
  /** 困难题 */

  // 天际线问题
  public static List<List<Integer>> getSkyline(int[][] buildings) {
    List<List<Integer>> ans = new ArrayList<>();
    // 垂直扫描线从左往右扫描，只需要处理大楼的左右边界就可构造出天际线。
    // 收集大楼左右边界并去重排序之后遍历以确定轮廓
    int[] boundaries =
        Arrays.stream(buildings)
            .flatMapToInt(building -> IntStream.of(building[0], building[1]))
            .distinct()
            .sorted()
            .toArray();
    // 构建基于大楼高度的最大堆
    PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(arr -> -arr[2]));
    // ph用于记录上次确定的高度
    int ph = 0;
    // 按左边界顺序加入大楼，buildings本来就是左边界有序的。
    int i = 0;
    for (int line : boundaries) {
      // 大楼入堆
      while (i < buildings.length && buildings[i][0] == line) {
        pq.add(buildings[i++]);
      }
      // 处理已扫过大楼
      while (!pq.isEmpty() && pq.peek()[1] <= line) {
        pq.poll();
      }
      // 为空直接确定
      if (pq.isEmpty()) {
        ans.add(List.of(line, 0));
        ph = 0;
        continue;
      }
      int high = pq.peek()[2];
      // 确定最高的大楼，判断是否需要记录，退出内层循环进入外层循环。
      if (high != ph) {
        ans.add(List.of(line, high));
        ph = high;
      }
    }
    return ans;
  }

  static class ladderLengthSolution {

    int id = 0; // id生成
    Map<String, Integer> wordId = new HashMap<>();
    List<List<Integer>> adj = new ArrayList<>();

    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
      // endWord不存在wordList则直接返回
      if (!wordList.contains(endWord)) {
        return 0;
      }
      // 构造无向图
      for (String word : wordList) {
        addEdge(word);
      }
      addEdge(beginWord);
      // 双向bfs查找，添加顶点和终点。
      int beginId = wordId.get(beginWord), endId = wordId.get(endWord);
      Queue queue = new Queue();
      queue.enqueue(beginId);
      queue.enqueue(endId + id);
      // 前半部分是顶点到begin的距离，后半部分是顶点到end的距离
      int[] dis = new int[2 * id];
      Arrays.fill(dis, -1);
      dis[beginId] = 0;
      dis[endId + id] = 0;
      while (!queue.isEmpty()) {
        int x = queue.dequeue();
        // begin端BFS
        if (x < id) {
          for (int v : adj.get(x)) {
            if (dis[v] < 0) {
              dis[v] = dis[x] + 1;
              queue.enqueue(v);
            }
          }
          continue;
        }
        // end端BFS时的真实顶点id
        int rx = x - id;
        // 如过已经从begin端BFS到该节点了，则返回结果。
        if (dis[rx] >= 0) {
          return dis[rx] + 1;
        }
        for (int v : adj.get(rx)) {
          if (dis[v + id] < 0) {
            dis[v + id] = dis[x] + 1;
            queue.enqueue(v + id);
          }
        }
      }
      return 0;
    }

    // 图构造，每个单词为顶点，同时添加虚拟顶点，每个单词的任一字符被替换为*，如dog的三个虚拟顶点，*og、d*g、do*
    public void addEdge(String word) {
      addWord(word);
      int id1 = wordId.get(word);
      char[] array = word.toCharArray();
      int length = array.length;
      for (int i = 0; i < length; ++i) {
        char tmp = array[i];
        array[i] = '*';
        // 创建虚拟顶点
        String newWord = new String(array);
        addWord(newWord);
        int id2 = wordId.get(newWord);
        // 添加普通单词顶点到虚拟顶点的边
        adj.get(id1).add(id2);
        // 添加虚拟顶点到该普通单词顶点的边
        adj.get(id2).add(id1);
        array[i] = tmp;
      }
    }

    // 添加单词顶点映射
    public void addWord(String word) {
      if (!wordId.containsKey(word)) {
        wordId.put(word, id++);
        adj.add(new ArrayList<>());
      }
    }
  }
}
