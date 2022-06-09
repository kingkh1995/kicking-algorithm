package com.kkk.leetcode;

import com.kkk.supports.TreeNode;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * 二叉树 & 二叉搜索树 <br>
 * 297
 *
 * @author KaiKoo
 */
public class TreeExx {

  // ===============================================================================================
  /** 基础题 */

  // 二叉树的前序遍历 使用栈实现
  public List<Integer> preorderTraversal(TreeNode root) {
    List<Integer> res = new ArrayList<>();
    if (root == null) {
      return res;
    }
    Deque<TreeNode> stack = new LinkedList<>();
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

  // 二叉树的后序遍历 使用栈实现
  public List<Integer> postorderTraversal(TreeNode root) {
    List<Integer> res = new ArrayList<>();
    if (root == null) {
      return res;
    }
    Deque<TreeNode> stack = new LinkedList<>();
    TreeNode prev = null;
    while (root != null || !stack.isEmpty()) {
      // 一直往左子树移动，并将根节点压入栈
      while (root != null) {
        stack.push(root);
        root = root.left;
      }
      // 一直到左子树为空 pop出根节点
      root = stack.pop();
      // 判断右子树
      if (root.right == null || root.right == prev) {
        res.add(root.val);
        // 记录下当前根节点，表示已经被遍历完成，避免节点一直重复入栈。
        prev = root;
        // 结束，进入下个循环，从栈中取节点
        root = null;
      } else {
        // 根节点重新入栈，并跳转到右节点
        stack.push(root);
        root = root.right;
      }
    }
    return res;
  }

  // 对称二叉树，递归解法，递归更快，但是可能栈溢出。
  public boolean isSymmetric(TreeNode root) {
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

  // 验证是否高度平衡的二叉树，自底向上的递归。
  public boolean isBalanced(TreeNode root) {
    // 不需要对左右子树调用isBalanced了
    return height(root) >= 0;
  }

  private int height(TreeNode node) {
    if (node == null) {
      return 0;
    }
    int lh = height(node.left);
    int rh = height(node.right);
    // 判断高度是否平衡，不平衡则返回-1
    if (lh < 0 || rh < 0 || Math.abs(lh - rh) > 1) {
      return -1;
    }
    return Math.max(lh, rh) + 1;
  }

  // ===============================================================================================
  /** 拔高题 */

  // 填充节点的next指针
  public TreeNode connect(TreeNode root) {
    if (root == null) {
      return null;
    }
    TreeNode lead = root;
    // 从根节点开始，更新下一层的next指针。
    while (lead != null) {
      TreeNode nextLead = null; // 下层开始节点
      TreeNode prev = null; // 未更新next指针的上一节点
      for (TreeNode p = lead; p != null; p = p.next) {
        if (p.left != null) {
          if (nextLead == null) {
            nextLead = p.left;
          }
          if (prev != null) {
            prev.next = p.left;
          }
          prev = p.left;
        }
        if (p.right != null) {
          if (nextLead == null) {
            nextLead = p.right;
          }
          if (prev != null) {
            prev.next = p.right;
          }
          prev = p.right;
        }
      }
      lead = nextLead;
    }
    return root;
  }

  // 二叉树的最近公共祖先，自顶向下递归求解。
  public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    if (root == null || root == p || root == q) {
      return root;
    }
    TreeNode left = lowestCommonAncestor(root.left, p, q);
    TreeNode right = lowestCommonAncestor(root.right, p, q);
    return left != null ? right != null ? root : left : right;
  }

  // 从前序与中序遍历序列构造二叉树，递归解法
  public TreeNode buildTree(int[] preorder, int[] inorder) {
    return buildTree(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1);
  }

  private TreeNode buildTree(int[] preorder, int pl, int pr, int[] inorder, int il, int ir) {
    if (pl > pr) {
      return null;
    }
    TreeNode root = new TreeNode(preorder[pl]); // 前序遍历第一位为根节点
    int i = il; // 中序遍历中根节点的位置，则左子树区间长度为 i-il
    for (; i <= ir; i++) { // 在中序遍历中找到根节点
      if (inorder[i] == preorder[pl]) {
        break;
      }
    }
    root.left = buildTree(preorder, pl + 1, pl + i - il, inorder, il, i - 1);
    root.right = buildTree(preorder, pl + i - il + 1, pr, inorder, i + 1, ir);
    return root;
  }
}
