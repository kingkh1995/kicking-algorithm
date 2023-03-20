package com.kkk.acm;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class BestDetectSpeed {

  /*
  核酸总最快检测效率
     */

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int m = in.nextInt(), n = in.nextInt();
    PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(arr -> -arr[1]));
    int sum = 0;
    for (int i = 0; i < m; ++i) {
      int t = in.nextInt();
      System.out.println(t);
      int k = 2 * t / 10; // 不安排志愿者损失2M
      System.out.println(k);
      pq.offer(new int[] {i, k});
      sum += (t - k);
    }
    int[] count = new int[m]; // 每个人员分配的志愿者数，默认都是不分配，即效率减少2M。
    for (int i = 0; i < n && !pq.isEmpty(); ++i) { // 每次都将志愿者分配到效率提高最快的地方
      int[] poll = pq.poll();
      if (count[poll[0]] == 0) {
        pq.offer(new int[] {poll[0], poll[1] / 2}); // 有志愿者后为M
      } else if (count[poll[0]] < 3) { // 等于3则不能再添加，因为志愿者上限是4
        pq.offer(poll); // 有志愿者后为M
      }
      sum += poll[1];
      count[poll[0]]++;
    }
    System.out.println(sum);
  }
}
