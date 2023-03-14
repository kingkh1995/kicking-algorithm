package com.kkk.acm;

import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class NumberGame {

  /*
  数字加减游戏
  输入：
  1 10 5 2
  输出：
  1
  输入：
  11 33 4 10
  输出：
  2
     */

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int target = in.nextInt() - in.nextInt(), a = in.nextInt(), b = in.nextInt(); // s->t
    int n = 1; // 因为要求次数最小，则a只能是全部为+或全部为-。
    while ((target + a * n) % b != 0 && (target - a * n) % b != 0) {
      n++;
    }
    System.out.println(n);
  }
}
