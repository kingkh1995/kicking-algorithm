package com.kkk.supports;

/** @author KaiKoo */
public class Queue {

  private int count = 0;

  private ListNode head = null;

  private ListNode tail = null;

  public void enqueue(int i) {
    ListNode node = new ListNode(i);
    count++;
    if (count == 1) {
      this.head = node;
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
    ListNode node = this.head;
    count--;
    if (count == 0) {
      this.head = null;
      this.tail = null;
    } else {
      this.head = node.next;
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
    return head.val;
  }
}
