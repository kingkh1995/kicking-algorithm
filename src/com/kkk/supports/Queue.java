package com.kkk.supports;

/** @author KaiKoo */
public class Queue {

  private int count = 0;

  private Node first = null;

  private Node tail = null;

  public void enqueue(int i) {
    Node node = new Node(i);
    count++;
    if (count == 1) {
      this.first = node;
      this.tail = node;
    } else {
      this.tail.next = node;
      this.tail = node;
    }
  }

  public int dequeue() {
    if (count == 0) {
      throw new UnsupportedOperationException();
    }
    Node node = this.first;
    count--;
    if (count == 0) {
      this.first = null;
      this.tail = null;
    } else {
      this.first = node.next;
    }
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
    return first.val;
  }
}
