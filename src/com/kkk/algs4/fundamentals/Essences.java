package com.kkk.algs4.fundamentals;

import java.util.ArrayList;
import java.util.List;

/**
 * 拔高题
 * @author KaiKoo
 */
public class Essences {

    /**
     * 超难题：
     * 1.3.49 使用多个栈实现一个队列，每次队列操作对应常数次的栈操作
     * 1.4.20 求矩阵中的局部最小值（小于上下左右的元素）（要求线性时间复杂度）
     */

    /**
     * 1.4.22
     * 斐波那契数列搜索 仅使用加法和减法
     * 对比二分查找 斐波那契查找的时间复杂度还是对数级，但是对磁盘的操作更省力
     */
    public static int fbSearch(int[] arr, int key) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        int[] fbArray = makeFbArray(arr.length);
        //从倒数第二数开始取，保证不会超出数组长度
        int k = fbArray.length - 2;
        int lo = 0;
        int hi = arr.length - 1;
        while (lo <= hi) {
            int mid = lo + fbArray[k] - 1;
            if (getOutOfRange(arr, mid) < key) {
                // 为什么减1 是为了保证mid不会大于hi
                // 下一步，mid = lo + f(n-1) + f(n-2) -1 = lo + f(n) -1 = hi
                k = k - 1;
                lo = mid + 1;
            } else if (getOutOfRange(arr, mid) > key) {
                // 为什么减2 是为了保证mid不会大于hi
                // 下一步 hi = lo + f(n-1) - 2
                // 如果只减去 1 下下步如果是小于则mid会大于hi mid = lo + f(n-2) + f(n-3) - 1 = lo + f(n-1) -1
                // 所以该减去 2 下下步如果是小于则 mid = lo + f(n-2) -1  hi -mid = f(n-3) -1 （明显f(n-3)大于等于1，所以mid不会超过hi）
                k = k - 2;
                hi = mid - 1;
            } else {
                // 如果等于最后一个元素，还要额外判断一次
                return mid > arr.length ? arr.length - 1 : mid;
            }
        }
        return -1;
    }

    // index超过数组大小则返回最后的元素
    private static int getOutOfRange(int[] arr, int index) {
        if (index < arr.length) {
            return arr[index];
        } else {
            return arr[arr.length - 1];
        }
    }

    // 根据数组长度构建一个斐波那契数列
    private static int[] makeFbArray(int length) {
        List<Integer> list = new ArrayList<>();
        int i = 2;
        list.add(1);
        list.add(1);
        int n;
        do {
            n = list.get(i - 1) + list.get(i - 2);
            list.add(n);
            i++;
        } while (n < length);
        int[] ints = new int[i];
        for (int j = 0; j < i; j++) {
            ints[j] = list.get(j);
        }
        return ints;
    }

}
