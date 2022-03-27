package com.kkk.leetcode;

import com.kkk.supports.TreeNode;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 二叉树 & 二叉搜索树 <br>
 *
 * @author KaiKoo
 */
public class BinaryTreeExx {

  // ===============================================================================================
  /** 基础题 */

  // 二叉树的前序遍历 使用栈实现
  public List<Integer> preorderTraversal(TreeNode root) {
    List<Integer> res = new ArrayList<>();
    if (root == null) {
      return res;
    }
    LinkedList<TreeNode> stack = new LinkedList<>();
    stack.push(root);
    while (!stack.isEmpty()) {
      TreeNode pop = stack.pop();
      res.add(pop.val);
      // 先右再左
      if (pop.right != null) {
        stack.push(pop.right);
      }
      if (pop.left != null) {
        stack.push(pop.left);
      }
    }
    return res;
  }

  // 二叉树的中序遍历 使用栈实现
  public List<Integer> inorderTraversal(TreeNode root) {
    LinkedList<TreeNode> stack = new LinkedList<>();
    List<Integer> res = new ArrayList<>();
    while (root != null || !stack.isEmpty()) {
      // 一直往左子树移动，并将根结点压入栈
      while (root != null) {
        stack.push(root);
        root = root.left;
      }
      // 一直到左子树为空 pop出根结点 打印 然后变为右结点
      root = stack.pop();
      res.add(root.val);
      root = root.right;
    }
    return res;
  }

  // 二叉树的后序遍历 使用栈实现
  public List<Integer> postorderTraversal(TreeNode root) {
    List<Integer> res = new ArrayList<>();
    if (root == null) {
      return res;
    }
    LinkedList<TreeNode> stack = new LinkedList<>();
    TreeNode prev = null;
    while (root != null || !stack.isEmpty()) {
      // 一直往左子树移动，并将根结点压入栈
      while (root != null) {
        stack.push(root);
        root = root.left;
      }
      // 一直到左子树为空 pop出根结点
      root = stack.pop();
      // 判断右子树
      if (root.right == null || root.right == prev) {
        res.add(root.val);
        // 记录下当前右结点，避免结点一直重复入栈。
        prev = root;
        // 结束，进入下个循环，从栈中取结点
        root = null;
      } else {
        // 根结点重新入栈，并跳转到右结点
        stack.push(root);
        root = root.right;
      }
    }
    return res;
  }

  // 对称二叉树，递归解法
  public boolean isSymmetric1(TreeNode root) {
    return isTwoSymmetric(root.left, root.right);
  }

  private boolean isTwoSymmetric(TreeNode left, TreeNode right) {
    if (left == null && right == null) {
      return true;
    }
    if (left != null && right != null && left.val == right.val) {
      return isTwoSymmetric(left.left, right.right) && isTwoSymmetric(left.right, right.left);
    }
    return false;
  }

  // 对称二叉树，迭代解法
  public boolean isSymmetric2(TreeNode root) {
    LinkedList<TreeNode> stack = new LinkedList<>();
    if (push(root.left, stack) != push(root.right, stack)) {
      return false;
    }
    while (!stack.isEmpty()) {
      TreeNode pop1 = stack.pop();
      TreeNode pop2 = stack.pop();
      if (pop1.val != pop2.val) {
        return false;
      }
      if (push(pop1.left, stack) != push(pop2.right, stack)) {
        return false;
      }
      if (push(pop2.left, stack) != push(pop1.right, stack)) {
        return false;
      }
    }
    return true;
  }

  private boolean push(TreeNode node, LinkedList<TreeNode> stack) {
    if (node == null) {
      return false;
    }
    stack.push(node);
    return true;
  }

  // ===============================================================================================
  /** 拔高题 */
}
