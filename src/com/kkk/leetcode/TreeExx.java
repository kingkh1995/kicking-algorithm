package com.kkk.leetcode;

import com.kkk.supports.TreeNode;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 二叉树 & 二叉搜索树 <br>
 * 297
 *
 * @author KaiKoo
 */
public class TreeExx {

  /**
   * 101. 对称二叉树 <br>
   * 递归和迭代解法
   */
  class isSymmetricSolution {
    public boolean isSymmetric1(TreeNode root) {
      return isTwoSymmetric(root.left, root.right);
    }

    private boolean isTwoSymmetric(TreeNode left, TreeNode right) { // 给定两个节点是否对称
      if (left == null && right == null) {
        return true;
      }
      if (left == null || right == null || left.val != right.val) {
        return false;
      }
      return isTwoSymmetric(left.left, right.right) && isTwoSymmetric(left.right, right.left);
    }

    public boolean isSymmetric2(TreeNode root) {
      Queue<TreeNode> queue = new LinkedList<>();
      queue.offer(root.left);
      queue.offer(root.right);
      while (!queue.isEmpty()) {
        TreeNode poll1 = queue.poll();
        TreeNode poll2 = queue.poll();
        if (poll1 == null && poll2 == null) {
          continue;
        } else if (poll1 == null || poll2 == null || poll1.val != poll2.val) {
          return false;
        }
        queue.offer(poll1.left);
        queue.offer(poll2.right);
        queue.offer(poll1.right);
        queue.offer(poll2.left);
      }
      return true;
    }
  }

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
