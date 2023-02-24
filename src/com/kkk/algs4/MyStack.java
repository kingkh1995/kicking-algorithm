package com.kkk.algs4;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * 基于 LinkedList 实现
 *
 * @author KaiKoo
 */
public class MyStack<Item> implements Iterable<Item> {

  private final Deque<Item> stack = new LinkedList<>();

  @Override
  public Iterator<Item> iterator() {
    return stack.iterator();
  }

  public void push(Item item) {
    stack.offerFirst(item);
  }

  /**
   * @return null or element
   */
  public Item pop() {
    return stack.pollFirst();
  }

  /**
   * @return null or element
   */
  public Item top() {
    return stack.peekFirst();
  }

  public boolean isEmpty() {
    return stack.isEmpty();
  }

  public int size() {
    return stack.size();
  }
}
