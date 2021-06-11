package com.kkk.algs4;

import com.kkk.supports.ArrayUtils;
import com.kkk.supports.Node;
import com.kkk.supports.NodeUtils;
import com.kkk.supports.Queue;
import com.kkk.supports.Stack;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * 第二章 排序
 * @author KaiKoo
 */
public class SortingExs {

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
    private static void sortQueue(Queue queue) {
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
    private static void naturalSort(int[] arr) {
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
    private static Node linkedNodeSort(Node node) {
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

    private static void add(Node head, Node next) {
        if (head == null || next == null) {
            return;
        }
        while (head.next != null) {
            head = head.next;
        }
        head.next = next;
    }

    private static Node merge(Node first, Node second) {
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

    private static Node findBlock(Node node) {
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

    /**
     * 2.3.15
     * 螺丝和螺母
     * N个螺丝和N个螺母，快速将其配对，不能直接比较螺丝和螺丝，螺母和螺母
     */
    /**
     * 使用快速排序
     * 先分为螺丝组和螺母组，将两组排序后，即可按顺序匹配。
     * 1、选螺丝组第一个螺丝为pivot，从螺母中找到该螺丝匹配的螺母，将其换到第一位上
     * 2、使用快速排序排序两个组，不过每次比对是螺丝组使用螺母组的pivot，螺母组使用螺丝组的pivot
     */

    //==============================================================================================

    /**
     * 2.3.17
     * 快速排序的哨兵，省略掉循环内部的边界检查
     * 使用一次冒泡排序将最大的元素排到最右边，这样它可以作为右边的哨兵
     */
    private static void sentinelQuickSort(int[] arr) {
        // 先将最大的元素排到最右边
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                ArrayUtils.swap(arr, i, i + 1);
            }
        }
        // 只需要排到length-2位即可
        sentinelQuickSort(arr, 0, arr.length - 2);
    }

    // 与原sort一致
    private static void sentinelQuickSort(int[] arr, int lo, int hi) {
        if (hi <= lo) {
            return;
        }
        int j = sentinelPartition(arr, lo, hi);
        sentinelQuickSort(arr, lo, j - 1);
        sentinelQuickSort(arr, j + 1, hi);
    }

    // 与原partition一致，只需要去掉循环内的if校验即可
    private static int sentinelPartition(int[] arr, int lo, int hi) {
        int pivot = arr[lo];
        // 左哨兵为lo 右哨兵为hi+1
        int i = lo, j = hi + 1;
        while (true) {
            // 从左边开始找到第一个大于等于pivot的元素
            // 循环必然会终止，因为右哨兵一定大于区间内所有的值
            while (arr[++i] < pivot) {
            }
            // 从右边开始找到第一个小于等于pivot的元素
            // 循环必然会终止，因为pivot为lo的值
            while (arr[--j] > pivot) {
            }
            // 此处判断不放在循环终止条件中是因为最后一步是j和pivot交换位置
            if (i >= j) {
                break;
            }
            ArrayUtils.swap(arr, i, j);
        }
        // 因为默认pivot是lo，所以最后一步是j和pivot交换位置
        ArrayUtils.swap(arr, lo, j);
        return j;
    }

    public static void sentinelQuickSortTest() {
        int[] arr = ArrayUtils.distinctArr(1000, 1, 2000);
        sentinelQuickSort(arr);
        System.out.println(ArrayUtils.isSorted(arr));
    }

    //==============================================================================================

    /**
     * 2.3.20
     * 非递归的快速排序
     * 用一个栈保存每次切分后的数组坐标
     */
    private static void unrecursionQuickSort(int[] arr) {
        Stack stack = new Stack();
        // 初始入栈，先入lo 再入hi
        stack.push(0);
        stack.push(arr.length - 1);
        while (!stack.isEmpty()) {
            // 出栈先出hi再出lo
            int hi = stack.pop();
            int lo = stack.pop();
            if (hi <= lo) {
                continue;
            }
            // 切分
            int j = partition(arr, lo, hi);
            // 将大的数组先入栈，即先排小的数组
            if (hi - j > j - lo) {
                stack.push(j + 1);
                stack.push(hi);
                stack.push(lo);
                stack.push(j - 1);
            } else {
                stack.push(lo);
                stack.push(j - 1);
                stack.push(j + 1);
                stack.push(hi);
            }
        }
    }

    // 快速排序原partition方法
    private static int partition(int[] arr, int lo, int hi) {
        int pivot = arr[lo];
        int i = lo, j = hi + 1;
        while (true) {
            // 从左边开始找到第一个大于等于pivot的元素
            while (arr[++i] < pivot) {
                if (i == hi) {
                    break;
                }
            }
            // 从右边开始找到第一个小于等于pivot的元素
            while (arr[--j] > pivot) {
            }
            if (i >= j) {
                break;
            }
            ArrayUtils.swap(arr, i, j);
        }
        ArrayUtils.swap(arr, lo, j);
        return j;
    }

    public static void unrecursionQuickSortTest() {
        int[] arr = ArrayUtils.distinctArr(1000, 1, 2000);
        unrecursionQuickSort(arr);
        System.out.println(ArrayUtils.isSorted(arr));
    }

    //==============================================================================================

    /**
     * 2.3.24
     * 取样排序 取样大小为2^k-1
     * 先将取样的元素排序，再以中位数分割分为两个子数组移动到数组的左右两端，则数组分为了四个部分：有序的取样数组、
     * 取样数组的中位数、无序的剩余数组、有序的取样数组。使用中位数作pivot，对未取样的剩余元素做快速排序进行切分
     * 切分完成后，接下来我们再对第一个部分取半，放到中位数之前；对最后一部分取半，放到中位数之后
     * 则此时中位数左右的数组都被分为了上一步一样的四部分，重复以上过程，直至完全排序
     */

    //==============================================================================================

    /**
     * 2.4.26
     * 无需交换的堆
     * 类似于插入排序的优化解法，使用半交换方式，每次只赋值被下沉或上浮的值，然后保存下待上浮或下沉的值
     */

    //==============================================================================================

    /**
     * 2.4.29
     * 同时面向最大元素和最小元素的堆
     * 使用一个最大堆和一个最小堆同时保存所有元素 但是相同元素之间有指针互相连接（保存下在另一个堆的位置的索引）
     * 插入时同时插入两个堆即可，插入完成后，保存下元素的位置
     * 删除时一个堆正常删除，另一个堆通过指针找到元素的位置，并对该位置的子堆做一个堆删除操作
     */

    //==============================================================================================

    /**
     * 2.4.31
     * 快速插入 基于比较的方式，使得往堆中插入元素只需要~loglogN次比较
     * 修改swim方法，将父结点组成一条路径，可知该路径是有序的，再通过二分查找，找到元素在该路径中该插入的位置
     * 然后插入该位置，并让其他元素沿着路径下移即swim完成。
     * 路径长度是~logN级别，可知加上二分查找后则是~loglogN级别
     */

    //==============================================================================================

    /**
     * 2.5.2
     * 从一列单词输入中打印出所有由两个单词组成的组合词
     * 类似于3-sum问题
     */
    private static void printCombinedWords(String[] arr) {
        // 先按单词长度排序
        Arrays.sort(arr, Comparator.comparingInt(String::length));
        // 从第三位开始
        for (int n = 2; n < arr.length; n++) {
            // 从前面的字符串里面查找
            int i = 0, j = n - 1;
            int length = arr[n].length();
            while (i < j) {
                int com = arr[i].length() + arr[j].length() - length;
                if (com == 0) {
                    if (arr[i] + arr[j] == arr[n] || arr[j] + arr[i] == arr[n]) {
                        System.out.println(arr[n]);
                        break;
                    }
                    i++;
                    j--;
                } else if (com > 0) {
                    j--;
                } else {
                    i++;
                }
            }
        }
    }

    //==============================================================================================

    /**
     * 2.5.13
     * 负载均衡 接受一个整数M，读取一系列的任务和所需的运行时间，将任务分配给M个处理器，使得完成所有任务的总时间最少
     */
    /**
     * 首先将所有任务按运行时间排序，为处理器构建一个M大小的最小堆，记录下处理器接受到任务所需的总时间
     * 将排序后的任务数组，按顺序开始分配，每次从堆顶取出当前总时间最小的处理器，分配当前任务，
     * 并给处理器加上任务时间，再重新加入堆中，分配完成后，删去所有元素，取出执行任务总时间最大的处理器。
     */

    //==============================================================================================

    /**
     * 2.5.18
     * 强制稳定 将任意一种排序方法变为稳定
     */
    private static <T extends Comparable> int[] stabilize(T[] arr, Consumer<Wrapper<T>[]> sort) {
        Wrapper<T>[] wrappers = new Wrapper[arr.length];
        for (int i = 0; i < arr.length; i++) {
            wrappers[i] = new Wrapper(arr[i], i);
        }
        sort.accept(wrappers);
        for (int i = 1; i < wrappers.length; i++) {
            // 插入排序
            for (; i < wrappers.length && wrappers[i].v == wrappers[i - 1].v; i++) {
                for (int j = i;
                        j > 0 && wrappers[j].v == wrappers[j - 1].v && wrappers[j].index < wrappers[
                                j - 1].index; j--) {
                    Wrapper temp = wrappers[j - 1];
                    wrappers[j - 1] = wrappers[j];
                    wrappers[j] = temp;
                }
            }
        }
        int[] indexs = new int[arr.length];
        for (int i = 0; i < wrappers.length; i++) {
            arr[i] = wrappers[i].v;
            indexs[i] = wrappers[i].index;
        }
        return indexs;
    }

    private static class Wrapper<T extends Comparable> implements Comparable<Wrapper<T>> {

        T v;
        int index;

        public Wrapper(T v, int index) {
            this.v = v;
            this.index = index;
        }

        @Override
        public int compareTo(Wrapper<T> o) {
            return v.compareTo(o.v);
        }
    }

    public static void stabilizeTest() {
        Integer[] array = Arrays.stream(ArrayUtils.randomArr(50, 1, 10)).boxed()
                .collect(Collectors.toList()).toArray(new Integer[50]);
        int[] indexs = stabilize(array, Essences::fast3DQuickSort);
        System.out.println(Arrays.toString(array));
        System.out.println(Arrays.toString(indexs));
    }

    //==============================================================================================

    /**
     * 2.5.23
     * 选择的取样 找出第K小的元素，使用取样改进算法
     * Floyd-Rivest算法
     */
    private static int selectKth(int[] arr, int k) {
        if (k >= arr.length) {
            throw new IllegalArgumentException();
        }
        return select(arr, k - 1, 0, arr.length - 1);
    }

    private static int select(int[] arr, int i, int lo, int hi) {
        while (lo < hi) {
            if (hi - lo > 600) {
                //太复杂忽略
            }
            // 选择i作为切分元素
            ArrayUtils.swap(arr, i, lo);
            int j = partition(arr, lo, hi);
            if (j > i) {
                hi = j - 1;
            } else if (j < i) {
                lo = j + 1;
            } else {
                break;
            }
        }
        return arr[i];
    }

    public static void selectKthTest() {
        int[] arr = ArrayUtils.distinctArr(100000, 0, 100000);
        System.out.println(selectKth(arr, 45555));
    }

    //==============================================================================================

}
