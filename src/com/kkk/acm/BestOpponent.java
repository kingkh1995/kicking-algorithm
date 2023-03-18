package com.kkk.acm;

import java.util.Arrays;
import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class BestOpponent {

  /*
  实力差距最小总和
  输入：
  6 30
  81 87 47 59 81 18
  输出：
  57
  输入：
  6 20
  81 87 47 59 81 18
  输出：
  12
  输入：
  4 10
  40 51 62 73
  输出：
  -1
    */

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    while (in.hasNextInt()) {
      int n = in.nextInt(), d = in.nextInt();
      int[] arr = new int[n];
      for (int i = 0; i < n; ++i) {
        arr[i] = in.nextInt();
      }
      Arrays.sort(arr);
      min = 0;
      maxCount = 0;
      sum = 0;
      count = 0;
      backTrack(0, arr, d, new boolean[n]);
      System.out.println(maxCount == 0 ? -1 : min);
    }
  }

  private static void backTrack(int index, int[] arr, int d, boolean[] marked) {
    if (index == arr.length) {
      if (count > maxCount) {
        maxCount = count;
        min = sum;
      } else if (count == maxCount) {
        min = Math.min(min, sum);
      }
      return;
    }
    backTrack(index + 1, arr, d, marked);
    if (marked[index]) {
      return;
    }
    for (int i = index + 1; i < arr.length; ++i) {
      if (marked[i]) {
        continue;
      }
      if (arr[i] > arr[index] + d) {
        break;
      }
      marked[i] = true;
      sum += arr[i] - arr[index];
      count++;
      backTrack(index + 1, arr, d, marked);
      count--;
      sum -= arr[i] - arr[index];
      marked[i] = false;
    }
  }

  public static int min, maxCount, sum, count;
}
