package com.kkk.algs4;

import java.util.Iterator;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class EdgeWeightedDigraph {

  private final int vertices;
  private MyBag<DirectedEdge> edges;
  private final MyBag<DirectedEdge>[] adj;

  public EdgeWeightedDigraph(int vertices) {
    this.vertices = vertices;
    this.edges = new MyBag<>();
    this.adj = (MyBag<DirectedEdge>[]) new MyBag[vertices];
    for (int v = 0; v < vertices; v++) {
      adj[v] = new MyBag<>();
    }
  }

  public int vertices() {
    return vertices;
  }

  public MyBag<DirectedEdge> edges() {
    return edges;
  }

  public void addEdge(DirectedEdge e) {
    if (hasEdge(e)) {
      return;
    }
    adj[e.from].add(e);
    edges.add(e);
  }

  public MyBag<DirectedEdge> adj(int v) {
    return adj[v];
  }

  public boolean hasEdge(DirectedEdge e) {
    for (DirectedEdge edge : adj[e.from]) {
      if (edge.to == e.to) {
        return true;
      }
    }
    return false;
  }

  public static class DirectedEdge implements Comparable<DirectedEdge> {

    private final int from;
    private final int to;
    private final double weight;

    public DirectedEdge(int from, int to, double weight) {
      this.from = from;
      this.to = to;
      this.weight = weight;
    }

    @Override
    public int compareTo(DirectedEdge o) {
      return Double.compare(this.weight, o.weight);
    }

    public int from() {
      return from;
    }

    public int to() {
      return to;
    }

    public double weight() {
      return weight;
    }

    @Override
    public String toString() {
      return String.format("%d->%d %f", from, to, weight);
    }
  }

  public static class DijkstraSP {

    private final DirectedEdge[] edgesTo; // 指向顶点的边
    private final double[] distTo; // 到顶点最短路径的长度，不可达则为无穷大
    private final IndexMinPQ<Double> pq;

    public DijkstraSP(EdgeWeightedDigraph digraph, int s) {
      this.edgesTo = new DirectedEdge[digraph.vertices()];
      this.distTo = new double[digraph.vertices];
      this.pq = new IndexMinPQ<>(digraph.vertices);
      for (int v = 0; v < digraph.vertices; v++) {
        distTo[v] = Double.POSITIVE_INFINITY;
      }
      distTo[s] = 0;
      pq.set(s, 0.0D);
      while (!pq.isEmpty()) {
        relax(digraph, pq.delMin());
      }
    }

    private void relax(EdgeWeightedDigraph digraph, int v) {
      for (DirectedEdge e : digraph.adj(v)) {
        int w = e.to();
        if (distTo[w] > distTo[v] + e.weight()) {
          distTo[w] = distTo[v] + e.weight;
          edgesTo[w] = e;
          pq.set(w, distTo[w]);
        }
      }
    }

    public double distTo(int v) {
      return distTo[v];
    }

    public boolean hasPathTo(int v) {
      return distTo(v) < Double.POSITIVE_INFINITY;
    }

    public Iterable<DirectedEdge> pathTo(int v) {
      if (!hasPathTo(v)) {
        return null;
      }
      MyStack<DirectedEdge> stack = new MyStack<>();
      // 反向查找，终止条件为到达edgesTo[s]，值为null。
      for (DirectedEdge e = edgesTo[v]; e != null; e = edgesTo[e.from()]) {
        stack.push(e);
      }
      return stack;
    }
  }

  public static class EdgeWeightedDirectedCycle {

    private EdgeWeightedDigraph graph;
    private boolean[] visited;
    private boolean hasNegativeCycle;
    private Iterable<DirectedEdge> negativeCycle;

    public EdgeWeightedDirectedCycle(EdgeWeightedDigraph graph) {
      this.graph = graph;
      this.visited = new boolean[graph.vertices()];
      // dfs访问每个顶点。
      for (int v = 0; v < graph.vertices(); v++) {
        if (!this.visited[v] && !hasNegativeCycle) {
          MyStack<DirectedEdge> stack = new MyStack<>();
          boolean[] onStack = new boolean[graph.vertices()];
          dfs(v, onStack, stack);
        }
      }
    }

    private void dfs(int v, boolean[] onStack, MyStack<DirectedEdge> stack) {
      visited[v] = true;
      onStack[v] = true;
      for (DirectedEdge edge : graph.adj(v)) {
        stack.push(edge);
        int w = edge.to();
        if (onStack[w]) {
          Iterator<DirectedEdge> iterator = stack.iterator();
          MyStack<DirectedEdge> cycle = new MyStack<>();
          double totalWeight = 0.0D;
          while (iterator.hasNext()) {
            DirectedEdge next = iterator.next();
            stack.push(next);
            totalWeight += next.weight();
            if (next.from() == w) {
              if (Double.compare(0.0D, totalWeight) > 0) {
                hasNegativeCycle = true;
                this.negativeCycle = cycle;
              }
              break;
            }
          }
        } else {
          // 不在栈上，继续dfs
          dfs(w, onStack, stack);
        }
        stack.pop();
      }
      // onStack标记取消，visited不取消。
      onStack[v] = false;
    }

    public Iterable<DirectedEdge> negativeCycle() {
      return negativeCycle;
    }
  }

  public static class BellmanFordSP {

    private final double[] distTo; // 到顶点最短路径的长度，不可达则为无穷大
    private final DirectedEdge[] edgesTo; // 指向顶点的边
    private final boolean[] onQueue; // 是否在队列中
    private final MyQueue<Integer> queue; // 正在放松的顶点
    private int count; // relax计数
    private Iterable<DirectedEdge> negativeCycle;

    public BellmanFordSP(EdgeWeightedDigraph digraph, int s) {
      this.distTo = new double[digraph.vertices];
      this.edgesTo = new DirectedEdge[digraph.vertices()];
      this.onQueue = new boolean[digraph.vertices];
      this.queue = new MyQueue<>();
      this.count = 0;
      for (int v = 0; v < digraph.vertices; v++) {
        distTo[v] = Double.POSITIVE_INFINITY;
      }
      distTo[s] = 0;
      queue.offer(s);
      onQueue[s] = true;
      while (!queue.isEmpty() && !hasNegativeCycle()) {
        int v = queue.poll();
        onQueue[v] = false;
        relax(digraph, v);
      }
    }

    public boolean hasNegativeCycle() {
      return negativeCycle != null;
    }

    public Iterable<DirectedEdge> negativeCycle() {
      return negativeCycle;
    }

    private void relax(EdgeWeightedDigraph digraph, int v) {
      for (DirectedEdge e : digraph.adj(v)) {
        int w = e.to();
        if (distTo[w] > distTo[v] + e.weight()) {
          distTo[w] = distTo[v] + e.weight;
          edgesTo[w] = e;
          // 如果不在队列中则加入
          if (!onQueue[w]) {
            queue.offer(w);
            onQueue[w] = true;
          }
        }
        // 每轮relax结束后，判断是否存在非负权重环
        if (count++ % digraph.vertices() == 0) {
          EdgeWeightedDigraph subDigraph = new EdgeWeightedDigraph(digraph.vertices());
          for (DirectedEdge edge : edgesTo) {
            if (edge != null) {
              subDigraph.addEdge(edge);
            }
          }
          this.negativeCycle = new EdgeWeightedDirectedCycle(subDigraph).negativeCycle();
        }
      }
    }

    public double distTo(int v) {
      return distTo[v];
    }

    public boolean hasPathTo(int v) {
      return distTo(v) < Double.POSITIVE_INFINITY;
    }

    public Iterable<DirectedEdge> pathTo(int v) {
      if (!hasPathTo(v)) {
        return null;
      }
      MyStack<DirectedEdge> stack = new MyStack<>();
      // 反向查找，终止条件为到达edgesTo[s]，值为null。
      for (DirectedEdge e = edgesTo[v]; e != null; e = edgesTo[e.from()]) {
        stack.push(e);
      }
      return stack;
    }
  }
}
