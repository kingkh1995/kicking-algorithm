package com.kkk.algs4;

import com.kkk.supports.Graph;
import com.kkk.supports.Queue;

/**
 * 第四章 图
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

  public static void main(String[] args) {
    graphPropertiesTest();
  }
}
