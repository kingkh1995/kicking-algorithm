package com.kkk.leetcode;

import java.util.Arrays;

/**
 * 位运算 <br>
 * &：与，两个位都为1才为1；|：或，两个位都为0才为0；^：异或，两个位不同才为1；~：所有位取反。<br>
 * 算术优先级：普通 > 左移和右移 > 位运算 <br>
 * 149
 *
 * @author KaiKoo
 */
public class BitOperationAndNumberExx {

  /**
   * 268. 丢失的数字 <br>
   * 将0-n添加到数组，转化为所有数字出现了两次只有一个数字出现了一次问题。
   */
  public int missingNumber(int[] nums) {
    int ans = 0;
    for (int n = 0; n <= nums.length; n++) {
      ans ^= n;
    }
    for (int n : nums) {
      ans ^= n;
    }
    return ans;
  }

  /**
   * 371. 两整数之和 <br>
   * 【模拟二进制加法】，使用^将所有不同位变为1，即相加后不需要进位的位，使用&找出要进位的位再左移1位，<br>
   * 即使同为1的位相加后进位的结果，然后再将两者相加，一直计算直到不再需要进位则是最终结果。
   */
  public int getSum(int a, int b) {
    while (b != 0) {
      int t = a ^ b;
      b = (a & b) << 1;
      a = t;
    }
    return a;
  }

  // ===============================================================================================

  /**
   * 190. 颠倒二进制位 <br>
   * 使用分治的思想，依次每2、4、8、16位左右交换。
   */
  public int reverseBits(int n) {
    n = n >>> 1 & 0x55555555 | (n & 0x55555555) << 1; // 0101 0101 0101 0101 0101 0101 0101 0101
    n = n >>> 2 & 0x33333333 | (n & 0x33333333) << 2; // 0011 0011 0011 0011 0011 0011 0011 0011
    n = n >>> 4 & 0x0f0f0f0f | (n & 0x0f0f0f0f) << 4; // 0000 1111 0000 1111 0000 1111 0000 1111
    n = n >>> 8 & 0x00ff00ff | (n & 0x00ff00ff) << 8; // 0000 0000 1111 1111 0000 0000 1111 1111
    return n >>> 16 | n << 16;
  }

  // ===============================================================================================
  /** 拔高题 */

  // 返回小于n的质数的数量
  public int countPrimes(int n) {
    // 标记数组，初始化为默认质数。
    boolean[] isPrime = new boolean[n];
    Arrays.fill(isPrime, true);
    int ans = 0;
    for (int i = 2; i < n; ++i) {
      if (isPrime[i]) {
        ans += 1;
        // 如果当前是质数，则将该质数所有的倍数均标记为非质数
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
}
