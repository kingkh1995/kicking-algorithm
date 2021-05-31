package com.kkk.supports;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author KaiKoo
 */
public class Utils {

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
        if (n == arr.length) {
            return arr;
        }
        for (int i = 0; i < n; i++) {
            swap(arr, i, random.nextInt(i + 1, arr.length));
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

    public static void printArray(int[] a) {
        System.out.println(Arrays.toString(a));
    }

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
