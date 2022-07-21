package com.kkk.hot;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * DFS & BFS <br>
 *
 * @author KaiKoo
 */
public class DfsAndBfsHot {
  static final int[][] dirs = new int[][] {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

  /**
   * 200. 岛屿数量 <br>
   * DFS & BFS都可以，并将给定数组作为标记数组。
   */
  class numIslandsSolution {

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

  /** 494. 目标和 <br> */
  class findTargetSumWaysSolution {
    private int count;
    private int[] nums;
    private int target;

    public int findTargetSumWays(int[] nums, int target) {
      this.count = 0;
      this.nums = nums;
      this.target = target;
      dfs(0, 0);
      return this.count;
    }

    private void dfs(int i, int sum) {
      if (i == nums.length) {
        if (sum == target) {
          count++;
        }
        return;
      }
      dfs(i + 1, sum + nums[i]);
      dfs(i + 1, sum - nums[i]);
    }
  }

  /** 733. 图像渲染 <br> */
  class floodFillSolution {
    int[][] image;
    int m, n, oldColor, newColor;

    public int[][] floodFill(int[][] image, int sr, int sc, int color) {
      this.image = image;
      m = image.length;
      n = image[0].length;
      oldColor = image[sr][sc];
      newColor = color;
      dfs(sr, sc);
      return image;
    }

    private void dfs(int row, int column) {
      if (image[row][column] == newColor) {
        return;
      }
      image[row][column] = newColor;
      for (int[] dir : dirs) {
        int nr = row + dir[0], nc = column + dir[1];
        if (nr >= 0 && nr < m && nc >= 0 && nc < n && image[nr][nc] == oldColor) {
          dfs(nr, nc);
        }
      }
    }
  }

  // ===============================================================================================

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

  /**
   * 399. 除法求值 <br>
   * 解法一：可以构建出一幅加权有向图，并使用BFS查询结果。 <br>
   * 解法二：带权并查集 <br>
   * todo...
   */
}
