package com.kkk.acm;

import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class PutBoxes {

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    String s = in.next();
    int n = in.nextInt();
    char[][] arr = new char[n][s.length() / n + 1];
    for (int col = 0, count = 0; count < s.length(); col++) {
      for (int row = 0; row < n && count < s.length(); ++row) { // 向下
        arr[row][col] = s.charAt(count++);
      }
      col++; // 下一列
      for (int row = n - 1; row >= 0 && count < s.length(); --row) { // 向上
        arr[row][col] = s.charAt(count++);
      }
    }
    for (int i = 0; i < n; ++i) {
      for (int j = 0; j < arr[0].length; ++j) {
        System.out.print(arr[i][j]);
      }
      System.out.println();
    }
  }
}
