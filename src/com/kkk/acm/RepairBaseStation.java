package com.kkk.acm;

import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class RepairBaseStation {

  /*
  最小基站维修距离
  输入：
  3
  0 2 1
  1 0 2
  2 1 0
  输出：
  3
     */

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int n = in.nextInt();
    int[][] dist = new int[n][n];
    for (int i = 0; i < n; ++i) {
      for (int j = 0; j < n; ++j) {
        dist[i][j] = in.nextInt();
      }
    }
    int ans = new BackTrack().minDist(dist);
    System.out.println(ans);
  }

  public static class BackTrack {

    int[][] dist;
    int ans;
    int cur;
    boolean[] mark;

    public int minDist(int[][] dist) {
      this.dist = dist;
      int n = dist.length;
      ans = Integer.MAX_VALUE;
      mark = new boolean[n];
      for (int i = 1; i < n; ++i) { // 从0出发
        cur = dist[0][i];
        backTrack(i);
      }
      return ans;
    }

    private void backTrack(int s) {
      if (s == 0) {
        ans = Math.min(ans, cur);
        return;
      }
      for (int i = 0; i < dist.length; ++i) { // 出发前往其他基站
        if (i == s || mark[i]) {
          continue;
        }
        mark[i] = true;
        cur += dist[s][i];
        backTrack(i);
        cur -= dist[s][i];
        mark[i] = false;
      }
    }
  }
}
