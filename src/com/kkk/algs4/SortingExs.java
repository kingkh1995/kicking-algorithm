package com.kkk.algs4;

import com.kkk.supports.ArrayUtils;
import com.kkk.supports.Node;
import com.kkk.supports.NodeUtils;
import com.kkk.supports.Queue;

/**
 * 第二章 排序
 * @author KaiKoo
 */
public class SortingExs {

    //==============================================================================================

    /**
     * 希尔排序
     */
    public static void shellSort(int[] arr) {
        int length = arr.length;
        int h = 1;
        while (h < length / 3) {
            h = 3 * h + 1;
        }
        System.out.println(h);
        for (; h >= 1; h /= 3) {
            // 将数组变为h有序
            for (int i = h; i < length; i++) {
                for (int j = i; j >= h && arr[j] < arr[j - h]; j -= h) {
                    ArrayUtils.swap(arr, j, j - h);
                }
            }
        }
    }

    public static void shellSortTest() {
        int[] arr = ArrayUtils.distinctArr(10000, 0, 20000);
        System.out.println(ArrayUtils.isSorted(arr));
        SortingExs.shellSort(arr);
        System.out.println(ArrayUtils.isSorted(arr));
    }

    //==============================================================================================

    /**
     * 2.1.14
     * 出列顺序，将一副扑克牌排序，每次只能查看或交换最上面的两张牌或将最上面的牌放到最下面
     */
    /**
     * 1、一直查看最上面的两张牌，将小牌放到最下面，直到找到最大的牌和第二大的牌
     * 2、将第二大的牌和最大的牌放在最下面，使他们有序的连在一起
     * 3、继续通过以上方法找出第三大的牌和第二大的牌
     * 4、可以知道最大的牌就跟在第二大的牌后面，同样将它们都放到最下面，使前三张牌有序的连在一起
     * 5、一直重复，并按顺序使所有的牌全部排序并连在一起，相当于组成了一个环
     */
    /**
     * 拓展：队列排序（无重复数据）
     * 类似于选择排序
     */
    public static void sortQueue(Queue queue) {
        if (queue == null || queue.size() < 2) {
            return;
        }
        // 先取到最小值
        int size = queue.size();
        int min = queue.dequeue();
        for (int i = 1; i < size; i++) {
            int temp = queue.dequeue();
            if (temp < min) {
                queue.enqueue(min);
                min = temp;
            } else {
                queue.enqueue(temp);
            }
        }
        queue.enqueue(min);
        System.out.println("min = " + min);
        // 上一个比它小的值
        int first = min;
        // 当前要找的小值
        int second;
        for (int i = 1; i < size; i++) {
            second = queue.dequeue();
            do {
                int temp = queue.dequeue();
                if (temp > first && temp < second) {
                    queue.enqueue(second);
                    second = temp;
                } else {
                    queue.enqueue(temp);
                }
            } while (queue.peek() != first); // 终止条件碰到上一个比它小的值
            queue.enqueue(queue.dequeue());
            queue.enqueue(second);
            first = second;
        }
    }

    public static void sortQueueTest() {
        int[] arr = ArrayUtils.distinctArr(300, 1, 1000);
        Queue queue = new Queue();
        for (int i : arr) {
            queue.enqueue(i);
        }
        sortQueue(queue);
        int start = queue.dequeue();
        while (!queue.isEmpty()) {
            int temp = queue.dequeue();
            if (temp <= start) {
                System.out.println("not sorted");
                return;
            }
        }
        System.out.println("sorted");
    }

    //==============================================================================================

    /**
     * 2.2.10
     * 快速归并 不用再判断两边数组是否用尽
     */
    private static void merge(int[] arr, int[] aux, int lo, int mid, int hi) {
        // 左边升序
        for (int i = lo; i <= mid; i++) {
            aux[i] = arr[i];
        }
        // 右边降序
        for (int i = mid + 1; i <= hi; i++) {
            aux[i] = arr[hi + mid + 1 - i];
        }
        // 得到一个双调数组
        // 从两端开始查找
        int i = lo, j = hi;
        for (int k = lo; k <= hi; k++) {
            if (aux[i] < aux[j]) {
                arr[k] = aux[i++];
            } else {
                arr[k] = aux[j--];
            }
        }
    }

    //==============================================================================================

