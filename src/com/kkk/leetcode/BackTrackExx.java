package com.kkk.leetcode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * 回溯 <br>
 * 494
 *
 * @author KaiKoo
 */
public class BackTrackExx {

  /** 47. 全排列 II <br> */
  class permuteUniqueSolution {
    int[] nums;
    int[] count;
    List<List<Integer>> ans;
    Deque<Integer> deque;

    public List<List<Integer>> permuteUnique(int[] nums) {
      this.nums = nums;
      this.ans = new ArrayList<>();
      this.deque = new ArrayDeque<>(nums.length);
      this.count = new int[21];
      for (int n : nums) { // 统计数字的出现的次数
        this.count[n + 10]++;
      }
      backTrack(0);
      return this.ans;
    }

    private void backTrack(int i) {
      if (i == nums.length) {
        ans.add(new ArrayList<>(deque));
      }
      for (int j = 0; j < count.length; ++j) {
        if (count[j] > 0) {
          count[j]--;
          deque.offerLast(j - 10);
          backTrack(i + 1);
          deque.pollLast();
          count[j]++;
        }
      }
    }
  }

  // ===============================================================================================

}
