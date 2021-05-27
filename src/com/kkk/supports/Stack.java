package com.kkk.supports;

import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author KaiKoo
 */
public class Stack<Item> implements Iterable<Item> {

    private LinkedList<Item> stack = new LinkedList<>();

    @Override
    public Iterator<Item> iterator() {
        return stack.iterator();
    }

    public void push(Item item) {
        stack.addFirst(item);
    }

    public void pop() {
        stack.removeFirst();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public int size() {
        return stack.size();
    }

    public Item peek() {
        return stack.peekFirst();
    }

}
