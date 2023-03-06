package com.kkk.acm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class BrotherWords {

  /*
  输入：
  3 abc bca cab abc 1
  输出：
  2
  bca
       */

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int n = in.nextInt();
    List<String> list = new ArrayList<>();
    while (n-- > 0) {
      list.add(in.next());
    }
    String word = in.next();
    String formatted = format(word);
    List<String> brothers =
        list.stream()
            .filter(s -> !word.equals(s) && formatted.equals(format(s)))
            .sorted()
            .collect(Collectors.toList());
    int size = brothers.size();
    System.out.println(size);
    int k = in.nextInt();
    if (size >= k) {
      System.out.println(brothers.get(k - 1));
    }
  }

  private static String format(String s) { // 将单词的字母排序后重组
    char[] arr = s.toCharArray();
    Arrays.sort(arr);
    return new String(arr);
  }
}
