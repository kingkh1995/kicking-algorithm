package com.kkk.acm;

import java.util.Scanner;

public class BaseConversion {

  /*
  输入：
  0xAA
  输出：
  170
  */

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    String input = in.nextLine();
    int n = 0;
    int mask = 1;
    for (int i = input.length() - 1; i > 1; --i) { // 从最低位开始计算
      char c = input.charAt(i);
      n += mask * (Character.isDigit(c) ? c - '0' : c - 'A' + 10);
      mask *= 16;
    }
    System.out.println(n);
  }
}
