package com.kkk.algs4;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * 基于 LinkedList 实现
 *
 * @author KaiKoo
 */
public class MyQueue<Item> implements Iterable<Item> {

  private final LinkedList<Item> queue = new LinkedList<>();

  @Override
  public Iterator<Item> iterator() {
    return queue.iterator();
  }

  public void enqueue(Item item) {
    queue.addLast(item);
  }

  public Item dequeue() {
    return queue.removeFirst();
  }

  public boolean isEmpty() {
    return queue.isEmpty();
  }

  public int size() {
    return queue.size();
  }

  public Item peek() {
    return queue.peekFirst();
  }
}
