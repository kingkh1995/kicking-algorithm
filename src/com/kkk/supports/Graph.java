package com.kkk.supports;

import java.util.Arrays;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class Graph {
  private final int vertices; // 顶点个数
  private int edges; // 边的数目
  private final ListNode[] adj; // 邻接表

  public Graph(int vertices) {
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
    ListNode nodeV = new ListNode(v);
    ListNode nodeW = new ListNode(w);
    nodeV.next = adj[w];
    nodeW.next = adj[v];
    adj[w] = nodeV;
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
}
