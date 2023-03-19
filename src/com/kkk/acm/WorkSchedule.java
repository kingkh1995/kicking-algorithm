package com.kkk.acm;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class WorkSchedule {

  /*
  工单调度策略
  输入：
  3
  1 2
  2 8
  2 8
  输出：
  16
  输入：
  7
  1 6
  1 7
  3 2
  3 1
  2 4
  2 5
  6 1
  输出：
  15
    */

  public static void main(String[] args) {
    try (Scanner in = new Scanner(System.in)) {
      int n = in.nextInt(), max = 0;
      int[][] tasks = new int[n][2];
      for (int i = 0; i < n; ++i) {
        tasks[i][0] = in.nextInt(); // 时间
        max = Math.max(max, tasks[i][0]);
        tasks[i][1] = in.nextInt(); // 积分
      }
      Arrays.sort(tasks, Comparator.comparingInt(arr -> -arr[0])); // 先按时间倒序
      PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.reverseOrder());
      // 工单调度时间不可能超过最大时间，则反向选择工单，从时间的最后一刻往前，每次都选择时间大于等于当前的最大工单。
      int ans = 0;
      for (int t = max, i = 0; t > 0; --t) {
        // 将时间大于当前的工单加入优先队列
        while (i < n && tasks[i][0] >= t) {
          pq.offer(tasks[i][1]);
          i++;
        }
        if (!pq.isEmpty()) { // 是否能获取工单
          ans += pq.poll();
        }
      }
      System.out.println(ans);
    }
  }
}
