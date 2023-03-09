package com.kkk.acm;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class Symmetry {

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    List<String> list = new ArrayList<>();
    list.add("R");
    while (in.hasNextInt()) {
      int n = in.nextInt(), k = in.nextInt();
      if (list.size() < n) {
        for (int i = list.size(); i < n; ++i) {
          String last = list.get(i - 1);
          list.add(reverse(last) + last);
        }
      }
      System.out.println(list.get(n - 1).charAt(k));
    }
  }

  public static String reverse(String s) {
    StringBuilder sb = new StringBuilder();
    for (char c : s.toCharArray()) {
      sb.append(c == 'R' ? 'B' : 'R');
    }
    return sb.toString();
  }
}
