package com.kkk.acm;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class MinDequeAdjustTimes {

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int n = Integer.parseInt(in.nextLine().trim());
    int add = 0, remove = 0, adjust = 0;
    Deque<Integer> deque = new ArrayDeque<>(n);
    for (int i = 0; i < 2 * n; ++i) {
      String order = in.nextLine();
      if (order.contains("head")) {
        deque.addFirst(++add);
      } else if (order.contains("tail")) {
        deque.addLast(++add);
      } else {
        int pollFirst = deque.pollFirst();
        if (++remove != pollFirst) { // 需要调整
          adjust++;
          deque.clear();
          for (int k = add; k > remove; --k) { // 将元素重新插入
            deque.addFirst(k);
          }
        }
      }
    }
    System.out.println(adjust);
  }
}
