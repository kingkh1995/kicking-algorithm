package com.kkk.algs4;

/**
 * 三向单词查找树 <br>
 *
 * @author KaiKoo
 */
public class TST {
  private Node root;

  private static class Node {
    char c;
    Node left, mid, right;
    Object val;
  }

  public Object get(String key) {
    Node node = get(root, key, 0);
    return node == null ? null : node.val;
  }

  private Node get(Node node, String key, int d) {
    if (node == null) {
      return null;
    }
    char c = key.charAt(d);
    if (c < node.c) {
      return get(node.left, key, d);
    } else if (c > node.c) {
      return get(node.right, key, d);
    } else if (d < key.length() - 1) { // 不同于TrieST根结点是有字符的故边界条件为key.length() - 1
      return get(node.mid, key, d + 1);
    } else {
      return node;
    }
  }

  public void put(String key, Object val) {
    root = put(root, key, val, 0);
  }

  private Node put(Node node, String key, Object val, int d) {
    char c = key.charAt(d);
    if (node == null) {
      node = new Node();
      node.c = c;
    }
    if (c < node.c) {
      node.left = put(node.left, key, val, d);
    } else if (c > node.c) {
      node.right = put(node.right, key, val, d);
    } else if (d < key.length() - 1) { // 不同于TrieST根结点是有字符的故边界条件为key.length() - 1
      node.mid = put(node.mid, key, val, d + 1);
    } else {
      node.val = val;
    }
    return node;
  }
}
