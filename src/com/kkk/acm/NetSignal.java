package com.kkk.acm;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class NetSignal {

  /*
  输入：
  6 5
  0 0 0 -1 0 0 0 0 0 0 0 0 -1 4 0 0 0 0 0 0 0 0 0 0 -1 0 0 0 0 0
  1 4
  输出：
  2
    */

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    while (in.hasNextInt()) {
      int m = in.nextInt(), n = in.nextInt(), sr = -1, sc = -1, x = 0;
      int[][] arr = new int[m][n]; // 也作为标记数组
      for (int i = 0; i < m; ++i) {
        for (int j = 0; j < n; ++j) {
          arr[i][j] = in.nextInt();
          if (arr[i][j] > 0) {
            sr = i;
            sc = j;
            x = arr[i][j];
          }
        }
      }
      int dr = in.nextInt(), dc = in.nextInt();
      Queue<int[]> queue = new ArrayDeque<>();
      queue.add(new int[] {sr, sc});
      int ans = 0;
      int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
      first:
      while (!queue.isEmpty() && x-- > 0) {
        int size = queue.size();
        while (size-- > 0) {
          int[] poll = queue.poll();
          for (int[] dir : dirs) {
            int nr = poll[0] + dir[0], nc = poll[1] + dir[1];
            if (nr >= 0 && nc >= 0 && nr < m && nc < n && arr[nr][nc] == 0) {
              if (nr == dr && nc == dc) {
                ans = x;
                break first;
              }
              arr[nr][nc] = x;
              queue.offer(new int[] {nr, nc});
            }
          }
        }
      }
      System.out.println(ans);
    }
  }
}
