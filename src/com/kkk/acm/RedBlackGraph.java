package com.kkk.acm;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class RedBlackGraph {

  /*
  红黑图
  输入：
  3 3
  0 1
  0 2
  1 2
  输出：
  4
  输入：
  4 3
  0 1
  1 2
  2 3
  输出：
  8
    */

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    while (in.hasNextInt()) {
      int n = in.nextInt(), m = in.nextInt();
      List<Integer>[] adj = new List[n];
      for (int i = 0; i < n; ++i) {
        adj[i] = new ArrayList<>();
      }
      for (int i = 0; i < m; ++i) {
        int a = in.nextInt(), b = in.nextInt();
        adj[a].add(b);
        adj[b].add(a);
      }
      ans = 0;
      backTrack(adj, 0, new boolean[n]);
      System.out.println(ans);
    }
  }

  public static void backTrack(List<Integer>[] adj, int v, boolean[] arr) {
    if (v == adj.length) {
      ans++;
      return;
    }
    // 1. 为黑色
    backTrack(adj, v + 1, arr);
    // 2.为红色
    for (int w : adj[v]) {
      if (w < v && arr[w]) { // 已染色的邻接点不能有红色
        return;
      }
    }
    arr[v] = true;
    backTrack(adj, v + 1, arr);
    arr[v] = false;
  }

  static int ans;
}
