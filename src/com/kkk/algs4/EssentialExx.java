package com.kkk.algs4;

import com.kkk.supports.ArrayUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * 拔高题
 *
 * @author KaiKoo
 */
public class EssentialExx {

  // ===============================================================================================

  /**
   * 超难题： <br>
   * 1.3.49 使用多个栈实现一个队列，每次队列操作对应常数次的栈操作 <br>
   * 1.4.20 求矩阵中的局部最小值（小于上下左右的元素）（要求线性时间复杂度） <br>
   * 1.4.34 冷还是热 猜出秘密数 2lgN 和 lgN 的解法 <br>
   * 2.5.32 8字谜题 <br>
   * 3.3.25 2-3-4树 三种实现：1）自顶向下递归 2）自顶向下迭代 3）自底向下递归 <br>
   * 4.4.35 双调最短路径，该最短路径上边的权重前半段为单调递增后半段为单调递减或前半段为单调递减后半段为单调递增 <br>
   * 4.4.45 快速Bellman-Ford算法，针对边的权重为整数且绝对值不大于某个常数的情况，运行时间低于EV级别。 <br>
   * 5.2.16 读取文本并计算出任意长度的不同子字符串的数量，使用后缀数组实现。
   */

  // ===============================================================================================

  /** 1.4.22 斐波那契数列搜索 仅使用加法和减法 对比二分查找 斐波那契查找的时间复杂度还是对数级，但是对磁盘的操作更省力 */
  public static int fbSearch(int[] arr, int key) {
    if (arr == null || arr.length == 0) {
      return -1;
    }
    int[] fbArray = makeFbArray(arr.length);
    // 从倒数第二数开始取，保证不会超出数组长度
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
        return mid >= arr.length ? arr.length - 1 : mid;
      }
    }
    return -(lo + 1);
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

  // ===============================================================================================

  /**
   * 1.4.24 扔鸡蛋问题 N层高的楼 鸡蛋从F楼扔下会摔碎 求F 碎掉的鸡蛋为对数级别：二分查找 碎掉的鸡蛋为
   * lgF级别：从第一层开始每次上移2的幂次方，摔碎了则可以确定F的区间，该区间最坏的情况下长度等于F，然后使用二分查找找到F 故摔碎的次数为 lgF级别
   */
  /**
   * 1.4.25 只有两个鸡蛋 如何求出F 表示最多只能摔碎两次。 解法一：可以将这个问题转化 ，2个鸡蛋测试k次最多能测试多少层 因为只能测试 k 次，因此我们第一个鸡蛋选择楼层的时候要慎重，
   * 设第一次选择的m层 如果第一次扔鸡蛋碎了，那么只能从最底层开始轮询，则最坏的情况下需要测试 m -1次才能测出来，所以为了确保能在k次完成，第一次的选择必然是k层
   * 如果鸡蛋没碎，此时还剩下k-1次测试机会，所以选择 k+(k-1)层 因此最后的方程应该是，楼层总数h = k+(k-1)+(k-2)+···+1;
   * 所以只需要能判断的楼层高度h大于N,就能一定能在k次测出，(1+k)*k/2>N，所以可以得出测出N层楼的最大次数 测100层最多需要14次 14+13+12+...+1
   *
   * <p>解法二：看做判断2个鸡蛋要测到N层最多需要多少次？以N为标准，从最底层开始测。 n从1开始递增，每次都测n的平方 则 n = sqrt(k)+1
   * 则要测到N层最坏的情况，先测n次，鸡蛋摔碎，然后从 (n-1)^2层开始一层层测到n^2 总次数：n+n^2-(n-1)^2 约等于 3n 总次数约等于 3sqrt(F)次
   */

  // ===============================================================================================

  /**
   * 2.3.22 快速三向切分 lo--p--i--j--q--hi 使[lo, p-1]和[q+1, hi]都等于pivot，[p, i-1]都小于pivot，[j+1,
   * q]都大于pivot，[i, j]是未排序的数组 排序完成后再将[lo, p-1]和[q+1, hi]的元素换到中间
   *
   * <p>优势是交换次数会少于普通的三向切分快排，因为可预见的是大多数情况下等于pivot的区间应该远小于小于和大于的区间，
   * 故普通的三向切分快排中等于pivot的区间需要一直右移，可知会有很多次交换，而快速三向切分克服了这个问题。
   */
  public static void fast3DQuickSort(Comparable[] arr) {
    fast3DQuickSort(arr, 0, arr.length - 1);
  }

  private static void fast3DQuickSort(Comparable[] arr, int lo, int hi) {
    if (hi <= lo) {
      return;
    }
    // 数组小于某个大小使用插入排序
    // 切分元素选择
    // 以上优化忽略
    int i = lo, j = hi + 1;
    int p = lo, q = hi + 1;
    Comparable pivot = arr[lo];
    while (true) {
      // 从左边开始找到第一个大于等于pivot的数
      while (arr[++i].compareTo(pivot) < 0) {
        if (i == hi) {
          break;
        }
      }
      // 从右边开始找到第一个小于等于pivot的数
      while (arr[--j].compareTo(pivot) > 0) {
        if (j == lo) {
          break;
        }
      }
      // 相遇有两种情况，在中间相遇，肯定等于pivot，第二种情况在hi处相遇，此时不一定等于。
      // 如果i j 相遇，且等于pivot，则交换到等于pivot的区间
      if (i == j && arr[i].compareTo(pivot) == 0) {
        ArrayUtils.swap(arr, ++p, i);
      }
      if (i >= j) {
        break;
      }
      // 和普通的partiion方法一样，交换i j
      ArrayUtils.swap(arr, i, j);
      if (arr[i].compareTo(pivot) == 0) {
        ArrayUtils.swap(arr, ++p, i);
      }
      if (arr[j].compareTo(pivot) == 0) {
        ArrayUtils.swap(arr, --q, j);
      }
    }
    // 交换等于的区间到中间
    // 循环终止时，j处的数必定小于pivot，因为如果等于pivot会和小于pivot的数交换
    // 故此时(p, j]区间小于pivot [j+1, q)区间大于pivot
    i = j + 1;
    for (int k = lo; k <= p; k++) {
      ArrayUtils.swap(arr, k, j--);
    }
    for (int k = hi; k >= q; k--) {
      ArrayUtils.swap(arr, k, i++);
    }
    fast3DQuickSort(arr, lo, j);
    fast3DQuickSort(arr, i, hi);
  }

  // ===============================================================================================

  /** 稀疏向量，使用散列表数组来存储稀疏向量数组，只存储非0的索引和值，使用乘法运算时只需要计算非0的元素。 */

  // ===============================================================================================

}
