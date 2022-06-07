package com.kkk.supports;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author KaiKoo
 */
public class ArrayUtils {

  private static final ThreadLocalRandom random = ThreadLocalRandom.current();

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
    if (a == b) {
      return;
    }
    int temp = arr[a];
    arr[a] = arr[b];
    arr[b] = temp;
  }

  public static void swap(Comparable<?>[] arr, int a, int b) {
    if (a >= arr.length || a < 0 || b >= arr.length || b < 0) {
      throw new IllegalArgumentException();
    }
    if (a == b) {
      return;
    }
    Comparable<?> temp = arr[a];
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

  public static int rank(int[] sorted, int key) {
    return rank(sorted, key, 0, sorted.length - 1);
  }

  public static int rank(int[] sorted, int key, int lo, int hi) {
    int index = hi + 1;
    while (lo <= hi) {
      int mid = lo + ((hi - lo) >> 1);
      if (sorted[mid] < key) {
        lo = mid + 1;
      } else {
        index = mid; // 大于等于情况下，将index设置为mid。
        hi = mid - 1;
      }
    }
    // index即为key应该插入的位置，也为数组中小于key的元素个数。
    return index;
  }

  public static void reverse(int[] nums, int left, int right) {
    while (left < right) {
      swap(nums, left, right);
      left++;
      right--;
    }
  }
}
