package com.kkk.acm;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class ArrangeNewEmployee {

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    String[] arr = in.nextLine().trim().split(" ");
    int max = 0, ans = 0;
    // 确定一个滑窗，滑窗内只有一个0，其他都是1。
    Deque<String> queue = new ArrayDeque<>(); // 使用队列维护该窗口
    int pos = 0; // 用来记录窗口内0的位置
    for (int i = 0; i < arr.length; ++i) {
      String s = arr[i];
      if ("1".equals(s)) { // 直接入栈
        queue.offer("1");
      } else {
        // 窗口内存在0可统计结果
        if (pos > 0 && queue.size() > max) {
          max = queue.size();
          ans = pos;
        }
        if ("0".equals(s)) { // 为0
          // 如果已有一个0，则丢弃窗口前段的0
          while (pos > 0 && !queue.isEmpty() && !"0".equals(queue.poll())) {}
          pos = i + 1; // 更新0的位置
          queue.offer("0");
        } else { // 为2 窗口重置
          queue.clear();
          pos = 0;
        }
      }
    }
    // 最后统计栈内剩余结果
    if (pos > 0 && queue.size() > max) {
      ans = pos;
    }
    System.out.println(ans);
  }
}
