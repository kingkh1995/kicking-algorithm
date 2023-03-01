package com.kkk.leetcode;

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
public class DfsAndBfsExx {

  static final int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

  /**
   * 329. 矩阵中的最长递增路径 <br>
   * 【DFS记忆化搜索】
   */
  class longestIncreasingPathSolution {
    int[][] matrix;
    int rows;
    int columns;
    int[][] cache; // 缓存结果，从当前位置出发的最长递增路径长度，同时也作为mark数组。
    int ans;

    public int longestIncreasingPath(int[][] matrix) {
      if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
        return 0;
      }
      this.matrix = matrix;
      this.rows = matrix.length;
      this.columns = matrix[0].length;
      this.cache = new int[rows][columns];
      for (int i = 0; i < rows; ++i) {
        for (int j = 0; j < columns; ++j) {
          ans = Math.max(ans, dfs(i, j)); // DFS尝试更新最大值
        }
      }
      return ans;
    }

    private int dfs(int i, int j) {
      if (cache[i][j] != 0) { // 非0表示已搜索则返回结果
        return cache[i][j];
      }
      cache[i][j] = 1; // 设置默认值也等同标记
      for (int[] dir : dirs) {
        int ni = i + dir[0];
        int nj = j + dir[1];
        if (ni >= 0 && ni < rows && nj >= 0 && nj < columns && matrix[ni][nj] > matrix[i][j]) {
          cache[i][j] = Math.max(cache[i][j], 1 + dfs(ni, nj));
        }
      }
      return cache[i][j];
    }
  }

  /**
   * 994. 腐烂的橘子 <br>
   * 【BFS】
   */
  public int orangesRotting(int[][] grid) {
    Queue<Integer> queue = new LinkedList<>();
    int m = grid.length, n = grid[0].length, fresh = 0, count = 0;
    for (int i = 0; i < m; ++i) { // 统计新鲜橘子数量，将烂橘子加入队列中。
      for (int j = 0; j < n; ++j) {
        if (grid[i][j] == 1) {
          fresh++;
        } else if (grid[i][j] == 2) {
          queue.offer(i * n + j);
        }
      }
    }
    while (!queue.isEmpty() && fresh > 0) { // fresh大于0判断很关键
      count++;
      int size = queue.size();
      while (size-- > 0) {
        int poll = queue.poll();
        for (int[] dir : dirs) {
          int i = poll / n + dir[0], j = poll % n + dir[1];
          if (i >= 0 && i < m && j >= 0 && j < n && grid[i][j] == 1) {
            fresh--;
            grid[i][j] = 2;
            queue.offer(i * n + j);
          }
        }
      }
    }
    return fresh == 0 ? count : -1;
  }

  // ===============================================================================================

  /**
   * 1042. 不邻接植花 <br>
   * 直接遍历花园进行填充即可，不需要DFS进行回退，因为必然存在结果，即填充过程中找到一个可用的颜色就可以继续了。
   */
  public int[] gardenNoAdj(int n, int[][] paths) {
    int[] ans = new int[n];
    int[] flowers = {1, 2, 3, 4};
    List<Integer>[] adj = new List[n]; // 邻接表
    Set<Integer>[] used = new Set[n]; // 当前花园的邻居已使用的颜色
    for (int i = 0; i < n; i++) { // 初始化
      adj[i] = new ArrayList<>();
      used[i] = new HashSet<>();
    }
    for (int[] edge : paths) { // 构建图
      adj[edge[0] - 1].add(edge[1] - 1);
      adj[edge[1] - 1].add(edge[0] - 1);
    }
    for (int i = 0, f; i < n; ++i) { // 直接遍历花园填充即可
      for (int j = 0; used[i].contains(f = flowers[j]); ++j) // 确定到任意一个可使用的颜色即可
        ;
      ans[i] = f; // 填充颜色
      for (int v : adj[i]) { // 颜色信息同步到剩余未填充颜色的邻居
        if (v > i) { // 之前已确定颜色的花园可以忽略
          used[v].add(f);
        }
      }
    }
    return ans;
  }
}
