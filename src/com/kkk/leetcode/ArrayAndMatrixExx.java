package com.kkk.leetcode;

import com.kkk.supports.ArrayUtils;

/**
 * 数组和矩阵
 *
 * @author KaiKoo
 */
public class ArrayAndMatrixExx {

  // ===============================================================================================
  /** 基础题 */

  // 将0全部移到末位，原地稳定排序
  // 解法1：类似冒泡排序 为0则交换
  public void moveZeroes1(int[] nums) {
    boolean flag = true;
    for (int i = nums.length - 1; i > 0 && flag; i--) {
      flag = false;
      for (int j = 0; j < i; j++) {
        if (nums[j] == 0 && nums[j + 1] != 0) {
          int temp = nums[j + 1];
          nums[j + 1] = nums[j];
          nums[j] = temp;
          flag = true;
        }
      }
    }
  }

  // 解法2：遍历 如果不为0则移动到前端 然后后端全设为0
  public void moveZeroes2(int[] nums) {
    int index = 0;
    for (int i = 0; i < nums.length; i++) {
      if (nums[i] != 0) {
        nums[index++] = nums[i];
      }
    }
    while (index < nums.length) {
      nums[index++] = 0;
    }
  }

  // 给定一个有序的数组 删除重复的元素，将不重复的元素全部移到数组前端
  public int removeDuplicates(int[] nums) {
    if (nums.length <= 1) {
      return nums.length;
    }
    // 从第二个元素开始，如果不等于前一个元素则移到前端
    int index = 1;
    for (int i = 1; i < nums.length; i++) {
      if (nums[i] != nums[index - 1]) {
        nums[index++] = nums[i];
      }
    }
    return index;
  }

  // 给定一个有序的数组 删除重复的元素，使每个元素最多出现两次 将不重复的元素全部移到数组前端
  public int removeDuplicates2(int[] nums) {
    if (nums.length <= 2) {
      return nums.length;
    }
    // 从第三个元素开始 直接和前面第二个元素对比即可
    int index = 2;
    for (int i = 2; i < nums.length; i++) {
      if (nums[i] != nums[index - 2]) {
        nums[index++] = nums[i];
      }
    }
    return index;
  }

  // 数组只有0，1，2 进行原地排序  使用类似三向切分快速排序
  public void sortColors(int[] nums) {
    // 选择切分元素1 排序一次就能有序
    // (0,l)=0 [l,i)=1 [i,h]未排序 (h,tail)=2
    int l = 0, i = 0, h = nums.length - 1;
    while (i <= h) {
      if (nums[i] == 1) {
        i++;
      } else if (nums[i] == 0) {
        ArrayUtils.swap(nums, i, l);
        l++;
        i++;
      } else {
        ArrayUtils.swap(nums, i, h);
        h--;
      }
    }
  }

  // ===============================================================================================
  /** 拔高题 */

  // n+1大小的数组，元素在[1,n]之间，只有一个元素重复出现了多次，求出该值且不能修改数组
  // 转换为求链表的环，使用快慢指针法
  // 证明至少存在一个重复的数字：快慢指针相遇就能证明
  public int findDuplicate(int[] nums) {
    int slow = 0, fast = 0;
    do {
      slow = nums[slow];
      fast = nums[nums[fast]];
    } while (slow != fast);
    slow = 0;
    do {
      slow = nums[slow];
      fast = nums[fast];
    } while (slow != fast);
    return slow;
  }

  // 找到第k大的元素
  public static int findKthLargest(int[] arr, int k) {
    if (k < 1 || k > arr.length) {
      throw new IllegalArgumentException();
    }
    var lo = 0;
    var hi = arr.length - 1;
    var index = arr.length - k;
    while (true) {
      // 小于5个元素直接插入排序
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
      // 使用快速三向切分快排
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
