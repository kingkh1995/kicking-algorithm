package com.kkk.leetcode;

import com.kkk.supports.ArrayUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 数组和矩阵 <br>
 * 324、498
 *
 * @author KaiKoo
 */
public class ArrayAndMatrixExx {

  /** 54. 螺旋矩阵 <br> */
  public List<Integer> spiralOrder(int[][] matrix) {
    int m = matrix.length, n = matrix[0].length;
    List<Integer> ans = new ArrayList<>(m * n);
    int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}; // 顺时针
    for (int i = 0, j = 0, ni, nj, dir = 0; ans.size() < m * n; ) { // 收集完成后结束
      ans.add(matrix[i][j]);
      matrix[i][j] = Integer.MAX_VALUE; // 使用原数组作为标记数组
      if ((ni = i + dirs[dir][0]) < 0
          || ni >= m
          || (nj = j + dirs[dir][1]) < 0
          || nj >= n
          || matrix[ni][nj] == Integer.MAX_VALUE) { // 沿着顺时针切换方向
        dir = (dir + 1) % 4;
      }
      i += dirs[dir][0];
      j += dirs[dir][1];
    }
    return ans;
  }

  /** 415. 字符串相加 <br> */
  public String addStrings(String num1, String num2) {
    int i = num1.length() - 1, j = num2.length() - 1, add = 0;
    StringBuffer ans = new StringBuffer();
    while (i >= 0 || j >= 0 || add != 0) {
      int n = (i >= 0 ? num1.charAt(i--) - '0' : 0) + (j >= 0 ? num2.charAt(j--) - '0' : 0) + add;
      add = n / 10;
      ans.append(n % 10);
    }
    return ans.reverse().toString();
  }

  // ===============================================================================================

  /**
   * 28. 实现 strStr() <br>
   * 即indexOf操作，使用KMP算法实现。
   */
  public int strStr(String haystack, String needle) {
    int m = haystack.length(), n = needle.length();
    if (n == 0) {
      return 0;
    }
    int[] aux = new int[n];
    aux[0] = -1;
    for (int i = 1; i < n; ++i) {
      int k = aux[i - 1];
      while (k != -1) {
        if (needle.charAt(i - 1) == needle.charAt(k)) {
          aux[i] = k + 1;
          break;
        } else {
          k = aux[k];
        }
      }
    }
    int i = 0, j = 0;
    while (i < m && j < n) {
      if (j == -1 || haystack.charAt(i) == needle.charAt(j)) {
        i++;
        j++;
      } else {
        j = aux[j];
      }
    }
    return j == n ? i - j : -1; // 模式扫描完成表示匹配成功
  }

  /** 1706. 球会落何处 <br> */
  public int[] findBall(int[][] grid) {
    int n = grid[0].length;
    int[] ans = new int[n];
    for (int j = 0; j < n; ++j) {
      int col = j; // 当前列
      for (int[] row : grid) { // 逐行判断模拟下落
        int dir = row[col]; // 移动方向
        col += dir;
        if (col < 0 || col == n || row[col] != dir) {
          col = -1;
          break;
        }
      }
      ans[j] = col;
    }
    return ans;
  }

  // ===============================================================================================
  /** 基础题 */

  // 计算右侧小于当前元素的个数
  static class countSmallerSolution {

    private int[] index; // 索引数组
    private int[] temp; // 归并排序辅助数组
    private int[] tempIndex; // 归并排序辅助索引数组
    private int[] ans; // 结果

    public List<Integer> countSmaller(int[] nums) {
      int n = nums.length;
      this.index = new int[n];
      this.temp = new int[n];
      this.tempIndex = new int[n];
      this.ans = new int[n];
      for (int i = 0; i < n; ++i) {
        index[i] = i;
      }
      mergeSort(nums, 0, n - 1);
      return Arrays.stream(ans).boxed().toList();
    }

    private void mergeSort(int[] a, int l, int r) {
      if (l >= r) {
        return;
      }
      int mid = (l + r) >> 1;
      mergeSort(a, l, mid);
      mergeSort(a, mid + 1, r);
      merge(a, l, mid, r);
    }

    private void merge(int[] a, int l, int mid, int r) {
      // 归并排序，左边l-mid，右边(mid+1)-r
      int i = l, j = mid + 1, p = l;
      while (i <= mid && j <= r) {
        if (a[i] <= a[j]) {
          temp[p] = a[i];
          tempIndex[p] = index[i];
          // 右边比其小的元素区间为 mid+1 - j-1，个数为j-(mid+1)。
          ans[index[i]] += (j - mid - 1);
          ++i;
          ++p;
        } else {
          temp[p] = a[j];
          tempIndex[p] = index[j];
          ++j;
          ++p;
        }
      }
      // 排序剩余元素
      while (i <= mid) {
        temp[p] = a[i];
        tempIndex[p] = index[i];
        ans[index[i]] += (j - mid - 1);
        ++i;
        ++p;
      }
      while (j <= r) {
        temp[p] = a[j];
        tempIndex[p] = index[j];
        ++j;
        ++p;
      }
      // 复制回原数组
      for (int k = l; k <= r; ++k) {
        index[k] = tempIndex[k];
        a[k] = temp[k];
      }
    }
  }

  // ===============================================================================================
  /** 拔高题 */

  // 找到第k大的元素 普通三向切分快排
  public static int findKthLargest(int[] arr, int k) {
    int lo = 0, hi = arr.length - 1;
    k--;
    while (true) {
      int pivot = arr[lo];
      // （lo,i）大于 [i,l)等于 [l,j]未排序 （j,hi）小于
      int i = lo, l = lo + 1, j = hi;
      while (l <= j) {
        int cmp = arr[l] - pivot;
        if (cmp == 0) {
          l++; // 等于直接左移 即等于区间右增一格
        } else if (cmp < 0) {
          ArrayUtils.swap(arr, l, j--); // 小于从未排序区间最右边换一个元素过来，即未排序区间左缩一格
        } else {
          ArrayUtils.swap(arr, l++, i++); // 大于则从等于区间交换一个等于元素过来 即等于区间整个左移
        }
      }
      // 排序完之后则[i,j]未等于区间 判断是否在该区间内
      if (k >= i && k <= j) {
        return pivot;
      } else if (k < i) {
        hi = i - 1; // 在左边则排序左边
      } else {
        lo = j + 1; // 在右边则排序右边
      }
    }
  }

  // 长度最小的子数组 找到和满足大于等于target的长度最小的连续子数组
  public static int minSubArrayLen(int target, int[] nums) {
    int min = Integer.MAX_VALUE, sum = 0;
    // 使用滑动窗口
    int i = 0, j = 0;
    while (j < nums.length) {
      sum += nums[j];
      while (sum >= target) {
        min = Math.min(min, j - i + 1);
        // 左端左移 左移永远不可能超过j
        sum -= nums[i++];
      }
      j++;
    }
    return min == Integer.MAX_VALUE ? 0 : min;
  }

  // 若M × N矩阵中某个元素为0，则将其所在的行与列清零，使用常数级别辅助空间。
  public void setZeroes(int[][] matrix) {
    // 需要两个辅助数组，分别标记行和列是否存在0
    // 为了节约空间，将矩阵第一行和第一列作为辅助数组，由于[0][0]位置同属于两个辅助数组，所以需要一个额外变量。
    int m = matrix.length;
    int n = matrix[0].length;
    // 额外变量用于标记第一列是否存在0，第一行剩余标记其他列，第一列用来标记行。
    boolean flag = false;
    // 标记辅助数组
    for (int i = 0; i < m; i++) {
      // 单独处理第一列
      if (matrix[i][0] == 0) {
        flag = true;
      }
      // 处理其余列
      for (int j = 1; j < n; j++) {
        // 如果当前为0，在两个辅助数组中均标记为0
        if (matrix[i][j] == 0) {
          matrix[i][0] = matrix[0][j] = 0;
        }
      }
    }
    // 从最下面一行开始逐行清零，因为最后才能清除标记数组。
    for (int i = m - 1; i >= 0; i--) {
      // 处理其余列
      for (int j = 1; j < n; j++) {
        if (matrix[i][0] == 0 || matrix[0][j] == 0) {
          matrix[i][j] = 0;
        }
      }
      // 最后处理第一列
      if (flag) {
        matrix[i][0] = 0;
      }
    }
  }

  // ===============================================================================================
  /** 困难题 */

  // 找到第k大的元素 使用快速三向切分快排
  public static int findKthLargest0(int[] arr, int k) {
    if (k < 1 || k > arr.length) {
      throw new IllegalArgumentException();
    }
    var lo = 0;
    var hi = arr.length - 1;
    var index = arr.length - k;
    while (true) {
      // 小于等于5个元素直接插入排序
      if (hi - lo < 5) {
        for (var i = lo + 1; i <= hi; i++) {
          var j = i;
          var n = arr[j];
          for (; j > lo && arr[j - 1] > n; j--) {
            arr[j] = arr[j - 1];
          }
          arr[j] = n;
        }
        return arr[index];
      }
      // 将index处的值作为pivot
      ArrayUtils.swap(arr, index, lo);
      // l--p--i--j--q--h
      var i = lo;
      var j = hi + 1;
      var p = lo; // 左边等于区间至少是[l,l]包含一个元素
      var q = hi + 1; // 右边等于区间可能没有元素 [h+1,h]
      var pivot = arr[lo];
      while (true) {
        // 从左边找到第一个大于等于切分值的数
        while (arr[++i] < pivot) {
          if (i == hi) {
            break;
          }
        }
        // 从右边找到第一个小于等于切分值的数
        while (arr[--j] > pivot) {}
        // 判断是否相遇 在中间相遇则必然等于pivot 在最后一个元素相遇则不一定（切分元素刚好是最大值）
        if (i == j && arr[i] == pivot) {
          // 如果等于pivot 则交换到左边保证j必然小于pivot
          ArrayUtils.swap(arr, i, ++p);
        }
        if (i >= j) {
          break; // 循环终止，经过上面的判断后[i,j]区间已完全排序
        }
        // 未终止则和普通的快速排序一样，交换i j
        ArrayUtils.swap(arr, i, j);
        // 再判断是否等于 等于则交换 小于区间右移 大于区间左移
        if (arr[i] == pivot) {
          ArrayUtils.swap(arr, ++p, i);
        }
        if (arr[j] == pivot) {
          ArrayUtils.swap(arr, --q, j);
        }
      }
      // 循环结束交换等于区间到中间
      i = j + 1;
      // 此时(p, j]区间小于pivot [i, q)区间大于pivot
      // 先计算出交换后等于pivot的区间
      var left = lo + (j - p); // 左端点
      var right = hi - (q - i); // 右断点
      // 如果index在等于区间内 可以直接返回结果
      if (index >= left && index <= right) {
        return pivot;
      }
      // 交换等于的区间到中间
      for (var t = lo; t <= p; t++) {
        ArrayUtils.swap(arr, t, j--);
      }
      for (var t = hi; t >= q; t--) {
        ArrayUtils.swap(arr, t, i++);
      }
      // 交换完之后，[l, j]小于pivot [i, h]大于pivot
      if (index <= j) {
        hi = j;
      } else if (index >= i) {
        lo = i;
      }
    }
  }
}
