package com.kkk.hot;

import com.kkk.supports.TreeNode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

/**
 * 树 【二叉树 & 二叉搜索树】<br>
 * 【节点非结点】
 *
 * @author KaiKoo
 */
public class TreeHot {

  /**
   * 94. 二叉树的中序遍历 <br>
   * 不使用递归而是迭代求解，需要借助栈。
   */
  public List<Integer> inorderTraversal(TreeNode root) {
    List<Integer> ans = new ArrayList<>();
    Deque<TreeNode> stack = new LinkedList<>();
    while (root != null || !stack.isEmpty()) {
      while (root != null) { // 一直往左子树移动
        stack.push(root);
        root = root.left;
      }
      root = stack.pop(); // 左子树为空了则pop
      ans.add(root.val);
      root = root.right; // 输出完根节点后移动到右子树
    }
    return ans;
  }

  /** 98. 验证二叉搜索树 <br> */
  class isValidBSTSolution {
    public boolean isValidBST(TreeNode root) {
      return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private boolean isValidBST(TreeNode root, long lower, long upper) {
      if (root == null) {
        return true;
      } else if (root.val <= lower || root.val >= upper) {
        return false;
      }
      return isValidBST(root.left, lower, root.val) && isValidBST(root.right, root.val, upper);
    }
  }

  /**
   * 101. 对称二叉树 <br>
   * 迭代解法，需要使用队列。
   */
  public boolean isSymmetric(TreeNode root) {
    Queue<TreeNode> queue = new LinkedList<>(); // 支持null
    // 处理根节点
    queue.offer(root.left);
    queue.offer(root.right);
    while (!queue.isEmpty()) {
      // poll出两个节点，分别属于根节点的左右子树
      TreeNode left = queue.poll();
      TreeNode right = queue.poll();
      if (left == null && right == null) {
        continue;
      } else if (left == null || right == null || left.val != right.val) {
        return false;
      }
      // 从两边分别选择左或右一起入队
      queue.offer(left.left);
      queue.offer(right.right);
      queue.offer(left.right);
      queue.offer(right.left);
    }
    return true;
  }

  /** 102. 二叉树的层序遍历 <br> */
  public List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> ans = new ArrayList<>();
    if (root == null) {
      return ans;
    }
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    while (!queue.isEmpty()) {
      List<Integer> list = new ArrayList<>();
      int size = queue.size(); // 当前层节点数为队列大小
      while (size-- > 0) {
        TreeNode poll = queue.poll();
        list.add(poll.val);
        if (poll.left != null) {
          queue.offer(poll.left);
        }
        if (poll.right != null) {
          queue.offer(poll.right);
        }
      }
      ans.add(list);
    }
    return ans;
  }

  /**
   * 104. 二叉树的最大深度 <br>
   * 使用迭代而不是递归，在【102题】基础上修改即可。
   */
  public int maxDepth(TreeNode root) {
    int count = 0;
    if (root == null) {
      return count;
    }
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    while (!queue.isEmpty()) {
      int size = queue.size(); // 当前层节点数为队列大小
      while (size-- > 0) {
        TreeNode poll = queue.poll();
        if (poll.left != null) {
          queue.offer(poll.left);
        }
        if (poll.right != null) {
          queue.offer(poll.right);
        }
      }
      count++; // 每轮循环层数增加
    }
    return count;
  }

  /**
   * 124. 二叉树中的最大路径和 <br>
   * 递归计算每个节点的最大贡献值，并在递归过程中更新最大路径和。
   */
  class maxPathSumSolution {
    int ans = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
      maxGain(root);
      return ans;
    }

