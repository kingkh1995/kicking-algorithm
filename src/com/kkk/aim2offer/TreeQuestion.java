package com.kkk.aim2offer;

import com.kkk.supports.TreeNode;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 树问题 前（根）序遍历-preorder，中（根）序遍历-inorder，后（根）序遍历-postorder 宽度优先遍历：逐层访问（同一层从左至右）
 *
 * @author KaiKoo
 * @date 2020/2/25 14:09
 */
public class TreeQuestion {

  private static final TreeQuestion TREE_QUESTION = new TreeQuestion();

  /*
  根据前序遍历（或后序遍历）和中序遍历重构二叉树（无重复结点）
   */
  public TreeNode reconstructBinaryTree(int[] preorder, int[] inorder) {
    return reconstruct(preorder, 0, preorder.length, inorder, 0, inorder.length);
  }

  private TreeNode reconstruct(
      int[] preorder, int pre1, int pre2, int[] inorder, int in1, int in2) {
    if (pre1 < pre2) {
      // 前序遍历第一个为根结点，后序遍历最后一个为根结点
      int root = preorder[pre1];
      TreeNode treeNode = new TreeNode(root);
      // 从中序遍历中找到根结点设为pivot
      int pivot = in1;
      for (; pivot < in2; pivot++) {
        if (inorder[pivot] == root) {
          break;
        }
      }
      // 从pivot处分成左右数组递归调用
      // 重构左子树
      treeNode.left = reconstruct(preorder, pre1 + 1, pivot - in1 + pre1 + 1, inorder, in1, pivot);
      // 重构右子树
      treeNode.right = reconstruct(preorder, pivot - in1 + pre1 + 1, pre2, inorder, pivot + 1, in2);
      return treeNode;
    }
    return null;
  }

  /*
  打印一个树的宽度优先遍历
   */
  /*
  解题思路：利用队列
   */
  public void printTreeBreadthFirstSearch(TreeNode treeNode) {
    if (treeNode != null) {
      // 仅仅使用一个队列
      Queue<TreeNode> queue = new LinkedList<>();
      // 根结点入队列
      queue.add(treeNode);
      // 边出边入直到队列为空
      while (!queue.isEmpty()) {
        TreeNode remove = queue.remove();
        System.out.print(remove.val);
        if (remove.left != null) {
          queue.add(remove.left);
        }
        if (remove.right != null) {
          queue.add(remove.right);
        }
      }
    }
  }

  /*
  环形宽度优先遍历
   */
  /*
  解题思路：利用两个栈
   */
  public void printTreeCircleBreadthFirstSearch(TreeNode treeNode) {
    if (treeNode != null) {
      Deque<TreeNode> stack1 = new LinkedList<>();
      Deque<TreeNode> stack2 = new LinkedList<>();
      // 根结点入栈
      stack1.push(treeNode);
      // 边出边入直到两栈均为空
      while (!stack1.isEmpty() || !stack2.isEmpty()) {
        // 两个栈左右结点入栈顺序相反
        while (!stack1.isEmpty()) {
          TreeNode pop = stack1.pop();
          System.out.print(pop.val);
          if (pop.left != null) {
            stack2.push(pop.left);
          }
          if (pop.right != null) {
            stack2.push(pop.right);
          }
        }
        System.out.print("|");
        while (!stack2.isEmpty()) {
          TreeNode pop = stack2.pop();
          System.out.print(pop.val);
          if (pop.right != null) {
            stack1.push(pop.right);
          }
          if (pop.left != null) {
            stack1.push(pop.left);
          }
        }
        System.out.print("|");
      }
    }
  }

  /*
  打印树的右视图（左视图）
   */
  /*
  解题思路：宽度优先遍历的变体
   */
  public void printLeftViewOfTree(TreeNode treeNode) {
    if (treeNode != null) {
      Queue<TreeNode> queue = new LinkedList<>();
      // 根结点入队列
      queue.add(treeNode);
      int count = 1;
      int child = 0;
      while (count != 0 || child != 0) {
        TreeNode remove = queue.remove();
        count--;
        // 右视图则从左往右添加，左视图则从右往左添加
        if (remove.right != null) {
          queue.add(remove.right);
          child++;
        }
        if (remove.left != null) {
          queue.add(remove.left);
          child++;
        }
        // 每层仅剩最后一个结点时打印
        if (count == 0) {
          System.out.print(remove.val);
          count = child;
          child = 0;
        }
      }
    }
  }

