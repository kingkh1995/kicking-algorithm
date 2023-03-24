package com.kkk.acm;

import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class NumberMinus {

  /*
  数字相减
  */

  public static void main(String[] args) {
    try (Scanner in = new Scanner(System.in)) {
      String a = in.next(), b = in.next();
      boolean positive = true; // 是否是正数
      int cmp = 1; // a和b的比较结果，默认a要大于b
      if (a.length() < b.length() || (a.length() == b.length() && (cmp = a.compareTo(b)) < 0)) {
        positive = false; // 为负数
        String t = a; // 交换
        a = b;
        b = t;
      } else if (cmp == 0) { // 相等直接返回0
        System.out.println("0");
        return;
      }
      // 反转，从最底位开始计算。
      StringBuilder ar = new StringBuilder(a).reverse(), br = new StringBuilder(b).reverse();
      StringBuilder sb = new StringBuilder();
      boolean take = false; // 需要借位
      for (int i = 0; i < ar.length() || i < br.length(); ++i) {
        int ac = ar.charAt(i) - '0', bc = i >= br.length() ? 0 : br.charAt(i) - '0';
        if (take) { // 借位处理
          if (ac == 0) { // 还需要向前借位
            ac = 10;
          } else {
            take = false;
          }
          ac--;
        }
        if (ac < bc) { // 是否需要借位
          take = true;
          ac += 10;
        }
        sb.append((char) (ac - bc + '0'));
      }
      // 翻转回来并去除前面的0
      String ans = sb.reverse().toString();
      int i = 0;
      while (i < ans.length() - 1 && ans.charAt(i++) == '0') // 最后一位可以不用判断了，至少存在一位。
        ;
      System.out.println((positive ? "" : "-") + ans.substring(i));
    }
  }
}
