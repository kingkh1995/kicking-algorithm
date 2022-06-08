package com.kkk.hot100;

import com.kkk.supports.ArrayUtils;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * 数组和矩阵 <br>
 *
 * @author KaiKoo
 */
public class ArrayAndMatrixHot {

  /**
   * 48. 旋转图像 <br>
   * 先沿着对角线对折，然后上下对折，即能将矩阵顺时针旋转90度。
   */
  public void rotate(int[][] matrix) {
    int n = matrix.length;
    // 先沿着对角线对折，[i][j]与[n-1-j][n-1-i]交换
    for (int i = 0; i < n; ++i) {
      for (int j = 0; j < n - i; ++j) {
        int temp = matrix[i][j];
        matrix[i][j] = matrix[n - 1 - j][n - 1 - i];
        matrix[n - 1 - j][n - 1 - i] = temp;
      }
    }
    // 再沿着中线对折，[i][j]与[n-1-i][j]交换
    for (int i = 0; i < n >> 1; ++i) {
      for (int j = 0; j < n; ++j) {
        int temp = matrix[i][j];
        matrix[i][j] = matrix[n - 1 - i][j];
        matrix[n - 1 - i][j] = temp;
      }
    }
  }

  /**
   * 56. 合并区间 <br>
   * 按区间左端点排序数组之后合并区间
   */
  public int[][] merge(int[][] intervals) {
    Arrays.sort(intervals, Comparator.comparingInt(i -> i[0]));
    LinkedList<int[]> ans = new LinkedList<>();
    int[] last;
    for (int[] i : intervals) {
      if (!ans.isEmpty() && (last = ans.peekLast())[1] >= i[0]) {
        last[1] = Math.max(last[1], i[1]);
      } else {
        ans.offer(i);
      }
    }
    return ans.toArray(new int[0][0]);
  }

  /**
   * 75. 颜色分类 <br>
   * 实际上就是三向切分快速排序，选择1作为切分，同时指针从0位置出发，只需要排序一次即可。
   */
  public void sortColors(int[] nums) {
    int lt = 0, i = 0, gt = nums.length - 1, cmp;
    while (i <= gt) { // 未排序区间为[i,gt]
      if ((cmp = nums[i] - 1) < 0) {
        ArrayUtils.swap(nums, lt++, i++);
      } else if (cmp > 0) {
        ArrayUtils.swap(nums, gt--, i);
      } else {
        i++;
      }
    }
  }

  // ===============================================================================================

  /**
   * 31. 下一个排列 <br>
   * 从右侧找到连续的非严格递减序列，将递减序列左边第一个元素交换到递减序列中，使得序列仍然是递减，然后反转递减序列即可。
   */
  public void nextPermutation(int[] nums) {
    // 确定出非严格递减序列的左侧
    int i = nums.length - 2; // 从倒数第二位开始，将单个元素视为递减序列。
    while (i >= 0 && nums[i] >= nums[i + 1]) {
      i--;
    }
    int j = nums.length - 1;
    // 如果需要交换，则从找到刚好大于待交换元素的元素。
    if (i >= 0) {
      while (j > i && nums[j] <= nums[i]) {
        j--;
      }
      ArrayUtils.swap(nums, i, j);
    }
    // 反转递减序列，如果i=-1则会重置。
    ArrayUtils.reverse(nums, i + 1, nums.length - 1);
  }
}
