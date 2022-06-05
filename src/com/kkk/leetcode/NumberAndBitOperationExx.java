package com.kkk.leetcode;

import java.util.Arrays;

/**
 * &：与，两个位都为1才为1、|：或，两个位都为0才为0，^：异或，两个位不同才为1，~：所有位取反。<br>
 * 左移和右移符号算术优先级更高 <br>
 * 二进制位运算 <br>
 * 149
 *
 * @author KaiKoo
 */
public class NumberAndBitOperationExx {

  // ===============================================================================================
  /** 基础题 */

  // 阶乘后的零，使用尾调递归
  public int trailingZeroes(int n) {
    if (n < 5) {
      return 0;
    }
    // n/5到n之间数的乘积，可能导致后面出现0的个数等于n/5。
    return n / 5 + trailingZeroes(n / 5);
  }

  // 颠倒无符号int的二进制位
  public static class reverseBitsSolution {

    static final int M1 = 0x55555555; // 0101 0101 0101 0101 ...
    static final int M2 = 0x33333335; // 0011 0011 0011 0011 ...
    static final int M4 = 0x0f0f0f0f; // 0000 1111 0000 1111 ...
    static final int M8 = 0x00ff00ff; // 0000 0000 1111 1111 ...

    public int reverseBits(int n) {
      // 使用分治的思想，依次每2、4、8、16位左右交换。
      n = n >>> 1 & M1 | (n & M1) << 1;
      n = n >>> 2 & M2 | (n & M2) << 2;
      n = n >>> 4 & M4 | (n & M4) << 4;
      n = n >>> 8 & M8 | (n & M8) << 8;
      return n >>> 16 | n << 16;
    }
  }

  // 缺失数字，包含[0, n]中n个数中缺失的那个数字
  public int missingNumber(int[] nums) {
    // 将0-n添加到数组，就变成了所有数字都出现了两次，只有一个数字出现一次，使用异或求解即可。
    int ans = 0;
    for (int n = 0; n <= nums.length; n++) {
      ans ^= n;
    }
    for (int n : nums) {
      ans ^= n;
    }
    return ans;
  }

  // ===============================================================================================
  /** 拔高题 */

  // n+1大小的数组，元素在[1,n]之间，只有一个元素重复出现了多次 求出该元素
  public static int findDuplicate(int[] nums) {
    int position = 0;
    int n = nums.length - 1;
    // 先判断二进制下有多少位
    while (n != 0) {
      n >>= 1;
      position++;
    }
    n = nums.length - 1;
    int t = 1;
    int ans = 0;
    for (int i = 0; i < position; i++) {
      // 统计当前位下1-n加起来总计有多个1
      int count = 0;
      for (int j = 1; j <= n; j++) {
        if ((j & t) == t) {
          count++;
        }
      }
      // 统计数组中所有元素当前位1的个数 如果超过count 则表示重复的数当前位为1
      // 因为1-n所有的元素并不是都存在，即用重复元素替换了缺失的元素去进行统计，但是结论仍然成立
      // 1、重复元素当前位为1、被替换的元素也为1即完美替换无影响、不为1则会使1的总数增加，判定重复元素当前位为1，故不影响。
      // 2、重复元素当前位为0、被替换的元素也为0即完美替换无影响、不为0则会使1的总数减小，即判断重复元素当前位不为1，不影响。
      for (int j : nums) {
        if ((j & t) == t) {
          count--;
        }
        // 如果超过了 表示重复元素
        if (count < 0) {
          ans += t;
          break;
        }
      }
      t <<= 1;
    }
    return ans;
  }

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
