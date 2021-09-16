package com.kkk.algs4;

import java.util.PriorityQueue;

/**
 * 索引优先队列，记录插入时的索引，并可以返回堆顶元素的索引 <br>
 *
 * @author KaiKoo
 */
public class IndexMinPQ<Item extends Comparable<Item>> {

  private final Entry<Item>[] arr;

  private final PriorityQueue<Entry<Item>> pq;

  public IndexMinPQ(int cap) {
    arr = (Entry<Item>[]) new Entry[cap];
    pq = new PriorityQueue<>(cap);
  }

  public void set(int index, Item item) {
    Entry<Item> entry = new Entry<>(index, item);
    pq.remove(arr[index]);
    arr[index] = entry;
    pq.offer(entry);
  }

  public int delMin() {
    int index = pq.poll().index;
    arr[index] = null;
    return index;
  }

  public Item get(int index) {
    Entry<Item> entry = arr[index];
    return entry == null ? null : entry.v;
  }

  public boolean isEmpty() {
    return pq.isEmpty();
  }

  public int size() {
    return pq.size();
  }

  private static class Entry<T extends Comparable<T>> implements Comparable<Entry<T>> {
    int index;
    T v;

    public Entry(int index, T v) {
      this.index = index;
      this.v = v;
    }

    @Override
    public int compareTo(Entry<T> o) {
      return this.v.compareTo(o.v);
    }
  }
}
