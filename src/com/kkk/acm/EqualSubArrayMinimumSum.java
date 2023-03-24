package com.kkk.acm;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class EqualSubArrayMinimumSum {

  /*
  星际篮球争霸赛、MVP争夺战、等和子数组最小和
     */

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int n = in.nextInt();
    Integer[] score = new Integer[n];
    int sum = 0;
    for (int i = 0; i < n; ++i) {
      score[i] = in.nextInt();
      sum += score[i];
    }
    Arrays.sort(score, Comparator.reverseOrder());
    for (int i = n; i >= 2; --i) { // 人数肯定不会超过n
      if (sum % i != 0) { // 无法除尽则直接失败
        continue;
      }
      int avg = sum / i;
      if (canDivide(score, i, avg)) { // 判断数组是否可以被拆分为i个和均为avg的子数组
        System.out.println(avg);
        return;
      }
    }
    System.out.println(sum); // 只派一个人得分，即结果为sum
  }

  public static boolean canDivide(Integer[] score, int num, int avg) {
    for (int s : score) { // 快速失败
      if (s > avg) {
        return false;
      }
    }
    boolean[] mark = new boolean[score.length];
    for (int k = 0; k < num; ++k) { // 寻找num次子数组，关键点是从大的数开始找。
      if (!dfs(score, mark, 0, avg)) { // 一次找不到则直接return
        return false;
      }
    }
    return true;
  }

  public static boolean dfs(
      Integer[] score, boolean[] mark, int index, int target) { // 数组已排序，需要从大的开始找。
    if (target == 0) { // 找到
      return true;
    }
    // 从当前位置一直找
    for (int i = index; i < score.length; ++i) {
      if (mark[i] || score[i] > target) { // 已被选择则跳过
        continue;
      }
      mark[i] = true; // 选择当前后，从下一个位置选择。
      if (dfs(score, mark, i + 1, target - score[i])) {
        return true;
      }
      mark[i] = false; // 回溯
    }
    return false; // 最终返回失败
  }
}
