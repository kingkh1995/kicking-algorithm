package com.kkk.supports;

/** @author KaiKoo */
public class Stack {

  private int count = 0;

  private ListNode top = null;

  public void push(int i) {
    count++;
    ListNode node = new ListNode(i);
    node.next = this.top;
    this.top = node;
  }

  public int pop() {
    if (count == 0) {
      throw new UnsupportedOperationException();
    }
    count--;
    ListNode node = this.top;
    this.top = node.next;
    return node.val;
  }

  public boolean isEmpty() {
    return count == 0;
  }

  public int size() {
    return count;
  }

  public int top() {
    if (count == 0) {
      throw new UnsupportedOperationException();
    }
    return this.top.val;
  }
}
