package com.kkk.acm;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class DumpFiles {

  /*
  最大连续文件之和、区块链文件转储系统
  输入：
  1000
  100 300 500 400 400 150 100
  输出：
  950
  输入：
  1000
  100 500 400 150 500 100
  输出：
  1000
    */

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    while (in.hasNextLine()) {
      int m = Integer.parseInt(in.nextLine().trim());
      String[] split = in.nextLine().trim().split(" ");
      int ans = 0, sum = 0;
      Deque<Integer> deque = new ArrayDeque<>();
      for (int i = 0; i < split.length; ++i) {
        int n = Integer.parseInt(split[i]);
        sum += n;
        deque.offerLast(n); // 加入尾端
        while (!deque.isEmpty() && sum > m) {
          sum -= deque.pollFirst();
        }
        ans = Math.max(ans, sum);
      }
      System.out.println(ans);
    }
  }
}
