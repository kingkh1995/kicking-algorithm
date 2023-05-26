package com.kkk.supports;

/**
 * @author KaiKoo
 */
public class Node {

  public int val;

  public Node next;

  public Node random;

  public Node() {}

  public Node(int val) {
    this.val = val;
  }

  public Node(int val, Node next) {
    this.val = val;
    this.next = next;
  }
}
