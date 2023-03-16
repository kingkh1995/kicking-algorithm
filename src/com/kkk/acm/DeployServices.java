package com.kkk.acm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class DeployServices {

  /*
  部署IT服务
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
    Scanner in = new Scanner(System.in);
    while (in.hasNextInt()) {
      int n = in.nextInt(), m = in.nextInt();
      List<Integer>[] dep = new List[n]; // 依赖关系
      for (int i = 0; i < n; i++) {
        dep[i] = new ArrayList<>();
      }
      for (int i = 0; i < m; ++i) {
        int a = in.nextInt(), b = in.nextInt(); // b 依赖 a
        dep[b].add(a);
      }
      boolean[] marked = new boolean[n]; // 标记数组
      int count = 0, total = 0;
      for (int i = 0; i < m && total < n; ++i) { // 最多m轮判断
        count++;
        boolean[] cur = Arrays.copyOf(marked, marked.length); // 需要额外的数组，防止本轮的任务互相影响
        for (int j = 0; j < n; ++j) {
          if (marked[j]) {
            continue;
          }
          boolean flag = true;
          for (int k : dep[j]) { // 判断依赖任务是否已经部署
            if (!marked[k]) {
              flag = false;
              break;
            }
          }
          if (flag) {
            cur[j] = true;
            total++;
          }
        }
        marked = cur;
      }
      System.out.println(count);
    }
  }
}
