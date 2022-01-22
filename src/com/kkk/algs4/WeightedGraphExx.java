package com.kkk.algs4;

import com.kkk.algs4.EdgeWeightedDigraph.DirectedEdge;
import com.kkk.algs4.EdgeWeightedGraph.Edge;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * 第四章 图 有权图 <br>
 *
 * @author KaiKoo
 */
public class WeightedGraphExx {

  // ===============================================================================================

  /** 4.3.14 给定一幅加权图G和最小生成树，从G中删除一条边且G仍然是联通的，在与边数量E为正比的时间内找到新的最小生成树。 */
  /**
   * 如果删去的边不属于最小生成树，则图的最小生成树不变，否则会生成一个切分，故只要找到新的最小横切边即可。<br>
   * 先在删去边后的最小生成树中使用DFS标记顶点获得切分，之后再遍历所有的边找到最小横切边即可，故时间复杂度与E正相关。
   */

  // ===============================================================================================

  /** 4.3.15 给定一幅加权图G和最小生成树，向G中添加一条边e，在与顶点数量V为正比的时间内找到新的最小生成树。 */
  /**
   * 将e加入最小生成树，必然会构成一个环，删除该环权重最大的边即可，此时的最小生成树仍然是连通的。 <br>
   * 使用DFS在最小生成树中，找到连通e两个顶点的路径，将路径上权重最大的边与e比较即可，故时间复杂度与V正相关。
   */

  // ===============================================================================================

  /** 4.3.26 找出关键边，复杂度为ElogE，如果删去该边则最小生成树的权重就会变大，边的权重会有重复，否则关键边则是最小生成树的每一条边。 */
  /** Kruskal算法加入边时，多条权重相同的边都符合，全部加入子图，如果这些边构成了环，存在环上的边则可以互相替代，不是关键边。 */
  public static MyQueue<Edge> findCriticalEdges(EdgeWeightedGraph edgeWeightedGraph) {
    MyQueue<Edge> result = new MyQueue<>();
    // Kruskal算法
    MyQueue<Edge> queue = new MyQueue<>();
    MyBag<Edge> edges = edgeWeightedGraph.edges();
    PriorityQueue<Edge> pq = new PriorityQueue<>(edges.size());
    edges.forEach(pq::add);
    UF uf = new UF(edgeWeightedGraph.vertices());
    while (queue.size() < edgeWeightedGraph.vertices() - 1) {
      // 获取当前最小边
      Edge poll = pq.poll();
      int p = poll.either();
      int q = poll.other(p);
      if (uf.connected(p, q)) {
        // 失效边
        continue;
      }
      // 获取所有权重相同的边
      MyBag<Edge> bag = new MyBag<>();
      bag.add(poll);
      while (!pq.isEmpty() && pq.peek().weight() == poll.weight()) {
        bag.add(pq.poll());
      }
      if (bag.size() == 1) {
        // 不存在权重相同边，则为关键边
        result.offer(poll);
        uf.connected(p, q);
        queue.offer(poll);
        continue;
      }
      // 新边和原边的映射
      Map<Edge, Edge> map = new IdentityHashMap<>();
      // 构建子图，并重新创建顶点为联通分量的边。
      EdgeWeightedGraph graph = new EdgeWeightedGraph(edgeWeightedGraph.vertices());
      for (Edge edge : bag) {
        int v1 = edge.either();
        int v2 = edge.other(v1);
        int c1 = uf.find(v1);
        int c2 = uf.find(v2);
        Edge newEdge = new Edge(c1, c2, edge.weight());
        map.put(newEdge, edge);
        graph.addEdge(newEdge);
      }
      // 判断是否在环路径上，也会存在自环（因为没有过滤失效边）
      EdgeWeightedCircle edgeWeightedCircle = new EdgeWeightedCircle(graph);
      for (Edge edge : map.keySet()) {
        Edge e = map.get(edge);
        if (!edgeWeightedCircle.onCircle(edge)) {
          result.offer(e);
        }
        int v1 = e.either();
        int v2 = e.other(v1);
        // 最后添加到最小生成树，顺序加入即可，可替代的边只会加入一条。
        if (!uf.connected(v1, v2)) {
          uf.union(v1, v2);
          queue.offer(e);
        }
      }
    }
    return result;
  }

  public static class EdgeWeightedCircle {

    private EdgeWeightedGraph graph;
    private boolean visited[];
    private HashSet<Edge> onCircle;

