package com.kkk.acm;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class Bonus {

  /*
  分奖金
  输入：
  3
  2 10 3
  输出：
  8 10 3
     */

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int n = in.nextInt();
    int[] arr = new int[n];
    for (int i = 0; i < n; ++i) {
      arr[i] = in.nextInt();
    }
    int[] ans = new int[n];
    Deque<Integer> stack = new ArrayDeque<>(n); // 递减的单调栈
    for (int i = 0; i < n; ++i) {
      while (!stack.isEmpty() && arr[stack.peek()] < arr[i]) {
        int pop = stack.pop();
        ans[pop] = (i - pop) * (arr[i] - arr[pop]);
      }
      stack.push(i);
    }
    while (!stack.isEmpty()) {
      int pop = stack.pop();
      ans[pop] = arr[pop];
    }
    for (int i = 0; i < n; ++i) {
      System.out.print(ans[i] + " ");
    }
  }
}
