package com.kkk.algs4;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 基于 LinkedList 实现
 *
 * @author KaiKoo
 */
public class MyQueue<Item> implements Iterable<Item> {

  private final Queue<Item> queue = new LinkedList<>();

  @Override
  public Iterator<Item> iterator() {
    return queue.iterator();
  }

  public void offer(Item item) {
    queue.offer(item);
  }

  /**
   * @return null or element
   */
  public Item poll() {
    return queue.poll();
  }

  /**
   * @return null or element
   */
  public Item peek() {
    return queue.peek();
  }

  public boolean isEmpty() {
    return queue.isEmpty();
  }

  public int size() {
    return queue.size();
  }
}
