package com.kkk.acm;

import java.util.Arrays;
import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class MVP {

  /*
  星际篮球争霸赛、MVP争夺战
     */

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int n = in.nextInt();
    int[] score = new int[n];
    int sum = 0;
    for (int i = 0; i < n; ++i) {
      score[i] = in.nextInt();
      sum += score[i];
    }
    Arrays.sort(score);
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

  public static boolean canDivide(int[] score, int num, int avg) {
    for (int s : score) { // 快速失败
      if (s > avg) {
        return false;
      }
    }
    boolean[] mark = new boolean[score.length];
    for (int k = 0; k < num; ++k) { // 寻找num次子数组
      if (!dfs(score, mark, 0, avg)) { // 一次找不到则直接return
        return false;
      }
    }
    return true;
  }

  public static boolean dfs(int[] score, boolean[] mark, int i, int target) { // 数组已排序，从前往后找即可。
    if (i == score.length || score[i] > target) { // 数组找完，或后面数都大于target
      return false;
    }
    if (mark[i]) { // 已被选取
      return dfs(score, mark, i + 1, target);
    } else if (score[i] == target) { // 数值匹配了则选取成功
      mark[i] = true;
      return true;
    } else if (dfs(score, mark, i + 1, target)) { // 不选当前
      return true;
    } else { // 选当前
      mark[i] = true;
      if (dfs(score, mark, i + 1, target - score[i])) {
        return true;
      }
      mark[i] = false; // 回退
      return false;
    }
  }
}
