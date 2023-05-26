package com.kkk.leetcode;

/**
 * 贪心算法 <br>
 * 只需要考虑局部的最优解即可，且局部之间互不影响，则可由局部最优解推出整体最优解。
 *
 * @author KaiKoo
 */
public class GreedyExx {

  /**
   * 122. 买卖股票的最佳时机 II <br>
   * 因为每天都可以买入和卖出，则可以使用贪心算法，每天都尝试操作。
   */
  public int maxProfit(int[] prices) {
    int n = prices.length, ans = 0;
    for (int i = 1; i < n; ++i) {
      ans += Math.max(0, prices[i] - prices[i - 1]); // 只要获得正收益则操作
    }
    return ans;
  }

  // ===============================================================================================

  /**
   * 334. 递增的三元子序列 <br>
   * 【维护前两个数】，尝试一直将first和second变小，一旦遇到大于second的数则表示存在。<br>
   */
  public boolean increasingTriplet(int[] nums) {
    if (nums.length < 3) {
      return false;
    }
    int first = nums[0], second = Integer.MAX_VALUE;
    for (int i = 1, n; i < nums.length; ++i) {
      if ((n = nums[i]) > second) {
        return true;
      } else if (n > first) { // 更新second结论依旧成立，因为之前肯定是存在一个比它小的数，不然它就会是first了。
        second = n;
      } else { // 更新first仍然成立，因为second没变，按second的证明故结论成立。
        first = n;
      }
    }
    return false;
  }
}
