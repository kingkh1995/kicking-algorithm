package com.kkk.supports;

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
    // 不允许存在自环
    if (v == w) {
      return;
    }
    //  不允许存在平行边
    if (hasEdge(v, w)) {
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

  public static class Topological {
    private final boolean[] marked;
    private final boolean[] onStack;
    private final Stack reversePost;
    private boolean hasCycle;

    public Topological(Digraph digraph) {
      onStack = new boolean[digraph.vertices()];
      marked = new boolean[digraph.vertices()];
      reversePost = new Stack();
      for (int v = 0; v < digraph.vertices(); v++) {
        if (!marked[v]) {
          dfs(digraph, v);
        }
      }
    }

    private void dfs(Digraph digraph, int v) {
      marked[v] = true;
      onStack[v] = true;
      for (int w : digraph.adj(v)) {
        if (!marked[w]) {
          dfs(digraph, w);
        } else if (onStack[w]) {
          hasCycle = true;
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
