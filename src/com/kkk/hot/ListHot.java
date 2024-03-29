package com.kkk.hot;

import com.kkk.supports.ListNode;
import com.kkk.supports.Node;
import java.util.ArrayList;
import java.util.List;

/**
 * 链表 <br>
 *
 * @author KaiKoo
 */
public class ListHot {

  /**
   * 2. 两数相加 <br>
   * 模拟加法运算即可
   */
  public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    ListNode head = new ListNode(), cur = head;
    int carry = 0, a, b, c; // 进位
    while (l1 != null || l2 != null || carry != 0) {
      if (l1 == null) {
        a = 0;
      } else {
        a = l1.val;
        l1 = l1.next;
      }
      if (l2 == null) {
        b = 0;
      } else {
        b = l2.val;
        l2 = l2.next;
      }
      c = a + b + carry;
      carry = c / 10;
      cur.next = new ListNode(c % 10);
      cur = cur.next;
    }
    return head.next;
  }

  /**
   * 19. 删除链表的倒数第 N 个结点 <br>
   * 快慢指针法求解，只需要遍历一遍。
   */
  public ListNode removeNthFromEnd(ListNode head, int n) {
    ListNode dummy = new ListNode(), fast = dummy, slow = dummy; // 虚拟头节点
    dummy.next = head;
    // 都从虚拟头节点出发，快指针先走n步，快指针走到最后一个结点时，慢指针在要被删除结点的前一个位置。
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
    ListNode head = new ListNode(), cur = head, tmp;
    while (list1 != null && list2 != null) {
      if (list1.val < list2.val) {
        cur.next = list1;
        list1 = list1.next;
      } else {
        cur.next = list2;
        list2 = list2.next;
      }
      cur = cur.next;
    }
    cur.next = list1 == null ? list2 : list1;
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

  /**
   * 61. 旋转链表 <br>
   * 构建环，并在指定位置断开。
   */
  public ListNode rotateRight(ListNode head, int k) {
    if (head == null || head.next == null) {
      return head;
    }
    ListNode node = head;
    int n = 1;
    while (node.next != null) {
      ++n;
      node = node.next;
    }
    if ((k = k % n) == 0) {
      return head;
    }
    node.next = head; // 头尾相连
    while (k++ < n) { // 从尾开始遍历
      node = node.next;
    }
    head = node.next;
    node.next = null; // 断开环
    return head;
  }

  /**
   * 138. 复制带随机指针的链表 <br>
   * 如果使用哈希表，则只需要遍历两遍，先遍历创建新链表并保存节点到哈希表，然后遍历设置随机节点即可。
   */
  public Node copyRandomList(Node head) {
    if (head == null) {
      return null;
    }
    // 1. 插入新节点
    for (Node node = head; node != null; node = node.next.next) {
      Node copy = new Node(node.val);
      copy.next = node.next;
      node.next = copy;
    }
    // 2.设置random节点
    for (Node node = head; node != null; node = node.next.next) {
      if (node.random != null) {
        node.next.random = node.random.next;
      }
    }
    // 3.拆分并还原
    Node ans = head.next;
    for (Node node = head; node != null; node = node.next) {
      Node copy = node.next;
      node.next = copy.next;
      if (node.next != null) {
        copy.next = node.next.next;
      }
    }
    return ans;
  }

  /** 141. 环形链表 <br> */
  public boolean hasCycle(ListNode head) {
    ListNode fast = head, slow = head;
    do {
      if (fast == null || (fast = fast.next) == null) {
        return false;
      }
    } while ((fast = fast.next) != (slow = slow.next));
    return true;
  }

  /**
   * 142. 环形链表 II <br>
   * 快指针每次两步，慢指针每次一步，相遇后，一个指针从起点出发，一个从相遇点出发，最后相遇的点即是入环的第一个节点。
   */
  public ListNode detectCycle(ListNode head) {
    ListNode fast = head, slow = head;
    do {
      if (fast == null || (fast = fast.next) == null) {
        return null;
      }
    } while ((fast = fast.next) != (slow = slow.next));
    fast = head;
    while (fast != slow) { // 可能第一个节点是交点，不能先赋值。
      fast = fast.next;
      slow = slow.next;
    }
    return fast;
  }

  /**
   * 160. 相交链表 <br>
   * 双指针分别从不同的链表出发，遍历完一个再遍历另一个，两个节点一定会相遇在交点。
   */
  public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
    ListNode listA = headA, listB = headB;
    while (listA != listB) {
      listA = listA == null ? headB : listA.next;
      listB = listB == null ? headA : listB.next;
    }
    return listA;
  }

  /**
   * 206. 反转链表 <br>
   * 迭代和递归
   */
  class reverseListSolution {
    public ListNode reverseList(ListNode head) {
      if (head == null || head.next == null) {
        return head;
      }
      ListNode reverse = reverseList(head.next); // head的next结点在反转后是head的前置结点
      head.next.next = head;
      head.next = null;
      return reverse;
    }

    public ListNode reverseList1(ListNode head) {
      ListNode reverse = null, next;
      while (head != null) {
        next = head.next;
        head.next = reverse;
        reverse = head;
        head = next;
      }
      return reverse;
    }
  }

  /**
   * 876. 链表的中间结点 <br>
   * 快慢指针法
   */
  public ListNode middleNode(ListNode head) {
    ListNode slow = head, fast = head;
    while (fast != null && fast.next != null) {
      slow = slow.next;
      fast = fast.next.next;
    }
    return slow;
  }

  // ===============================================================================================

  /**
   * 148. 排序链表 <br>
   * 将链表拆分为k个升序链表，然后使用归并排序的方式合并即可。
   */
  public ListNode sortList(ListNode head) {
    List<ListNode> list = new ArrayList<>();
    while (head != null) {
      list.add(head); // 添加结果
      ListNode node = head;
      while (node.next != null && node.val <= node.next.val) {
        node = node.next;
      }
      head = node.next; // 跳到下一段链表的起点
      node.next = null; // 拆分链表
    }
    return mergeKLists(list.toArray(new ListNode[0]));
  }

  /**
   * 234. 回文链表 <br>
   * 快慢指针法，反转前半部分链表，之后与后半段链表对比即可，只需要遍历一遍，时间复杂度o(n) 空间复杂度o(1)。
   */
  public boolean isPalindrome(ListNode head) {
    ListNode reverse = null, slow = head, fast = head; // 慢指针同时也是剩余未反转链表的头节点
    while (fast != null && fast.next != null) {
      fast = fast.next.next; // 快指针走两步
      ListNode next = slow.next; // 迭代反转链表
      slow.next = reverse;
      reverse = slow;
      slow = next;
    }
    slow = fast == null ? slow : slow.next; // fast非空表示链表长度为奇数，慢指针还需要走一步
    while (slow != null) { // 遍历比对反转的前半部分链表和未反转后半部分链表
      if (slow.val != reverse.val) {
        return false;
      }
      slow = slow.next;
      reverse = reverse.next;
    }
    return true;
  }
}
