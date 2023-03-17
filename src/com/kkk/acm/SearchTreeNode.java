package com.kkk.acm;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class SearchTreeNode {

  /*
  查找树中元素
  输入：
  14
  0 1 2 3 4
  -11 5 6 7 8
  113 9 10 11
  24 12
  35
  66 13
  77
  88
  99
  101
  102
  103
  25
  104
  2 5
  输出：
  {102}
  输入：
  14
  0 1 2 3 4
  -11 5 6 7 8
  113 9 10 11
  24 12
  35
  66 13
  77
  88
  99
  101
  102
  103
  25
  104
  3 2
  输出：
  {}
    */

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    while (in.hasNextLine()) {
      int n = Integer.parseInt(in.nextLine().trim());
      String[] arr = new String[n];
      for (int i = 0; i < n; ++i) {
        arr[i] = in.nextLine().trim();
      }
      TreeNode root = build(arr);
      String[] split = in.nextLine().trim().split(" ");
      int x = Integer.parseInt(split[0]), y = Integer.parseInt(split[1]);
      TreeNode node = find(root, x, y);
      System.out.println("{" + (node == null ? "" : node.val) + "}");
    }
  }

  public static TreeNode build(String[] arr) {
    TreeNode[] nodes = new TreeNode[arr.length];
    for (int i = 0; i < nodes.length; ++i) {
      nodes[i] = new TreeNode();
    }
    for (int i = 0; i < arr.length; ++i) {
      String[] split = arr[i].split(" ");
      nodes[i].val = Integer.parseInt(split[0]);
      for (int j = 1; j < split.length; ++j) {
        nodes[i].children.add(nodes[Integer.parseInt(split[j])]);
      }
    }
    return nodes[0];
  }

  public static TreeNode find(TreeNode root, int x, int y) {
    Queue<TreeNode> queue = new ArrayDeque<>();
    queue.offer(root);
    int cur = 0; // 层数
    while (!queue.isEmpty()) {
      int size = queue.size(), off = -1;
      if (cur == x && size <= y) {
        return null;
      }
      while (size-- > 0) {
        TreeNode poll = queue.poll();
        if (cur == x && ++off == y) {
          return poll;
        }
        for (TreeNode child : poll.children) {
          queue.offer(child);
        }
      }
      cur++;
    }
    return null;
  }

  public static class TreeNode {
    int val;
    List<TreeNode> children = new ArrayList<>();
  }
}
