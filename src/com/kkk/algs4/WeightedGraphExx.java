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
    private boolean[] visited;
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

  /** 4.4.23 给定两点的最短路径，实现DijkstraSP的改进版本，快速求得给定两点的最短路径 */
  /**
   * 只需要在将终点加入最短路径树的时候停止算法即可，因为对一个顶点的放松不会减小该顶点的distTo值，且因为不存在负权重边， <br>
   * 所以一个顶点被加入最短路径树中（被放松完）后，该顶点的distTo值已经不可能再减小了。
   */

  // ===============================================================================================

  /** 4.4.41 双向搜索，解决4.4.23给定两点的最短路径问题，在最开始时将起点和终点都加入队列。 */
  /**
   * 将终点作为反转图的起点，两个图同时执行Dijkstra算法，每次选择两个图中最近的顶点放松，当该顶点已经加入图和反向图的 <br>
   * 最短路径树的时候终止算法即可，终止后从取两个图中distTo值和的最小值，得到中间顶点，最后得出最短路径。
   */
  public static class DijkstraSPSourceSinkBidirectional {

    // 原图
    private DirectedEdge[] edgeToSource;
    private double[] distToSource;
    private boolean[] relaxedFromSource;

    // 反转图
    private DirectedEdge[] edgeToTarget;
    private double[] distToTarget;
    private boolean[] relaxedFromTarget;

    // 公共队列，反转图序号为vertices+index
    private final IndexMinPQ<Double> pq;

    // 最短路径
    public Iterable<DirectedEdge> path;
    public Double distTo;

    public DijkstraSPSourceSinkBidirectional(EdgeWeightedDigraph digraph, int source, int target) {
      int vertices = digraph.vertices();
      this.edgeToSource = new DirectedEdge[vertices];
      this.distToSource = new double[vertices];
      this.relaxedFromSource = new boolean[vertices];
      this.edgeToTarget = new DirectedEdge[vertices];
      this.distToTarget = new double[vertices];
      this.relaxedFromTarget = new boolean[vertices];
      this.pq = new IndexMinPQ<>(2 * vertices);
      // 反转图
      EdgeWeightedDigraph reverseDigraph = new EdgeWeightedDigraph(vertices);
      for (int vertex = 0; vertex < vertices; vertex++) {
        for (DirectedEdge edge : digraph.adj(vertex)) {
          reverseDigraph.addEdge(new DirectedEdge(edge.to(), edge.from(), edge.weight()));
        }
      }
      for (int v = 0; v < vertices; v++) {
        distToSource[v] = Double.POSITIVE_INFINITY;
        distToTarget[v] = Double.POSITIVE_INFINITY;
      }
      distToSource[source] = 0;
      distToTarget[target] = 0;
      pq.set(source, 0.0D);
      pq.set(target + vertices, 0.0D);
      while (!pq.isEmpty()) {
        int nextVertexToRelax = pq.delMin();
        boolean inSource = nextVertexToRelax < vertices;
        int midVertex = inSource ? nextVertexToRelax : nextVertexToRelax - vertices;
        if (inSource) {
          relax(digraph, distToSource, edgeToSource, midVertex, true);
          relaxedFromSource[midVertex] = true;
        } else {
          relax(reverseDigraph, distToTarget, edgeToTarget, midVertex, false);
          relaxedFromTarget[midVertex] = true;
        }
        // 终止条件，顶点已经加入了两个图的最短路径树
        if (relaxedFromSource[midVertex] && relaxedFromTarget[midVertex]) {
          compute();
          break;
        }
      }
    }

    private void relax(
        EdgeWeightedDigraph digraph,
        double[] distTo,
        DirectedEdge[] edgeTo,
        int v,
        boolean inSource) {
      for (DirectedEdge e : digraph.adj(v)) {
        int w = e.to();
        if (distTo[w] > distTo[v] + e.weight()) {
          distTo[w] = distTo[v] + e.weight();
          edgeTo[w] = e;
          pq.set(inSource ? w : w + digraph.vertices(), distTo[w]);
        }
      }
    }

    private void compute() {
      // 先找到到中间顶点
      int midVertex = -1;
      double weight = Double.POSITIVE_INFINITY;
      for (int i = 0; i < distToSource.length; i++) {
        if (distToSource[i] + distToTarget[i] < weight) {
          weight = distToSource[i] + distToTarget[i];
          midVertex = i;
        }
      }
      // 未找到则表示不存在
      if (midVertex == -1) {
        return;
      }
      MyStack<DirectedEdge> stack = new MyStack<>();
      // 先从正向图中反向查找
      for (DirectedEdge e = edgeToSource[midVertex]; e != null; e = edgeToSource[e.from()]) {
        stack.push(e);
      }
      MyQueue<DirectedEdge> queue = new MyQueue<>();
      stack.forEach(queue::offer);
      // 再从反向图中反向查找
      for (DirectedEdge e = edgeToTarget[midVertex]; e != null; e = edgeToTarget[e.from()]) {
        queue.offer(new DirectedEdge(e.to(), e.from(), e.weight()));
      }
      this.path = queue;
      this.distTo = weight;
    }
  }

  public static void DijkstraSPSourceSinkBidirectionalTest() {
    EdgeWeightedDigraph edgeWeightedDigraph = new EdgeWeightedDigraph(8);
    edgeWeightedDigraph.addEdge(new DirectedEdge(4, 5, 0.35));
    edgeWeightedDigraph.addEdge(new DirectedEdge(5, 4, 0.35));
    edgeWeightedDigraph.addEdge(new DirectedEdge(4, 7, 0.37));
    edgeWeightedDigraph.addEdge(new DirectedEdge(5, 7, 0.28));
    edgeWeightedDigraph.addEdge(new DirectedEdge(7, 5, 0.28));
    edgeWeightedDigraph.addEdge(new DirectedEdge(5, 1, 0.32));
    edgeWeightedDigraph.addEdge(new DirectedEdge(0, 4, 0.38));
    edgeWeightedDigraph.addEdge(new DirectedEdge(0, 2, 0.26));
    edgeWeightedDigraph.addEdge(new DirectedEdge(7, 3, 0.39));
    edgeWeightedDigraph.addEdge(new DirectedEdge(1, 3, 0.29));
    edgeWeightedDigraph.addEdge(new DirectedEdge(2, 7, 0.34));
    edgeWeightedDigraph.addEdge(new DirectedEdge(6, 2, 0.40));
    edgeWeightedDigraph.addEdge(new DirectedEdge(3, 6, 0.52));
    edgeWeightedDigraph.addEdge(new DirectedEdge(6, 0, 0.58));
    edgeWeightedDigraph.addEdge(new DirectedEdge(6, 4, 0.93));
    int source = 0;
    int target = 6;
    DijkstraSPSourceSinkBidirectional dijkstraSPSourceSinkBidirectional =
        new DijkstraSPSourceSinkBidirectional(edgeWeightedDigraph, source, target);
    System.out.println("Shortest path from 0 to 6:");

    if (dijkstraSPSourceSinkBidirectional.path != null) {
      System.out.printf(
          "%d to %d (%.2f)  ", source, target, dijkstraSPSourceSinkBidirectional.distTo);
      for (DirectedEdge edge : dijkstraSPSourceSinkBidirectional.path) {
        System.out.print(edge + "   ");
      }
      System.out.println();
    } else {
      System.out.printf("%d to %d         no path\n", source, target);
    }
    System.out.println("Expected:");
    System.out.println("0 to 6 (1.51)  0->2 0.26   2->7 0.34   7->3 0.39   3->6 0.52");
    EdgeWeightedDigraph edgeWeightedDigraph2 = new EdgeWeightedDigraph(8);
    edgeWeightedDigraph2.addEdge(new DirectedEdge(0, 1, 1));
    edgeWeightedDigraph2.addEdge(new DirectedEdge(0, 2, 4));
    edgeWeightedDigraph2.addEdge(new DirectedEdge(1, 3, 5));
    edgeWeightedDigraph2.addEdge(new DirectedEdge(2, 7, 1));
    edgeWeightedDigraph2.addEdge(new DirectedEdge(3, 4, 3));
    edgeWeightedDigraph2.addEdge(new DirectedEdge(4, 6, 1));
    edgeWeightedDigraph2.addEdge(new DirectedEdge(5, 6, 5));
    edgeWeightedDigraph2.addEdge(new DirectedEdge(7, 4, 2));
    int source2 = 0;
    int target2 = 6;
    DijkstraSPSourceSinkBidirectional dijkstraSPSourceSinkBidirectional2 =
        new DijkstraSPSourceSinkBidirectional(edgeWeightedDigraph2, source2, target2);
    System.out.println("\nShortest path from 0 to 6:");
    if (dijkstraSPSourceSinkBidirectional2.path != null) {
      System.out.printf(
          "%d to %d (%.2f)  ", source2, target2, dijkstraSPSourceSinkBidirectional2.distTo);
      for (DirectedEdge edge : dijkstraSPSourceSinkBidirectional2.path) {
        System.out.print(edge + "   ");
      }
      System.out.println();
    } else {
      System.out.printf("%d to %d         no path\n", source2, target2);
    }
    System.out.println("Expected:");
    System.out.println("0 to 6 (8.00)  0->2 4.00   2->7 1.00   7->4 2.00   4->6 1.00");
    EdgeWeightedDigraph edgeWeightedDigraph3 = new EdgeWeightedDigraph(5);
    edgeWeightedDigraph3.addEdge(new DirectedEdge(0, 1, 3));
    edgeWeightedDigraph3.addEdge(new DirectedEdge(0, 2, 5));
    edgeWeightedDigraph3.addEdge(new DirectedEdge(1, 3, 3));
    edgeWeightedDigraph3.addEdge(new DirectedEdge(3, 4, 3));
    edgeWeightedDigraph3.addEdge(new DirectedEdge(2, 4, 5));
    int source3 = 0;
    int target3 = 4;
    DijkstraSPSourceSinkBidirectional dijkstraSPSourceSinkBidirectional3 =
        new DijkstraSPSourceSinkBidirectional(edgeWeightedDigraph3, source3, target3);
    System.out.println("\nShortest path from 0 to 4:");
    if (dijkstraSPSourceSinkBidirectional3.path != null) {
      System.out.printf(
          "%d to %d (%.2f)  ", source3, target3, dijkstraSPSourceSinkBidirectional3.distTo);
      for (DirectedEdge edge : dijkstraSPSourceSinkBidirectional3.path) {
        System.out.print(edge + "   ");
      }
      System.out.println();
    } else {
      System.out.printf("%d to %d         no path\n", source3, target3);
    }
    System.out.println("Expected:");
    System.out.println("0 to 4 (9.00)  0->1 3.00   1->3 3.00   3->4 3.00");
  }

  // ===============================================================================================

  /** 4.4.36 邻居顶点，找出加权有向图中与给定顶点距离在d之内的所有顶点。 */
  /** 与4.4.23类似，只需要当前加入最短路径树的顶点距离大于d时终止算法并在最后删除该顶点即可。 */

  // ===============================================================================================

  /** 4.4.25 给定一个起点集合和一个终点集合，实现DijkstraSP的改进版本，快速求得从任意起点到任意终点的最短路径 */
  /** 在算法最开始时，将所有起点的distTo设为0，同时在所有终点都加入最短路径树的后停止算法即可。 */

  // ===============================================================================================

  /** 4.4.33 网格图的最短路径，给定一个n*n的正整数矩阵，找到从(0,0)到(n-1，n-1)的最短路径，如果存在只能向下和向右限制呢。 */
  /**
   * 构造一幅无负权重的有向图，将每一个格子均视为一个顶点，从每一个格子出发构造边，可以向四个方向移动，则每个方向的边为，<br>
   * 起点为自身，终点为该方向的相邻格，权重为自身数值与相邻格的数值之和，构造有向图完成后，使用Dijkstra算法即可。<br>
   * 存在只能向下和向右限制则只能选择两个方向构造边，其他相同。
   */

  // ===============================================================================================

  /** 4.4.34 单调最短路径，该最短路径上边的权重为单调递增或递减。 */
  /**
   * 将所有边按权重排序，以递增的顺序放松每一条边，即可得到单调递增的最短路径，同理可以得到单调递减的最短路径，之后对比取短的即可。<br>
   * 因为最短路径树是从起点开始慢慢构建的，即每条边如果其起点未被加入树中则不可能被放松，则以边权重的单调顺序依次放松，则最短路径也是单调的。
   */

  // ===============================================================================================

  /** 4.4.43 负权重环检测，基于BellmanFordSP实现， */
  /** 只需要向原图新增一个起点，并创建其指向所有的顶点的权重为0的边即可，使用BellmanFordSP的构造函数，起点为新增的起点即可。 */

  // ===============================================================================================

}
