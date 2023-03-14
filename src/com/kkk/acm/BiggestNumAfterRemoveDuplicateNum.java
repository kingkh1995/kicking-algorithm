package com.kkk.acm;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class BiggestNumAfterRemoveDuplicateNum {
  /*
  移除重复数字后的最大数字
  输入：
  54457950454
  输出：
  7950454
     */

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    String s = in.next();
    int[] count = new int[10]; // 标记数字的次数
    for (char c : s.toCharArray()) {
      count[c - '0']++;
    }
    for (int i = 0; i < 10; ++i) { // 修改数组值为每个数字要被移除的个数。
      if (count[i] > 2) {
        count[i] -= 2;
      } else {
        count[i] = 0;
      }
    }
    // 维护单调栈，过程中移除数字，从高位开始重复数字的后一位大于它则可以移除。
    Deque<Integer> stack = new ArrayDeque<>();
    for (int i = 0; i < s.length(); ++i) {
      int num = s.charAt(i) - '0', top;
      // 当前数字要保留，且当前数字大于栈顶数字，且栈顶数字要移除，且栈顶数字还可以被移除。
      while (count[num] == 0 && !stack.isEmpty() && num > (top = stack.peek()) && count[top] > 0) {
        stack.pop();
        count[top]--;
      }
      stack.push(num);
    }
    // 移除剩余未移除的数字，从低位开始。
    List<Integer> ans = new ArrayList<>();
    while (!stack.isEmpty()) {
      int pop = stack.pop();
      if (count[pop] > 0) {
        count[pop]--;
      } else {
        ans.add(pop);
      }
    }
    for (int i = ans.size() - 1; i >= 0; --i) {
      System.out.print(ans.get(i));
    }
  }
}
