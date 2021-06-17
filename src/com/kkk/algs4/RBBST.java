package com.kkk.algs4;

/**
 * 红黑树
 *
 * @author KaiKoo
 */
public class RBBST {

  private class RBNode {

    public int val;

    public RBNode left;

    public RBNode right;

    public int size = 1;

    public boolean isRed = true;

    public RBNode(int val) {
      this.val = val;
    }
  }

  private RBNode root;

  private static int size(RBNode node) {
    return node == null ? 0 : node.size;
  }

  private static boolean isRed(RBNode node) {
    return node == null ? false : node.isRed;
  }

  // 将右斜红链接变为左斜红链接
  private static RBNode rotateLeft(RBNode node) {
    // right为红结点
    RBNode right = node.right;
    right.isRed = node.isRed;
    node.isRed = true;
    node.right = right.left;
    right.left = node;
    right.size = node.size;
    node.size = size(node.left) + size(node.right) + 1;
    return right;
  }

  // 将左斜红链接变为右斜红链接
  private static RBNode rotateRight(RBNode node) {
    // left为红结点
    RBNode left = node.left;
    left.isRed = node.isRed;
    node.isRed = true;
    node.left = left.right;
    left.right = node;
    left.size = node.size;
    node.size = size(node.left) + size(node.right) + 1;
    return left;
  }

  // 将一个结点下的两个红链接变为黑链接 将指向结点的链接变为红色
  // 将一个4-结点变为3个2-结点
  private static void flipColors(RBNode node) {
    node.isRed = true;
    node.left.isRed = false;
    node.right.isRed = false;
  }

  public void put(int key) {
    root = put(root, key);
    // 每次插入完成都将根结点设置为黑色
    root.isRed = false;
  }

  private RBNode put(RBNode node, int key) {
    if (node == null) {
      // 插入的结点默认为红结点
      return new RBNode(key);
    }
    int cmp = key - node.val;
    // 先插入
    if (cmp < 0) {
      node.left = put(node.left, key);
    } else if (cmp > 0) {
      node.right = put(node.right, key);
    }
    // 再旋转和变换颜色 按三步来
    // 第一步 左子结点为黑，右子结点为红 左旋
    if (isRed(node.right) && !isRed(node.left)) {
      node = rotateLeft(node);
    }
    // 第二步 左子结点为红，且左子节点的子节点为红 右旋
    if (isRed(node.left) && isRed(node.left.left)) {
      node = rotateRight(node);
    }
    // 第三步，左子结点和右子结点都为红
    if (isRed(node.left) && isRed(node.right)) {
      flipColors(node);
    }
    // 设置size
    node.size = 1 + size(node.left) + size(node.right);
    return node;
  }
}
