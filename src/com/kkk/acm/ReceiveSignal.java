package com.kkk.acm;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class ReceiveSignal {

  /*
  信号发射和接收
  输入:
  1 6
  2 4 1 5 3 3
  输出:
  1 6
  0 1 1 2 1 1
  输入:
  2 6
  2 5 4 3 2 8 9 7 5 10 10 3
  输出:
  2 6
  0 1 1 1 1 4 1 2 2 4 2 2
    */

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    while (in.hasNextInt()) {
      int m = in.nextInt(), n = in.nextInt();
      int[][] height = new int[m][n];
      for (int i = 0; i < m; ++i) {
        for (int j = 0; j < n; ++j) {
          height[i][j] = in.nextInt();
        }
      }
      int[][] ans = new int[m][n];
      // 单调栈解法，维护严格单调递减栈，每次判断栈内有几个高度小于当前高度。
      Deque<Integer> stack = new ArrayDeque<>();
      for (int i = 0; i < m; ++i) { // 逐行
        stack.clear();
        for (int j = 0; j < n; ++j) {
          int count = 0;
          while (!stack.isEmpty() && height[i][j] > stack.peek()) { // 大于栈顶，可以收到信号，栈顶出栈
            stack.pop();
            count++;
          }
          if (!stack.isEmpty()) { // 等于或小于栈顶，也可以收到
            count++;
          }
          ans[i][j] = count;
          if (!stack.isEmpty() && height[i][j] == stack.peek()) { // 等于栈顶时才不需要入栈
            continue;
          }
          stack.push(height[i][j]);
        }
      }
      for (int j = 0; j < n; ++j) { // 逐列
        stack.clear();
        for (int i = 0; i < m; ++i) {
          int count = 0;
          while (!stack.isEmpty() && height[i][j] > stack.peek()) { // 大于栈顶，可以收到信号，栈顶出栈
            stack.pop();
            count++;
          }
          if (!stack.isEmpty()) { // 等于或小于栈顶，也可以收到
            count++;
          }
          ans[i][j] += count;
          if (!stack.isEmpty() && height[i][j] == stack.peek()) { // 等于栈顶时才不需要入栈
            continue;
          }
          stack.push(height[i][j]);
        }
      }
      System.out.println(m + " " + n);
      for (int i = 0; i < m; ++i) {
        for (int j = 0; j < n; ++j) {
          System.out.print(ans[i][j] + " ");
        }
      }
    }
  }

}
