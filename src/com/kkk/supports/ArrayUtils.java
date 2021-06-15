package com.kkk.supports;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/** @author KaiKoo */
public class ArrayUtils {

  private static ThreadLocalRandom random = ThreadLocalRandom.current();

  private static IntStream randomArr0(int n, int origin, int bound) {
    return Stream.generate(() -> random.nextInt(origin, bound))
        .limit(n)
        .mapToInt(Integer::intValue);
  }

  public static int[] randomSortedArr(int n, int origin, int bound) {
    return randomArr0(n, origin, bound).sorted().toArray();
  }

  public static int[] randomArr(int n, int origin, int bound) {
    return randomArr0(n, origin, bound).toArray();
  }

  private static IntStream distinctArr0(int n, int origin, int bound) {
    if (bound - origin < n) {
      throw new IllegalArgumentException();
    }
    return Stream.generate(() -> random.nextInt(origin, bound))
        .distinct()
        .limit(n)
        .mapToInt(Integer::intValue);
  }

  public static int[] distinctSortedArr(int n, int origin, int bound) {
    return distinctArr0(n, origin, bound).sorted().toArray();
  }

  public static int[] distinctArr(int n, int origin, int bound) {
    return distinctArr0(n, origin, bound).toArray();
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
