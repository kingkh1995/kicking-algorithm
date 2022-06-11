package com.kkk.leetcode;

import com.kkk.supports.ListNode;

/**
 * 链表 <br>
 * 148、430
 *
 * @author KaiKoo
 */
public class LinkedListExx {

  // ===============================================================================================
  /** 基础题 */

  // 旋转链表，将链表每个节点向右移动 k 个位置
  public ListNode rotateRight(ListNode head, int k) {
    if (head == null || head.next == null) {
      return head;
    }
    // 构建一个环并统计个数
    ListNode node = head;
    int count = 1;
    while (node.next != null) {
      node = node.next;
      count++;
    }
    // node为最后一个结点
    node.next = head;
    k = count - k % count;
    if (k == count) {
      node.next = null;
      return head;
    }
    while (k-- > 0) {
      node = node.next;
    }
    ListNode next = node.next;
    // 断开环
    node.next = null;
    return next;
  }
}
