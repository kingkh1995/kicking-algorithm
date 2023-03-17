package com.kkk.acm;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class MatchedBinaryGroups {

  /*
  统计匹配的二元组个数
  输入：
  5
  4
  1 2 3 4 5
  4 3 2 1
  输出：
  4
  输入：
  6
  3
  1 2 4 4 2 1
  1 2 3
  输出：
  4
    */

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    while (in.hasNextInt()) {
      int m = in.nextInt(), n = in.nextInt();
      Map<Integer, Integer> mmap = new HashMap<>();
      for (int i = 0; i < m; ++i) {
        int k = in.nextInt();
        mmap.put(k, mmap.getOrDefault(k, 0) + 1);
      }
      Map<Integer, Integer> nmap = new HashMap<>();
      for (int i = 0; i < n; ++i) {
        int k = in.nextInt();
        nmap.put(k, nmap.getOrDefault(k, 0) + 1);
      }
      int ans = 0;
      for (Map.Entry<Integer, Integer> entry : mmap.entrySet()) {
        ans += entry.getValue() * nmap.getOrDefault(entry.getKey(), 0);
      }
      System.out.println(ans);
    }
  }
}
