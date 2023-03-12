package com.kkk.acm;

import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class ArrangeJobs {

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int ts = in.nextInt(); // 总时间
    int n = in.nextInt();
    int[][] jobs = new int[n][2];
    for (int i = 0; i < n; ++i) {
      jobs[i][0] = in.nextInt(); // 时长
      jobs[i][1] = in.nextInt(); // 报酬
    }
    max = 0;
    backTrack(jobs, 0, ts, 0);
    System.out.println(max);
  }

  public static int max;

  public static void backTrack(int[][] jobs, int i, int remain, int profit) {
    if (i == jobs.length) {
      max = Math.max(max, profit);
      return;
    }
    backTrack(jobs, i + 1, remain, profit); // 不执行当前任务
    if (remain >= jobs[i][0]) { // 执行当前任务
      backTrack(jobs, i + 1, remain - jobs[i][0], profit + jobs[i][1]);
    }
  }
}
