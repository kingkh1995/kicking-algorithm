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
public class DeployITServices {

  /*
  快速开租建站
  输入：
  5
  5
  0 4
  1 2
  1 3
  2 3
  2 4
  输出：
  3
    */

  public static void main(String[] args) {
    try (Scanner in = new Scanner(System.in)) {
      int n = in.nextInt(), m = in.nextInt();
      List<Integer>[] post = new List[n]; // 后置任务
      int[] inDegree = new int[n]; // 入度
      for (int i = 0; i < n; i++) {
        post[i] = new ArrayList<>();
      }
      for (int i = 0; i < m; ++i) {
        int a = in.nextInt(), b = in.nextInt(); // b是a的后置任务
        post[a].add(b);
        inDegree[b]++;
      }
      Queue<Integer> queue = new ArrayDeque<>(n);
      for (int i = 0; i < n; ++i) { // 入度为0的加入
        if (inDegree[i] == 0) {
          queue.offer(i);
        }
      }
      int count = 0;
      while (!queue.isEmpty()) {
        count++;
        int size = queue.size();
        while (size-- > 0) {
          int poll = queue.poll();
          for (int next : post[poll]) { // 后置任务入度减一
            if (--inDegree[next] == 0) { // 入度为0了，可以在下一轮部署
              queue.offer(next);
            }
          }
        }
      }
      System.out.println(count);
    }
  }
}
