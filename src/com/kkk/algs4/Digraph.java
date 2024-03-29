package com.kkk.algs4;

import com.kkk.supports.ListNode;
import java.util.Arrays;

/**
 * 有向图 <br>
 *
 * @author KaiKoo
 */
public class Digraph {
  private final int vertices; // 顶点个数
  private int edges; // 边的数目
  private final ListNode[] adj; // 邻接表

  public Digraph(int vertices) {
    this.vertices = vertices;
    this.edges = 0;
    this.adj = new ListNode[vertices];
  }

  public int vertices() {
    return this.vertices;
  }

  public int edges() {
    return this.edges;
  }

  public void addEdge(int v, int w) {
    // 不允许存在自环 且 不允许存在平行边
    if (v == w || hasEdge(v, w)) {
      return;
    }
    ListNode nodeW = new ListNode(w);
    nodeW.next = adj[v];
    adj[v] = nodeW;
    edges++;
  }

  public int[] adj(int v) {
    int[] temp = new int[this.vertices];
    ListNode node = adj[v];
    int i = 0;
    while (node != null) {
      temp[i++] = node.val;
      node = node.next;
    }
    return Arrays.copyOf(temp, i);
  }

  public boolean hasEdge(int v, int w) {
    for (int i : adj(v)) {
      if (i == w) {
        return true;
      }
    }
    return false;
  }

  public Digraph reverse() {
    Digraph digraph = new Digraph(this.vertices);
    for (int v = 0; v < this.vertices; v++) {
      for (int w : adj(v)) {
        digraph.addEdge(w, v);
      }
    }
    return digraph;
  }

  /** 获取拓扑排序：逆后序排列 */
  public static class Topological {
    private final boolean[] marked;
    private final boolean[] onStack; // 记录DFS路径的点
    private final Stack reversePost;
    private final Digraph digraph;
    private boolean hasCycle;

    public Topological(Digraph digraph) {
      onStack = new boolean[digraph.vertices()];
      marked = new boolean[digraph.vertices()];
      reversePost = new Stack();
      this.digraph = digraph;
      for (int v = 0; v < digraph.vertices(); v++) {
        if (!marked[v] && !hasCycle) {
          dfs(v);
        }
      }
    }

    private void dfs(int v) {
      marked[v] = true;
      onStack[v] = true;
      for (int w : digraph.adj(v)) {
        if (!marked[w]) {
          dfs(w);
        } else if (onStack[w]) {
          // 如果遇到已被遍历点，判断是否在DFS路径上，如果在则表示有环u才存在
          hasCycle = true;
          return;
        }
      }
      onStack[v] = false;
      reversePost.push(v);
    }

    public boolean isDAG() {
      return !hasCycle;
    }

    public int[] order() {
      if (hasCycle) {
        throw new UnsupportedOperationException();
      }
      int n = reversePost.size();
      int[] order = new int[n];
      for (int i = 0; i < n; i++) {
        order[i] = reversePost.pop();
      }
      return order;
    }
  }
}
