package com.kkk.acm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class CommunicationErrors {

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    String[] arr = in.nextLine().split(" ");
    Map<String, Integer> map = new HashMap<>();
    int max = 0;
    for (String s : arr) { // 统计频次最大值
      map.put(s, map.getOrDefault(s, 0) + 1);
      max = Math.max(max, map.get(s));
    }
    List<String> list = new ArrayList<>();
    for (Entry<String, Integer> entry : map.entrySet()) { // 找出频次为最大值的元素
      if (entry.getValue() == max) {
        list.add(entry.getKey());
      }
    }
    int min = arr.length;
    for (String s : list) { // 分别计算每一个元素，找到第一个和最后一个出现的位置，计算出最小长度。
      int lo = 0, hi = arr.length - 1;
      while (lo < arr.length && !s.equals(arr[lo])) {
        lo++;
      }
      while (hi >= 0 && !s.equals(arr[hi])) {
        hi--;
      }
      min = Math.min(min, hi - lo + 1);
    }
    System.out.println(min);
  }
}
