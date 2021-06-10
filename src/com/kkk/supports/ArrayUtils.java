package com.kkk.supports;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author KaiKoo
 */
public class ArrayUtils {

    private static ThreadLocalRandom random = ThreadLocalRandom.current();

    public static int[] randomSortedArr(int n, int origin, int bound) {
        int[] arr = randomArr(n, origin, bound);
        Arrays.sort(arr);
        return arr;
    }

    public static int[] randomArr(int n, int origin, int bound) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = random.nextInt(origin, bound);
        }
        return arr;
    }

    public static int[] distinctSortedArr(int n, int origin, int bound) {
        int[] arr = distinctArr(n, origin, bound);
        Arrays.sort(arr);
        return arr;
    }

    public static int[] distinctArr(int n, int origin, int bound) {
        int[] arr = new int[bound - origin];
        if (arr.length < n) {
            throw new IllegalArgumentException();
        }
        for (int i = origin; i < bound; i++) {
            arr[i - origin] = i;
        }
        for (int i = 0; i < n - 1; i++) {
            swap(arr, i, random.nextInt(i + 1, arr.length));
        }
        if (n == arr.length) {
            return arr;
        }
        return Arrays.copyOf(arr, n);
    }

    public static void swap(int[] arr, int a, int b) {
        if (a >= arr.length || a < 0 || b >= arr.length || b < 0) {
            throw new IllegalArgumentException();
        }
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    public static void swap(Comparable[] arr, int a, int b) {
        if (a >= arr.length || a < 0 || b >= arr.length || b < 0) {
            throw new IllegalArgumentException();
        }
        Comparable temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    public static void printArray(int[] a) {
        System.out.println(Arrays.toString(a));
    }

    public static boolean isSorted(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i - 1]) {
                return false;
            }
        }
        return true;
    }

}
