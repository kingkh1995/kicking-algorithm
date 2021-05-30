package com.kkk.supports;

/**
 *
 * @author KaiKoo
 */
public class Utils {

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
