package com.kkk.aim2offer;

/**
 * 链表问题
 *
 * @author KaiKoo
 * @date 2020/2/25 13:42
 */
public class LinkedListQuestion {

    private final static LinkedListQuestion LINKED_LIST_QUESTION = new LinkedListQuestion();

    public static class ListNode {

        int val;
        ListNode next;
        ListNode random;

        public ListNode(int val) {
            this.val = val;
        }

        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    /*
    给定一个单向链表和链表中的一个节点，使用o(1)的复杂度删除该节点
    单向链表的删除时间复杂度理应是o(n)，解题思路非常重要，要突破常规。
     */
    public ListNode deleteNode(ListNode headNode, ListNode node) {
        //默认该节点在链表中存在，但也理应注意到有参数传入的节点不在该链表中的可能
        if (headNode != null && node != null) {
            ListNode next = node.next;
            if (next != null) {
                //如果该节点不是尾节点，则可以在o(1)的复杂度实现，不需要删除，而是选择覆盖
                node.val = next.val;
                node.next = next.next;
            } else {
                //如果该链表只有一个节点
                if (headNode == node) {
                    return null;
                }
                //如果该节点为尾节点，则只能顺序遍历
                while (true) {
                    if (headNode.next == node) {
                        headNode.next = null;
                        break;
                    } else {
                        headNode = headNode.next;
                    }
                }
            }
        }
        return headNode;
    }

    /*
    找到单向链表倒数第n个节点，只能遍历一次
     */
    /*
    解题思路：快慢指针法，类似题目如求链表最中间的节点等
     */
    public int findLastKthNode(ListNode headNode, int k) {
        if (headNode == null || k < 1) {
            return -1;
        }
        ListNode fast = headNode;
        ListNode slow = headNode;
        //快指针先走k-1步
        for (int i = 0; i < k - 1; i++) {
            fast = fast.next;
            //如果fast已经为null了，则表示k大于链表长度
            if (fast == null) {
                return -1;
            }
        }
        //快指针走一步
        fast = fast.next;
        //快慢指针一起走
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }
        return slow.val;
    }

    /*
    复杂链表复制，复杂链表的每一个节点包含一个指针指向一个随机节点或者null
     */
    /*
    解题思路：第一次遍历，在链表的每一个节点后添加一个复制节点；第二次遍历，设置随机指针并组装新链表
     */
    public ListNode complexLinkedListClone(ListNode listNode) {
        if (listNode == null) {
            return null;
        }
        ListNode tempNode = listNode;
        while (tempNode != null) {
            ListNode copy = new ListNode(tempNode.val * 10);
            copy.next = tempNode.next;
            tempNode.next = copy;
            tempNode = copy.next;
        }
        ListNode clone = listNode.next;
        while (listNode != null) {
            ListNode copy = listNode.next;
            copy.random = listNode.random == null ? null : listNode.random.next;
            listNode = copy.next;
            copy.next = copy.next == null ? null : copy.next.next;
        }
        return clone;
    }

    /*
    约瑟夫问题 总人数 n，每次计数到 m 则淘汰，最后剩下的人是第几位
     */
    /*
    解法2：使用环形链表
     */
    public int josephusCircle(int n, int m) {
        ListNode head = new ListNode(1);
        // 构造环形链表
        ListNode pre = head;
        for (int i = 2; i <= n; i++) {
            ListNode node = new ListNode(i);
            pre.next = node;
            if (i == n) {
                node.next = head;
            }
            pre = node;
        }
        // 开始淘汰
        int count = 0;
        while (head.next != head) {
            if (++count == m - 1) {
                head.next = head.next.next;
                count = 0;
            }
            head = head.next;
        }
        return head.val;
    }

    //测试用例

    public static void deleteNodeTest() {
        ListNode node5 = new ListNode(5);
        ListNode node4 = new ListNode(4, node5);
        ListNode node3 = new ListNode(3, node4);
        ListNode node2 = new ListNode(2, node3);
        ListNode node1 = new ListNode(1, node2);
        LINKED_LIST_QUESTION.deleteNode(null, null);
        LINKED_LIST_QUESTION.deleteNode(node1, null);
        // 1 2 3 4 5
        LINKED_LIST_QUESTION.deleteNode(node1, node1);
        // 1(2) 3 4 5
        LINKED_LIST_QUESTION.deleteNode(node1, node5);
        // 1(2) 3 4
        LINKED_LIST_QUESTION.deleteNode(node1, node3);
        // 1(2) 3(4)
        LINKED_LIST_QUESTION.deleteNode(node1, node1);
        // 1(4)
        System.out.println(LINKED_LIST_QUESTION.deleteNode(node1, node1));
        // null
    }

    public static void findLastKthNodeTest() {
        ListNode node5 = new ListNode(5);
        ListNode node4 = new ListNode(4, node5);
        ListNode node3 = new ListNode(3, node4);
        ListNode node2 = new ListNode(2, node3);
        ListNode node1 = new ListNode(1, node2);
        System.out.println(LINKED_LIST_QUESTION.findLastKthNode(null, 6));
        System.out.println(LINKED_LIST_QUESTION.findLastKthNode(node1, 0));
        System.out.println(LINKED_LIST_QUESTION.findLastKthNode(node1, 6));
        System.out.println(LINKED_LIST_QUESTION.findLastKthNode(node1, 1));
        System.out.println(LINKED_LIST_QUESTION.findLastKthNode(node1, 4));
        System.out.println(LINKED_LIST_QUESTION.findLastKthNode(node1, 5));
        System.out.println(LINKED_LIST_QUESTION.findLastKthNode(node3, 1));
    }

    public static void complexLinkedListCloneTest() {
        ListNode node5 = new ListNode(5);
        ListNode node4 = new ListNode(4, node5);
        ListNode node3 = new ListNode(3, node4);
        ListNode node2 = new ListNode(2, node3);
        ListNode node1 = new ListNode(1, node2);
        node1.random = node3;
        node2.random = null;
        node3.random = node3;
        node4.random = node5;
        node5.random = node1;
        ListNode listNode = node1;
        while (listNode != null) {
            System.out.print(String.format("%d(%s)-", listNode.val,
                    listNode.random == null ? "null" : listNode.random.val));
            listNode = listNode.next;
        }
        System.out.println();
        listNode = LINKED_LIST_QUESTION.complexLinkedListClone(node1);
        while (listNode != null) {
            System.out.print(String.format("%d(%s)-", listNode.val,
                    listNode.random == null ? "null" : listNode.random.val));
            listNode = listNode.next;
        }
        System.out.println();
    }

    public static void josephusCircleTest() {
        System.out.println(LINKED_LIST_QUESTION.josephusCircle(41, 5));
    }
}
