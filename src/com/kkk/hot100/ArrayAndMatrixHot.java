package com.kkk.hot100;

import com.kkk.supports.ArrayUtils;

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
   * 53.最大子数组和 <br>
   * 可以使用动态规划，但没必要，只需要使用一个变量即可。
   */
  public int maxSubArray(int[] nums) {
    int ans = nums[0], dp = nums[0];
    for (int i = 1; i < nums.length; ++i) {
      ans = Math.max(ans, dp = nums[i] + Math.max(dp, 0));
    }
    return ans;
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
