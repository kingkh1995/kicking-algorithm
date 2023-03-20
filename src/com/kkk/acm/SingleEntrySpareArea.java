package com.kkk.acm;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class SingleEntrySpareArea {

  /*
  查找单入口空闲区域
  输入：
  4 5
  X X X X
  X O O O
  O X X O
  O O X X
  O X X O
  输出：
  3 4 1
     */

  static int[][] dirs = new int[][] {{0, 1}, {-1, 0}, {0, -1}, {1, 0}};

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int m = in.nextInt(), n = in.nextInt();
    boolean[][] grid = new boolean[m][n];
    for (int i = 0; i < m; ++i) {
      for (int j = 0; j < n; ++j) {
        if ("O".equals(in.next())) {
          grid[i][j] = true;
        }
      }
    }
    int[] ans = countSpare(grid);
    if (ans != null) {
      if (ans[3] > 1) {
        System.out.println(ans[2]);
      } else {
        System.out.println(ans[0] + " " + ans[1] + " " + ans[2]);
      }
    } else {
      System.out.println("NULL");
    }
  }

  public static int[] countSpare(boolean[][] grid) {
    int m = grid.length, n = grid[0].length;
    int[] ans = null;
    for (int i = 0; i < m; ++i) {
      for (int j = 0; j < n; ++j) {
        if (isBoard(i, j, m, n) && grid[i][j]) { // 只从边界出发寻找即可
          Queue<int[]> queue = new ArrayDeque<>();
          int count = 0;
          boolean flag = false;
          queue.offer(new int[] {i, j});
          grid[i][j] = false;
          while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
              count++; // 统计区域大小
              int[] poll = queue.poll();
              for (int[] dir : dirs) {
                int r = poll[0] + dir[0], c = poll[1] + dir[1];
                if (r >= 0 && r < m && c >= 0 && c < n && grid[r][c]) {
                  queue.offer(new int[] {r, c});
                  grid[r][c] = false;
                  if (isBoard(r, c, m, n)) { // 该区域存在其他边界上的入口
                    flag = true;
                  }
                }
              }
            }
          }
          if (!flag) { // 更新结果
            if (ans == null || count > ans[2]) {
              ans = new int[] {i, j, count, 1};

            } else if (count == ans[2]) {
              ans[3]++;
            }
          }
        }
      }
    }
    return ans;
  }

  private static boolean isBoard(int r, int c, int m, int n) {
    return r == 0 || r == m - 1 || c == 0 || c == n - 1;
  }
}
