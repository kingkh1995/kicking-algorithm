package com.kkk.acm;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class MergeArrays {

  /*
  数组合并
    */

  public static void main(String[] args) {
    try (Scanner in = new Scanner(System.in)) {
      int k = in.nextInt(), n = in.nextInt();
      in.nextLine();
      Queue<Queue<String>> queues = new ArrayDeque<>(n);
      for (int i = 0; i < n; ++i) {
        String[] split = in.nextLine().trim().split(",");
        Queue<String> queue = new ArrayDeque<>();
        for (String s : split) {
          queue.offer(s);
        }
        queues.offer(queue);
      }
      List<String> list = new ArrayList<>();
      while (!queues.isEmpty()) {
        Queue<String> poll = queues.poll();
        int t = k;
        while (t-- > 0 && !poll.isEmpty()) {
          list.add(poll.poll());
        }
        if (!poll.isEmpty()) {
          queues.offer(poll);
        }
      }
      System.out.println(list);
    }
  }
}
