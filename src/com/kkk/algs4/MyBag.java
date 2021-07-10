package com.kkk.algs4;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * 基于 LinkedList 实现
 *
 * @author KaiKoo
 */
public class MyBag<Item> implements Iterable<Item> {

  private final LinkedList<Item> bag = new LinkedList<>();

  @Override
  public Iterator<Item> iterator() {
    return bag.iterator();
  }

  public void add(Item item) {
    bag.add(item);
  }

  public boolean isEmpty() {
    return bag.isEmpty();
  }

  public int size() {
    return bag.size();
  }
}
