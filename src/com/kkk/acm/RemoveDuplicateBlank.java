package com.kkk.acm;

import java.util.Arrays;
import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class RemoveDuplicateBlank {

  /*
  去除多余空格
  输入：
  Life is painting a  picture, not doing 'a  sum'.
  8 15,20 26,43 45
  输出：
  Life is painting a picture, not doing 'a  sum'.
  [8, 15][19, 25][42, 44]
     */

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    String s = in.nextLine();
    String[] split = in.nextLine().split(",");
    int[][] arr = new int[split.length][2];
    for (int i = 0; i < split.length; ++i) {
      String[] tmp = split[i].split(" ");
      arr[i][0] = Integer.parseInt(tmp[0]);
      arr[i][1] = Integer.parseInt(tmp[1]);
    }
    StringBuilder sb = new StringBuilder();
    int index = 0; // 新字符串的当前索引
    boolean flag = false; // 是否存在未匹配的引号
    int imp = 0; // 关键词的索引
    for (int i = 0; i < s.length(); ++i) {
      if (imp < arr.length && index > arr[imp][0]) { // 当前关键词已经处理完
        imp++;
      }
      char c = s.charAt(i);
      if (c == ' ' && !flag && i > 0 && s.charAt(i - 1) == ' ') { // 为空格，不在单引号之间，前一个字符也是空格，则移除空格。
        // 更新关键字的索引，后面所以还未处理到的关键词，索引都减一。
        for (int j = imp; j < arr.length; ++j) {
          arr[j][0]--;
          arr[j][1]--;
        }
        continue;
      }
      // 插入
      sb.append(c);
      index++;
      if (c == '\'') { // 为引号
        flag = !flag;
      }
    }
    // 输出
    System.out.println(sb);
    String[] ans = new String[arr.length];
    for (int i = 0; i < ans.length; ++i) {
      System.out.print(Arrays.toString(arr[i]));
    }
  }
}