    public EdgeWeightedCircle(EdgeWeightedGraph graph) {
      this.graph = graph;
      this.visited = new boolean[graph.vertices()];
      this.onCircle = new HashSet<>();
      // dfs访问每个顶点。
      for (int v = 0; v < graph.vertices(); v++) {
        if (!this.visited[v]) {
          MyStack<Edge> stack = new MyStack<>();
          boolean[] onStack = new boolean[graph.vertices()];
          dfs(v, onStack, stack);
        }
      }
    }

    private void dfs(int v, boolean[] onStack, MyStack<Edge> stack) {
      visited[v] = true;
      onStack[v] = true;
      for (Edge edge : graph.adj(v)) {
        stack.push(edge);
        int other = edge.other(v);
        if (onStack[other]) {
          // 在栈上则发现了环，环上的边全部加入set中
          Iterator<Edge> iterator = stack.iterator();
          onCircle.add(iterator.next());
          while (iterator.hasNext()) {
            Edge next = iterator.next();
            onCircle.add(next);
            // 一直到上一条含other顶点的边结束
            if (next.has(other)) {
              break;
            }
          }
        } else {
          // 不在栈上，继续dfs
          dfs(other, onStack, stack);
        }
        stack.pop();
      }
      // onStack标记取消，visited不取消。
      onStack[v] = false;
    }

    public boolean onCircle(Edge edge) {
      return onCircle.contains(edge);
    }
  }

  public static void findCriticalEdgesTest() {
    EdgeWeightedGraph edgeWeightedGraph1 = new EdgeWeightedGraph(4);
    edgeWeightedGraph1.addEdge(new Edge(0, 1, 1)); // Critical edge
    edgeWeightedGraph1.addEdge(new Edge(1, 2, 3));
    edgeWeightedGraph1.addEdge(new Edge(2, 3, 2)); // Critical edge
    edgeWeightedGraph1.addEdge(new Edge(3, 0, 3));
    System.out.println("Critical edges 1:");
    for (Edge edge : findCriticalEdges(edgeWeightedGraph1)) {
      System.out.println(edge);
    }
    System.out.println("Expected:\n" + "0-1 1.00000\n" + "2-3 2.00000\n");
    EdgeWeightedGraph edgeWeightedGraph2 = new EdgeWeightedGraph(4);
    edgeWeightedGraph2.addEdge(new Edge(0, 1, 5));
    edgeWeightedGraph2.addEdge(new Edge(1, 2, 5));
    edgeWeightedGraph2.addEdge(new Edge(0, 2, 5));
    edgeWeightedGraph2.addEdge(new Edge(2, 3, 3)); // Critical edge

    System.out.println("Critical edges 2:");
    for (Edge edge : findCriticalEdges(edgeWeightedGraph2)) {
      System.out.println(edge);
    }
    System.out.println("Expected:\n" + "2-3 3.00000");
  }

  // ===============================================================================================

  /** 4.3.29 使用即时的Prim算法，不使用优先队列，且在V^2次加权边比较内完成，在稠密图下性能更好。 */
  public static class PrimMST {
    private boolean[] marked; // 最小生成树顶点
    private Edge[] edges; // 最小生成树的边

    public PrimMST(EdgeWeightedGraph graph) {
      marked = new boolean[graph.vertices()];
      edges = new Edge[graph.vertices()];
      for (int next = 0; next != -1; ) {
        next = visit(graph, next);
      }
    }

    // 添加顶点，并添加或替换横切边
    private int visit(EdgeWeightedGraph graph, int v) {
      marked[v] = true;
      for (Edge e : graph.adj(v)) {
        int w = e.other(v);
        if (marked[w]) {
          // 边失效
          continue;
        }
        // 即时处理
        if (edges[w] == null || edges[w].weight() > e.weight()) {
          edges[w] = e;
        }
      }
      // 模拟IndexMinPQ的delmin操作，遍历选出下一个顶点，即目前未加入树中且距离最短的顶点
      int next = -1;
      double min = Double.POSITIVE_INFINITY;
      for (int i = 0; i < edges.length; i++) {
        if (!marked[i] && edges[i].weight() < min) {
          next = i;
          min = edges[i].weight();
        }
      }
      return next;
    }
  }

  // ===============================================================================================

  /** 4.4.7 实现DijkstraSP的另一个版本，支持返回从s到t的所有最短路径（不存在负权重，且存在多条权重相同的最短路径） */
  public static class PathDijkstraSP {

