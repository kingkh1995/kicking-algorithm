package com.kkk.acm;

import java.util.HashMap;
import java.util.HashSet;
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
    try (Scanner scanner = new Scanner(System.in)) {
      int n = scanner.nextInt();
      scanner.nextLine();
      int[] seq = new int[n];
      for (int i = 0; i < n; i++) {
        seq[i] = scanner.nextInt();
      }
      int res = solution(seq, n);
      System.out.println(res);
    }
  }

  private static int solution(int[] seq, int n) {
    int max = 0;
    int[] dp = new int[n]; // 使用dp优化子序列求和
    Map<Integer, Integer> sumCount = new HashMap<>(); // 保存子序列和出现的频次
    Map<Integer, HashSet<Integer>> sumPos = new HashMap<>(); // 记录子序列和及对应使用过的索引
    for (int i = 0; i < n; i++) { // 子序列长度-1
      for (int j = 0; j + i < n; j++) { // 从0位置开始
        dp[j] = dp[j] + seq[j + i];
        int sum = dp[j];
        if (!sumCount.containsKey(sum)) {
          sumCount.put(sum, 0);
          sumPos.put(sum, new HashSet<>());
        }
        boolean exists = false;
        HashSet<Integer> poss = sumPos.get(sum);
        for (int k = j; k <= j + i; k++) { // 判断当前子序列是否已被使用过
          if (exists = poss.contains(k)) {
            break;
          }
        }
        if (!exists) { // 当前子序列未被使用，则可以作为一个结果
          int newSum = sumCount.get(sum) + 1;
          sumCount.put(sum, newSum);
          max = Math.max(max, newSum); // 更新结果
          for (int k = j; k <= j + i; k++) { // 将子序列所有的索引都加入
            poss.add(k);
          }
        }
      }
    }

    return max;
  }
}
