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

  /** 328. 奇偶链表 <br> */
  public ListNode oddEvenList(ListNode head) {
    if (head == null) {
      return null;
    }
    ListNode evenHead = head.next, odd = head, even = evenHead;
    while (even != null && even.next != null) {
      odd.next = even.next;
      odd = odd.next;
      even.next = odd.next;
      even = even.next;
    }
    odd.next = evenHead;
    return head;
  }

  // ===============================================================================================

}
