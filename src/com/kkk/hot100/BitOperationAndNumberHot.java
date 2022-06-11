package com.kkk.hot100;

/**
 * 位运算和数字 <br>
 *
 * @author KaiKoo
 */
public class BitOperationAndNumberHot {

  /** 136. 只出现一次的数字 <br> */
  public int singleNumber(int[] nums) {
    int ans = 0;
    for (int n : nums) { // 所有数字异或操作
      ans ^= n;
    }
    return ans;
  }

  // ===============================================================================================

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
