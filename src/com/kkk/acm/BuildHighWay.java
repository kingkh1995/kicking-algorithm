package com.kkk.acm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class BuildHighWay {

  /*
  最优高铁修建方案
  输入：
  3 3 1
  1 2 10
  1 3 100
  2 3 50
  1 3
  输出：110
    */

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    while (in.hasNextInt()) {
      int n = in.nextInt(), m = in.nextInt(), k = in.nextInt();
      Map<Integer, Integer>[] adj = new Map[n];
      for (int i = 0; i < n; ++i) {
        adj[i] = new HashMap<>();
      }
      List<Edge> edges = new ArrayList<>();
      for (int i = 0; i < m; ++i) {
        Edge e = new Edge(in.nextInt() - 1, in.nextInt() - 1, in.nextInt());
        edges.add(e);
        int v = Math.min(e.v, e.w);
        int w = Math.max(e.v, e.w);
        adj[v].put(w, e.weight);
      }
      Collections.sort(edges, Comparator.comparingInt(edge -> edge.weight));
      int ans = 0;
      UF uf = new UF(n);
      for (int i = 0; i < k; ++i) { // 必要高铁线路加入
        int v = in.nextInt() - 1, w = in.nextInt() - 1;
        if (v > w) {
          int t = v;
          v = w;
          w = t;
        }
        uf.union(v, w);
        ans += adj[v].get(w);
      }
      while (uf.count() > 1) {
        Edge add = null; // 找到一条能加入的边
        for (Edge e : edges) {
          if (!uf.connected(e.v, e.w)) {
            add = e;
            uf.union(e.v, e.w);
            ans += e.weight;
            break;
          }
        }
        if (add == null) {
          break;
        }
      }
      System.out.println(uf.count() == 1 ? ans : -1);
    }
  }

  public static class Edge {
    int v, w, weight;

    public Edge(int v, int w, int weight) {
      this.v = v;
      this.w = w;
      this.weight = weight;
    }
  }

  public static class UF {

    private int count;
    private final int[] id;

    public UF(int n) {
      this.id = new int[n];
      this.count = n;
      for (int i = 0; i < n; i++) {
        this.id[i] = i;
      }
    }

    public int find(int v) {
      return id[v];
    }

    public void union(int p, int q) {
      int pID = find(p);
      int qID = find(q);
      if (pID == qID) {
        return;
      }
      for (int i = 0; i < id.length; i++) {
        if (id[i] == qID) {
          id[i] = pID;
        }
      }
      count--;
    }

    public boolean connected(int p, int q) {
      return find(p) == find(q);
    }

    public int count() {
      return this.count;
    }
  }
}
