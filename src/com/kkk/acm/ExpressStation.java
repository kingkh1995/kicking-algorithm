package com.kkk.acm;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class ExpressStation {

  /*
  至少需要多少个快递主站点
  输入：
  4
  1 1 1 1
  1 1 1 0
  1 1 1 0
  1 0 0 1
  输出：
  1
     */

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int n = in.nextInt();
    List<Integer>[] adj = new List[n];
    for (int i = 0; i < n; ++i) {
      adj[i] = new ArrayList<>();
    }
    for (int i = 0; i < n; ++i) {
      for (int j = 0; j < n; ++j) {
        int t = in.nextInt();
        if (i < j && t == 1) {
          adj[i].add(j);
          adj[j].add(i);
        }
      }
    }
    int count = 0;
    boolean[] marked = new boolean[n];
    for (int i = 0; i < n; ++i) {
      if (!marked[i]) {
        count++;
        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(i);
        marked[i] = true;
        while (!queue.isEmpty()) {
          int poll = queue.poll();
          for (int j : adj[poll]) {
            if (!marked[j]) {
              queue.offer(j);
              marked[j] = true;
            }
          }
        }
      }
    }
    System.out.println(count);
  }
}