  /*
  判断树1是不是树2的子结构（空树不是任意一个树的子结构）
   */
  public boolean isTree1SubStructOfTree2(TreeNode treeNode1, TreeNode treeNode2) {
    if (treeNode1 == null || treeNode2 == null) {
      return false;
    } else {
      return isTree1SubStructOfTree2FromRoot(treeNode1, treeNode2)
          || isTree1SubStructOfTree2(treeNode1, treeNode2.left)
          || isTree1SubStructOfTree2(treeNode1, treeNode2.right);
    }
  }

  // 从根结点开始树1是不是树2的子结构
  private boolean isTree1SubStructOfTree2FromRoot(TreeNode treeNode1, TreeNode treeNode2) {
    if (treeNode1 == null) {
      return true;
    } else if (treeNode2 == null) {
      return false;
    } else {
      return treeNode1.val == treeNode2.val
          && isTree1SubStructOfTree2FromRoot(treeNode1.left, treeNode2.left)
          && isTree1SubStructOfTree2FromRoot(treeNode1.right, treeNode2.right);
    }
  }

  /*
  将一颗二叉树变成一个双向链表
   */
  public TreeNode convertBinaryTreeToDoubleLinkedList(TreeNode root) {
    if (root != null) {
      if (root.right != null) {
        // next直接设置为 rightDLList 的头结点
        TreeNode rightDLListHead = convertBinaryTreeToDoubleLinkedList(root.right);
        root.right = rightDLListHead;
        rightDLListHead.left = root;
      }
      if (root.left != null) {
        // pre则遍历 leftDLList 找到其尾结点
        TreeNode leftDLListHead = convertBinaryTreeToDoubleLinkedList(root.left);
        TreeNode leftDLListEnd = leftDLListHead;
        while (leftDLListEnd.right != null) {
          leftDLListEnd = leftDLListEnd.right;
        }
        root.left = leftDLListEnd;
        leftDLListEnd.right = root;
        // 返回 leftDLList 的头结点
        return leftDLListHead;
      } else {
        // 左子树为空时直接返回root
        return root;
      }
    }
    return null;
  }

  /*
  给定一个二叉树和其中的一个结点，请找出中序遍历顺序的下一个结点并且返回。（每个结点包含父结点的指针）
   */
  /*
  解题思路：存在四种情况
   */
  public TreeNode getNextNodeInInorderSearch(TreeNode node) {
    if (node == null) {
      return null;
    } else if (node.right != null) {
      // 如果存在右子树，那么找到第一个左结点
      node = node.right;
      while (node.left != null) {
        node = node.left;
      }
      return node;
    } else if (node.parent == null) {
      // 如果不存在右子树，且为根结点
      return null;
    } else if (node == node.parent.left) {
      // 如果自身为左结点
      return node.parent;
    } else {
      // 如果自身为右结点，向上寻找，直到第一个左结点的父结点
      do {
        node = node.parent;
      } while (node.parent != null && node == node.parent.right);
      // 返回该结点的父结点
      return node.parent;
    }
  }

  // 测试用例

  /*
              1
          2       3
      4        5    6
        7         8
                9
  */
  public static void reconstructBinaryTreeTest() {
    TreeNode treeNode =
        TREE_QUESTION.reconstructBinaryTree(
            new int[] {1, 2, 4, 7, 3, 5, 6, 8}, new int[] {4, 7, 2, 1, 5, 3, 8, 6});
    TREE_QUESTION.printTreeBreadthFirstSearch(treeNode);
  }

  public static void printTreeCircleBreadthFirstSearchTest() {
    TreeNode node8 = new TreeNode(8);
    TreeNode node7 = new TreeNode(7);
    TreeNode node6 = new TreeNode(6, node8, null);
    TreeNode node5 = new TreeNode(5);
    TreeNode node4 = new TreeNode(4, null, node7);
    TreeNode node3 = new TreeNode(3, node5, node6);
    TreeNode node2 = new TreeNode(2, node4, null);
    TreeNode node1 = new TreeNode(1, node2, node3);
    TREE_QUESTION.printTreeCircleBreadthFirstSearch(node1);
  }

  public static void printLeftViewOfTreeTest() {
    TreeNode node9 = new TreeNode(9);
    TreeNode node8 = new TreeNode(8, node9, null);
    TreeNode node7 = new TreeNode(7);
    TreeNode node6 = new TreeNode(6, node8, null);
    TreeNode node5 = new TreeNode(5);
    TreeNode node4 = new TreeNode(4, null, node7);
    TreeNode node3 = new TreeNode(3, node5, node6);
    TreeNode node2 = new TreeNode(2, node4, null);
    TreeNode node1 = new TreeNode(1, node2, node3);
    TREE_QUESTION.printLeftViewOfTree(node1);
  }

