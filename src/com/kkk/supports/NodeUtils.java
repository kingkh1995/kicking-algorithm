package com.kkk.supports;

/** @author KaiKoo */
public class NodeUtils {

  public static ListNode randomSortedLinkedList(int n, int origin, int bound) {
    return toLinkedList(ArrayUtils.randomSortedArr(n, origin, bound));
  }

  public static ListNode randomLinkedList(int n, int origin, int bound) {
    return toLinkedList(ArrayUtils.randomArr(n, origin, bound));
  }

  public static ListNode distinctSortedLinkedList(int n, int origin, int bound) {
    return toLinkedList(ArrayUtils.distinctSortedArr(n, origin, bound));
  }

  public static ListNode distinctLinkedList(int n, int origin, int bound) {
    return toLinkedList(ArrayUtils.distinctArr(n, origin, bound));
  }

  public static ListNode toLinkedList(int[] arr) {
    ListNode head = new ListNode(arr[0]);
    ListNode node = head;
    for (int i = 1; i < arr.length; i++) {
      ListNode temp = new ListNode(arr[i]);
      node.next = temp;
      node = temp;
    }
    return head;
  }

  public static boolean isSorted(ListNode node) {
    ListNode next = node.next;
    while (next != null) {
      if (node.val > next.val) {
        return false;
      }
      node = next;
      next = node.next;
    }
    return true;
  }

  public static void printNode(ListNode node) {
    if (node == null) {
      return;
    }
    System.out.print(node.val);
    node = node.next;
    while (node != null) {
      System.out.print(" => " + node.val);
      node = node.next;
    }
    System.out.println();
  }

  public static void threadedPrint(TreeNode treeNode) {
    if (treeNode != null) {
      while (treeNode.left != null) {
        treeNode = treeNode.left;
      }
      while (treeNode.next != null) {
        System.out.print(
            " ("
                + ((treeNode.prev == null ? "*" : treeNode.prev.val)
                    + "|"
                    + treeNode.val
                    + "|"
                    + (treeNode.next == null ? "*" : treeNode.next.val))
                + ") ");
        treeNode = treeNode.next;
      }
      System.out.println(
          " (" + ((treeNode.prev == null ? "*" : treeNode.prev.val) + "|" + treeNode.val + "|*) "));
    }
  }
}
