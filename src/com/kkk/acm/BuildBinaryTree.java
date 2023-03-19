package com.kkk.acm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class BuildBinaryTree {

  /*
  创建二叉树
  输入：
  operations=[[0, 0], [1, 0], [1, 0], [2, 1], [2, 1], [2, 1], [2, 0], [3, 1], [2, 0]]
  输出：
  [-1, 0, null, 1, 2, null, null, 6, 8, 3, 4, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null]
    */

  public static void main(String[] args) {
    try (Scanner in = new Scanner(System.in)) {
      String s = in.nextLine().trim();
      s = s.substring(11, s.length() - 1);
      String[] split = s.split(",");
      System.out.println(Arrays.toString(split));
      // 完全二叉树表示，每层的第一个节点为2^n-1，节点i的左节点为2i+1，右节点为2i+2
      List<Integer> tree = new ArrayList<>();
      tree.add(-1);
      for (int i = 0; i < split.length / 2; ++i) {
        String s1 = split[2 * i], s2 = split[2 * i + 1];
        int cell = Integer.parseInt(s1.substring(2));
        int index = Integer.parseInt(s2.substring(1, s2.length() - 1));
        int l = (1 << cell) - 1 + index, left = 2 * l + 1, right = 2 * l + 2;
        if (tree.get(l) == null) {
          continue;
        }
        if (left >= tree.size()) {
          for (int j = 1 << (cell + 1); j > 0; --j) {
            tree.add(null);
          }
        }
        if (tree.get(left) == null) {
          tree.set(left, i);
        } else if (tree.get(right) == null) {
          tree.set(right, i);
        }
      }
      System.out.println(tree);
    }
  }
}
