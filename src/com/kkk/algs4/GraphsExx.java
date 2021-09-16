package com.kkk.algs4;

import com.kkk.algs4.Digraph.Topological;
import com.kkk.supports.ArrayUtils;
import com.kkk.supports.Queue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 第四章 图 有向图
 *
 * @author KaiKoo
 */
public class GraphsExx {

  // ===============================================================================================

  public static class UFSearch {
    private int count;
    private final int[] id;
    private final int sID;

    public UFSearch(Graph g, int s) {
      this.id = new int[g.vertices()];
      // 对每条边使用union操作
      for (int v = 0; v < g.vertices(); v++) {
        for (int i : g.adj(v)) {
          union(v, i);
        }
      }
      this.sID = quickFind(s);
      this.count = -1;
      for (int i = 0; i < id.length; i++) {
        if (id[i] == this.sID) {
          count++;
        }
      }
    }

    // 使用quick-find方法
    private int quickFind(int v) {
      return id[v];
    }

    private void union(int p, int q) {
      int pID = quickFind(p);
      int qID = quickFind(q);
      if (pID == qID) {
        return;
      }
      for (int i = 0; i < id.length; i++) {
        if (id[i] == qID) {
          id[i] = pID;
        }
      }
    }

    public boolean marked(int v) {
      return quickFind(v) == this.sID;
    }

    public int count() {
      return this.count;
    }
  }

  // ===============================================================================================

  /** 4.1.10 在一幅连通图找到一个顶点，删去它不会影响到图的连通性 dfs找到第一个所有相邻顶点都被标记过的顶点即可 */

  // ===============================================================================================

  /** 4.1.13 添加一个distTo方法 返回给定点到起点最短路径的长度 */
  public static int[] breadthFirstPaths(Graph graph, int source) {
    // 标记是否被访问以及层数（层数即使最短路径的长度）
    int[] distTo = new int[graph.vertices()];
    for (int i = 0; i < distTo.length; i++) {
      distTo[i] = Integer.MAX_VALUE;
    }
    distTo[source] = 0;
    // 记录最短路径
    int[] edgeTo = new int[graph.vertices()];
    // 使用bfs
    Queue queue = new Queue();
    queue.enqueue(source);
    while (!queue.isEmpty()) {
      int size = queue.size();
      while (size-- > 0) {
        int v = queue.dequeue();
        for (int i : graph.adj(v)) {
          if (i != source && distTo[i] == Integer.MAX_VALUE) {
            edgeTo[i] = v;
            distTo[i] = distTo[v] + 1;
            queue.enqueue(i);
          }
        }
      }
    }
    return distTo;
  }

  public static void breadthFirstPathsTest() {
    Graph graph = new Graph(12);
    graph.addEdge(8, 4);
    graph.addEdge(2, 3);
    graph.addEdge(1, 11);
    graph.addEdge(0, 6);
    graph.addEdge(3, 6);
    graph.addEdge(10, 3);
    graph.addEdge(7, 11);
    graph.addEdge(7, 8);
    graph.addEdge(11, 8);
    graph.addEdge(2, 0);
    graph.addEdge(6, 2);
    graph.addEdge(5, 2);
    graph.addEdge(5, 10);
    graph.addEdge(5, 0);
    graph.addEdge(8, 1);
    graph.addEdge(4, 1);
    int[] breadthFirstPaths = breadthFirstPaths(graph, 0);
    System.out.println("Distance from 0 to 0: " + breadthFirstPaths[0] + " Expected: 0");
    System.out.println("Distance from 0 to 6: " + breadthFirstPaths[6] + " Expected: 1");
    System.out.println("Distance from 0 to 10: " + breadthFirstPaths[10] + " Expected: 2");
    System.out.println("Distance from 0 to 3: " + breadthFirstPaths[3] + " Expected: 2");
    System.out.println("Distance from 0 to 8: " + breadthFirstPaths[8] + " Expected: 2147483647");
  }

  // ===============================================================================================

  public static class GraphProperties {
    private final int[] eccentricities; // 离心率
    private int diameter; // 直径（最大离心率）
    private int radius; // 半径（最小离心率）
    private int center; // 中点（离心率为半径的某个顶点）

    GraphProperties(Graph graph) {
      eccentricities = new int[graph.vertices()];
      diameter = 0;
      radius = Integer.MAX_VALUE;
      center = 0;
      // 判断图是否连通省略
      // 构造参数
      this.getProperties(graph);
    }

