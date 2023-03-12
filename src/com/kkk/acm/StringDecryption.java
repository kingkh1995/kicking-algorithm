package com.kkk.acm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class StringDecryption {

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    String s1 = in.nextLine(), s2 = in.nextLine();
    String[] split = s1.split("[a-f,0-9]+");
    int diff = diff(s2);
    Map<String, Integer> map = new HashMap<>();
    int target = 0;
    for (String s : split) {
      if("".equals(s)){
        continue;
      }
      int t = diff(s);
      if (t <= diff) {
        target = Math.max(target, t);
        map.put(s, t);
      }
    }
    List<String> list = new ArrayList<>();
    for (Map.Entry<String, Integer> entry : map.entrySet()) {
      if (entry.getValue() == target) {
        list.add(entry.getKey());
      }
    }
    Collections.sort(list);
    if (list.isEmpty()) {
      System.out.println("Not Found");
    } else {
      System.out.println(list.get(list.size() - 1));
    }
  }

  public static int diff(String s) {
    Set<Character> set = new HashSet<>(); // 也可以使用数组统计
    for (char c : s.toCharArray()) {
      set.add(c);
    }
    return set.size();
  }
}