    private int maxGain(TreeNode root) {
      if (root == null) {
        return 0;
      }
      int lm = Math.max(0, maxGain(root.left)), rm = Math.max(0, maxGain(root.right));
      ans = Math.max(ans, root.val + lm + rm);
      return root.val + Math.max(lm, rm); // 最大贡献值必须包含当前节点
    }
  }

  /**
   * 226. 翻转二叉树 <br>
   * 递归和迭代
   */
  class invertTreeSolution {
    public TreeNode invertTree(TreeNode root) {
      if (root == null) {
        return null;
      }
      TreeNode tmp = root.left;
      root.left = invertTree(root.right);
      root.right = invertTree(tmp);
      return root;
    }

    public TreeNode invertTree0(TreeNode root) {
      Queue<TreeNode> queue = new LinkedList<>();
      queue.offer(root);
      while (!queue.isEmpty()) {
        TreeNode node = queue.poll();
        if (node != null) {
          queue.offer(node.left);
          queue.offer(node.right);
          TreeNode tmp = node.left;
          node.left = node.right;
          node.right = tmp;
        }
      }
      return root;
    }
  }

  /**
   * 235. 二叉搜索树的最近公共祖先<br>
   * 因为是二叉搜索树，则只要找到第一个位于两个值中间的值即可。<br>
   * 236. 二叉树的最近公共祖先 <br>
   * 找到从根节点到两个节点的路径并进行比对路径找出最近公共祖先，使用递归。<br>
   * 分别从左右子树查找节点，如果两个节点都被找到，则当前节点为最近公共祖先，向上返回即可，否则返回找到的节点。<br>
   */
  class lowestCommonAncestorSolution {
    public TreeNode lowestCommonAncestor235(TreeNode root, TreeNode p, TreeNode q) {
      if (p.val > q.val) {
        return lowestCommonAncestor235(root, q, p);
      }
      while (root != null) {
        if (root.val < p.val) {
          root = root.right;
        } else if (root.val > q.val) {
          root = root.left;
        } else {
          return root;
        }
      }
      return null;
    }

    public TreeNode lowestCommonAncestor236(TreeNode root, TreeNode p, TreeNode q) {
      if (root == null || root == p || root == q) { // 找到任一节点则返回该节点
        return root;
      }
      TreeNode left = lowestCommonAncestor236(root.left, p, q);
      TreeNode right = lowestCommonAncestor236(root.right, p, q);
      // left和right都非空则返回root，否则返回其中非空的那个。
      return null != left ? null != right ? root : left : right;
    }
  }

  /**
   * 297. 二叉树的序列化与反序列化 <br>
   * 按先序遍历的方式遍历二叉树的所有节点，并标记空子树。 <br>
   */
  public class Codec {
    public String serialize(TreeNode root) {
      StringBuilder sb = new StringBuilder();
      serialize(root, sb);
      return sb.toString();
    }

    private void serialize(TreeNode root, StringBuilder sb) {
      if (root == null) {
        sb.append("#,");
        return;
      }
      sb.append(root.val).append(",");
      serialize(root.left, sb);
      serialize(root.right, sb);
    }

    public TreeNode deserialize(String data) {
      Queue<String> queue =
          Arrays.stream(data.split(",")).collect(Collectors.toCollection(LinkedList::new));
      return deserialize(queue);
    }

    private TreeNode deserialize(Queue<String> queue) {
      String poll = queue.poll();
      if ("#".equals(poll)) { // 不用考虑其他终止条件，如果是合法的字符串，必然会自行终止。
        return null;
      }
      TreeNode root = new TreeNode(Integer.parseInt(poll));
      root.left = deserialize(queue);
      root.right = deserialize(queue);
      return root;
    }
  }

  /**
   * 337. 打家劫舍 III <br>
   * 有两种情况：1、选择根节点；2、不选择根节点。
   */
  class robSolution {
    public int rob(TreeNode root) {
      int[] dfs = dfs(root);
      return Math.max(dfs[0], dfs[1]);
    }

    private int[] dfs(TreeNode root) { // 深度优先搜索并返回两种情况下的最大值
      if (root == null) {
        return new int[] {0, 0};
      }
      int[] left = dfs(root.left), right = dfs(root.right);
      return new int[] {
        root.val + left[1] + right[1], Math.max(left[0], left[1]) + Math.max(right[0], right[1])
      };
    }
  }

  /**
   * 543. 二叉树的直径 <br>
   * 与【124题】相似，只是在dfs中求深度。
   */
  class diameterOfBinaryTreeSolution {
    int ans = 0;

    public int diameterOfBinaryTree(TreeNode root) {
      depth(root);
      return ans;
    }

    private int depth(TreeNode root) { // 求节点深度并更新结果
      if (root == null) {
        return 0;
      }
      int ld = depth(root.left), rd = depth(root.right);
      ans = Math.max(ans, ld + rd); // 当前节点下最大直径为左右子树深度之和
      return 1 + Math.max(ld, rd);
    }
  }

  /** 589. N 叉树的前序遍历 <br> */
  public List<Integer> preorder(Node root) {
    List<Integer> ans = new ArrayList<>();
    if (root == null) {
      return ans;
    }
    Deque<Node> stack = new LinkedList<>();
    stack.push(root);
    while (!stack.isEmpty()) {
      Node pop = stack.pop();
      ans.add(pop.val);
      for (int i = pop.children.size() - 1; i >= 0; --i) { // 等同于二叉树反向将子结点入栈即可
        stack.push(pop.children.get(i));
      }
    }
    return ans;
  }

  static class Node {
    public int val;
    public List<Node> children;
  }

  /** 617. 合并二叉树 <br> */
  public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
    if (root1 == null) {
      return root2;
    } else if (root2 == null) {
      return root1;
    }
    root1.val = root1.val + root2.val;
    root1.left = mergeTrees(root1.left, root2.left);
    root1.right = mergeTrees(root1.right, root2.right);
    return root1;
  }

  // ===============================================================================================

  /**
   * 96. 不同的二叉搜索树 <br>
   * 动态规划解法，结果也等于长度为n且值不重复的数组可以组成的不同的二叉搜索树的个数。
   */
  public int numTrees(int n) {
    int[] dp = new int[n + 1]; // dp[i]可以表示结果，也表示长度为i的值不重复数组可构造的二叉搜索树的数量。
    dp[0] = dp[1] = 1;
    for (int i = 2; i <= n; ++i) {
      for (int j = 1; j <= i; ++j) { // 根节点从第一个位置开始选择直到最后一个位置
        dp[i] += dp[j - 1] * dp[i - j]; // 左子树区间长度为j-1，右子树区间长度为i-j。
      }
    }
    return dp[n];
  }

  /**
   * 105. 从前序与中序遍历序列构造二叉树 <br>
   * 迭代解法，用一个栈来维护【当前节点的所有还没有考虑过右儿子的祖先节点】。
   */
  public TreeNode buildTree(int[] preorder, int[] inorder) {
    if (preorder == null || preorder.length == 0) {
      return null;
    }
    TreeNode root = new TreeNode(preorder[0]); // 前序遍历第一个节点即为根节点
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

  /**
   * 114. 二叉树展开为链表 <br>
   * 如果是递归输出前序遍历序列，则只能之后拼接链表；如果是迭代输出，则可以边遍历边拼接。<br>
   * 最优解法：寻找前驱节点，不需要辅助空间，如果不存在左子树，则不需要修改结构，否则为右子树找到它的前驱节点。<br>
   * 可知某个节点的左节点的最右边的节点在前序遍历中一定位于该节点的右子树之前，故其为该节点右节点的前驱节点。 <br>
   * 虽然不一定是前置节点，但可以先设置到其之后，在后续遍历中，会消除所有左子树，并调整到正确的位置。
   */
  class flattenSolution {

    public void flatten(TreeNode root) {
      while (root != null) {
        if (root.left != null) { // 左子树非空，处理左子树
          TreeNode prev = root.left;
          while (prev.right != null) {
            prev = prev.right;
          }
          prev.right = root.right; // 找到右子树的前驱节点，将右子树添加到前驱节点的后置节点
          root.right = root.left; // 左节点为当前节点的后置节点
          root.left = null;
        }
        root = root.right; // 如果无左子树则无需修改
      }
    }

    public void flatten0(TreeNode root) {
      if (root == null) {
        return;
      }
      Deque<TreeNode> stack = new LinkedList<>();
      stack.push(root);
      TreeNode prev = new TreeNode(); // 前置节点，默认虚拟头节点。
      while (!stack.isEmpty()) {
        TreeNode pop = stack.pop();
        // 先右节点再左节点
        if (pop.right != null) {
          stack.push(pop.right);
        }
        if (pop.left != null) {
          stack.push(pop.left);
        }
        pop.left = null;
        prev.right = pop;
        prev = pop;
      }
    }
  }

  /**
   * 437. 路径总和 III <br>
   * 深度搜索统计结果。
   */
  class pathSumSolution {
    public int pathSum(TreeNode root, int targetSum) {
      if (root == null) {
        return 0;
      }
      // 结果等于：【当前节点为第一个节点的路径数】加上【左子树的子问题答案】加上【右子树的子问题答案】
      return dfs(root, targetSum) + pathSum(root.left, targetSum) + pathSum(root.right, targetSum);
    }

    private int dfs(TreeNode root, int targetSum) { // 以当前节点为第一个节点满足条件的路径数
      if (root == null) {
        return 0;
      }
      return ((targetSum -= root.val) == 0 ? 1 : 0) // 路径可以只包含当前节点一个
          + dfs(root.left, targetSum) // 左节点为第一个节点的路径数
          + dfs(root.right, targetSum); // 右节点为第一个节点的路径数
    }
  }

  /**
   * 538. 把二叉搜索树转换为累加树 <br>
   * 解法一：按中序遍历输出后顺序累加上右边的节点。 <br>
   * 解法二：反序中序遍历的过程中累加节点值并修改节点。
   */
  class convertBSTSolution {
    int sum = 0;

    public TreeNode convertBST(TreeNode root) {
      if (root != null) {
        convertBST(root.right);
        root.val = sum += root.val;
        convertBST(root.left);
      }
      return root;
    }
  }
}
