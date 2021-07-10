package com.kkk.leetcode;

import com.kkk.supports.ListNode;

/**
 * 链表 <br>
 * 430、138
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
    while (node1 != node2) {
      node1 = node1 == null ? headB : node1.next;
      node2 = node2 == null ? headA : node2.next;
    }
    return node1; // 最终一定会相等，有交点则为相交结点，否则同为null，则返回node1即可
  }

  // 删除链表倒数第n个节点 只遍历一遍 使用快慢指针法
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

  // 合并两个有序的链表 递归解决
  public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
    if (l2 == null) {
      return l1;
    }
    if (l1 == null) {
      return l2;
    }
    if (l1.val <= l2.val) {
      l1.next = mergeTwoLists(l1.next, l2);
      return l1;
    } else {
      l2.next = mergeTwoLists(l1, l2.next);
      return l2;
    }
  }

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

  // ===============================================================================================
  /** 拔高题 */

  // 判断是否是回文链表，时间复杂度o(n) 空间复杂度o(1)
  public static boolean isPalindrome(ListNode head) {
    if (head == null || head.next == null) {
      return true;
    }
    ListNode slow = head;
    ListNode fast = head.next;
    ListNode reverse = null;
    // 迭代反转前半部分链表 并使用快慢指针法找到中点
    // 或者先找到中点再反转后半部分链表
    while (true) {
      ListNode next = slow.next;
      slow.next = reverse;
      reverse = slow;
      slow = next;
      if (fast.next == null) { // 结束循环 且长度为偶数
        break;
      }
      fast = fast.next.next;
      if (fast == null) { // 结束循环 长度为奇数 跳过中位数
        slow = slow.next;
        break;
      }
    }
    // 反转完之后开始对比 后半段链表和反转后的前半部分链表
    while (slow != null) {
      if (reverse.val != slow.val) {
        return false;
      }
      slow = slow.next;
      reverse = reverse.next;
    }
    return true;
  }
}
