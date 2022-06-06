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

  // 找到两个单链表相交的起始结点，未相交则返回null
  public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
    if (headA == null || headB == null) {
      return null;
    }
    // 双指针，遍历完一个链表再遍历另一个即可，两个节点最终走的距离是相同的，一定会相遇在交点。
    ListNode node1 = headA;
    ListNode node2 = headB;
    while (node1 != node2) {
      node1 = node1 == null ? headB : node1.next;
      node2 = node2 == null ? headA : node2.next;
    }
    return node1; // 最终一定会相等，有交点则为相交结点，否则同为null，则返回node1即可
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
  public boolean isPalindrome(ListNode head) {
    if (head == null || head.next == null) {
      return true;
    }
    // 使用快慢指针法找到中点同时迭代反转前半部分链表，慢指针只需要遍历一遍即可。
    // 慢指针同时也是剩余未反转链表的头节点
    ListNode first = head;
    ListNode reverse = null;
    // 快指针提前走一步，用于判断长度是否为偶数
    ListNode fast = head.next;
    while (true) {
      // 迭代反转链表
      ListNode second = first.next;
      first.next = reverse;
      reverse = first;
      first = second;
      if (fast.next == null) { // 链表长度为偶数时结束循环
        break;
      }
      // 快指针走两步
      fast = fast.next.next;
      if (fast == null) { // 链表长度为奇数场景下需要跳过中位数
        first = first.next;
        break;
      }
    }
    // 反转完完成，此时慢指针为后半段链表的头节点，与反转后的前半部分链表进行对比。
    while (first != null) {
      if (first.val != reverse.val) {
        return false;
      }
      first = first.next;
      reverse = reverse.next;
    }
    return true;
  }

  // n+1大小的数组，元素在[1,n]之间，只有一个元素重复出现了多次，求出该值且不能修改数组
  // 转换为求有环链表的交点，链表从0位置出发开始构建。
  // 如果是证明至少存在一个重复的数字，只需要快慢指针相遇就能证明。
  public int findDuplicate(int[] nums) {
    int slow = 0, fast = 0;
    do {
      slow = nums[slow];
      fast = nums[nums[fast]];
    } while (slow != fast);
    slow = 0;
    do {
      slow = nums[slow];
      fast = nums[fast];
    } while (slow != fast);
    return slow;
  }
}
