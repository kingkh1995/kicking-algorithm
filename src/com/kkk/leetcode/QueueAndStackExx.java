package com.kkk.leetcode;

import com.kkk.algs4.MyStack;
import com.kkk.supports.Queue;
import com.kkk.supports.Stack;

/**
 * 队列和栈 (BFS & DFS) <br>
 *
 * @author KaiKoo
 */
public class QueueAndStackExx {

  // ===============================================================================================
  /** 基础题 */

  // 岛屿问题
  // 解法：遍历所有格子 找到第一个陆地 通过遍历相邻的陆地，并将探索过的陆地标记为已探索
  public int numIslands(char[][] grid) {
    int count = 0;
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[0].length; j++) {
        if (grid[i][j] == '1') {
          count++;
          // 找到第一个陆地探索
          bfs(grid, i, j); // 使用BFS或DFS
        }
      }
    }
    return count;
  }

  // 解法1：BFS 使用队列
  private void bfs(char[][] grid, int i, int j) {
    // 起始点先置为0
    grid[i][j] = '0';
    int column = grid.length;
    int row = grid[0].length;
    Queue queue = new Queue();
    // 起始点入队列
    // 只使用一个数字表示坐标 ro必定小于row co=code/row ro=code%row
    queue.enqueue(i * row + j);
    while (!queue.isEmpty()) {
      // 出队列
      int code = queue.dequeue();
      int co = code / row;
      int ro = code % row;
      // 上下左右入队列 如果为1 则设为0并且加入队列
      // 上
      if (co > 0 && grid[co - 1][ro] == '1') {
        grid[co - 1][ro] = '0';
        queue.enqueue((co - 1) * row + ro);
      }
      // 下
      if (co < column - 1 && grid[co + 1][ro] == '1') {
        grid[co + 1][ro] = '0';
        queue.enqueue((co + 1) * row + ro);
      }
      // 左
      if (ro > 0 && grid[co][ro - 1] == '1') {
        grid[co][ro - 1] = '0';
        queue.enqueue(co * row + ro - 1);
      }
      // 右
      if (ro < row - 1 && grid[co][ro + 1] == '1') {
        grid[co][ro + 1] = '0';
        queue.enqueue(co * row + ro + 1);
      }
    }
  }

  // 解法2：DFS 使用递归（即不显示使用栈，而是使用系统的方法栈）
  private void dfs(char[][] grid, int co, int ro) {
    // 当前点置为0
    grid[co][ro] = '0';
    int column = grid.length;
    int row = grid[0].length;
    // 上下左递归探索
    // 上
    if (co > 0 && grid[co - 1][ro] == '1') {
      dfs(grid, co - 1, ro);
    }
    // 下
    if (co < column - 1 && grid[co + 1][ro] == '1') {
      dfs(grid, co + 1, ro);
    }
    // 左
    if (ro > 0 && grid[co][ro - 1] == '1') {
      dfs(grid, co, ro - 1);
    }
    // 右
    if (ro < row - 1 && grid[co][ro + 1] == '1') {
      dfs(grid, co, ro + 1);
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
    MyStack<StringBuilder> stack = new MyStack<>();
    for (char c : s.toCharArray()) {
      if (Character.isDigit(c)) {
        if (!stack.isEmpty() && Character.isDigit(stack.top().charAt(0))) { // 当前栈顶为数字直接追加
          stack.push(stack.pop().append(c));
        } else {
          stack.push(new StringBuilder().append(c)); // 否则push
        }
      } else if (Character.isLetter(c)) {
        if (!stack.isEmpty() && Character.isLetter(stack.top().charAt(0))) { // 当前栈顶为字符直接追加
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
        if (!stack.isEmpty() && Character.isLetter(stack.top().charAt(0))) {
          stack.push(stack.pop().append(repeat)); // 栈顶仍然为字符追加
        } else {
          stack.push(new StringBuilder(repeat));
        }
      }
    }
    return stack.pop().toString();
  }
}
