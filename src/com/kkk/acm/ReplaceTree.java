package com.kkk.acm;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.Scanner;

public class ReplaceTree {

  /*
  输入：
  [1,1,2,0,0,4,5]
  /1/1
  [5,3,0]
  输出：
  [1,5,2,3,4,5]
   */

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    TreeNode tree1 = build(in.nextLine().replace("[", "").replace("]", "").split(","));
    String[] path = in.nextLine().split("/");
    TreeNode tree2 = build(in.nextLine().replace("[", "").replace("]", "").split(","));
    replace(tree1, tree2, path, 1);
    printTree(tree1);
  }

  private static TreeNode build(String[] tree) {
    TreeNode root = new TreeNode(tree[0]);
    generate(root, 0, tree);
    return root;
  }

  private static void generate(TreeNode root, int index, String[] tree) {
    int left = 2 * index + 1, right = 2 * index + 2; // 确定左右子树的索引
    if (left < tree.length && !"0".equals(tree[left])) {
      root.left = new TreeNode(tree[left]);
      generate(root.left, left, tree);
    }
    if (right < tree.length && !"0".equals(tree[right])) {
      root.right = new TreeNode(tree[right]);
      generate(root.right, right, tree);
    }
  }

  private static void replace(TreeNode tree1, TreeNode tree2, String[] path, int index) {
    if (!Objects.equals(tree1.val, path[index])) { // 不匹配当前节点则回退
      return;
    }
    if (index == path.length - 1) { // 找到节点，直接值替换即可。
      tree1.val = tree2.val;
      tree1.left = tree2.left;
      tree1.right = tree2.right;
      return;
    }
    replace(tree1.left, tree2, path, index + 1); // 左右子树DFS查找
    replace(tree1.right, tree2, path, index + 1);
  }

  private static void printTree(TreeNode treeNode) { // 层序遍历打印
    List<String> list = new ArrayList<>();
    Queue<TreeNode> queue = new ArrayDeque<>();
    queue.offer(treeNode);
    while (!queue.isEmpty()) {
      TreeNode poll = queue.poll();
      list.add(poll.val);
      if (poll.left != null) {
        queue.offer(poll.left);
      }
      if (poll.right != null) {
        queue.offer(poll.right);
      }
    }
    System.out.println(list.toString().replace(" ", ""));
  }

  static class TreeNode {
    String val;
    TreeNode left;
    TreeNode right;

    public TreeNode(String val) {
      this.val = val;
    }
  }
}
