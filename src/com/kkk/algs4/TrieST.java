package com.kkk.algs4;

/**
 * R向单词查找树 <br>
 *
 * @author KaiKoo
 */
public class TrieST {

  // 基数，字母表的大小
  private static int R = 256;

  private Node root;

  private static class Node {
    private int size;
    private Object val;
    private Node[] next = new Node[R];
  }

  public Object getIterative(String key) {
    Node node = root;
    for (int d = 0; d < key.length() && node != null; d++) {
      node = node.next[key.charAt(d)];
    }
    return node == null ? null : node.val;
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

  public void putIterative(String key, Object val) {
    if (key.length() == 0) {
      return;
    }
    if (root == null) {
      root = new Node();
    }
    Node node = root;
    boolean newKey = getIterative(key) == null;
    for (int d = 0; d < key.length(); d++) {
      if (newKey) {
        node.size++;
      }
      if (node.next[key.charAt(d)] == null) {
        node.next[key.charAt(d)] = new Node();
      }
      node = node.next[key.charAt(d)];
    }
    node.val = val;
  }

  public void put(String key, Object val) {
    boolean newKey = get(key) == null;
    root = put(root, key, val, 0, newKey);
  }

  private Node put(Node node, String key, Object val, int d, boolean newKey) {
    if (node == null) {
      node = new Node();
    }
    if (key.length() == d) {
      node.val = val;
      return node;
    }
    if (newKey) {
      node.size++;
    }
    char c = key.charAt(d);
    node.next[c] = put(node.next[c], key, val, d + 1, newKey);
    return node;
  }

  public Iterable<String> keys() {
    return keysWithPrefix("");
  }

  public Iterable<String> keysWithPrefixIterative(String pre) {
    MyBag<String> bag = new MyBag<>();
    MyQueue<NodeWithPrefixInformation> queue = new MyQueue<>();
    Node node = get(root, pre, 0);
    if (node == null) {
      return bag;
    }
    queue.offer(new NodeWithPrefixInformation(node, pre));
    while (!queue.isEmpty()) {
      NodeWithPrefixInformation poll = queue.poll();
      if (poll.node.val != null) {
        bag.add(poll.prefix);
      }
      for (char c = 0; c < R; c++) {
        if (poll.node.next[c] != null) {
          queue.offer(new NodeWithPrefixInformation(poll.node.next[c], poll.prefix + c));
        }
      }
    }
    return bag;
  }

  private static class NodeWithPrefixInformation {
    private Node node;
    private String prefix;

    NodeWithPrefixInformation(Node node, String prefix) {
      this.node = node;
      this.prefix = prefix;
    }
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

  public String longestPrefixOfIterative(String s) {
    Node node = root;
    int length = 0;
    for (int d = 0; d < s.length() && node != null; d++) {
      if (node.val != null) {
        length = d;
      }
      node = node.next[s.charAt(d)];
    }
    return s.substring(0, length);
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

  public void deleteIterative(String key) {
    if (key.length() == 0) {
      return;
    }
    if (getIterative(key) == null) {
      return;
    }
    Node node = root;
    MyStack<NodeWithCInformation> stack = new MyStack<>();
    for (int d = 0; d < key.length() && node != null; d++) {
      node.size--;
      char c = key.charAt(d);
      // 不匹配则直接结束算法
      if (node.next[c] == null) {
        return;
      }
      stack.push(new NodeWithCInformation(node, c));
      node = node.next[c];
    }
    // 删除
    node.val = null;
    // 删除空链接
    NodeWithCInformation current = stack.pop();
    while (!stack.isEmpty()) {
      NodeWithCInformation parent = stack.pop();
      if (current.node.val != null) {
        break;
      }
      for (char c = 0; c < R; c++) {
        if (current.node.next[c] != null) {
          break;
        }
      }
      parent.node.next[current.c] = null;
      current = parent;
    }
  }

  private static class NodeWithCInformation {
    private Node node;
    private char c;

    NodeWithCInformation(Node node, char c) {
      this.node = node;
      this.c = c;
    }
  }

  public void delete(String key) {
    if (get(key) == null) {
      return;
    }
    root = delete(root, key, 0);
  }

  private Node delete(Node node, String key, int d) {
    if (node == null) {
      return null;
    }
    if (key.length() == d) {
      node.val = null;
    } else {
      char c = key.charAt(d);
      node.size--;
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

  public int size() {
    return size(root);
  }

  private int size(Node node) {
    if (node == null) {
      return 0;
    }
    return node.size;
  }

  // 是否存在以s为前缀的键
  public boolean containsPrefix(String s) {
    Node node = get(root, s, 0);
    return node != null && node.size > 0;
  }
}
