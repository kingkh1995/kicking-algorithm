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
}
