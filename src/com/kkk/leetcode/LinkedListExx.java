package com.kkk.leetcode;

import com.kkk.supports.ListNode;

/**
 * 链表
 *
 * @author KaiKoo
 */
public class LinkedListExx {
  // ===============================================================================================
  /** 基础题 */

  // 找到两个单链表相交的起始结点 未相交则返回null
  public static ListNode getIntersectionNode(ListNode headA, ListNode headB) {
    if (headA == null || headB == null) {
      return null;
    }
    // 双指针 遍历完一个链表再遍历另一个即可 两个节点最终走的距离是相同的 故一定会相遇
    ListNode node1 = headA;
    ListNode node2 = headB;
    while (node1 != node2) { // 最终一定会相同，有交点则为同一个结点，否则同为null
      node1 = node1 == null ? headB : node1.next;
      node2 = node2 == null ? headA : node2.next;
    }
    return node1;
  }

  // 删除倒数第n个节点 只遍历一遍 使用快慢指针法
  public ListNode removeNthFromEnd(ListNode head, int n) {
    if (head == null || n < 1) {
      return head;
    }
    // 添加一个起始节点
    ListNode t = new ListNode(0);
    t.next = head;
    ListNode fast = t, slow = t;
    // 从起始结点开始 让快指针先走n个结点 直到快指针到了最后一个结点
    // 则慢指针到了倒数第n+1个结点 删除掉慢指针的下一个结点则删除了倒数第n个结点
    while (fast.next != null) { // next为空则到了最后一个结点
      fast = fast.next;
      if (n-- <= 0) {
        slow = slow.next;
      }
    }
    if (n > 0) { // n大于链表长度
      return head;
    } else if (n == 0) { // n刚好等于链表长度
      return head.next;
    }
    // 删除下一个结点
    slow.next = slow.next.next;
    return head;
  }

  // ===============================================================================================
  /** 拔高题 */
}
