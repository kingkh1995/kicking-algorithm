package com.demo.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 排序问题
 *
 * @author KaiKoo
 * @date 2020/3/5 14:01
 */
public class SortQuestion {

    private final static SortQuestion SORT_QUESTION = new SortQuestion();

    /*
    找出数组中最小的k个数字
    如果k的值很小，可以使用冒泡排序。
    如果是海量数据的查找，且不能改变原数组，那么使用最大堆的方式，循环插入数字，建立一个k容量的最大堆，
    如果数字小于堆顶数字，则删除堆顶并插入该数字，这一操作时间复杂度为o(logk)，故时间复杂度为o(nlogk)
     */
    /*
    解题思路：非海量数据，只需找出，无需排序，且有重复数据数据 使用三分快速排序
    时间复杂度o(klogn)
     */
    public List<Integer> findLeastKNumber(int[] arr, int k) {
        List<Integer> list = new ArrayList<>(k);
        if (arr == null || arr.length < 1 || k < 1 || k > arr.length) {
            return list;
        }
        if (k < arr.length) {
            int start = 0;
            int end = arr.length;
            int index1;
            int index2;
            while (true) {
                int[] indexs = partition(arr, start, end);
                index1 = indexs[0];
                index2 = indexs[1];
                if (index2 < k - 1) {
                    //pivot区间在k左边，重排区间右侧开始到数组尾端
                    start = index2 + 1;
                } else if (index1 > k - 1) {
                    //pivot区间在k右边，重排数组前端开始到区间左侧
                    end = index1;
                } else {
                    //循环终止条件，k在pivot区间内
                    break;
                }
            }
        }
        for (int i = 0; i < k; i++) {
            list.add(arr[i]);
        }
        return list;
    }

    //三向切分单向快速排序，含头不含尾
    private static int[] partition(int[] arr, int start, int end) {
        if (end - start < 1) {
            return new int[]{start, end - 1};
        }
        //选择start作为pivot
        int pivot = arr[start];
        //（start->i）小于pivot的数字 [i->k）等于pivot的数字 （k->j]还未排序的数字 （j->end）大于pivot的数字
        // k从i下一个元素开始遍历，如果小于则将元素换到pivot区间左边，然后将pivot区间前端右移一位，如果大于则将元素移到pivot区间右边，并将pivot区间左移一位。
        int i = start, j = end - 1, k = start + 1;
        //循环终止条件，数字全部排序完成，即k>j
        while (k <= j) {
            if (arr[k] < pivot) {
                swap(arr, k, i);
                i++;
                k++;
            } else if (arr[k] == pivot) {
                k++;
            } else {
                swap(arr, k, j);
                j--;
            }
        }
        //返回等于pivot的区间
        return new int[]{i, j};
    }

    public static void swap(int[] arr, int i1, int i2) {
        if (i1 != i2) {
            int temp = arr[i1];
            arr[i1] = arr[i2];
            arr[i2] = temp;
        }
    }

    /*
    求一个正整数数组中所有数字拼接成的最小数字
     */
    /*
    解题思路：转换为排序问题，比较规则：mn>nm 则m>n
    如何证明：1.自反性 2.对称性 3.传递性
     */
    public String minMadeUpNumberOfArrayTest(int[] arr) {
        if (arr == null || arr.length < 1) {
            return "";
        }
        //int[]转换为String[]
        String[] array = new String[arr.length];
        for (int i = 0; i < arr.length; i++) {
            array[i] = String.valueOf(arr[i]);
        }
        //使用字符串进行比较
        Arrays.sort(array, (m, n) -> {
            String mn = m + n;
            String nm = n + m;
            for (int i = 0; i < mn.length(); i++) {
                if (mn.charAt(i) - nm.charAt(i) > 0) {
                    return 1;
                } else if (mn.charAt(i) - nm.charAt(i) < 0) {
                    return -1;
                }
            }
            return 0;
        });
        StringBuilder sb = new StringBuilder();
        for (String s : array) {
            sb.append(s);
        }
        return sb.toString();
    }

    /*
     输入一个数组（海量数据）,求出这个数组中的逆序对的总数P，输出P%1000000007
     */
    /*
    解题思路：使用二路归并的方式求逆序对总数 [A，B]中的逆序对=[A]内部的逆序对+[B]内部中的逆序对+[A][B]合并后新增的逆序对
    时间复杂度：o(nlogn)
     */
    public int amountOfInversePairsInMassiveData(int[] arr) {
        return mergeSort(arr, 0, arr.length);
    }

    //使用归并排序区间并统计逆序对总数
    private int mergeSort(int[] arr, int start, int end) {
        if (end - start <= 1) {
            return 0;
        }
        int mid = (start + end) / 2;
        int a = mergeSort(arr, start, mid);
        int b = mergeSort(arr, mid, end);
        //二路归并 并统计两个区间合并后增加的逆序数对
        int count = 0;
        int[] temp = new int[end - start];
        int k = 0, i = start, j = mid;
        while (i < mid && j < end) {
            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
                //如果大于 则有逆序对 并且左边区间 i 之后的所有元素均大于 j元素
                count = (count + mid - i) % 1000000007;
            }
        }
        while (i < mid) {
            temp[k++] = arr[i++];
        }
        while (j < end) {
            temp[k++] = arr[j++];
        }
        //复制temp到原数组
        for (int l = start; l < end; l++) {
            arr[l] = temp[l - start];
        }
        return (a + b + count) % 1000000007;
    }

    //测试用例

    public static void findLeastKNumberTest() {
        System.out.println(SORT_QUESTION.findLeastKNumber(new int[]{5, 4, 2, 3, 2, 6, 5}, 0));
        System.out.println(SORT_QUESTION.findLeastKNumber(new int[]{}, 1));
        System.out.println(SORT_QUESTION.findLeastKNumber(new int[]{5}, 3));
        System.out.println(SORT_QUESTION.findLeastKNumber(new int[]{5}, 1));
        System.out.println(SORT_QUESTION.findLeastKNumber(new int[]{1, 1, 1, 1,}, 2));
        System.out.println(SORT_QUESTION.findLeastKNumber(new int[]{1, 1, 1, 1,}, 4));
        System.out.println(SORT_QUESTION.findLeastKNumber(new int[]{5, 4, 2, 3, 2, 6, 5, 2}, 4));
        System.out.println(SORT_QUESTION.findLeastKNumber(new int[]{4, 5, 1, 6, 2, 7, 3, 8}, 4));
    }

    public static void minMadeUpNumberOfArrayTest() {
        System.out.println(SORT_QUESTION.minMadeUpNumberOfArrayTest(null));
        System.out.println(SORT_QUESTION.minMadeUpNumberOfArrayTest(new int[]{}));
        System.out.println(SORT_QUESTION.minMadeUpNumberOfArrayTest(new int[]{321, 32, 3}));
    }

    public static void amountOfInversePairsInMassiveDataTest() {
        System.out.println(SORT_QUESTION.amountOfInversePairsInMassiveData(new int[]{7, 5, 6, 4}));
    }
}
