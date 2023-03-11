package com.kkk.acm;

import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class LogReport {

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    String[] arr = in.nextLine().split(" ");
    int max = Integer.MIN_VALUE;
    for (int i = 0; i < arr.length; ++i) { // 遍历计算
      int score = 0, sum = 0;
      for (int j = 0; j <= i; ++j) { // 从t0时刻统计到ti时刻
        int n = Integer.parseInt(arr[j]);
        score += (j - i + 1) * Math.min(100 - sum, n); // 日志上限为100
        sum = Math.min(sum + n, 100); // 日志上报数不能超过100
      }
      max = Math.max(score, max); // 更新结果
      if (sum == 100) { // 本轮收集日志数超过了100，则上报时间不能超过当前时刻。
        break;
      }
    }
    System.out.println(max);
  }
}
