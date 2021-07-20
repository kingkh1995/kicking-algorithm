package com.kkk.algs4;

import com.kkk.supports.Graph;
import com.kkk.supports.ListNode;

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
      this.id = new int[g.vertex()];
      // 对每条边使用union操作
      for (int v = 0; v < g.vertex(); v++) {
        ListNode adj = g.adj(v);
        while (adj != null) {
          union(v, adj.val);
          adj = adj.next;
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

  /**
   * <br>
   * <br>
   */

  // ===============================================================================================

}
