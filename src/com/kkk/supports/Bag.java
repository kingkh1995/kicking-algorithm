package com.kkk.supports;

import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author KaiKoo
 */
public class Bag<Item> implements Iterable<Item> {

    private LinkedList<Item> bag = new LinkedList<>();

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
