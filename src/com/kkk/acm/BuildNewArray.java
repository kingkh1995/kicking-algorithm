package com.kkk.acm;

import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class BuildNewArray {

  /*
  组装新的数组
  输入：
  2 3
  5
  输出：
  2
    */

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    while (in.hasNextLine()) {
      String[] split = in.nextLine().trim().split(" ");
      int start = Integer.parseInt(split[0]),
          end = Integer.parseInt(split[split.length - 1]) + 1,
          m = Integer.parseInt(in.nextLine().trim());
      System.out.println(new BackTrack().buildNewArray(start, end, m));
    }
  }

  public static class BackTrack {
    int ans, start, end, m;

    public int buildNewArray(int start, int end, int m) {
      this.start = start;
      this.end = end;
      this.m = m;
      this.ans = 0;
      for (int i = 0; i < start; ++i) { // 最多有一个数不在区间内
        backTrack(start, m - i); // 从start开始，选数组成数组，使得和为m-i
      }
      return ans;
    }

    public void backTrack(int n, int target) {
      if (target == 0) { // 成功
        ans++;
        return;
      }
      for (int i = n; i < end && i <= target; ++i) { // 选一个数字
        backTrack(i, target - i);
      }
    }
  }
}