    private final MyBag<Path>[] pathTo; // 指向顶点的路径
    private final double[] distTo; // 到顶点最短路径的长度，不可达则为无穷大
    private final IndexMinPQ<Double> pq;

    public PathDijkstraSP(EdgeWeightedDigraph digraph, int s) {
      this.pathTo = (MyBag<Path>[]) new MyBag[digraph.vertices()];
      this.distTo = new double[digraph.vertices()];
      this.pq = new IndexMinPQ<>(digraph.vertices());
      for (int v = 0; v < digraph.vertices(); v++) {
        distTo[v] = Double.POSITIVE_INFINITY;
      }
      distTo[s] = 0;
      pq.set(s, 0.0D);
      while (!pq.isEmpty()) {
        relax(digraph, pq.delMin());
      }
    }

    // 修改relax方法，保存所有权重相同的路径。
    private void relax(EdgeWeightedDigraph digraph, int v) {
      for (DirectedEdge e : digraph.adj(v)) {
        int w = e.to();
        if (distTo[w] < distTo[v] + e.weight()) {
          continue;
        }
        MyBag<Path> bag;
        if (distTo[w] > distTo[v] + e.weight()) {
          // 大于情况直接覆盖
          bag = new MyBag<>();
        } else {
          // 等于情况添加
          bag = pathTo[w];
        }
        if (pathTo[v] == null || pathTo[v].isEmpty()) {
          bag.add(new Path(null, e));
        } else {
          for (Path path : pathTo[v]) {
            bag.add(new Path(path, e));
          }
        }
        distTo[w] = distTo[v] + e.weight();
        pathTo[w] = bag;
        pq.set(w, distTo[w]);
      }
    }

    public Iterable<Iterable<DirectedEdge>> pathTo(int v) {
      if (pathTo[v] == null || pathTo[v].isEmpty()) {
        return null;
      }
      MyBag<Iterable<DirectedEdge>> iterables = new MyBag<>();
      for (Path path : pathTo[v]) {
        iterables.add(path.getPath());
      }
      return iterables;
    }

    public static class Path {
      private Path previousPath;
      private DirectedEdge directedEdge;
      private double weight;

      public Path(Path previousPath, DirectedEdge directedEdge) {
        this.previousPath = previousPath;
        this.directedEdge = directedEdge;
        this.weight = directedEdge.weight() + (previousPath == null ? 0.0D : previousPath.weight);
      }

      public Iterable<DirectedEdge> getPath() {
        MyQueue<DirectedEdge> queue;
        if (previousPath == null) {
          queue = new MyQueue<>();
        } else {
          queue = (MyQueue<DirectedEdge>) previousPath.getPath();
        }
        queue.offer(directedEdge);
        return queue;
      }
    }
  }

  public static void pathDijkstraSPTest() {
    EdgeWeightedDigraph edgeWeightedDigraph = new EdgeWeightedDigraph(8);
    edgeWeightedDigraph.addEdge(new DirectedEdge(4, 5, 35));
    edgeWeightedDigraph.addEdge(new DirectedEdge(5, 4, 35));
    edgeWeightedDigraph.addEdge(new DirectedEdge(4, 7, 37));
    edgeWeightedDigraph.addEdge(new DirectedEdge(5, 7, 28));
    edgeWeightedDigraph.addEdge(new DirectedEdge(7, 5, 28));
    edgeWeightedDigraph.addEdge(new DirectedEdge(5, 1, 32));
    edgeWeightedDigraph.addEdge(new DirectedEdge(0, 4, 38));
    edgeWeightedDigraph.addEdge(new DirectedEdge(2, 0, 59));
    edgeWeightedDigraph.addEdge(new DirectedEdge(7, 3, 39));
    edgeWeightedDigraph.addEdge(new DirectedEdge(2, 7, 34));
    edgeWeightedDigraph.addEdge(new DirectedEdge(6, 2, 40));
    edgeWeightedDigraph.addEdge(new DirectedEdge(3, 6, 52));
    edgeWeightedDigraph.addEdge(new DirectedEdge(6, 0, 58));
    edgeWeightedDigraph.addEdge(new DirectedEdge(6, 4, 93));
    PathDijkstraSP pathDijkstraSP = new PathDijkstraSP(edgeWeightedDigraph, 2);
    pathDijkstraSP
        .pathTo(4)
        .forEach(
            directedEdges -> {
              System.out.println("===============");
              directedEdges.forEach(System.out::println);
            });
  }

  // ===============================================================================================

}
