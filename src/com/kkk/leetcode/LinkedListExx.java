package com.kkk.leetcode;

import com.kkk.supports.ListNode;

/**
 * 链表 <br>
 * 148、430
 *
 * @author KaiKoo
 */
public class LinkedListExx {

  /**
   * 202. 快乐数 <br>
   * 如果不是快乐数，一直计算下去最终一定会出现重复的数字。<br>
   * 解法一：【哈希表】，保存计算过程中的所有数字即可。<br>
   * 解法二：【快慢指针法】，可以将计算过程视作遍历有环的链表。
   */
  class isHappySolution {
    public boolean isHappy(int n) {
      int slow = n, fast = n;
      do {
        if ((fast = getNext(fast)) == 1 || (fast = getNext(fast)) == 1) {
          return true;
        }
        slow = getNext(slow);
      } while (fast != slow);
      return false;
    }

    private int getNext(int n) {
      int sum = 0;
      while (n != 0) {
        int d = n % 10;
        sum += d * d;
        n /= 10;
      }
      return sum;
    }
  }

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
