package com.kkk.supports;

import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author KaiKoo
 */
public class Queue<Item> implements Iterable<Item> {

    private LinkedList<Item> queue = new LinkedList<>();

    @Override
    public Iterator<Item> iterator() {
        return queue.iterator();
    }

    public void enqueue(Item item) {
        queue.addLast(item);
    }

    public void dequeue() {
        queue.removeFirst();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public int size() {
        return queue.size();
    }

}
