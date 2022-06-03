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
public class BinaryTreeExx {

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

  // 二叉树的中序遍历 使用栈实现
  public List<Integer> inorderTraversal(TreeNode root) {
    Deque<TreeNode> stack = new LinkedList<>();
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
    Deque<TreeNode> stack = new LinkedList<>();
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
    Deque<TreeNode> stack = new LinkedList<>();
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

  private boolean push(TreeNode node, Deque<TreeNode> stack) {
    if (node == null) {
      return false;
    }
    stack.push(node);
    return true;
  }

  // 验证是否是二叉搜索树
  public boolean isValidBST(TreeNode root) {
    return isValidBST(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
  }

  private boolean isValidBST(TreeNode node, long lower, long upper) {
    if (node == null) {
      return true;
    }
    if (node.val <= lower || node.val >= upper) {
      return false;
    }
    return isValidBST(node.left, lower, node.val) && isValidBST(node.right, node.val, upper);
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

  // 二叉树中的最大路径和，从任意节点出发到达任意节点，路径不允许重复。
  static class maxPathSumSolution {

    int max = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
      if (root == null) {
        return 0;
      }
      max(root);
      return max;
    }

    // 返回结点的最大贡献值，即左路径和右路径的大值
    private int max(TreeNode node) {
      if (node == null) {
        return 0;
      }
      int maxLeft = Math.max(max(node.left), 0);
      int maxRight = Math.max(max(node.right), 0);
      // 更新最大值
      max = Math.max(max, node.val + maxLeft + maxRight);
      return node.val + Math.max(maxLeft, maxRight);
    }
  }

  // ===============================================================================================
  /** 困难题 */

  // 从前序与中序遍历序列构造二叉树，迭代解法
  public TreeNode buildTree(int[] preorder, int[] inorder) {
    if (preorder == null || preorder.length == 0) {
      return null;
    }
    // 前序遍历第一个结点即为根节点
    TreeNode root = new TreeNode(preorder[0]);
    // 用一个栈来维护「当前节点的所有还没有考虑过右儿子的祖先节点」，栈顶就是当前节点
    Deque<TreeNode> stack = new LinkedList<>();
    stack.push(root);
    // 前序遍历的某个节点如果是左儿子，则必然只能是上一个节点的左儿子。
    // 对于前序遍历中的每个节点，我们依次判断它是栈顶节点的左儿子，还是栈中某个节点的右儿子
    int inorderIndex = 0;
    for (int i = 1; i < preorder.length; i++) {
      int preorderVal = preorder[i];
      TreeNode node = stack.peek();
      // 栈顶节点依次与中序遍历对比，如果相同表示前序遍历中下一个节点为右儿子，反之为栈顶节点的左儿子。
      if (node.val != inorder[inorderIndex]) {
        node.left = new TreeNode(preorderVal);
        stack.push(node.left);
      } else {
        // 因为栈中每一个节点的右儿子都还没有被遍历过，那么这些节点的顺序和它们在中序遍历中出现的顺序一定是相反的
        while (!stack.isEmpty() && stack.peek().val == inorder[inorderIndex]) {
          node = stack.pop();
          inorderIndex++;
        }
        // 直到中序遍历中找到一个不存在于栈内的节点，表示只有栈顶节点存在右儿子，则是当前右儿子的父亲。
        node.right = new TreeNode(preorderVal);
        // 右儿子也入栈，因为右儿子的右儿子也还没考虑。
        stack.push(node.right);
      }
    }
    return root;
  }
}