    private void getProperties(Graph graph) {
      for (int v = 0; v < graph.vertices(); v++) {
        // 遍历所有顶点 统计其他所有顶点到该点的距离
        int[] distTo = breadthFirstPaths(graph, v);
        for (int w = 0; w < graph.vertices(); w++) {
          if (w == v) {
            continue;
          }
          // w-v的距离
          int dist = distTo[w];
          // 设置离心率
          eccentricities[v] = Math.max(eccentricities[v], dist);
          // 设置属性
          if (dist > diameter) {
            this.diameter = dist;
          } else if (dist < radius) {
            this.radius = dist;
            this.center = v;
          }
        }
      }
    }
  }

  public static void graphPropertiesTest() {
    Graph graph = new Graph(11);
    graph.addEdge(0, 1);
    graph.addEdge(1, 2);
    graph.addEdge(2, 3);
    graph.addEdge(3, 4);
    graph.addEdge(4, 5);
    graph.addEdge(5, 6);
    graph.addEdge(6, 7);
    graph.addEdge(7, 8);
    graph.addEdge(8, 9);
    graph.addEdge(9, 10);
    GraphProperties graphProperties = new GraphProperties(graph);
    System.out.println("Diameter: " + graphProperties.diameter + " Expected: 10");
    System.out.println("Radius: " + graphProperties.radius + " Expected: 5");
    System.out.println("Center: " + graphProperties.center + " Expected: 5");
  }

  // ===============================================================================================

  /**
   * 4.1.28 判断是否存在环，图中允许存在自环和平行边，并且不能被视为一个环。 <br>
   * 原始解法：dfs遍历，直到相邻顶点出现一个已被访问的顶点，且该顶点不是路径的上一个顶点，则表示有环 <br>
   * 解法：前面步骤一致，不过要额外判断相邻顶点不能为自身（因为存在自环），同时记录下最短路径的最后一条边，还要保证不能是同一条边
   */

  // ===============================================================================================

  /**
   * 4.1.35 如果一幅连通图某条边被删除后会被分为两个连通分量，该边被称为桥，没有桥的图为边连通图，判断是否为边连通图 <br>
   * 如果点和其邻接点不构成桥，需要判断从邻接点出发是否有反向边，指向该点或者dfs路径上的其前置点 <br>
   */
  public static class EdgeConnectivity {
    private final int[] dfsOrder; // 每个顶点在dfs路径中的序号
    private final int[] lowAncestor; // 每个顶点反向边祖先的最低序号，必然小于等于顶点自身序号

    private int order;
    private final Graph graph;

    public List<Bridge> bridges;

    public class Bridge {
      int v1;
      int v2;

      public Bridge(int v1, int v2) {
        this.v1 = v1;
        this.v2 = v2;
      }
    }

    public EdgeConnectivity(Graph graph) {
      this.graph = graph;
      order = 0;
      bridges = new ArrayList<>();
      dfsOrder = new int[graph.vertices()];
      lowAncestor = new int[graph.vertices()];
      // 因为默认是连通图 直接从顶点0出发做dfs
      dfs(0, 0);
    }

    private void dfs(int v, int pre) {
      order++;
      // 设置当前序号
      dfsOrder[v] = order;
      // 反向边最低祖先序号也设为当前序号
      lowAncestor[v] = order;
      // 遍历邻接点
      for (int w : graph.adj(v)) {
        // 如果邻接点未被访问
        if (dfsOrder[w] == 0) {
          // 要先dfs设置序号和反向边序号
          dfs(w, v);
          // dfs完成后回溯设置值
          // 没被访问，邻接点序号必然大于当前点，故不用判断
          if (lowAncestor[w] <= dfsOrder[v]) {
            // 如果邻接点反向边最低祖先序号小于当前点 将当前点反向边最低祖先序号也设置为其值
            // 因为显然  v-w-w.lowAncestor构成一条路径 即 v.lowAncestor=w.lowAncestor
            lowAncestor[v] = lowAncestor[w];
          } else {
            // 大于情况，即不存在反向边，反向边最低祖先序号等于自身序号，则为桥
            bridges.add(new Bridge(v, w));
          }
        } else if (w != pre && dfsOrder[w] < lowAncestor[v]) {
          // 已被访问情况且邻接点不是其上一个顶点
          // 如果临界点为之前的点，即序号小于当前点的反向边最低祖先序号，重新设置当前顶点反向边最低祖先序号
          lowAncestor[v] = dfsOrder[w];
          // 其实还可以再判断一次lowAncestor[w]，其必然是小于dfsOrder[w]
          // 不过上一步设置之后已经足以判定当前顶点和该邻接点不可能组成桥了。
        }
      }
    }
  }

  // ===============================================================================================

  /**
   * 检测给定排序是否是图的拓扑排序 排序下每个顶点均不存在反向边（指向序列下靠前的顶点）<br>
   * 唯一拓扑排序：排序下每对相邻顶点都存在一条有向边
   */
  public static class TopologicalOrderCheck {
    private final boolean[] marked;
    private final int[] idx; // 记录每个顶点在给定序列中的序号
    private boolean isOrder;

