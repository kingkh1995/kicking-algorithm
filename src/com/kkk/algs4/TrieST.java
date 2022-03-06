package com.kkk.algs4;

/**
 * 单词查找树 <br>
 *
 * @author KaiKoo
 */
public class TrieST {

  // 基数，字母表的大小
  private static int R = 256;

  private Node root;

  private static class Node {
    private Object val;
    private Node[] next = new Node[R];
  }

  public Object get(String key) {
    Node node = get(root, key, 0);
    return node == null ? null : node.val;
  }

  private Node get(Node node, String key, int d) {
    if (node == null) {
      return null;
    }
    if (key.length() == d) {
      return node;
    }
    return get(node.next[key.charAt(d)], key, d + 1);
  }

  public void put(String key, Object val) {
    root = put(root, key, val, 0);
  }

  private Node put(Node node, String key, Object val, int d) {
    if (node == null) {
      node = new Node();
    }
    if (key.length() == d) {
      node.val = val;
      return node;
    }
    char c = key.charAt(d);
    node.next[c] = put(node.next[c], key, val, d + 1);
    return node;
  }

  public Iterable<String> keys() {
    return keysWithPrefix("");
  }

  public Iterable<String> keysWithPrefix(String pre) {
    MyQueue<String> queue = new MyQueue<>();
    // 找到值为pre的结点并以其为起点开始收集
    collect(get(root, pre, 0), pre, queue);
    return queue;
  }

  private void collect(Node node, String pre, MyQueue<String> queue) {
    if (node == null) {
      return;
    }
    if (node.val != null) {
      queue.offer(pre);
    }
    // 遍历字符集
    for (char c = 0; c < R; c++) {
      collect(node.next[c], pre + c, queue);
    }
  }

  public String longestPrefixOf(String s) {
    return s.substring(0, search(root, s, 0, 0));
  }

  private int search(Node node, String s, int d, int length) {
    if (node == null) {
      return length;
    }
    // 匹配则更新length值
    if (node.val != null) {
      length = d;
    }
    if (s.length() == d) {
      return length;
    }
    // 继续递归查找
    return search(node.next[s.charAt(d)], s, d + 1, length);
  }

  public void delete(String key) {}

  private Node delete(Node node, String key, int d) {
    if (node == null) {
      return null;
    }
    if (key.length() == d) {
      node.val = null;
    } else {
      char c = key.charAt(d);
      node.next[c] = delete(node.next[c], key, d + 1);
    }
    // 该结点和所有链接均为空则返回空
    if (node.val != null) {
      return node;
    }
    for (char c = 0; c < R; c++) {
      if (node.next[c] != null) {
        return node;
      }
    }
    return null;
  }
}
