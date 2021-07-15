package com.kkk.leetcode;

import com.kkk.algs4.MyStack;
import com.kkk.supports.TreeNode;
import java.util.ArrayList;
import java.util.List;

/**
 * 二叉树 & 二叉搜索树 <br>
 *
 * @author KaiKoo
 */
public class BinaryTreeExx {

  // ===============================================================================================
  /** 基础题 */

  // 二叉树的中序遍历 使用栈实现
  public List<Integer> inorderTraversal(TreeNode root) {
    MyStack<TreeNode> stack = new MyStack<>();
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

  // ===============================================================================================
  /** 拔高题 */
}
