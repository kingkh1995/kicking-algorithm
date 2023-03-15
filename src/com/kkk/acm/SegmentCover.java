package com.kkk.acm;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class SegmentCover {

  /*
  最少数量线段覆盖
  输入：
  3
  1,4
  2,5
  3,6
  输出：
  2
     */

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int n = in.nextInt();
    List<int[]> list = new ArrayList<>(n);
    for (int i = 0; i < n; ++i) {
      String[] split = in.next().split(",");
      list.add(new int[] {Integer.parseInt(split[0]), Integer.parseInt(split[1])});
    }
    Collections.sort(
        list, Comparator.<int[]>comparingInt(arr -> arr[0]).thenComparingInt(arr -> arr[1]));
    int ans = n;
    boolean[] marked = new boolean[n]; // 标记被移除的线段
    for (int i = 0; i < n; ++i) { // 判断当前线段能否删除
      if (tryRemove(i, list, marked)) {
        ans--;
      }
    }
    System.out.println(ans);
  }

  private static boolean tryRemove(int i, List<int[]> list, boolean[] marked) {
    marked[i] = true;
    int[] cur = list.get(i);
    Deque<int[]> stack = new ArrayDeque<>();
    for (int j = 0; j < list.size(); ++j) {
      if (marked[j]) {
        continue;
      }
      int[] get = list.get(j), peek = stack.peek();
      // 先尝试合并剩余区间
      if (stack.isEmpty() || get[0] > peek[1]) {
        stack.push(get);
      } else {
        int[] pop = stack.pop();
        stack.push(new int[] {pop[0], Math.max(peek[1], get[1])});
      }
      // 判断是否可删除，即cur区间完全位于peek区间内。
      peek = stack.peek();
      if (peek[0] > cur[0]) { // 已超出，无法删除。
        break;
      } else if (peek[1] >= cur[1]) { // 可以删除
        return true;
      }
    }
    marked[i] = false; // 回溯
    return false;
  }
}
