package com.kkk.algs4;

import com.kkk.supports.ArrayUtils;
import com.kkk.supports.NodeUtils;
import com.kkk.supports.TreeNode;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 第三章 查找
 *
 * @author KaiKoo
 */
public class SearchingExs {
  // ===============================================================================================

  /** 3.2.25 完美平衡 利用一组数构建一个和二分查找完全等价的二叉树 */
  /** 先排序，再按二分查找的方式，将中位数设置为根结点，再递归的将左子数组构建成左子树，将右子数组构建为右子树。 */
  private static TreeNode buildBST(int[] arr) {
    return buildBST(arr, 0, arr.length - 1);
  }

  private static TreeNode buildBST(int[] arr, int lo, int hi) {
    if (lo > hi) {
      return null;
    }
    int mid = (lo + hi) >>> 1;
    TreeNode treeNode = new TreeNode(arr[mid]);
    treeNode.left = buildBST(arr, lo, mid - 1);
    treeNode.right = buildBST(arr, mid + 1, hi);
    treeNode.size += size(treeNode.left) + size(treeNode.right);
    return treeNode;
  }

  public static void buildBSTTest() {
    int[] arr = ArrayUtils.distinctSortedArr(10, 1, 20);
    TreeNode treeNode = buildBST(arr);
    System.out.println(treeNode);
  }

  // ===============================================================================================

  /** 3.2.34 线性符号表 使找到下一个结点和下一个结点的操作为常熟操作，即每个添加变量prev next，并在方法中维护。 */
  private static TreeNode put(TreeNode node, int key) {
    // 根结点情况
    if (node == null) {
      return new TreeNode(key);
    }
    int compare = key - node.val;
    if (compare < 0) {
      if (node.left == null) {
        // 临界条件 应该插入左子树且左子树为空则找到插入位置
        TreeNode temp = new TreeNode(key);
        insertNode(temp, node.prev, node);
        node.left = temp;
      } else {
        put(node.left, key);
      }
    } else if (compare > 0) {
      if (node.right == null) {
        // 临界条件 应该插入右子树且右子树为空则找到插入位置
        TreeNode temp = new TreeNode(key);
        insertNode(temp, node, node.next);
        node.right = temp;
      } else {
        put(node.right, key);
      }
    }
    node.size = 1 + size(node.right) + size(node.left);
    return node;
  }

  // 插入元素到两个结点中间，改变prev next
  private static void insertNode(TreeNode node, TreeNode prev, TreeNode next) {
    if (prev != null) {
      node.prev = prev;
      prev.next = node;
    }
    if (next != null) {
      node.next = next;
      next.prev = node;
    }
  }

  // 删除一个结点不改变整体顺序 故整个过程中只需要调用deleteNode一次
  private static TreeNode deleteMin(TreeNode node) {
    if (node.left == null) {
      deleteNode(node);
      return node.right;
    }
    node.left = deleteMin(node.left);
    node.size = 1 + size(node.right) + size(node.left);
    return node;
  }

  // 删除一个结点不改变整体顺序 故整个过程中只需要调用deleteNode一次
  private static TreeNode delete(TreeNode node, int key) {
    // 根结点情况
    if (node == null) {
      return null;
    }
    int compare = key - node.val;
    if (compare < 0) {
      node.left = delete(node.left, key);
    } else if (compare > 0) {
      node.right = delete(node.right, key);
    } else {
      // 直接删除，因为删除之后整体顺序是不变的，故只需要此时改变prev next一次即可
      deleteNode(node);
      if (node.left == null) {
        return node.right;
      } else if (node.right == null) {
        return node.left;
      }
      // 用next替换node的位置
      TreeNode temp = node;
      node = node.next;
      node.right = deleteMin0(temp.right); // 使用原始的deleteMin操作，而不用改变prev next，因为删除node的时候已经变更过了
      node.left = temp.left;
    }
    node.size = 1 + size(node.right) + size(node.left);
    return node;
  }

  private static TreeNode deleteMin0(TreeNode node) {
    if (node.left == null) {
      return node.right;
    }
    node.left = deleteMin0(node.left);
    node.size = 1 + size(node.right) + size(node.left);
    return node;
  }

  // 改变prev next
  private static void deleteNode(TreeNode node) {
    if (node.next != null) {
      node.next.prev = node.prev;
    }
    if (node.prev != null) {
      node.prev.next = node.next;
    }
  }

  public static void threadedSTTest() {
    int[] arr = ArrayUtils.distinctArr(10, 1, 20);
    TreeNode treeNode1 = new TreeNode(arr[0]);
    for (int i = 1; i < arr.length; i++) {
      put(treeNode1, arr[i]);
    }
    System.out.println(treeNode1);
    NodeUtils.threadedPrint(treeNode1);
    for (int i = 0; i < arr.length; i++) {
      treeNode1 = deleteMin(treeNode1);
      NodeUtils.threadedPrint(treeNode1);
    }
    int[] arr1 = ArrayUtils.distinctArr(10, 0, 10);
    ArrayUtils.printArray(arr1);
    TreeNode treeNode2 = new TreeNode(arr1[0]);
    for (int i = 1; i < arr1.length; i++) {
      put(treeNode2, arr1[i]);
    }
    System.out.println(treeNode2);
    NodeUtils.threadedPrint(treeNode2);
    int[] arr2 = ArrayUtils.distinctArr(10, 0, 10);
    for (int i = 0; i < arr2.length; i++) {
      treeNode2 = delete(treeNode2, arr2[i]);
      NodeUtils.threadedPrint(treeNode2);
    }
  }

  // ===============================================================================================

  private static int size(TreeNode node) {
    return node == null ? 0 : node.size;
  }

  // ===============================================================================================

  /** 红黑树测试 */
  public static void RedBlackBSTTest() {
    ThreadLocalRandom random = ThreadLocalRandom.current();
    RedBlackBST rbbst = new RedBlackBST();
    int n = 250;
    System.out.println("delete");
    for (int i = 0; i < 4 * n; i++) {
      rbbst.put(random.nextInt(n));
    }
    System.out.println("isBalanced:" + rbbst.isBalanced());
    for (int i = 0; i < 4 * n; i++) {
      rbbst.delete(random.nextInt(n));
    }
    System.out.println("delete max");
    for (int i = 0; i < 4 * n; i++) {
      rbbst.put(random.nextInt(n));
    }
    System.out.println("isBalanced:" + rbbst.isBalanced());
    int count = rbbst.count();
    for (int i = 0; i < count; i++) {
      rbbst.deleteMax();
    }
    System.out.println("delete min");
    for (int i = 0; i < 4 * n; i++) {
      rbbst.put(random.nextInt(n));
    }
    System.out.println("isBalanced:" + rbbst.isBalanced());
    count = rbbst.count();
    for (int i = 0; i < count; i++) {
      rbbst.deleteMin();
    }
  }

  // ===============================================================================================
}
