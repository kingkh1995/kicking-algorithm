package com.kkk.acm;

import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class Sudoku {

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int[][] sudoku = new int[9][9];
    for (int i = 0; i < 9; ++i) {
      for (int j = 0; j < 9; ++j) {
        sudoku[i][j] = in.nextInt();
      }
    }
    // 开始回溯
    backTrack(sudoku);
    //  print
    for (int i = 0; i < 9; ++i) {
      for (int j = 0; j < 9; ++j) {
        System.out.print(sudoku[i][j] + " ");
      }
      System.out.println();
    }
  }

  private static boolean backTrack(int[][] sudoku) {
    for (int i = 0; i < 9; ++i) {
      for (int j = 0; j < 9; ++j) {
        if (sudoku[i][j] == 0) {
          for (int k = 1; k <= 9; ++k) { // 从1-9依次设定
            if (isValidSudoku(i, j, k, sudoku)) {
              sudoku[i][j] = k;
              if (backTrack(sudoku)) { // 成功了直接return true。
                return true;
              }
              sudoku[i][j] = 0; // 回退
            }
          }
          return false; // 1-9都设定完成，且不符合则表示此路不通。
        }
      }
    }
    return true; // 遍历到最深处，未找到==0的格子，则完成。
  }

  public static boolean isValidSudoku(int row, int col, int val, int[][] sudoku) {
    // 同行是否重复
    for (int i = 0; i < 9; i++) {
      if (sudoku[row][i] == val) {
        return false;
      }
    }
    // 同列是否重复
    for (int j = 0; j < 9; j++) {
      if (sudoku[j][col] == val) {
        return false;
      }
    }
    // 9宫格里是否重复
    int startRow = (row / 3) * 3;
    int startCol = (col / 3) * 3;
    for (int i = startRow; i < startRow + 3; i++) {
      for (int j = startCol; j < startCol + 3; j++) {
        if (sudoku[i][j] == val) {
          return false;
        }
      }
    }
    return true;
  }
}
