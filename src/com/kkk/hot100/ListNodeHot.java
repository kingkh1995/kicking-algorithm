package com.kkk.hot100;

import com.kkk.supports.ListNode;

/**
 * 链表 <br>
 *
 * @author KaiKoo
 */
public class ListNodeHot {

  /**
   * 2. 两数相加 <br>
   * 模拟加法运算即可
   */
  public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    ListNode head = new ListNode(); // 虚拟头节点
    ListNode node = head;
    int carry = 0; // 进位
    while (l1 != null || l2 != null || carry != 0) {
      int val = (l1 != null ? l1.val : 0) + (l2 != null ? l2.val : 0) + carry;
      node.next = new ListNode(val % 10);
      carry = val / 10;
      node = node.next;
      l1 = l1 != null ? l1.next : l1;
      l2 = l2 != null ? l2.next : l2;
    }
    return head.next;
  }

  /**
   * 19. 删除链表的倒数第 N 个结点 <br>
   * 快慢指针法求解，只需要遍历一遍。
   */
  public ListNode removeNthFromEnd(ListNode head, int n) {
    ListNode dummy = new ListNode(); // 虚拟头节点
    dummy.next = head;
    // 都从虚拟头节点出发，快指针先走n步。
    ListNode fast = dummy, slow = dummy;
    while (fast.next != null) {
      fast = fast.next;
      if (--n < 0) {
        slow = slow.next;
      }
    }
    slow.next = slow.next.next; // n >= 1，即slow.next不可能为空。
    return dummy.next;
  }

  /**
   * 21. 合并两个有序链表 <br>
   * 使用迭代解决。
   */
  public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
    if (list1 == null) {
      return list2;
    } else if (list2 == null) {
      return list1;
    }
    ListNode head = new ListNode(); // 虚拟头节点
    ListNode node = head;
    while (list1 != null && list2 != null) {
      if (list1.val < list2.val) {
        node.next = list1;
        list1 = list1.next;
      } else {
        node.next = list2;
        list2 = list2.next;
      }
      node = node.next;
    }
    node.next = list1 == null ? list2 : list1;
    return head.next;
  }

  /**
   * 23. 合并K个升序链表 <br>
   * 最优解法：分治合并。 <br>
   * 解法二：使用迭代合并，时间复杂度最差。 <br>
   * 解法三：使用优先队列合并。
   */
  public ListNode mergeKLists(ListNode[] lists) {
    if (lists.length == 0) {
      return null;
    }
    // 分治合并，自底向上。区间大小从1开始，每次乘2。
    for (int n = 1; n < lists.length; n <<= 1) {
      for (int l = 0; l + n < lists.length; l += n + n) {
        // 合并连续区间的两个链表并放置到l位置处，则最后结果会位于0位置处。
        lists[l] = mergeTwoLists(lists[l], lists[l + n]);
      }
    }
    return lists[0];
  }
}
