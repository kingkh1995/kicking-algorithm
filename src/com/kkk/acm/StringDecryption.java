package com.kkk.acm;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class StringDecryption {

  /*
  字符串解密
  输入：
  123admyffc79pt
  ssyy
  输出：
  pt
  输入：
  123admyffc79ptaagghi2222smeersst88mnrt
  ssyyfgh
  输出：
  mnrt
  输入：
  abcmnq
  rt
  输出：
  Not Found
     */

  public static void main(String[] args) {
    try (Scanner in = new Scanner(System.in)) {
      String s1 = in.nextLine().trim(), s2 = in.nextLine().trim();
      String[] split = s1.split("[a-f,0-9]+"); // 去除干扰字符串
      int diff = diff(s2);
      Map<String, Integer> map = new HashMap<>();
      // 按字典序排序
      List<String> collect =
          Arrays.stream(split)
              .filter(Predicate.not(String::isBlank))
              .sorted(Comparator.reverseOrder())
              .collect(Collectors.toList());
      int max = 0;
      String ans = "Not Found";
      for (String s : collect) {
        int cur = diff(s);
        if (cur <= diff && cur > max) {
          max = cur;
          ans = s;
        }
      }
      System.out.println(ans);
    }
  }

  public static int diff(String s) { // 统计不同字符串的个数
    Set<Character> set = new HashSet<>(); // 也可以使用数组统计
    for (char c : s.toCharArray()) {
      set.add(c);
    }
    return set.size();
  }
}
