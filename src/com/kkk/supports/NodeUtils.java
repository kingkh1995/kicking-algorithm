package com.kkk.supports;

/**
 *
 * @author KaiKoo
 */
public class NodeUtils {

    public static Node randomSortedLinkedNode(int n, int origin, int bound) {
        return toLinkedNode(ArrayUtils.randomSortedArr(n, origin, bound));
    }

    public static Node randomLinkedNode(int n, int origin, int bound) {
        return toLinkedNode(ArrayUtils.randomArr(n, origin, bound));
    }

    public static Node distinctSortedLinkedNode(int n, int origin, int bound) {
        return toLinkedNode(ArrayUtils.distinctSortedArr(n, origin, bound));
    }

    public static Node distinctLinkedNode(int n, int origin, int bound) {
        return toLinkedNode(ArrayUtils.distinctArr(n, origin, bound));
    }

    public static Node toLinkedNode(int[] arr) {
        Node head = new Node(arr[0]);
        Node node = head;
        for (int i = 1; i < arr.length; i++) {
            Node temp = new Node(arr[i]);
            node.next = temp;
            node = temp;
        }
        return head;
    }

    public static boolean isSorted(Node node) {
        Node next = node.next;
        while (next != null) {
            if (node.val > next.val) {
                return false;
            }
            node = next;
            next = node.next;
        }
        return true;
    }

    public static void printNode(Node node) {
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

}
