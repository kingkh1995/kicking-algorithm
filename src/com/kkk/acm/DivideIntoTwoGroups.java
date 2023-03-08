package com.kkk.acm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class DivideIntoTwoGroups {

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    while (in.hasNextInt()) { // 注意 while 处理多个 case
      int n = in.nextInt();
      List<Integer> list = new ArrayList<>();
      int sum5 = 0, sum3 = 0, sum = 0;
      for (int i = 0; i < n; ++i) {
        int num = in.nextInt();
        sum += num;
        if (num % 5 == 0) {
          sum5 += num;
        } else if (num % 3 == 0) {
          sum3 += num;
        } else {
          list.add(num);
        }
      }
      System.out.println(canDivide(sum, sum5, sum3, list));
    }
  }

  private static boolean canDivide(int sum, int sum5, int sum3, List<Integer> list) {
    if ((sum & 1) == 1) { // sum非偶数
      return false;
    }
    if (sum5 == sum / 2 || sum3 == sum / 2) { // 是否已经满足条件
      return true;
    }
    int target = sum / 2 - sum5; // 确定出存在一个子数组的和等于target即可。
    // 如果能走到这里，可选择数组必然不为空。
    HashSet<Integer> set = new HashSet<>();
    for (int i = 0; i < list.size(); ++i) { // 求0~i的数组的子数组的可能和
      int n = list.get(i);
      if (n == target) {
        return true;
      }
      List<Integer> last = new ArrayList<>(set);
      set.add(n); // 默认添加
      for (int lastSum : last) { // 0~i-1的数组的可能和
        int t = lastSum + n; // 添加当前元素
        if (t == target) {
          return true;
        }
        set.add(t);
      }
    }
    return false;
  }
}
