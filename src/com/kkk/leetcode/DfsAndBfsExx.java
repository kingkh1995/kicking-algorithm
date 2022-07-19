package com.kkk.leetcode;

/**
 * DFS & BFS <br>
 *
 * @author KaiKoo
 */
public class DfsAndBfsExx {

  /**
   * 329. 矩阵中的最长递增路径 <br>
   * 【DFS记忆化搜索】
   */
  class longestIncreasingPathSolution {
    static final int[][] dirs = new int[][] {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
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

  // ===============================================================================================
}
