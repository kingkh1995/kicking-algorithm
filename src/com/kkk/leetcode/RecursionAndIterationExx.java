package com.kkk.leetcode;

import java.util.Arrays;

/**
 * 递归和迭代 <br>
 *
 * @author KaiKoo
 */
public class RecursionAndIterationExx {

  /**
   * 50. Pow(x, n) <br>
   * 不断将n二分，使用递归或迭代解法。
   */
  class myPowSolution {
    public double myPow(double x, int n) {
      return n >= 0 ? pow(x, n) : 1D / pow(x, -(long) n); // 处理-2^31
    }

    private double pow(double x, long n) { // 递归解法
      if (n == 0) {
        return 1D;
      }
      double ans = pow(x, n / 2);
      ans *= ans;
      return (n & 1) == 1 ? ans * x : ans;
    }

    private double pow0(double x, long n) { // 迭代解法
      double ans = 1, temp = x; // temp为x^(2^i)的结果
      while (n > 0) {
        if ((n & 1) == 1) {
          ans *= temp;
        }
        temp *= temp;
        n >>= 1;
      }
      return ans;
    }
  }

  /** 204. 计数质数 <br> */
  public int countPrimes(int n) {
    // 标记数组，初始化为默认质数。
    boolean[] isPrime = new boolean[n];
    Arrays.fill(isPrime, true);
    int ans = 0;
    for (int i = 2; i < n; ++i) {
      if (isPrime[i]) { // 如果当前是质数，则将该质数所有的倍数均标记为非质数
        ans += 1; // 计数增加
        // 直接从i*i开始，因为2i、3i至i*i在前面的循环中肯定已经被标记过了
        if ((long) i * i < n) { // 需要注意数字溢出的问题
          for (int j = i * i; j < n; j += i) {
            isPrime[j] = false;
          }
        }
      }
    }
    return ans;
  }

  /**
   * 724. 寻找数组的中心下标 <br>
   * 【前缀和】，遍历即可，过程中统计左边区间的总和。
   */
  public int pivotIndex(int[] nums) {
    int total = 0;
    for (int n : nums) {
      total += n;
    }
    for (int sum = 0, i = 0; i < nums.length; ++i) {
      if (sum + sum + nums[i] == total) {
        return i;
      }
      sum += nums[i];
    }
    return -1;
  }

  /**
   * 807. 保持城市天际线 <br>
   * 暴力解法，先遍历统计出每行和每列的最大高度，再遍历计算即可。
   */
  public int maxIncreaseKeepingSkyline(int[][] grid) {
    int m = grid.length, n = grid[0].length, ans = 0;
    int[] rowMax = new int[m], columnMax = new int[n];
    for (int i = 0; i < m; ++i) {
      for (int j = 0; j < n; ++j) {
        rowMax[i] = Math.max(rowMax[i], grid[i][j]);
        columnMax[j] = Math.max(columnMax[j], grid[i][j]);
      }
    }
    for (int i = 0; i < m; ++i) {
      for (int j = 0; j < n; ++j) {
        ans += Math.min(rowMax[i], columnMax[j]) - grid[i][j];
      }
    }
    return ans;
  }

  // ===============================================================================================

  /**
   * 172. 阶乘后的零 <br>
   * 即 N! 有多少个质因数 5
   */
  public int trailingZeroes(int n) {
    if (n < 5) {
      return 0;
    }
    // n/5到n之间数的乘积，可能导致后面出现0的个数等于n/5。
    return n / 5 + trailingZeroes(n / 5);
  }
}
