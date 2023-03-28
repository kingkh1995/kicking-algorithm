package com.kkk.hot;

import java.util.ArrayList;
import java.util.List;

/**
 * 位运算 & 数学模拟 <br>
 *
 * @author KaiKoo
 */
public class BitOperationAndMathHot {

  /** 7. 整数反转 <br> */
  public int reverse(int x) {
    int rev = 0;
    while (x != 0) {
      if (rev < Integer.MIN_VALUE / 10 || rev > Integer.MAX_VALUE / 10) { // 判断防止越界
        return 0;
      }
      rev = rev * 10 + x % 10;
      x = x / 10;
    }
    return rev;
  }

  /**
   * 9. 回文数 <br>
   * 类似【7题】，将整数反转一半后即可，如位数为偶数直接对比，如为奇数需要抹去最后一位。
   */
  public boolean isPalindrome(int x) {
    if (x < 0 || (x % 10 == 0 && x != 0)) { // 需要先处理特殊情况【最后一位为0】，否则后面会判断为true。
      return false;
    }
    int rev = 0;
    while (x > rev) { // 终止条件
      rev = rev * 10 + x % 10;
      x = x / 10;
    }
    return x == rev || x == rev / 10;
  }

  /** 136. 只出现一次的数字 <br> */
  public int singleNumber(int[] nums) {
    int ans = 0;
    for (int n : nums) { // 所有数字异或操作
      ans ^= n;
    }
    return ans;
  }

  /**
   * 231. 2 的幂 <br>
   * 即二进制表示只有一位是1，可以直接使用Integer.bitCount()。
   */
  public boolean isPowerOfTwo(int n) {
    return n > 0 && ((n - 1) ^ n) == 0;
  }

  /**
   * 338. 比特位计数 <br>
   * 计算每一个数二进制表示中1的个数有两种方式：不断使用(n - 1) & n运算将最低位的1变为0、使用掩码判断每一位上的1。<br>
   * 因为需要求1-n所有数字的比特位计数，故可以使用动态规划优化，关键是如何将小的数的状态推导到大的数。 <br>
   * 解法一：dp[i] = dp[(i-1)&i] + 1，将最右边一位变为0后加上一个1； <br>
   * 解法二：dp[i] = dp[i>>>1] + (i & 1)，二进制右移一位，如果最后一位是1则加上一个1； <br>
   * 解法三：dp[i] = dp[i ^ mask] + 1，使用最高位为1的掩码去掉最高位的1，然后加上一个1即可。
   */
  public int[] countBits(int n) { // 解法三
    int[] dp = new int[n + 1];
    for (int i = 1, mask = 0; i <= n; ++i) {
      if (((i - 1) & i) == 0) { // 使用(n - 1) & n技巧去掉最低位的1，如果为0了则表示只有一位0，即为掩码。
        mask = i;
        dp[i] = 1;
      } else {
        dp[i] = dp[i ^ mask] + 1; // 使用异或运算去掉最高位的1
      }
    }
    return dp;
  }

  /** 461. 汉明距离 <br> */
  public int hammingDistance(int x, int y) {
    return Integer.bitCount(x ^ y); // 直接异或并统计1的个数即可
  }

  // ===============================================================================================

  /**
   * 29. 两数相除 <br>
   * 需要求出 Z * Y < X < (Z + 1) * Y <br>
   * 类似【50题】，Z可以拆分为2的幂的累加，因数为0或1。 <br>
   * 考虑到溢出的问题，需要全部转为负数，负数最小为-2^31，能被2整除。
   */
  public int divide(int dividend, int divisor) {
    if (dividend == Integer.MIN_VALUE) { // 被除数为最小值
      if (divisor == 1) {
        return Integer.MIN_VALUE;
      }
      if (divisor == -1) {
        return Integer.MAX_VALUE;
      }
    }
    if (divisor == Integer.MIN_VALUE) { // 除数为最小值的情况
      return dividend == Integer.MIN_VALUE ? 1 : 0;
    }
    if (dividend == 0) { // 考虑除数为0
      return 0;
    }
    boolean rev = false;
    if (dividend > 0) {
      dividend = -dividend;
      rev = !rev;
    }
    if (divisor > 0) {
      divisor = -divisor;
      rev = !rev;
    }
    List<Integer> list = new ArrayList<>(); // 收集所有小于 X 的 Y * 2^i 的结果，用于查找。
    list.add(divisor);
    for (int i = 0, n; (n = list.get(i)) >= dividend - n; ++i) { // 注意溢出，使用减法
      list.add(n + n);
    }
    int ans = 0;
    for (int i = list.size() - 1; i >= 0; --i) { // 从最大值开始，判断因子是0还是1。
      if (list.get(i) >= dividend) {
        ans += 1 << i; // 当前因子为 2^i
        dividend -= list.get(i); // 减去
      }
    }
    return rev ? -ans : ans;
  }

  /**
   * 287. 寻找重复数 <br>
   * 二进制按位还原重复数，统计[1,n]区间每一位上的1的总数，因为相当于是使用重复数字替换了某个[1,n]区间的数字，<br>
   * 则数组所有数字在该位上1的总数如果变多了，则表示重复数字该位上就是1。 <br>
   * 1、重复元素当前位为1、被替换的元素也为1即完美替换无影响、不为1则会使1的总数增加，判定重复元素当前位为1，结论成立。
   * 2、重复元素当前位为0、被替换的元素也为0即完美替换无影响、不为0则会使1的总数减小，即判断重复元素当前位不为1，结论成立。
   */
  public static int findDuplicate(int[] nums) {
    int ans = 0, n = nums.length - 1, bits = 32 - Integer.numberOfLeadingZeros(n); // n的二进制下有效位数
    for (int i = 0, mask = 1; i < bits; i++) { // 按位还原重复数
      int count = 0; // 统计[1,n]区间在当前位的1的总数
      for (int j = 1; j <= n; j++) {
        if ((j & mask) == mask) {
          count++;
        }
      }
      for (int j : nums) { // 减去数组中所有元素在当前位的1的个数
        if ((j & mask) == mask && --count < 0) { // 1的总数增加了可以确认数字当前位为1
          ans += mask;
          break;
        }
      }
      mask <<= 1;
    }
    return ans;
  }
}