  public static void isTree1SubStructOfTree2Test() {
    TreeNode node9 = new TreeNode(9);
    TreeNode node8 = new TreeNode(8, node9, null);
    TreeNode node7 = new TreeNode(7);
    TreeNode node6 = new TreeNode(6, node8, null);
    TreeNode node5 = new TreeNode(5);
    TreeNode node4 = new TreeNode(4, null, node7);
    TreeNode node3 = new TreeNode(3, node5, node6);
    TreeNode node2 = new TreeNode(2, node4, null);
    TreeNode node1 = new TreeNode(1, node2, node3);
    System.out.println(TREE_QUESTION.isTree1SubStructOfTree2(null, node1));
    System.out.println(TREE_QUESTION.isTree1SubStructOfTree2(node1, null));
    System.out.println(TREE_QUESTION.isTree1SubStructOfTree2(node1, node1));
    System.out.println(TREE_QUESTION.isTree1SubStructOfTree2(new TreeNode(5), node1));
    System.out.println(TREE_QUESTION.isTree1SubStructOfTree2(new TreeNode(9), node1));
    System.out.println(TREE_QUESTION.isTree1SubStructOfTree2(node2, node1));
    TreeNode anode6 = new TreeNode(6);
    TreeNode anode5 = new TreeNode(5);
    TreeNode anode3 = new TreeNode(3, anode5, anode6);
    System.out.println(TREE_QUESTION.isTree1SubStructOfTree2(anode3, node1));
    TreeNode bnode4 = new TreeNode(4);
    TreeNode bnode3 = new TreeNode(3);
    TreeNode bnode2 = new TreeNode(2, bnode3, bnode4);
    System.out.println(TREE_QUESTION.isTree1SubStructOfTree2(bnode2, node1));
  }

  public static void convertBinaryTreeToDoubleLinkedListTest() {
    TreeNode node4 = new TreeNode(4);
    TreeNode node7 = new TreeNode(7);
    TreeNode node8 = new TreeNode(8, node7, null);
    TreeNode node12 = new TreeNode(12);
    TreeNode node16 = new TreeNode(16);
    TreeNode node6 = new TreeNode(6, node4, node8);
    TreeNode node14 = new TreeNode(14, node12, node16);
    TreeNode node10 = new TreeNode(10, node6, node14);
    TreeNode listNode = TREE_QUESTION.convertBinaryTreeToDoubleLinkedList(node10);
    while (listNode != null) {
      System.out.print(listNode.val + " ");
      listNode = listNode.right;
    }
  }

  public static void getNextNodeInInorderSearchTest() {
    TreeNode node9 = new TreeNode(9);
    TreeNode node8 = new TreeNode(8, node9, null);
    TreeNode node7 = new TreeNode(7);
    TreeNode node6 = new TreeNode(6, node8, null);
    TreeNode node5 = new TreeNode(5);
    TreeNode node4 = new TreeNode(4, null, node7);
    TreeNode node3 = new TreeNode(3, node5, node6);
    TreeNode node2 = new TreeNode(2, node4, null);
    TreeNode node1 = new TreeNode(1, node2, node3);
    node2.parent = node1;
    node3.parent = node1;
    node4.parent = node2;
    node5.parent = node3;
    node6.parent = node3;
    node7.parent = node4;
    node8.parent = node6;
    node9.parent = node8;
    System.out.println(TREE_QUESTION.getNextNodeInInorderSearch(node1).val);
    System.out.println(TREE_QUESTION.getNextNodeInInorderSearch(node2).val);
    System.out.println(TREE_QUESTION.getNextNodeInInorderSearch(node3).val);
    System.out.println(TREE_QUESTION.getNextNodeInInorderSearch(node4).val);
    System.out.println(TREE_QUESTION.getNextNodeInInorderSearch(node5).val);
    System.out.println(TREE_QUESTION.getNextNodeInInorderSearch(node6));
    System.out.println(TREE_QUESTION.getNextNodeInInorderSearch(node7).val);
    System.out.println(TREE_QUESTION.getNextNodeInInorderSearch(node8).val);
    System.out.println(TREE_QUESTION.getNextNodeInInorderSearch(node9).val);
  }
}
