package com.kkk.acm;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class WorstProduct {

  /*
  最差产品奖
  输入：
  3
  12,3,8,6,5
  输出：
  3,3,5
     */

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int m = in.nextInt();
    String[] split = in.next().split(",");
    int[] arr = new int[split.length];
    for (int i = 0; i < arr.length; ++i) {
      arr[i] = Integer.parseInt(split[i]);
    }
    int[] ans = new int[arr.length - m + 1];
    Deque<Integer> deque = new ArrayDeque<>(arr.length); // 双端的单调递增队列，首部最小，尾部最大。
    for (int i = 0; i < arr.length; ++i) {
      if (deque.isEmpty() || arr[i] < arr[deque.peekFirst()]) {
        deque.offerFirst(i);
      } else {
        while (!deque.isEmpty() && arr[i] < arr[deque.peekLast()]) {
          deque.pollLast();
        }
        deque.offerLast(i);
      }
      int index = i + 1 - m;
      if (index >= 0) {
        while (deque.peek() < index) {
          deque.pollFirst();
        }
        ans[index] = arr[deque.peekFirst()];
      }
    }
    String s = Arrays.toString(ans);
    System.out.println(s.substring(1, s.length() - 1).replace(" ", ""));
  }
}
