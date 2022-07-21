package com.kkk.hot;

import com.kkk.supports.ArrayUtils;

/**
 * 贪心 <br>
 * 【贪心属于迭代】
 *
 * @author KaiKoo
 */
public class GreedyHot {

  /**
   * 55. 跳跃游戏 <br>
   * 贪心算法，遍历数组，记录下可以达到的最远距离max，因为从0位置到max的所有位置都一定可达，判断max能否到达最后一个位置。
   */
  public boolean canJump(int[] nums) {
    for (int i = 0, max = nums[0]; i <= max; ++i) {
      if (max >= nums.length - 1) {
        return true;
      }
      max = Math.max(max, i + nums[i]); // 遍历过程中更新可达的最远位置
    }
    return false; // 如果最终循环结束表示最后一个位置不可达
  }

  // ===============================================================================================

  /**
   * 300. 最长递增子序列 <br>
   * 最优解法：贪心算法+二分查找，只考虑固定长度下最小的递增子序列，同时使用二分查找优化算法效率。<br>
   * 维护一个严格单调递增的数组，aux[i]为所有长度为i+1的递增子序列中最小的结尾元素。 <br>
   * 遍历并更新数组，由贪心算法可证明该数组的单调性，因为【长度为i+1的递增子序列必然包含长度为i的递增子序列】；<br>
   * 使用二分查找确定出每个元素应该插入该辅助数组的位置rank，元素大于数组rank之前的元素，小于等于rank及其之后的元素。<br>
   * 如何更新数组？添加当前元素后，长度为l且结尾元素最小的递增子序列的情况分为以下：
   * 1、l小于rank，可以使用当前元素作为序列结尾元素使长度加一，但无法更新aux[l]，因为当前元素大于aux[l]；<br>
   * 2、l等于rank，可以更新aux[l]也就是aux[rank]，因为当前元素小于等于aux[rank]； <br>
   * 3、l大于rank，不可以将当前元素作为序列结尾；<br>
   * 4、元素大于辅助数组内所有元素，则新增aux[rank]，即最长递增子序列的长度加一。
   */
  public int lengthOfLIS(int[] nums) {
    int len = 1; // 最长递增子序列的长度
    int[] aux = new int[nums.length];
    aux[0] = nums[0];
    for (int i = 1; i < nums.length; ++i) {
      int rank = ArrayUtils.rank(aux, nums[i], 0, len - 1); // rank==len或nums[i]<=aux[rank]
      aux[rank] = nums[i];
      if (rank == len) { // 最大长度加一
        len++;
      }
    }
    return len;
  }

  /**
   * 621. 任务调度器 <br>
   * 贪心算法，永远都是考虑执行次数最多的那种任务。<br>
   * 执行顺序如下，每轮表示n+1的时间间隔： <br>
   * 【A】【B】【C】【D】【E】...<br>
   * 【A】【B】【C】【D】...<br>
   * 【A】【B】【C】...<br>
   * 【A】【B】...<br>
   */
  public static int leastInterval(char[] tasks, int n) {
    int[] aux = new int[26];
    int maxTimes = 0; // 最多的执行次数
    for (char c : tasks) {
      maxTimes = Math.max(maxTimes, ++aux[c - 'A']);
    }
    int maxCount = 0; // 具有最多执行次数的任务种类数量
    for (int i : aux) {
      if (i == maxTimes) {
        ++maxCount;
      }
    }
    return Math.max((maxTimes - 1) * (n + 1) + maxCount, tasks.length);
  }
}
