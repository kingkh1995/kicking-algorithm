package com.kkk.algs4;

/**
 * AVL树
 *
 * @author KaiKoo
 */
public class AVLBST {

  // ===============================================================================================

  private class Node {

    public int val;

    public Node left;

    public Node right;

    public int size = 1;
    public int height = 1;

    public Node(int val) {
      this.val = val;
    }
  }

  // ===============================================================================================

  private Node root;

  public int count() {
    return size(this.root);
  }

  private static int size(Node node) {
    return node == null ? 0 : node.size;
  }

  private static int height(Node node) {
    return node == null ? 0 : node.height;
  }

  public Node min(Node node) {
    if (node.left != null) {
      return min(node.left);
    }
    return node;
  }

  // ===============================================================================================

  private static Node rotateLeft(Node node) {
    // right为红结点
    Node right = node.right;
    node.right = right.left;
    right.left = node;
    right.size = node.size;
    node.size = size(node.left) + size(node.right) + 1;
    node.height = Math.max(height(node.left), height(node.right)) + 1;
    right.height = Math.max(height(right.left), height(right.right)) + 1;
    return right;
  }

  private static Node rotateRight(Node node) {
    // left为红结点
    Node left = node.left;
    node.left = left.right;
    left.right = node;
    left.size = node.size;
    node.size = size(node.left) + size(node.right) + 1;
    node.height = Math.max(height(node.left), height(node.right)) + 1;
    left.height = Math.max(height(left.left), height(left.right)) + 1;
    return left;
  }

  // ===============================================================================================

  public void put(int key) {
    root = put(root, key);
    if (!isAVL()) {
      System.out.println("not balanced!");
    }
  }

  private Node put(Node node, int key) {
    if (node == null) {
      return new Node(key);
    }
    int cmp = key - node.val;
    // 插入
    if (cmp < 0) {
      node.left = put(node.left, key);
    } else if (cmp > 0) {
      node.right = put(node.right, key);
    }
    return balance(node);
  }

  // ===============================================================================================

  public void deleteMin() {
    if (root == null) {
      return;
    }
    root = deleteMin(root);
    if (!isAVL()) {
      System.out.println("not balanced!");
    }
  }

  private Node deleteMin(Node node) {
    if (node.left == null) {
      // 找到最小值，直接删除
      return node.right;
    }
    // 删除结点
    node.left = deleteMin(node.left);
    // 删除完 再重新平衡
    return balance(node);
  }

  // ===============================================================================================

  public void delete(int key) {
    if (root == null) {
      return;
    }
    // 判断key是否存在，删除操作就不用判断临界条件了
    /*    if (!contains(key)) {
      return;
    }*/
    root = delete(root, key);
    if (!isAVL()) {
      System.out.println("not balanced!");
    }
  }

  private Node delete(Node node, int key) {
    if (node == null) {
      return null;
    }
    int compare = key - node.val;
    if (compare < 0) {
      node.left = delete(node.left, key);
    } else if (compare > 0) {
      node.right = delete(node.right, key);
    } else {
      if (node.left == null) {
        return node.right;
      } else if (node.right == null) {
        return node.left;
      } else {
        Node aux = min(node.right);
        node.val = aux.val;
        node.right = deleteMin(node.right);
      }
    }
    node.size = size(node.left) + 1 + size(node.right);
    node.height = 1 + Math.max(height(node.left), height(node.right));
    return balance(node);
  }

  // ===============================================================================================

  // 删除结点恢复平衡 只用旋转一次
  private Node balance(Node node) {
    int cmp = balanceCmp(node);
    if (cmp > 1) {
      // 如果是下面这个形式需要先左旋一次
      //          3                       3                   2
      //      1           =>           2          =>       1     3
      //        2                   1
      if (balanceCmp(node.left) < 0) {
        node.left = rotateLeft(node.left);
      }
      node = rotateRight(node);
    } else if (cmp < -1) {
      // 类似，判断是否需要先右旋一次
      if (balanceCmp(node.right) > 0) {
        node.right = rotateRight(node.right);
      }
      node = rotateLeft(node);
    }
    node.size = size(node.left) + size(node.right) + 1;
    node.height = Math.max(height(node.left), height(node.right)) + 1;
    return node;
  }

  private int balanceCmp(Node node) {
    return height(node.left) - height(node.right);
  }

  // 判断是否是AVL树
  public boolean isAVL() {
    return isAVL(root);
  }

  private boolean isAVL(Node x) {
    if (x == null) {
      return true;
    }
    return Math.abs(height(x.left) - height(x.right)) <= 1
        && height(x) == 1 + Math.max(height(x.left), height(x.right))
        && isAVL(x.left)
        && isAVL(x.right);
  }

  // ===============================================================================================

}
