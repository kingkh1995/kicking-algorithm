package com.kkk.algs4;

import com.kkk.supports.ArrayUtils;
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
     * 拓展：排序队列（无重复数据）
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
        int first = min;
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
            } while (queue.peek() != first);
            queue.enqueue(queue.dequeue());
            queue.enqueue(second);
            first = second;
        }
    }

    public static void sortQueueTest() {
        int[] arr = ArrayUtils.distinctArr(3, 1, 1000);
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
}
