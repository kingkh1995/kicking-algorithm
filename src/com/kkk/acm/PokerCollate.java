package com.kkk.acm;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class PokerCollate {

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    String[] arr = in.nextLine().trim().split(" ");
    int[] poker = new int[14];
    for (String s : arr) { // 统计张数
      if ("".equals(s)) {
        continue;
      }
      poker[Integer.parseInt(s)]++;
    }
    Queue<Integer> queue = new LinkedList<>();
    for (int i = 13; i > 0; --i) { // 统计炸弹
      if (poker[i] >= 4) {
        queue.offer(i);
        queue.offer(i);
        queue.offer(i);
        queue.offer(i);
        poker[i] -= 4;
        i++;
      }
    }
    for (int i = 13; i > 1; --i) { // 葫芦，i至少2。
      if (poker[i] == 3) {
        int j = 13; // 找到一个对子
        while (j > 0) {
          if (j != i && poker[j] >= 2) {
            break;
          }
          --j;
        }
        if (j > 0) {
          queue.offer(i);
          queue.offer(i);
          queue.offer(i);
          queue.offer(j);
          queue.offer(j);
          poker[i] -= 3;
          poker[j] -= 2;
        }
      }
    }
    for (int i = 13; i > 0; --i) { // 三张
      if (poker[i] == 3) {
        queue.offer(i);
        queue.offer(i);
        queue.offer(i);
        poker[i] -= 3;
      }
    }
    for (int i = 13; i > 0; --i) { // 对子
      if (poker[i] == 2) {
        queue.offer(i);
        queue.offer(i);
        poker[i] -= 2;
      }
    }
    for (int i = 13; i > 0; --i) { // 单张
      if (poker[i] == 1) {
        queue.offer(i);
        poker[i] -= 1;
      }
    }
    for (int i : queue) {
      System.out.print(i + " ");
    }
  }
}
