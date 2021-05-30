package com.kkk.algorithms.fundamentals;

import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author KaiKoo
 */
public class MyQueue<Item> implements Iterable<Item> {

    private LinkedList<Item> queue = new LinkedList<>();

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
