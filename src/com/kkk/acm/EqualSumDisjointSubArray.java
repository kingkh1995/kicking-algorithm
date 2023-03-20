package com.kkk.acm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class EqualSumDisjointSubArray {

  /*
  最多等和不相交连续子序列
  输入：
  10
  8 8 9 1 9 6 3 9 1 0
  输出：
  4
  输入：
  10
  -1 0 4 -3 6 5 -6 5 -7 -3
  输出：
  3
    */

  public static void main(String[] args) {
    try (Scanner in = new Scanner(System.in)) {
      int n = in.nextInt();
      int[] arr = new int[n];
      for (int i = 0; i < n; ++i) {
        arr[i] = in.nextInt();
      }
      // 记录子序列的和以及其包含的区间
      Map<Integer, List<int[]>> map = new HashMap<>();
      int max = 0;
      int[] dp = new int[n]; // 使用dp记录前缀和以优化求和
      for (int i = 0; i < n; i++) { // 长度从1开始，这样才不会出错。
        for (int j = 0; j + i < n; ++j) { // 每一列，当前区间为[j, j+i]
          dp[j] += arr[j + i]; // 序列增加
          int sum = dp[j];
          if (!map.containsKey(sum)) {
            map.put(sum, new ArrayList<>());
          }
          // 判断区间是否存在了
          List<int[]> indexList = map.get(sum);
          boolean flag = false;
          for (int[] cover : indexList) {
            if (cover[0] <= j + i && cover[1] >= j) {
              flag = true;
              break;
            }
          }
          if (flag) { // 存在了则不匹配
            continue;
          }
          indexList.add(new int[] {j, j + i}); // 匹配，加入结果集
          max = Math.max(max, indexList.size()); // 更新结果
        }
      }
      System.out.println(max);
    }
  }
}
