package com.kkk.acm;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class DeliverPacakages {

  /*
  快递投放问题
  输入：
  4 2
  package1 A C
  package2 A C
  package3 B C
  package4 A C
  A B package1
  A C package2
  输出：
  package2
   */

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    while (in.hasNextInt()) {
      int m = in.nextInt(), n = in.nextInt();
      in.nextLine(); // 转到下一行
      String[] packages = new String[m];
      for (int i = 0; i < m; ++i) {
        packages[i] = in.nextLine().trim(); // 包裹 出发快递点 达到快递点
      }
      Map<String, List<Edge>> map = new HashMap<>(); // 每个快递点邻接的道路信息
      for (int i = 0; i < n; ++i) {
        String[] split = in.nextLine().trim().split(" "); // 两个快递点的道路 无法通过该道路的包裹
        Set<String> set = new HashSet<>();
        for (int j = 2; j < split.length; ++j) {
          set.add(split[j]);
        }
        Edge edge = new Edge(split[0], split[1], set);
        if (!map.containsKey(split[0])) {
          map.put(split[0], new ArrayList<>());
        }
        if (!map.containsKey(split[1])) {
          map.put(split[1], new ArrayList<>());
        }
        map.get(split[0]).add(edge);
        map.get(split[1]).add(edge);
      }
      // 依次处理每个包裹
      List<String> ans = new ArrayList<>();
      for (String s : packages) {
        String[] split = s.split(" ");
        String p = split[0], from = split[1], to = split[2];
        Set<String> mark = new HashSet<>();
        Queue<String> queue = new ArrayDeque<>();
        queue.offer(from);
        mark.add(from);
        boolean arrive = false;
        while (!queue.isEmpty()) {
          String poll = queue.poll();
          if (to.equals(poll)) { // 派送达到
            arrive = true;
            break;
          }
          for (Edge e : map.get(poll)) { // 从邻接的道路出发
            String other = e.other(poll);
            if (e.allow(p) && !mark.contains(other)) {
              queue.offer(other);
              mark.add(other);
            }
          }
        }
        if (!arrive) {
          ans.add(p);
        }
      }
      Collections.sort(ans);
      for (String s : ans) {
        System.out.print(s + " ");
      }
    }
  }

  public static class Edge {
    String v, w;
    Set<String> set;

    public Edge(String v, String w, Set<String> set) {
      this.v = v;
      this.w = w;
      this.set = set;
    }

    public String other(String station) {
      return v.equals(station) ? w : v;
    }

    public boolean allow(String s) {
      return !set.contains(s);
    }
  }
}
