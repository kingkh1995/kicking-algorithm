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

  /**
   * 110. 平衡二叉树 <br>
   * 递归，自顶向下（时间复杂度O(n^2)）或自底向上（时间复杂度O(n)）。
   */
  class isBalancedSolution {
    public boolean isBalanced1(TreeNode root) {
      if (root == null) {
        return true;
      }
      return Math.abs(height(root.left) - height(root.right)) <= 1
          && isBalanced1(root.left)
          && isBalanced1(root.right);
    }

    private int height(TreeNode node) {
      if (node == null) {
        return 0;
      }
      return 1 + Math.max(height(node.left), height(node.right));
    }

    public boolean isBalanced2(TreeNode root) {
      return depth(root) >= 0; // 不需要对左右子树调用isBalanced了，只需要求深度即可。
    }

    private int depth(TreeNode node) { // 深度求解过程中顺便判断是否平衡
      if (node == null) {
        return 0;
      }
      int ld = depth(node.left);
      int rd = depth(node.right);
      // 判断高度是否平衡，不平衡则返回-1
      if (ld < 0 || rd < 0 || Math.abs(ld - rd) > 1) {
        return -1;
      }
      return Math.max(ld, rd) + 1;
    }
  }

  /**
   * 144. 二叉树的前序遍历 <br>
   * 145. 二叉树的后序遍历 <br>
   * 迭代实现，使用栈。
   */
  class Traversal {
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
        if (pop.right != null) { // 先右后左
          stack.push(pop.right);
        }
        if (pop.left != null) {
          stack.push(pop.left);
        }
      }
      return res;
    }

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
  }

  /**
   * 230. 二叉搜索树中第K小的元素 <br>
   * 按中序遍历顺序找到第K个元素。
   */
  public int kthSmallest(TreeNode root, int k) {
    Deque<TreeNode> stack = new LinkedList<>();
    while (true) {
      while (root != null) { // 左子树一直入栈
        stack.push(root);
        root = root.left;
      }
      TreeNode pop = stack.pop(); // pop出元素
      if (--k == 0) {
        return pop.val;
      }
      root = pop.right; // 转到右子树
    }
  }

  // ===============================================================================================

  /**
   * 116. 填充每个节点的下一个右侧节点指针 <br>
   * 117. 填充每个节点的下一个右侧节点指针 II <br>
   * 最简单解法，借助队列使用BFS即可。 <br>
   * 在此基础上，使用循环方式构造链表，只需要借助几个变量即可，空间复杂度为O(1)。
   */
  public TreeNode connect(TreeNode root) {
    if (root == null) {
      return null;
    }
    TreeNode curHead = root; // 当前层链表头节点，根节点作为第一层链表。
    while (curHead != null) {
      TreeNode nextHead = null; // 下层链表头节点
      TreeNode prev = null; // 未更新next指针的上一节点
      for (TreeNode p = curHead; p != null; p = p.next) { // 遍历当前层链表，构建下层的链表。
        if (p.left != null) {
          if (nextHead == null) {
            nextHead = p.left;
          }
          if (prev != null) {
            prev.next = p.left;
          }
          prev = p.left;
        }
        if (p.right != null) {
          if (nextHead == null) {
            nextHead = p.right;
          }
          if (prev != null) {
            prev.next = p.right;
          }
          prev = p.right;
        }
      }
      curHead = nextHead; // 进入下一层
    }
    return root;
  }
}
