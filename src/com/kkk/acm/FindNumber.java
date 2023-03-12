package com.kkk.acm;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class FindNumber {

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int m = in.nextInt(), n = in.nextInt();
    int[][] matrix = new int[m][n];
    for (int i = 0; i < m; ++i) {
      for (int j = 0; j < n; ++j) {
        matrix[i][j] = in.nextInt();
      }
    }
    int[][] ans = new int[m][n];
    for (int i = 0; i < m; ++i) {
      for (int j = 0; j < n; ++j) {
        ans[i][j] = find(matrix, i, j);
      }
    }
    // 打印
    String[] print = new String[m];
    for (int i = 0; i < m; ++i) {
      print[i] = Arrays.toString(ans[i]);
    }
    System.out.println(Arrays.toString(print).replace(" ", ""));
  }

  public static int find(int[][] matrix, int row, int col) { // bfs查找
    int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    Queue<int[]> queue = new ArrayDeque<>();
    queue.offer(new int[] {row, col});
    boolean[][] marked = new boolean[matrix.length][matrix[0].length]; // 标记数组
    marked[row][col] = true;
    while (!queue.isEmpty()) { // 一直入队出队即可
      int[] poll = queue.poll();
      for (int[] dir : dirs) {
        int nr = poll[0] + dir[0], nc = poll[1] + dir[1];
        if (nr >= 0 && nr < matrix.length && nc >= 0 && nc < matrix[0].length && !marked[nr][nc]) {
          if (matrix[nr][nc] == matrix[row][col]) { // 找到第一个数字则直接返回结果
            return Math.abs(nr - row) + Math.abs(nc - col);
          }
          marked[nr][nc] = true;
          queue.offer(new int[] {nr, nc});
        }
      }
    }
    return -1;
  }
}
