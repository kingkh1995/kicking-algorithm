package com.kkk.acm;

import java.util.Arrays;
import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class FindMostTriangle {

  /*
  最多几个直角三角形
  输入：
  1
  7 3 4 5 6 5 12 13
  输出：
  2
    */

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int t = in.nextInt();
    while (t-- > 0) {
      int n = in.nextInt();
      int[] arr = new int[n];
      for (int i = 0; i < n; ++i) {
        arr[i] = in.nextInt();
      }
      Arrays.sort(arr);
      System.out.println(new FindTriangle().findTriangle(arr));
    }
  }

  public static class FindTriangle {
    int ans, count;

    int[] arr;
    boolean[] marked;

    public int findTriangle(int[] arr) {
      this.arr = arr;
      this.marked = new boolean[arr.length];
      ans = 0;
      count = 0;
      dfs(0);
      return ans;
    }

    public void dfs(int index) {
      if (index == arr.length) {
        ans = Math.max(ans, count);
        return;
      }
      // 不选三角形
      dfs(index + 1);
      // 选三角形
      if (marked[index]) {
        return;
      }
      for (int i = index + 1; i < arr.length - 1; ++i) { // 找第二条边
        if (marked[i]) {
          continue;
        }
        for (int j = i + 1; j < arr.length; ++j) { // 找第三条边，组合成一个三角形后，继续dfs。
          if (!marked[j] && arr[index] * arr[index] + arr[i] * arr[i] == arr[j] * arr[j]) {
            marked[i] = true;
            marked[j] = true;
            count++;
            dfs(index + 1); // 继续
            count--;
            marked[i] = false;
            marked[j] = false;
            break; // 退出循环，后续不可能继续找到了，重复的数字也忽略
          }
        }
      }
    }
  }
}
