package com.kkk.supports;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class Graph {
  private final int vertex; // 顶点个数
  private int edge; // 边的数目
  private final ListNode[] adj; // 邻接表

  public Graph(int vertex) {
    this.vertex = vertex;
    this.edge = 0;
    this.adj = new ListNode[vertex];
  }

  public int vertex() {
    return this.vertex;
  }

  public int edge() {
    return this.edge;
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
    edge++;
  }

  public ListNode adj(int v) {
    return adj[v];
  }

  public boolean hasEdge(int v, int w) {
    ListNode adj = adj(v);
    while (adj != null) {
      if (adj.val == w) {
        return true;
      }
      adj = adj.next;
    }
    return false;
  }
}
