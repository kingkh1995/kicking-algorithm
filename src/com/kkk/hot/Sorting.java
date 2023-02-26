package com.kkk.hot;

import com.kkk.supports.ArrayUtils;

/**
 * 排序算法 <br>
 *
 * @author KaiKoo
 */
public class Sorting {

  /**
   * 选择排序 <br>
   * 运行时间与输入无关，总是需要 ~N2/2 次比较和 ~N 次交换。 <br>
   * 不稳定，时间复杂度为O(N^2)，空间复杂度为O(1)。
   */
  public static class SelectionSort {
    public void sort(int[] arr) {
      for (int n = arr.length, i = 0; i < n; ++i) {
        int min = i; // 遍历找到剩余区间的最小元素的索引
        for (int j = i + 1; j < n; ++j) {
          if (arr[j] < arr[min]) {
            min = j;
          }
        }
        ArrayUtils.swap(arr, i, min); // 交换到前面的位置
      }
    }
  }

  /**
   * 冒泡排序 <br>
   * 最坏情况下需要 ~N2/2 次比较和 ~N2/2 次交换，最好情况下只需要 ~N 次比较，适合基本有序的场景。 <br>
   * 稳定排序，平均时间复杂度为O(N^2)，空间复杂度为O(1)。
   */
  public static class BubbleSort {
    public void sort(int[] arr) {
      boolean flag = true;
      for (int n = arr.length, i = n - 1; flag && i > 0; --i) {
        flag = false;
        for (int j = 0; j < i; ++j) { // 从0位置开始将剩余区间内最大元素上浮到区间的最右端
          if (arr[j] > arr[j + 1]) {
            ArrayUtils.swap(arr, j, j + 1);
            flag = true;
          }
        }
      }
    }
  }

  /**
   * 插入排序 <br>
   * 最坏和最好情况与冒泡排序相同，但在三种初级排序算法中平均性能最好。 <br>
   * 稳定排序，平均时间复杂度为O(N^2)，空间复杂度为O(1)。
   */
  public static class InsertionSort {
    public void sort(int[] arr) {
      for (int n = arr.length, i = 1; i < n; ++i) {
        for (int j = i; j > 0 && arr[j] < arr[j - 1]; --j) { // 之前区间为排序数组，交换元素到正确位置。
          ArrayUtils.swap(arr, j - 1, j);
        }
      }
    }
  }

  /**
   * 希尔排序 <br>
   * 对于大规模的数组，插入排序很慢，因为它每次只能将逆序数减少 1，而希尔排序可以减少多个。 <br>
   * 非稳定排序，平均时间复杂度为O(NlogN)，空间复杂度为O(1)。
   */
  public static class ShellSort {
    public void sort(int[] arr) {
      int n = arr.length, h = 1;
      while (h < n / 3) { // 确定出h的值
        h = h * 3 + 1;
      }
      for (; h >= 1; h /= 3) {
        for (int i = h; i < n; ++i) { // 使用插入排序的方式将数组变为h有序
          for (int j = i; j >= h && arr[j] < arr[j - h]; j -= h) {
            ArrayUtils.swap(arr, j - h, j);
          }
        }
      }
    }
  }

  /**
   * 归并排序 <br>
   * 稳定排序，任何场景下时间复杂度都为O(NlogN)，空间复杂度为O(N)。
   */
  public static class MergeSort {
    private int[] aux;

    private void merge(int[] arr, int lo, int mid, int hi) {
      for (int k = lo; k <= hi; ++k) {
        aux[k] = arr[k];
      }
      for (int k = lo, i = lo, j = mid + 1; k <= hi; ++k) {
        if (i > mid) {
          arr[k] = aux[j++];
        } else if (j > hi) {
          arr[k] = aux[i++];
        } else if (aux[i] > aux[j]) {
          arr[k] = aux[j++];
        } else {
          arr[k] = aux[i++];
        }
      }
    }

    public void sort1(int[] arr) { // 自顶向下
      int n = arr.length;
      aux = new int[n];
      sort1(arr, 0, n - 1);
    }

    private void sort1(int[] arr, int lo, int hi) {
      if (lo >= hi) {
        return;
      }
      int mid = lo + ((hi - lo) >> 1);
      sort1(arr, lo, mid);
      sort1(arr, mid + 1, hi);
      merge(arr, lo, mid, hi);
    }

    public void sort2(int[] arr) { // 自底向上
      int n = arr.length;
      aux = new int[n];
      for (int sz = 1; sz < n; sz <<= 1) {
        for (int i = 0; i < n - sz; i += sz + sz) {
          merge(arr, i, i + sz - 1, Math.min(i + sz + sz - 1, n - 1));
        }
      }
    }
  }

  /**
   * 快速排序 <br>
   * 非稳定排序，平均时间复杂度为O(NlogN)，但是在最坏的情况下，如果本身是有序的则复杂度为O(N^2)。<br>
   * 虽然不需要额外空间，但由于是递归调用需要栈空间，即空间复杂度为O(logN)。
   */
  public static class QuickSort {

    public void sort1(int[] arr) {
      sort1(arr, 0, arr.length - 1);
    }

    private void sort1(int[] arr, int lo, int hi) { // 普通快速排序
      if (hi <= lo) {
        return;
      }
      int pivot = arr[lo], ge = hi + 1; // [ge,hi]区间为大于等于pivot的区间
      for (int i = hi; i > lo; i--) { // 从右边开始找到大于等于pivot的元素则交换到区间最右边
        if (arr[i] >= pivot) {
          ArrayUtils.swap(arr, i, --ge);
        }
      }
      ArrayUtils.swap(arr, lo, --ge); // 最后把pivot放到[ge,hi]区间的左边
      sort1(arr, lo, ge - 1);
      sort1(arr, ge + 1, hi);
    }

    public void sort2(int[] arr) {
      sort2(arr, 0, arr.length - 1);
    }

    private void sort2(int[] arr, int lo, int hi) { // 三向切分快速排序
      if (hi <= lo) {
        return;
      }
      int pivot = arr[lo], lt = lo, i = lo + 1, gt = hi; // 小于(lo,lt) 等于[lt,gt] 大于(gt,hi)
      while (i <= gt) { // 未排序区间为[i,gt]
        int cmp = arr[i] - pivot;
        if (cmp == 0) {
          i++;
        } else if (cmp > 0) {
          ArrayUtils.swap(arr, i, gt--);
        } else {
          ArrayUtils.swap(arr, lt++, i++);
        }
      }
      sort2(arr, lo, lt - 1);
      sort2(arr, gt + 1, hi);
    }
  }
}
