package com.kkk.acm;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class OutstandingStudentsStatics {

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    Set<String> set = new LinkedHashSet<>(); // 记录顺序
    Map<String, Integer> map = new HashMap<>(); // 统计打卡天数
    in.nextLine();
    in.nextLine();
    for (int i = 0; i < 30; ++i) {
      for (String s : in.nextLine().trim().split(" ")) { // 每天的打卡记录自然按id排序
        if (set.contains(s)) {
          map.put(s, map.get(s) + 1);
        } else {
          set.add(s);
          map.put(s, 1);
        }
      }
    }
    List<Integer> list =
        map.values().stream()
            .sorted(Comparator.reverseOrder())
            .limit(5)
            .collect(Collectors.toList());
    for (int i : list) {
      for (String s : set) {
        if (Objects.equals(i, map.get(s))) {
          set.remove(s);
          System.out.print(s + " ");
          break;
        }
      }
    }
  }
}
