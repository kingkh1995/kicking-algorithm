package com.kkk.algs4;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class UF {

  private int count;
  private final int[] id;

  public UF(int n) {
    this.id = new int[n];
    this.count = n;
    for (int i = 0; i < n; i++) {
      this.id[i] = i;
    }
  }

  public int find(int v) {
    return id[v];
  }

  public void union(int p, int q) {
    int pID = find(p);
    int qID = find(q);
    if (pID == qID) {
      return;
    }
    for (int i = 0; i < id.length; i++) {
      if (id[i] == qID) {
        id[i] = pID;
      }
    }
    count--;
  }

  public boolean connected(int p, int q) {
    return find(p) == find(q);
  }

  public int count() {
    return this.count;
  }
}