    public TopologicalOrderCheck(Digraph digraph, int[] order) {
      marked = new boolean[digraph.vertices()];
      idx = new int[order.length];
      for (int i = 0; i < order.length; i++) {
        idx[order[i]] = i;
      }
      isOrder = true;
      for (int v = 0; v < digraph.vertices(); v++) {
        if (!marked[v] && isOrder) {
          dfs(digraph, v);
        }
      }
    }

    private void dfs(Digraph digraph, int v) {
      marked[v] = true;
      for (int w : digraph.adj(v)) {
        // 无论是否已被访问，先判断边是否是反向的
        if (idx[w] < idx[v]) {
          isOrder = false;
          return;
        }
        if (!marked[w]) {
          dfs(digraph, w);
        }
      }
    }

    public boolean isOrder() {
      return this.isOrder;
    }
  }

  // ===============================================================================================

  /** 查找有向无环图中两个顶点高度最大的公共祖先 */
  public static class LCAInDAG {
    private final Digraph digraph;
    private final Digraph reverse;
    private final int[] maxHeights;

    public LCAInDAG(Digraph digraph) {
      this.digraph = digraph;
      this.reverse = digraph.reverse();
      this.maxHeights = new int[digraph.vertices()];
      // 计算出每个顶点距离起点的最大高度
      // 入度为0的则为起点，因为是有向无环图，所以必然存在一个起点
      for (int source = 0; source < reverse.vertices(); source++) {
        if (reverse.adj(source).length == 0) {
          Queue queue = new Queue();
          queue.enqueue(source);
          int count = 0;
          // 因为是DAG所以不会出现无限循环
          while (!queue.isEmpty()) {
            int n = queue.size();
            while (n-- > 0) {
              int i = queue.dequeue();
              this.maxHeights[i] = Math.max(this.maxHeights[i], count);
              for (int vertice : digraph.adj(i)) {
                queue.enqueue(vertice);
              }
            }
            count++;
          }
        }
      }
    }

    public int getLCA(int v1, int v2) {
      // 在反向图中分别从v和w出发找到各自所有的祖先
      boolean[] ancestors1 = getAncestors(v1);
      boolean[] ancestors2 = getAncestors(v2);
      // 找出所有的公共祖先 并选出高度最大的点
      int res = -1;
      for (int i = 0; i < digraph.vertices(); i++) {
        if (ancestors1[i] && ancestors2[i]) {
          res = res >= 0 && this.maxHeights[res] > this.maxHeights[i] ? res : i;
        }
      }
      return res;
    }

    private boolean[] getAncestors(int v) {
      boolean[] ancestors = new boolean[digraph.vertices()];
      Queue queue = new Queue();
      queue.enqueue(v);
      while (!queue.isEmpty()) {
        int i = queue.dequeue();
        if (ancestors[i]) {
          continue;
        }
        ancestors[i] = true;
        for (int vertice : this.reverse.adj(i)) {
          queue.enqueue(vertice);
        }
      }
      return ancestors;
    }
  }

  // ===============================================================================================

  /** 线性时间复杂度计算给定顶点的强连通分量 平方时间复杂度计算所有连通分量 */
  public static class StrongComponentSearch {
    private final Digraph digraph;
    private final int[] sID;

    public StrongComponentSearch(Digraph digraph) {
      this.digraph = digraph;
      this.sID = new int[digraph.vertices()];
      for (int i = 0; i < this.sID.length; i++) {
        this.sID[i] = -1;
      }
      for (int i = 0; i < this.sID.length; i++) {
        if (this.sID[i] < 0) {
          searchStrongComponent(i);
        }
      }
    }

    private void searchStrongComponent(int v) {
      // 先找出从顶点可达的点
      int[] reachable = new int[this.digraph.vertices()];
      reachDFS(v, reachable);
      // 2表示与顶点属于同一个强连通分量
      reachable[v] = 2;
      // 从可到达顶点出发，计算强连通分量
      for (int i = 0; i < reachable.length; i++) {
        // 当前点可达 并且还未计算
        if (reachable[i] == 1) {
          boolean[] onStack = new boolean[this.digraph.vertices()];
          setStrongComponent(i, reachable, onStack);
        }
      }
      // 设置连通分量ID
      for (int i = 0; i < reachable.length; i++) {
        if (reachable[i] == 2) {
          this.sID[i] = v;
        }
      }
    }

    private void reachDFS(int v, int[] reachable) {
      // 1表示起点可达
      reachable[v] = 1;
      for (int i : this.digraph.adj(v)) {
        if (reachable[i] == 0) {
          reachDFS(i, reachable);
        }
      }
    }

