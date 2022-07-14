package com.kkk.leetcode;

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
}
