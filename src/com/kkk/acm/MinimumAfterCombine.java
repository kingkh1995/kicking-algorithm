package com.kkk.acm;

import java.util.Arrays;
import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class MinimumAfterCombine {

  /*
  组合出合法最小数
  输入：
  20 1
  输出：
  120
  输入：
  08 10 2
  输出：
  10082
  输入：
  01 02
  输出：
  102
    */

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    while (in.hasNextLine()) {
      String[] split = in.nextLine().trim().split(" ");
      // 作为字符串排序组合后就是最小的数，选出最小的不是以0开头的数字作为头部，剩余字符依次排列即可。
      Arrays.sort(split);
      StringBuilder sb = new StringBuilder();
      boolean flag = true;
      for (String s : split) {
        if (flag && !s.startsWith("0")) { // 找到第一个不是0卡开头的数字
          sb = new StringBuilder(s + sb);
          flag = false;
        } else {
          sb.append(s);
        }
      }
      System.out.println(Integer.parseInt(sb.toString()));
    }
  }
}
