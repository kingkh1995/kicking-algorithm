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
public class TaskDeploy {

  /*
  任务混部、最大化控制资源成本
  输入：
  3
  2 3 1
  6 9 2
  0 5 1
  输出：
  2
  输入：
  2
  3 9 2
  4 7 3
  输出：
  5
    */

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    while (in.hasNextInt()) {
      int n = in.nextInt();
      int[][] task = new int[n][3]; // start,end,parallelism
      for (int i = 0; i < n; ++i) {
        task[i][0] = in.nextInt();
        task[i][1] = in.nextInt();
        task[i][2] = in.nextInt();
      }
      // 按start,end,parallelism排序
      Arrays.sort(
          task,
          Comparator.<int[]>comparingInt(arr -> arr[0])
              .thenComparingInt(arr -> arr[1])
              .thenComparingInt(arr -> arr[2]));
      PriorityQueue<Integer> pq = new PriorityQueue<>(); // 结束时间维护最小堆，表示使用的处理器数量
      int ans = 0; // 使用的处理器数量
      for (int[] arr : task) {
        int count = arr[2];
        while (!pq.isEmpty() && arr[0] >= pq.peek() && count-- > 0) { // 利用已经使用完成的服务器
          pq.poll();
        }
        ans += count; // 需要补充服务器
        for (int i = arr[2]; i > 0; --i) {
          pq.offer(arr[1]);
        }
      }
      System.out.println(ans);
    }
  }
}