    /**
     * 2.2.12
     * 次线性的额外空间
     * 将大小为N的数组分为M大小的N/M块，使用归并方法，使所需额外空间减少到O(M)级别
     */
    /**
     * 1、将数组按M大小分块，每块使用选择排序或插入排序
     * 2、归并第一个子数组和第二个子数组
     * 3、循环归并前面所有归并完的数组和下一个未归并的子数组
     * 归并方法是：
     *  将右边的M大小的子数组复制到M大小的辅助数组中，然后从两个数组右边开始取值，将大值放到右边数组原区间的最右边，
     *  直到右边数组全部归并完成，因为左右数组都是排序的，所以左边数组剩余的部分也是有序的且全部小于右边数组
     *  可知该算法的辅助数组大小永远等于M
     */

    //==============================================================================================

    /**
     * 2.2.16
     *  自然的归并排序 编写一个自底向上的归并排序，能够利用数组中有序的部分，连续归并两个递增的区块
     */
    public static void naturalSort(int[] arr) {
        if (arr.length <= 1) {
            return;
        }
        int[] aux = new int[arr.length];
        int lo = 0;
        int hi = arr.length - 1;
        while (true) {
            int i = lo, j = lo, k = lo;
            while (true) {
                // 找到第一个递增的区块
                j = findBlock(arr, j);
                if (j == hi) {
                    break;
                }
                // 找到第二个递增的区块
                k = findBlock(arr, j + 1);
                merge(arr, aux, i, j, k);
                if (k == hi) {
                    break;
                }
                i = j = k = k + 1;
            }
            if (i == lo && (j == hi || k == hi)) {
                break;
            }
        }
    }

    private static int findBlock(int[] arr, int start) {
        int i = start;
        for (; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                break;
            }
        }
        return i;
    }

    public static void naturalSortTest() {
        int[] arr1 = ArrayUtils.randomArr(200, 1, 100);
        SortingExs.naturalSort(arr1);
        System.out.println(ArrayUtils.isSorted(arr1));
        int[] arr2 = ArrayUtils.randomArr(200, 1, 1000);
        SortingExs.naturalSort(arr2);
        System.out.println(ArrayUtils.isSorted(arr2));
    }

    //==============================================================================================

    /**
     * 2.2.17
     * 链表排序 使用自然排序，这是链表最佳的排序方法
     */
    public static Node linkedNodeSort(Node node) {
        Node first, second, third;
        // 归并之后的头
        Node head = node;
        while (true) {
            // 先确定头
            first = head;
            second = findBlock(first);
            if (second.next == null) {
                break;
            }
            third = findBlock(second.next);
            // 归并区间后一个结点
            Node next = third.next;
            // 合并两个链表前先拆开
            Node temp = second.next;
            second.next = null;
            third.next = null;
            head = merge(first, temp);
            // 归并完与next连在一起
            if (next == null) {
                break;
            }
            add(head, next);
            // 开始循环
            do {
                first = next;
                second = findBlock(first);
                if (second.next == null) {
                    break;
                }
                third = findBlock(second.next);
                next = third.next;
                // 合并两个链表前先拆开
                temp = second.next;
                second.next = null;
                third.next = null;
                head = merge(first, temp);
                // 归并完与next连在一起
                add(first, next);
            } while (next != null);
        }
        return head;
    }

    public static void add(Node head, Node next) {
        if (head == null || next == null) {
            return;
        }
        while (head.next != null) {
            head = head.next;
        }
        head.next = next;
    }

    public static Node merge(Node first, Node second) {
        if (second == null) {
            return first;
        } else if (first == null) {
            return second;
        }
        Node head;
        if (first.val < second.val) {
            head = first;
            first = first.next;
        } else {
            head = second;
            second = second.next;
        }
        Node node = head;
        while (true) {
            if (first == null) {
                node.next = second;
                break;
            } else if (second == null) {
                node.next = first;
                break;
            } else if (first.val < second.val) {
                node.next = first;
                first = first.next;
                node = node.next;
            } else {
                node.next = second;
                second = second.next;
                node = node.next;
            }
        }
        return head;
    }

    public static Node findBlock(Node node) {
        if (node == null) {
            return null;
        }
        Node next = node.next;
        while (next != null) {
            if (node.val > next.val) {
                return node;
            }
            node = next;
            next = node.next;
        }
        return node;
    }

    public static void linkedNodeSortTest() {
        Node node1 = NodeUtils.randomLinkedNode(50, 1, 50);
        Node node2 = NodeUtils.distinctLinkedNode(50, 1, 100);
        node1 = linkedNodeSort(node1);
        node2 = linkedNodeSort(node2);
        System.out.println(NodeUtils.isSorted(node1));
        System.out.println(NodeUtils.isSorted(node2));
    }

    //==============================================================================================
}
