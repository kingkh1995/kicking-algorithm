package com.kkk.algs4;

import java.util.Arrays;
import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class EdgeWeightedGraph {

  private final int vertices;
  private MyBag<Edge> edges;
  private final MyBag<Edge>[] adj;

  public EdgeWeightedGraph(int vertices) {
    this.vertices = vertices;
    this.edges = new MyBag<>();
    this.adj = (MyBag<Edge>[]) new MyBag[vertices];
    for (int v = 0; v < vertices; v++) {
      adj[v] = new MyBag<Edge>();
    }
  }

  public int vertices() {
    return vertices;
  }

  public MyBag<Edge> edges() {
    return edges;
  }

  public void addEdge(Edge e) {
    if (hasEdge(e)) {
      return;
    }
    adj[e.v].add(e);
    adj[e.w].add(e);
    edges.add(e);
  }

  public MyBag<Edge> adj(int v) {
    return adj[v];
  }

  public boolean hasEdge(Edge e) {
    int v = e.either();
    for (Edge edge : adj[v]) {
      if (edge.other(v) == e.other(v)) {
        return true;
      }
    }
    return false;
  }

  public static class Edge implements Comparable<Edge> {

    private final int v;
    private final int w;
    private final double weight;

    public Edge(int v, int w, double weight) {
      if (v == w) {
        throw new IllegalArgumentException();
      }
      this.v = v;
      this.w = w;
      this.weight = weight;
    }

    @Override
    public int compareTo(Edge o) {
      return Double.compare(this.weight, o.weight);
    }

    public int either() {
      return v;
    }

    public int other(int vertext) {
      if (vertext == v) {
        return w;
      } else if (vertext == w) {
        return v;
      } else {
        throw new IllegalArgumentException();
      }
    }

    public double weight() {
      return weight;
    }

    @Override
    public String toString() {
      return String.format("%d-%d %f", v, w, weight);
    }
  }

  public static class LazyPrimMST {
    private boolean[] marked; // 最小生成树顶点
    private MyBag<Edge> mst; // 最小生成树的边
    private PriorityQueue<Edge> pq; // 横切边，辅助优先队列

    public LazyPrimMST(EdgeWeightedGraph graph) {
      pq = new PriorityQueue<>();
      marked = new boolean[graph.vertices];
      mst = new MyBag<>();
      visit(graph, 0);
      // 判断条件，pq为空即可，前提条件是图必然为连通的
      while (!pq.isEmpty()) {
        // 取出权重最小的边
        Edge e = pq.poll();
        int v = e.either(), w = e.other(v);
        // 延迟处理，检查是否为横切边
        if (marked[v] && marked[w]) {
          continue;
        }
        // 添加边，并添加下一个顶点
        mst.add(e);
        if (marked[v]) {
          visit(graph, w);
        } else if (marked[w]) {
          visit(graph, v);
        }
      }
    }

    // 添加顶点，并添加横切边
    private void visit(EdgeWeightedGraph graph, int v) {
      // 标记顶点
      marked[v] = true;
      // 将所有横切边加入堆
      for (Edge e : graph.adj(v)) {
        if (!marked[e.other(v)]) {
          pq.offer(e);
        }
      }
    }

    public Iterator<Edge> edges() {
      return mst.iterator();
    }
  }

  public static class PrimMST {
    private boolean[] marked; // 最小生成树顶点
    private Edge[] edges; // 最小生成树的边
    private IndexMinPQ<Edge> pq; // 只保留每个顶点到树权重最小横切边，辅助索引优先队列

    public PrimMST(EdgeWeightedGraph graph) {
      pq = new IndexMinPQ<>(graph.vertices);
      marked = new boolean[graph.vertices];
      edges = new Edge[graph.vertices];
      visit(graph, 0);
      while (!pq.isEmpty()) {
        visit(graph, pq.delMin());
      }
    }

    // 添加顶点，并添加或替换横切边
    private void visit(EdgeWeightedGraph graph, int v) {
      marked[v] = true;
      for (Edge e : graph.adj(v)) {
        int w = e.other(v);
        if (marked[w]) {
          // 边失效
          continue;
        }
        // 即时处理，替换掉权重大的边
        if (pq.get(w) == null || pq.get(w).weight() > e.weight()) {
          edges[w] = e;
          pq.set(w, e);
        }
      }
    }

    public Iterator<Edge> edges() {
      return Arrays.stream(edges).iterator();
    }
  }
}
