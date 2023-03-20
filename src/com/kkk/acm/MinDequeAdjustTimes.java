package com.kkk.acm;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class MinDequeAdjustTimes {

  /*
  特异性双端队列
   */

  public static void main(String[] args) {
    try (Scanner in = new Scanner(System.in)) {
      int n = Integer.parseInt(in.nextLine().trim());
      Deque<Integer> deque = new LinkedList<>();
      int count = 0; // 修改次数
      boolean flag = false; // 需要调整了，等remove时才真的操作。
      for (int i = 0; i < 2 * n; ++i) { // head操作时才会导致需要调整
        String[] split = in.nextLine().trim().split(" ");
        if ("head".equals(split[0])) {
          if (deque.isEmpty()) {
            deque.addFirst(Integer.parseInt(split[2]));
          } else {
            flag = true;
            deque.addLast(Integer.parseInt(split[2]));
          }
        } else if ("tail".equals(split[0])) {
          deque.addLast(Integer.parseInt(split[2]));
        } else {
          // 移除
          if (flag) {
            count++;
            flag = false;
          }
          deque.pollFirst();
        }
      }
      System.out.println(count);
    }
  }
}
