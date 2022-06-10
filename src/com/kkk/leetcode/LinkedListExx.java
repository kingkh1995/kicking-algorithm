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

  // ===============================================================================================
  /** 拔高题 */

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
