package com.kkk.acm;

import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class RoadToCompany {

  /*
  上班之路
  输入：
  2 1
  5 5
  ..S..
  ****.
  T...*
  ****.
  .....
  输出：
  YES
    */

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    while (in.hasNextInt()) {
      int change = in.nextInt(), clear = in.nextInt(), n = in.nextInt(), m = in.nextInt();
      in.nextLine();
      String[] grid = new String[n];
      for (int i = 0; i < n; ++i) {
        grid[i] = in.nextLine().trim();
      }
      System.out.println(new FindRoad().canFindRoad(change, clear, grid) ? "YES" : "NO");
    }
  }

  public static class FindRoad {

    int change, clear, n, m;
    int sr, sc, tr, tc; // 公司的位置
    boolean[][] arr;
    boolean[][] marked;

    public boolean canFindRoad(int change, int clear, String[] grid) {
      this.change = change;
      this.clear = clear;
      this.n = grid.length;
      this.m = grid[0].length();
      this.arr = new boolean[n][m];
      for (int i = 0; i < n; ++i) {
        for (int j = 0; j < m; ++j) {
          char c = grid[i].charAt(j);
          if (c == 'S') {
            this.sr = i;
            this.sc = j;
          } else if (c == 'T') {
            this.tr = i;
            this.tc = j;
          } else if (c == '*') { // 路障为true
            arr[i][j] = true;
          }
        }
      }
      this.marked = new boolean[n][m];
      marked[sr][sc] = true;
      // 从家里出发，四个方向都可以选择。
      return dfs(0, sr, sc) || dfs(1, sr, sc) || dfs(2, sr, sc) || dfs(3, sr, sc);
    }

    public static int[][] dirs = {{-1, 0}, {0, -1}, {1, 0}, {0, 1}}; // 上左下右

    private boolean dfs(int dir, int row, int col) {
      if (row == tr && col == tc) { // 判断是否到达
        return true;
      }
      if (arr[row][col]) { // 当前是路障，则需要清除
        if (clear == 0) { // 无清除次数则失败
          return false;
        } else {
          clear--;
        }
      }
      // 1.继续当前方向
      if (move(dir, row, col)) {
        return true;
      }
      // 2.拐弯
      if (change == 0) { // 无拐弯次数则失败
        return false;
      }
      change--; // 拐弯，上下则可改成左右，左右改成上下。
      if (move((dir + 1) % 4, row, col) || move((dir + 3) % 4, row, col)) {
        return true;
      }
      change++; // 回溯
      if (arr[sr][sc]) { // 走到这里肯定清除了路障，进行回溯。
        clear++;
      }
      return false;
    }

    private boolean move(int dir, int row, int col) {
      int nr = row + dirs[dir][0], nc = col + dirs[dir][1];
      if (nr >= 0 && nr < n && nc >= 0 && nc < m && !marked[nr][nc]) {
        marked[nr][nc] = true;
        if (dfs(dir, nr, nc)) {
          return true;
        }
        marked[nr][nc] = false;
      }
      return false;
    }
  }
}
