package com.kkk.acm;

import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class ComputerRoomLayout {

  /*
  机房布局
  输入：
  MIIM
  输出：
  2
  输入：
  MIM
  输出：
  1
  输入：
  M
  输出：
  -1
  输入：
  MIIMMIMIMMIIIIIIIMIMIIM
  输出：
  7
     */

  public static void main(String[] args) {
    try (Scanner in = new Scanner(System.in)) {
      System.out.println(putElectricBox(in.next()));
    }
  }

  public static int putElectricBox(String s) {
    int ans = 0;
    // 计算出局部最优解，为每个机柜都默认添加一个电箱，一旦遇到了MIM，则可以使电箱数减一，同时当前确定了一个局部最优解。
    for (int i = 0, board = 0; i < s.length(); ++i) {
      if (s.charAt(i) == 'M') { // 机柜
        if ((i == board || s.charAt(i - 1) == 'M')
            && (i == s.length() - 1 || s.charAt(i + 1) == 'M')) { // 左右都无法安置电箱则直接失败
          return -1;
        }
        ans++; // 默认增加一个电箱
      } else if (i > board
          && s.charAt(i - 1) == 'M'
          && i < s.length() - 1
          && s.charAt(i + 1) == 'M') { // 左右都是机柜
        i++;
        board = i + 1; // 重新计算下一段，因为之前的区间已经是最优解。
      }
    }
    return ans;
  }
}
