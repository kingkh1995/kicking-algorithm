package com.kkk.leetcode;

import java.util.LinkedList;
import java.util.Queue;

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
      if (cache[i][j] != 0) {
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

  /** 815. 公交路线 <br> */
}
