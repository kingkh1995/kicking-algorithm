package com.kkk.supports;

/**
 *
 * @author KaiKoo
 */
public class NodeUtils {

    public static Node makeLinkedList(int from, int to) {
        Node first = new Node(from);
        Node node = first;
        for (int i = from + 1; i <= to; i++) {
            Node temp = new Node(i);
            node.next = temp;
            node = temp;
        }
        return first;
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
