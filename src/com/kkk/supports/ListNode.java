package com.kkk.supports;

/**
 * @author KaiKoo
 */
public class ListNode {

  public int val;

  public ListNode next;

  public ListNode random;

  public ListNode(int val) {
    this.val = val;
    next = null;
  }

  public ListNode(int val, ListNode next) {
    this.val = val;
    this.next = next;
  }
}
