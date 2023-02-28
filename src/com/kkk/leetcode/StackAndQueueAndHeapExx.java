package com.kkk.leetcode;

import com.kkk.algs4.Queue;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.IntStream;

/**
 * 栈 (DFS) & 队列（BFS） & 堆（优先队列） <br>
 *
 * @author KaiKoo
 */
public class StackAndQueueAndHeapExx {

  /**
   * 496. 下一个更大元素 I <br>
   * 使用递减的单调栈计算出nums2中每个元素的下一个更大元素，然后使用哈希表在nums2中快速查找nums1的元素。
   */
  public int[] nextGreaterElement(int[] nums1, int[] nums2) {
    int n1 = nums1.length, n2 = nums2.length;
    Deque<Integer> stack = new ArrayDeque<>(n2);
    Map<Integer, Integer> map = new HashMap<>(n2);
    for (int i = 0; i < n2; ++i) {
      int n = nums2[i];
      while (!stack.isEmpty() && n > nums2[stack.peek()]) {
        map.put(nums2[stack.pop()], n); // 保存nums2中元素与其下一个更大元素的关系
      }
      stack.push(i); // 维护递减的单调栈
    }
    int[] ans = new int[n1];
    for (int i = 0; i < n1; ++i) {
      ans[i] = map.getOrDefault(nums1[i], -1);
    }
    return ans;
  }

  /** 735. 行星碰撞 <br> */
  public int[] asteroidCollision(int[] asteroids) {
    Deque<Integer> stack = new ArrayDeque<>(asteroids.length);
    for (int i : asteroids) {
      boolean alive = true;
      int peek;
      // 当前行星向左移动，且栈顶行星向右移动，才会发生碰撞。
      while (alive && i < 0 && !stack.isEmpty() && (peek = stack.peek()) > 0) {
        alive = peek + i < 0; // 判断是否继续存活
        if (peek + i <= 0) { // 判断栈顶行星是否爆炸
          stack.pop();
        }
      }
      if (alive) {
        stack.push(i);
      }
    }
    int[] ans = new int[stack.size()];
    for (int i = ans.length - 1; i >= 0; --i) {
      ans[i] = stack.pop();
    }
    return ans;
  }

  /** 1046. 最后一块石头的重量 <br> */
  public int lastStoneWeight(int[] stones) {
    PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.reverseOrder());
    for (int n : stones) {
      pq.offer(n);
    }
    while (pq.size() > 1) {
      int n = pq.poll() - pq.poll();
      if (n > 0) {
        pq.offer(n);
      }
    }
    return pq.isEmpty() ? 0 : pq.poll();
  }

  // ===============================================================================================

  /**
   * 218. 天际线问题 <br>
   * 使用垂直扫描线从左往右扫描大楼，只需要处理大楼的左右边界就可构造出天际线。
   */
  public static List<List<Integer>> getSkyline(int[][] buildings) {
    List<List<Integer>> ans = new ArrayList<>();
    // 构建基于大楼高度的最大堆，以此快速找出最大高度。
    PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(arr -> -arr[2]));
    // 收集大楼左右边界并去重排序，之后使用该顺序确定轮廓。
    int[] boundaries =
        Arrays.stream(buildings)
            .flatMapToInt(building -> IntStream.of(building[0], building[1]))
            .distinct()
            .sorted()
            .toArray();
    int height, lastHeight = 0; // 记录本次和上次确定的高度
    int i = 0; // 大楼指针，按左边界顺序往最大堆内加入大楼，buildings本来就是左边界有序的。
    for (int boundary : boundaries) {
      while (i < buildings.length && buildings[i][0] == boundary) { // 按左边界将当前扫描到的大楼入堆
        pq.add(buildings[i++]);
      }
      while (!pq.isEmpty() && pq.peek()[1] <= boundary) { // 按右边界将当前扫过的大楼出堆
        pq.poll();
      }
      if (pq.isEmpty()) { // 当前扫描线经过的大楼为空，则直接确定轮廓高度为0。
        ans.add(List.of(boundary, 0));
        lastHeight = 0;
      } else if ((height = pq.peek()[2]) != lastHeight) { // 使用最大堆确定到最高的大楼，并判断是否需要记录。
        ans.add(List.of(boundary, height));
        lastHeight = height;
      }
    }
    return ans;
  }

  /**
   * 456. 132 模式 <br>
   * 单调栈，从右往左维护一个单调递减栈，其作为2元素的候选，并找出2元素的最大值。
   */
  public boolean find132pattern(int[] nums) {
    if (nums.length < 3) {
      return false;
    }
    int max2 = Integer.MIN_VALUE;
    Deque<Integer> stack = new ArrayDeque<>(nums.length);
    stack.push(nums[nums.length - 1]);
    for (int i = nums.length - 2; i >= 0; --i) {
      // 首先判断每一个元素是否可以作为1元素
      if (nums[i] < max2) {
        return true;
      }
      // 然后判断是否可作为3元素并更新2元素的最大值，占到单调栈内小于其的最大值，更新max2。
      while (!stack.isEmpty() && nums[i] > stack.peek()) {
        max2 = stack.pop();
      }
      // 最后如果大于max2则也作为2元素的候选
      if (nums[i] > max2) {
        stack.push(nums[i]);
      }
    }
    return false;
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
    int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
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
