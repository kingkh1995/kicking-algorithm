package com.kkk.acm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class TestMicroService {

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int n = in.nextInt();
    List<Integer>[] pre = new List[n]; // 前置依赖
    for (int i = 0; i < n; ++i) {
      pre[i] = new ArrayList<>();
    }
    int[] cost = new int[n]; // 启动时间
    for (int i = 0; i < n; ++i) {
      for (int j = 0; j < n; ++j) {
        int t = in.nextInt();
        if (i == j) {
          cost[i] = t;
        } else if (t == 1) { // 存在依赖关系，i依赖j
          pre[i].add(j);
        }
      }
    }
    int k = in.nextInt() - 1;
    int[] dist = new int[n];
    Arrays.fill(dist, Integer.MAX_VALUE);
    // Bellman-Ford算法 放松n轮即可。
    for (int count = 0; count < n; ++count) {
      for (int i = 0; i < n; ++i) {
        if (dist[i] != Integer.MAX_VALUE) {
          continue;
        }
        if (pre[i].isEmpty()) {
          dist[i] = cost[i];
        } else {
          int max = 0;
          for (int j : pre[i]) { // 取依赖的服务的最大等待时间
            max = Math.max(max, dist[j]);
          }
          if (max != Integer.MAX_VALUE) {
            dist[i] = max + cost[i];
          }
        }
      }
    }
    System.out.println(dist[k]);
  }
}
