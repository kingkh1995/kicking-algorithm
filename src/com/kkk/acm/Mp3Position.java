package com.kkk.acm;

import java.util.Scanner;

public class Mp3Position {

  /*
  输入：
  83
  UUDUDDDDUDUUDDDDUDD
  输出：
  3 4 5 6
  6
   */

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    // 注意 hasNext 和 hasNextLine 的区别
    while (in.hasNextInt()) { // 注意 while 处理多个 case
      int n = in.nextInt();
      int a = 1, b = Math.min(n, 4), c = 1;
      String s = in.next();
      for (int i = 0; i < s.length(); ++i) {
        if (s.charAt(i) == 'U') {
          if (c == a && a == 1) {
            a = n < 4 ? 1 : n - 3;
            b = n;
            c = n;
          } else if (c == a) {
            a--;
            c--;
            b--;
          } else {
            c--;
          }
        } else {
          if (c == b && b == n) {
            a = 1;
            b = Math.min(n, 4);
            c = a;
          } else if (c == b) {
            a++;
            c++;
            b++;
          } else {
            c++;
          }
        }
      }
      for (int i = a; i <= b; ++i) {
        System.out.print(i + " ");
      }
      System.out.println();
      System.out.println(c);
    }
  }
}