    private void setStrongComponent(int i, int[] reachable, boolean[] onStack) {
      // 入栈
      onStack[i] = true;
      for (int v : this.digraph.adj(i)) {
        if (!onStack[v]) {
          int r = reachable[v];
          if (r == 2) {
            // 如果临界点属于强连通分量
            for (int j = 0; j < onStack.length; j++) {
              if (onStack[j]) {
                reachable[j] = 2;
              }
            }
          } else if (r == 1) {
            // 如果临界点同样是起点可达 继续DFS
            setStrongComponent(v, reachable, onStack);
          }
          // 临接点如果起点不可达则终止
        }
      }
      // 退栈
      onStack[i] = false;
    }

    public int[] getStrongComponent(int i) {
      int[] arr = new int[this.sID.length];
      int id = this.sID[i];
      int count = 0;
      for (int j = 0; j < this.sID.length; j++) {
        if (id == this.sID[j]) {
          arr[count++] = j;
        }
      }
      return Arrays.copyOf(arr, count);
    }
  }

  // ===============================================================================================

  /** 基于队列的拓扑排序 */
  private static int[] queueBasedTopological(Digraph digraph) {
    boolean isDAG = new Topological(digraph).isDAG();
    if (!isDAG) {
      throw new UnsupportedOperationException();
    }
    boolean[] marked = new boolean[digraph.vertices()];
    int[] indegrees = new int[digraph.vertices()];
    //  计算入度
    Digraph reverse = digraph.reverse();
    for (int i = 0; i < digraph.vertices(); i++) {
      indegrees[i] = reverse.adj(i).length;
    }
    int[] sort = new int[digraph.vertices()];
    Queue queue = new Queue();
    // 入度为0为起点 入队列
    for (int i = 0; i < digraph.vertices(); i++) {
      if (indegrees[i] == 0) {
        queue.enqueue(i);
      }
    }
    // 开始计算 删除起点并标记 将相邻点的入度减一 如果入度减少至0则入起点队列
    int order = 0;
    while (!queue.isEmpty()) {
      int v = queue.dequeue();
      sort[order++] = v;
      marked[v] = true;
      for (int i : digraph.adj(v)) {
        if (marked[i]) {
          continue;
        }
        // 邻接点入度减去1
        int indegree = indegrees[i];
        if (indegree == 1) {
          queue.enqueue(i);
        }
        indegrees[i] = indegree - 1;
      }
    }
    return sort;
  }

  // ===============================================================================================

  public static void topologicalTest() {
    Digraph digraph = new Digraph(13);
    digraph.addEdge(8, 7);
    digraph.addEdge(7, 6);
    digraph.addEdge(2, 3);
    digraph.addEdge(2, 0);
    digraph.addEdge(3, 5);
    digraph.addEdge(0, 6);
    digraph.addEdge(0, 1);
    digraph.addEdge(0, 5);
    digraph.addEdge(6, 9);
    digraph.addEdge(6, 4);
    digraph.addEdge(9, 10);
    digraph.addEdge(9, 11);
    digraph.addEdge(9, 12);
    digraph.addEdge(11, 12);
    digraph.addEdge(5, 4);
    Topological topological = new Topological(digraph);
    System.out.println(topological.isDAG());
    int[] order = topological.order();
    // 8, 7, 2, 3, 0, 6, 9, 10, 11, 12, 1, 5, 4
    System.out.println(Arrays.toString(order));
    TopologicalOrderCheck topologicalOrderCheck1 = new TopologicalOrderCheck(digraph, order);
    System.out.println(topologicalOrderCheck1.isOrder()); // true
    // 8, 7, 2, 3, 0, 6, 9, 10, 11, 12, 4, 5, 1
    ArrayUtils.swap(order, 10, 12);
    TopologicalOrderCheck topologicalOrderCheck2 = new TopologicalOrderCheck(digraph, order);
    System.out.println(topologicalOrderCheck2.isOrder()); // false
    // 8, 7, 2, 3, 0, 6, 9, 10, 11, 12, 5, 4, 1
    ArrayUtils.swap(order, 10, 11);
    TopologicalOrderCheck topologicalOrderCheck3 = new TopologicalOrderCheck(digraph, order);
    System.out.println(topologicalOrderCheck3.isOrder()); // true
    // queue based topological order
    int[] queueBasedOrder = queueBasedTopological(digraph);
    System.out.println(Arrays.toString(queueBasedOrder));
    // 2, 8, 0, 3, 7, 1, 5, 6, 4, 9, 11, 10, 12
    TopologicalOrderCheck topologicalOrderCheck4 =
        new TopologicalOrderCheck(digraph, queueBasedOrder);
    System.out.println(topologicalOrderCheck4.isOrder()); // true
  }

  // ===============================================================================================

}
