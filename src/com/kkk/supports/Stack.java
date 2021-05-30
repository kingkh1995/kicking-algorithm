package com.kkk.supports;

/**
 *
 * @author KaiKoo
 */
public class Stack {

    private int count = 0;

    private Node top = null;

    public void push(int i) {
        count++;
        Node node = new Node(i);
        node.next = this.top;
        this.top = node;
    }

    public int pop() {
        if (count == 0) {
            throw new UnsupportedOperationException();
        }
        count--;
        Node node = this.top;
        this.top = node.next;
        return node.val;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public int size() {
        return count;
    }

    public int peek() {
        if (count == 0) {
            throw new UnsupportedOperationException();
        }
        return this.top.val;
    }
}
