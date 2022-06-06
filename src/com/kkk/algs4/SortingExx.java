package com.kkk.algs4;

import com.kkk.supports.ArrayUtils;
import com.kkk.supports.ListNode;
import com.kkk.supports.NodeUtils;
import com.kkk.supports.Queue;
import com.kkk.supports.Stack;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Consumer;

/**
 * 第二章 排序
 *
 * @author KaiKoo
 */
public class SortingExx {

  // ===============================================================================================

  /** 2.1.14 出列顺序，将一副扑克牌排序，每次只能查看或交换最上面的两张牌或将最上面的牌放到最下面 */
  /**
   * 1、一直查看最上面的两张牌，将小牌放到最下面，直到找到最大的牌和第二大的牌 <br>
   * 2、将第二大的牌和最大的牌放在最下面，使他们有序的连在一起 <br>
   * 3、继续通过以上方法找出第三大的牌和第二大的牌 <br>
   * 4、可以知道最大的牌就跟在第二大的牌后面，同样将它们都放到最下面，使前三张牌有序的连在一起 <br>
   * 5、一直重复，并按顺序使所有的牌全部排序并连在一起，相当于组成了一个环
   */
  /** 拓展：队列排序（且无重复数据） 相当于选择排序 */
  private static void sortQueue(Queue queue) {
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
    // 上一个找出的小值
    int first = min;
    // 当前要找的小值
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
      } while (queue.peek() != first); // 每次选择终止条件是碰到上一个比它小的值
      queue.enqueue(queue.dequeue());
      queue.enqueue(second);
      first = second;
    }
  }

  public static void sortQueueTest() {
    int[] arr = ArrayUtils.distinctArr(300, 1, 1000);
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

  // ===============================================================================================

  /** 2.2.10 快速归并 不用再判断两边数组是否用尽 */
  private static void merge(int[] arr, int[] aux, int lo, int mid, int hi) {
    // 左边升序
    for (int i = lo; i <= mid; i++) {
      aux[i] = arr[i];
    }
    // 右边降序
    for (int i = mid + 1; i <= hi; i++) {
      aux[i] = arr[hi + mid + 1 - i];
    }
    // 得到一个双调数组
    // 双指针从两端往中间靠近
    int i = lo, j = hi;
    for (int k = lo; k <= hi; k++) {
      if (aux[i] < aux[j]) {
        arr[k] = aux[i++];
      } else {
        arr[k] = aux[j--];
      }
    }
  }

  // ===============================================================================================

  /** 2.2.12 次线性的额外空间 将大小为N的数组分为M大小的N/M块，使用归并方法，使所需额外空间减少到O(M)级别 */
  /**
   * 1、将数组按M大小分块，每块使用选择排序或插入排序 2、第一次归并第一个子数组和第二个子数组 3、之后的循环每次归并前面所有归并完的数组和下一个未归并的子数组 <br>
   * 归并方法是： <br>
   * 将右边的M大小的子数组复制到M大小的辅助数组中，然后从两个数组右端开始归并，将大值放到右边数组原区间的右边，直到右边数组全部归并完成即可，
   * 因为左右数组都是排序的，所以左边数组剩余的部分也是有序的且全部小于右边数组 可知该算法的辅助数组大小永远等于M
   */

  // ===============================================================================================

  /**
   * 2.2.16 自然的归并排序 编写一个自底向上的归并排序，能够利用数组中有序的部分，连续归并两个递增的区块 <br>
   * 迭代实现，每轮循环的都是顺序寻找连续的两个有序子数组两两归并，直到一趟循环只剩两个有序子数组，归并完即排序完成。
   */
  private static void naturalSort(int[] arr) {
    if (arr.length <= 1) {
      return;
    }
    int[] aux = new int[arr.length];
    int lo = 0;
    int hi = arr.length - 1;
    while (true) {
      int i = lo, j = lo, k = lo;
      while (true) {
        // 找到第一个递增的区块
        j = findBlock(arr, j);
        if (j == hi) { // 到达最右端则退出该次循环
          break;
        }
        // 找到第二个递增的区块
        k = findBlock(arr, j + 1);
        // 找到第二个区块后归并两个区间
        merge(arr, aux, i, j, k);
        if (k == hi) { // 到达最右端则退出该次循环
          break;
        }
        // 循环未结束则移动指针
        i = j = k = k + 1;
      }
      // 每趟循环结束后判断数组是否已排序 完成
      if (i == lo) {
        break;
      }
    }
  }

  private static int findBlock(int[] arr, int start) {
    int i = start;
    for (; i < arr.length - 1; i++) {
      if (arr[i] > arr[i + 1]) {
        break;
      }
    }
    return i;
  }

  public static void naturalSortTest() {
    int[] arr1 = ArrayUtils.randomArr(200, 1, 100);
    SortingExx.naturalSort(arr1);
    System.out.println(ArrayUtils.isSorted(arr1));
    int[] arr2 = ArrayUtils.randomArr(200, 1, 1000);
    SortingExx.naturalSort(arr2);
    System.out.println(ArrayUtils.isSorted(arr2));
  }

  // ===============================================================================================

  /** 2.2.17 链表排序 使用自然归并排序的方式排序，这是链表最佳的排序方法 */
  private static ListNode linkedNodeSort(ListNode node) {
    // 添加一个虚拟头结点
    ListNode head = new ListNode();
    head.next = node;
    ListNode tempHead = new ListNode(); // 备用虚拟结点
    ListNode first, prev;
    while (true) {
      ListNode block1Tail, block2Tail;
      first = block1Tail = block2Tail = prev = head;
      while (true) {
        block1Tail = findBlock(first.next);
        if (block1Tail.next == null) { // 判断是否循环结束
          break;
        }
        block2Tail = findBlock(block1Tail.next);
        // merge前把链表拆开
        ListNode second = block1Tail.next; // 第二个归并链表的头结点
        ListNode next = block2Tail.next; // 剩下未排序链表的头结点
        block1Tail.next = null;
        block2Tail.next = null;
        prev = merge(prev, first, second, next); // 归并完设置prev
        if (next == null) { // 判断是否循环结束
          break;
        }
        // 循环未结束则赋值
        first = tempHead; // 设置为备用虚拟结点
        tempHead.next = next;
      }
      // 判断是否排序完成
      if (first == head && (block1Tail.next == null || block2Tail.next == null)) {
        break;
      }
    }
    return head.next;
  }

  //

  /**
   * @param prev 前一端归并链表的尾结点 第一趟循环时则为虚拟头结点
   * @param first 第一个结点是备份虚拟头结点 固定
   * @param second 下一段链表的头结点
   * @param next 剩余未归并链表的头结点
   */
  private static ListNode merge(ListNode prev, ListNode first, ListNode second, ListNode next) {
    ListNode head = first; // 保留虚拟头结点 作为归并后链表的头
    ListNode node = head;
    first = first.next;
    while (true) {
      if (first == null) {
        node.next = second;
        break;
      } else if (second == null) {
        node.next = first;
        break;
      } else if (first.val < second.val) {
        node.next = first;
        first = first.next;
        node = node.next;
      } else {
        node.next = second;
        second = second.next;
        node = node.next;
      }
    }
    // 归并完后的处理 与前后的链表相连
    // 将头连到前面的链表尾结点
    prev.next = head.next;
    // 将链表尾结点连到剩余结点的头结点
    if (next != null) {
      while (node.next != null) {
        node = node.next;
      }
      node.next = next;
    }
    return node; // 返回归并后链表的最后一个结点
  }

  private static ListNode findBlock(ListNode node) {
    if (node == null) {
      return null;
    }
    ListNode next = node.next;
    while (next != null) {
      if (node.val > next.val) {
        return node;
      }
      node = next;
      next = node.next;
    }
    return node;
  }

  public static void linkedNodeSortTest() {
    int[] ints = ArrayUtils.randomArr(100000, 1, 8000);
    ListNode node = NodeUtils.arr2LinkedList(ints);
    node = linkedNodeSort(node);
    var list = new ArrayList<Integer>(100000);
    while (node != null) {
      list.add(node.val);
      node = node.next;
    }
    int[] array = list.stream().mapToInt(i -> i).toArray();
    Arrays.sort(ints);
    System.out.println(Arrays.equals(ints, array));
  }

  // ===============================================================================================

  /** 2.3.15 螺丝和螺母 N个螺丝和N个螺母，快速将其配对，不能直接比较螺丝和螺丝，螺母和螺母 */
  /**
   * 使用快速排序 先分为螺丝组和螺母组，将两组排序后，即可按顺序匹配。 1、选螺丝组第一个螺丝为pivot，从螺母中找到该螺丝匹配的螺母，将其换到第一位上
   * 2、使用快速排序排序两个组，不过每次比对是螺丝组使用螺母组的pivot，螺母组使用螺丝组的pivot
   */

  // ===============================================================================================

  /** 2.3.17 快速排序的哨兵，省略掉循环内部的边界检查 使用一次冒泡排序将最大的元素排到最右边，这样它可以作为右边的哨兵 */
  private static void sentinelQuickSort(int[] arr) {
    // 先将最大的元素排到最右边
    for (int i = 0; i < arr.length - 1; i++) {
      if (arr[i] > arr[i + 1]) {
        ArrayUtils.swap(arr, i, i + 1);
      }
    }
    // 只需要排到length-2位即可
    sentinelQuickSort(arr, 0, arr.length - 2);
  }

  // 与原sort一致
  private static void sentinelQuickSort(int[] arr, int lo, int hi) {
    if (hi <= lo) {
      return;
    }
    int j = sentinelPartition(arr, lo, hi);
    sentinelQuickSort(arr, lo, j - 1);
    sentinelQuickSort(arr, j + 1, hi);
  }

  // 与原partition一致，只需要去掉循环内的if校验即可
  private static int sentinelPartition(int[] arr, int lo, int hi) {
    int pivot = arr[lo];
    // 左哨兵为lo 右哨兵为hi+1
    int i = lo, j = hi + 1;
    while (true) {
      // 从左边开始找到第一个大于等于pivot的元素
      // 循环必然会终止，因为右哨兵一定大于区间内所有的值
      while (arr[++i] < pivot) {}
      // 从右边开始找到第一个小于等于pivot的元素
      // 循环必然会终止，因为pivot为lo的值
      while (arr[--j] > pivot) {}
      // 此处判断不放在循环终止条件中是因为最后一步是j和pivot交换位置
      if (i >= j) {
        break;
      }
      ArrayUtils.swap(arr, i, j);
    }
    // 因为默认pivot是lo，所以最后一步是j和pivot交换位置
    ArrayUtils.swap(arr, lo, j);
    return j;
  }

  public static void sentinelQuickSortTest() {
    int[] arr = ArrayUtils.distinctArr(1000, 1, 2000);
    sentinelQuickSort(arr);
    System.out.println(ArrayUtils.isSorted(arr));
  }

  // ===============================================================================================

  /** 2.3.20 非递归的快速排序 用一个栈保存每次切分后的数组坐标 */
  private static void unrecursionQuickSort(int[] arr) {
    Stack stack = new Stack();
    // 初始入栈，先入lo 再入hi
    stack.push(0);
    stack.push(arr.length - 1);
    while (!stack.isEmpty()) {
      // 出栈则先出hi再出lo
      int hi = stack.pop();
      int lo = stack.pop();
      if (hi <= lo) {
        continue;
      }
      // 切分
      int j = partition(arr, lo, hi);
      // 将大的数组先入栈，即先排小的数组
      if (hi - j > j - lo) {
        stack.push(j + 1);
        stack.push(hi);
        stack.push(lo);
        stack.push(j - 1);
      } else {
        stack.push(lo);
        stack.push(j - 1);
        stack.push(j + 1);
        stack.push(hi);
      }
    }
  }

  // 快速排序原partition方法
  private static int partition(int[] arr, int lo, int hi) {
    int pivot = arr[lo];
    int i = lo, j = hi + 1;
    while (true) {
      // 从左边开始找到第一个大于等于pivot的元素
      while (arr[++i] < pivot) {
        if (i == hi) {
          break;
        }
      }
      // 从右边开始找到第一个小于等于pivot的元素
      while (arr[--j] > pivot) {}
      if (i >= j) {
        break;
      }
      ArrayUtils.swap(arr, i, j);
    }
    ArrayUtils.swap(arr, lo, j);
    return j;
  }

  public static void unrecursionQuickSortTest() {
    int[] arr = ArrayUtils.distinctArr(1000, 1, 2000);
    unrecursionQuickSort(arr);
    System.out.println(ArrayUtils.isSorted(arr));
  }

  // ===============================================================================================

  /**
   * 2.3.24 取样排序 取样大小为2^k-1 <br>
   * 先将取样的元素排序，再以中位数分割分为两个子数组移动到数组的左右两端， <br>
   * 则数组分为了四个部分：有序的取样数组、取样数组的中位数、无序的剩余数组、有序的取样数组。 <br>
   * 使用中位数作pivot，对未取样的剩余元素做快速排序进行切分，切分完成后，接下来我们再对第一个部分取半，放到中位数之前； <br>
   * 对最后一部分取半，放到中位数之后 则此时中位数左右的数组都被分为了上一步一样的四部分，重复以上过程，直至完全排序
   */

  // ===============================================================================================

  /** 2.4.26 无需交换的堆 类似于插入排序的优化解法，使用半交换方式，每次只移动被下沉或上浮的值，找到位置后再赋值 */

  // ===============================================================================================

  /**
   * 2.4.29 同时面向最大元素和最小元素的堆 使用一个最大堆和一个最小堆同时保存所有元素 但是相同元素之间有指针互相连接（保存下在另一个堆的位置的索引）
   * 插入时同时插入两个堆即可，插入完成后，保存下元素的位置 删除时一个堆正常删除，另一个堆通过指针找到元素的位置，并对该位置的子堆做一个堆删除操作
   */

  // ===============================================================================================

  /**
   * 2.4.31 快速插入 基于比较的方式，使得往堆中插入元素只需要~loglogN次比较
   * 修改swim方法，将父结点组成一条路径，可知该路径是有序的，再通过二分查找，找到元素在该路径中该插入的位置 然后插入该位置，并让其他元素沿着路径下移即swim完成。
   * 路径长度是~logN级别，可知加上二分查找后则是~loglogN级别
   */

  // ===============================================================================================

  /** 2.5.2 从一列单词输入中打印出所有由两个单词组成的组合词 类似于3-sum问题 */
  private static void printCombinedWords(String[] arr) {
    // 先按单词长度排序
    Arrays.sort(arr, Comparator.comparingInt(String::length));
    // 从第三位开始
    for (int n = 2; n < arr.length; n++) {
      // 从前面的区间里面查找 因为前面的字符串长度都小于当前字符串
      int i = 0, j = n - 1;
      int length = arr[n].length();
      while (i < j) {
        int com = arr[i].length() + arr[j].length() - length;
        if (com == 0) {
          if ((arr[i] + arr[j]).equals(arr[n]) || (arr[j] + arr[i]).equals(arr[n])) {
            System.out.println(arr[n]);
            break;
          }
          i++;
          j--;
        } else if (com > 0) {
          j--;
        } else {
          i++;
        }
      }
    }
  }

  // ===============================================================================================

  /** 2.5.13 负载均衡 接受一个整数M，读取一系列的任务和所需的运行时间，将任务分配给M个处理器，使得完成所有任务的总时间最少 */
  /**
   * 首先将所有任务按运行时间排序，然后为处理器构建一个M大小的最小堆，排序基于处理器接受到任务所需的总时间 <br>
   * 按顺序开始分配任务，每次从处理器堆顶取出当前任务总时间最小的处理器，分配当前任务，为处理器增加任务时间，再重新加入处理堆中，并恢复平衡 <br>
   * 任务全部分配完成后，遍历选择出执行任务总时间最大的处理器即可。
   */

  // ===============================================================================================

  /** 2.5.18 强制稳定 将任意一种排序方法变为稳定 */
  private static <T extends Comparable> int[] stabilize(T[] arr, Consumer<Wrapper<T>[]> sort) {
    Wrapper<T>[] wrappers = new Wrapper[arr.length];
    for (int i = 0; i < arr.length; i++) {
      wrappers[i] = new Wrapper(arr[i], i);
    }
    sort.accept(wrappers);
    for (int i = 1; i < wrappers.length; i++) {
      // 插入排序
      for (; i < wrappers.length && wrappers[i].v.compareTo(wrappers[i - 1].v) == 0; i++) {
        for (int j = i;
            j > 0
                && wrappers[j].v.compareTo(wrappers[j - 1].v) == 0
                && wrappers[j].index < wrappers[j - 1].index;
            j--) {
          Wrapper temp = wrappers[j - 1];
          wrappers[j - 1] = wrappers[j];
          wrappers[j] = temp;
        }
      }
    }
    int[] indexes = new int[arr.length];
    for (int i = 0; i < wrappers.length; i++) {
      arr[i] = wrappers[i].v;
      indexes[i] = wrappers[i].index;
    }
    return indexes;
  }

  private static class Wrapper<T extends Comparable> implements Comparable<Wrapper<T>> {

    T v;
    int index;

    public Wrapper(T v, int index) {
      this.v = v;
      this.index = index;
    }

    @Override
    public int compareTo(Wrapper<T> o) {
      return v.compareTo(o.v);
    }
  }

  public static void stabilizeTest() {
    Integer[] array =
        Arrays.stream(ArrayUtils.randomArr(50, 1, 10)).boxed().toList().toArray(new Integer[50]);
    int[] indexes = stabilize(array, EssentialExx::fast3DQuickSort);
    System.out.println(Arrays.toString(array));
    System.out.println(Arrays.toString(indexes));
  }

  // ===============================================================================================

}
