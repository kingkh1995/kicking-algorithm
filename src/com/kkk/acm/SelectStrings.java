package com.kkk.acm;

import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class SelectStrings {

  /*
  挑选字符串
    */

  public static void main(String[] args) {
    try (Scanner scanner = new Scanner(System.in)) {
      String a = scanner.nextLine();
      String b = scanner.nextLine();
      int res = solution(a, b);
      System.out.print(res);
    }
  }

  private static int solution(String a, String b) {
    int count = 0;
    char[] chars = b.toCharArray();
    while (true) { // 循环挑选字符串
      int last = 0; // 保持顺序不变，作为下一个字符查找的起点。
      for (char c : chars) {
        int index = a.indexOf(c, last);
        last = index;
        if (index != -1) {
          a = a.replaceFirst(c + "", "_"); // 选取后不能再重复选择，直接替换为占位字符。
        } else { // 找不到了，则可以退出循环
          return count;
        }
      }
      count++;
    }
  }
}
