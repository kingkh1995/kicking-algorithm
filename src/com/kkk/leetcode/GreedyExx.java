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
   * 134. 加油站 <br>
   * 只需要一次遍历即可，统计出每站之间的实际油耗，遍历累加值不能小于0，【注意指针只需要前进不用后退】。
   */
  public int canCompleteCircuit(int[] gas, int[] cost) {
    int n = gas.length;
    for (int i = 0; i < n; ++i) {
      gas[i] -= cost[i];
    }
    for (int i = 0; i < n; ++i) {
      int t = i;
      for (int sum = 0; i < t + n; ++i) {
        if ((sum += gas[i % n]) < 0) {
          break;
        }
      }
      if (i == t + n) {
        return t;
      }
    }
    return -1;
  }

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
