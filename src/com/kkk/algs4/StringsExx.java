package com.kkk.algs4;

import java.util.HashSet;
import java.util.StringJoiner;

/**
 * 第五章 字符串
 *
 * @author KaiKoo
 */
public class StringsExx {

  // ===============================================================================================

  /** TrieST和TST测试 */
  public static void TrieSTAndTSTTest() {
    TrieST trieST = new TrieST();
    trieST.put("", 0);
    trieST.put("Rene", 0);
    trieST.put("Re", 1);
    trieST.put("Re", 10);
    trieST.put("Algorithms", 2);
    trieST.put("Algo", 3);
    trieST.putIterative("", 4);
    trieST.putIterative("Algor", 4);
    trieST.putIterative("Tree", 5);
    trieST.putIterative("Trie", 6);
    trieST.putIterative("TST", 7);
    trieST.putIterative("Trie123", 8);
    trieST.putIterative("Z-Function", 9);
    for (String key : trieST.keys()) {
      System.out.println(key);
    }
    // Get tests
    System.out.println("\nGet tests");
    System.out.println("Get Re: " + trieST.get("Re"));
    System.out.println("Expected: 10");
    System.out.println("Get Algorithms: " + trieST.get("Algorithms"));
    System.out.println("Expected: 2");
    System.out.println("Get Trie123: " + trieST.get("Trie123"));
    System.out.println("Expected: 8");
    System.out.println("Get Algori: " + trieST.get("Algori"));
    System.out.println("Expected: null");
    System.out.println("Get Zooom: " + trieST.get("Zooom"));
    System.out.println("Expected: null");
    // GetIterative
    System.out.println("\nGetIterative tests");
    System.out.println("Get Re: " + trieST.getIterative("Re"));
    System.out.println("Expected: 10");
    System.out.println("Get Algorithms: " + trieST.getIterative("Algorithms"));
    System.out.println("Expected: 2");
    System.out.println("Get Trie123: " + trieST.getIterative("Trie123"));
    System.out.println("Expected: 8");
    System.out.println("Get Algori: " + trieST.getIterative("Algori"));
    System.out.println("Expected: null");
    System.out.println("Get Zooom: " + trieST.getIterative("Zooom"));
    System.out.println("Expected: null");
    // Keys with prefix tests
    System.out.println("\nKeys with prefix Tests");
    StringJoiner keysWithPrefix1 = new StringJoiner(" ");
    for (String key : trieST.keysWithPrefix("Alg")) {
      keysWithPrefix1.add(key);
    }
    System.out.println(keysWithPrefix1);
    System.out.println("Expected: Algo Algor Algorithms");
    System.out.println("Keys with prefix T");
    StringJoiner keysWithPrefix2 = new StringJoiner(" ");
    for (String key : trieST.keysWithPrefix("T")) {
      keysWithPrefix2.add(key);
    }
    System.out.println(keysWithPrefix2);
    System.out.println("Expected: TST Tree Trie Trie123");
    System.out.println("Keys with prefix R");
    StringJoiner keysWithPrefix3 = new StringJoiner(" ");
    for (String key : trieST.keysWithPrefix("R")) {
      keysWithPrefix3.add(key);
    }
    System.out.println(keysWithPrefix3);
    System.out.println("Expected: Re Rene");
    System.out.println("Keys with prefix ZZZ");
    StringJoiner keysWithPrefix4 = new StringJoiner(" ");
    for (String key : trieST.keysWithPrefix("ZZZ")) {
      keysWithPrefix4.add(key);
    }
    System.out.println(keysWithPrefix4);
    System.out.println("Expected: ");
    // Keys with prefix iterative tests
    System.out.println("\nKeys with prefix Iterative Tests");
    StringJoiner keysWithPrefixIterative1 = new StringJoiner(" ");
    for (String key : trieST.keysWithPrefixIterative("Alg")) {
      keysWithPrefixIterative1.add(key);
    }
    System.out.println(keysWithPrefixIterative1);
    System.out.println("Expected: Algo Algor Algorithms");
    System.out.println("Keys with prefix T");
    StringJoiner keysWithPrefixIterative2 = new StringJoiner(" ");
    for (String key : trieST.keysWithPrefixIterative("T")) {
      keysWithPrefixIterative2.add(key);
    }
    System.out.println(keysWithPrefixIterative2);
    System.out.println("Expected: TST Tree Trie Trie123");
    System.out.println("Keys with prefix R");
    StringJoiner keysWithPrefixIterative3 = new StringJoiner(" ");
    for (String key : trieST.keysWithPrefixIterative("R")) {
      keysWithPrefixIterative3.add(key);
    }
    System.out.println(keysWithPrefixIterative3);
    System.out.println("Expected: Re Rene");
    System.out.println("Keys with prefix ZZZ");
    StringJoiner keysWithPrefixIterative4 = new StringJoiner(" ");
    for (String key : trieST.keysWithPrefixIterative("ZZZ")) {
      keysWithPrefixIterative4.add(key);
    }
    System.out.println(keysWithPrefixIterative4);
    System.out.println("Expected: ");
    // Longest-prefix-of tests
    System.out.println("\nLongest prefix of Re: " + trieST.longestPrefixOf("Re"));
    System.out.println("Expected: Re");
    System.out.println("Longest prefix of Algori: " + trieST.longestPrefixOf("Algori"));
    System.out.println("Expected: Algor");
    System.out.println("Longest prefix of Trie12345: " + trieST.longestPrefixOf("Trie12345"));
    System.out.println("Expected: Trie123");
    System.out.println("Longest prefix of Zooom: " + trieST.longestPrefixOf("Zooom"));
    System.out.println("Expected: ");
    // Longest-prefix-of-iterative tests
    System.out.println(
        "\nLongest prefix iterative of Re: " + trieST.longestPrefixOfIterative("Re"));
    System.out.println("Expected: Re");
    System.out.println(
        "Longest prefix of iterative Algori: " + trieST.longestPrefixOfIterative("Algori"));
    System.out.println("Expected: Algor");
    System.out.println(
        "Longest prefix of iterative Trie12345: " + trieST.longestPrefixOfIterative("Trie12345"));
    System.out.println("Expected: Trie123");
    System.out.println(
        "Longest prefix of iterative Zooom: " + trieST.longestPrefixOfIterative("Zooom"));
    System.out.println("Expected: ");
    // Delete tests
    trieST.delete("Z-Function");
    System.out.println("\nKeys() after deleting Z-Function key: " + trieST.size());
    for (String key : trieST.keys()) {
      System.out.println(key);
    }
    trieST.delete("Re");
    System.out.println("\nKeys() after deleting Re key: " + trieST.size());
    for (String key : trieST.keys()) {
      System.out.println(key);
    }
    trieST.deleteIterative("Rene");
    System.out.println("\nKeys() after deleting Rene key: " + trieST.size());
    for (String key : trieST.keys()) {
      System.out.println(key);
    }
    trieST.deleteIterative("Trie123");
    System.out.println("\nKeys() after deleting Trie123 key: " + trieST.size());
    for (String key : trieST.keys()) {
      System.out.println(key);
    }
    trieST.deleteIterative("Algorithms");
    System.out.println("\nKeys() after deleting Algorithms key: " + trieST.size());
    for (String key : trieST.keys()) {
      System.out.println(key);
    }
    // TST tests
    TST tst = new TST();
    tst.put("", 0);
    tst.put("Rene", 0);
    tst.put("Re", 1);
    tst.put("Re", 10);
    tst.put("Algorithms", 2);
    tst.put("Algo", 3);
    tst.put("", 4);
    tst.put("Algor", 4);
    tst.put("Tree", 5);
    tst.put("Trie", 6);
    tst.put("TST", 7);
    tst.put("Trie123", 8);
    tst.put("Z-Function", 9);
    System.out.println("\nKeys with prefix TST Tests");
    StringJoiner keysWithPrefixTST1 = new StringJoiner(" ");
    for (String key : tst.keysWithPrefix("Alg")) {
      keysWithPrefixTST1.add(key);
    }
    System.out.println(keysWithPrefixTST1);
    System.out.println("Expected: Algo Algor Algorithms");
    System.out.println("Keys with prefix T");
    StringJoiner keysWithPrefixTST2 = new StringJoiner(" ");
    for (String key : tst.keysWithPrefix("T")) {
      keysWithPrefixTST2.add(key);
    }
    System.out.println(keysWithPrefixTST2);
    System.out.println("Expected: TST Tree Trie Trie123");
    System.out.println("Keys with prefix R");
    StringJoiner keysWithPrefixTST3 = new StringJoiner(" ");
    for (String key : tst.keysWithPrefix("R")) {
      keysWithPrefixTST3.add(key);
    }
    System.out.println(keysWithPrefixTST3);
    System.out.println("Expected: Re Rene");
    System.out.println("Keys with prefix ZZZ");
    StringJoiner keysWithPrefixTST4 = new StringJoiner(" ");
    for (String key : tst.keysWithPrefix("ZZZ")) {
      keysWithPrefixTST4.add(key);
    }
    System.out.println(keysWithPrefixTST4);
    System.out.println("Expected: ");
    System.out.println("\nLongest prefix of Re: " + tst.longestPrefixOf("Re"));
    System.out.println("Expected: Re");
    System.out.println("Longest prefix of Algori: " + tst.longestPrefixOf("Algori"));
    System.out.println("Expected: Algor");
    System.out.println("Longest prefix of Trie12345: " + tst.longestPrefixOf("Trie12345"));
    System.out.println("Expected: Trie123");
    System.out.println("Longest prefix of Zooom: " + tst.longestPrefixOf("Zooom"));
    System.out.println("Expected: ");
  }

  // ===============================================================================================

  /** 5.2.13 R^2向分支的三向单词查找树，即前两层结点为R向单词查找树。 */
  public static class HybridTernarySearchTrie {
    private static final int R = 256;
    // 定义TST矩阵，为R*(R+1)，索引为R的列用于储存单字符的键。
    private Node[][] next = new Node[R][R + 1];

    private static class Node {
      private TST tst;
      private Object val;
    }

    public Object get(String key) {
      int length = key.length();
      if (length == 0) {
        return null;
      }
      char first = key.charAt(0);
      char second = length == 1 ? R : key.charAt(1);
      Node node = next[first][second];
      if (node == null) {
        return null;
      }
      if (length <= 2) {
        return node.val;
      }
      return node.tst.get(key.substring(2, length));
    }

    public void put(String key, Object val) {
      int length = key.length();
      if (length == 0) {
        return;
      }
      char first = key.charAt(0);
      char second = length == 1 ? R : key.charAt(1);
      Node node = next[first][second];
      if (node == null) {
        node = next[first][second] = new Node();
      }
      if (length <= 2) {
        node.val = val;
      } else {
        node.tst = new TST();
        node.tst.put(key.substring(2, length), val);
      }
    }
  }

  // ===============================================================================================

  /** 5.2.21 子字符串匹配，给定一列字符串，找到所有包含指定字符串的所有字符串。 */
  public static class TSTWithList {

    private Node root;

    private static class Node {
      char c;
      Node left, mid, right;
      HashSet<String> strings = new HashSet<>();
    }

    public Iterable<String> keysWithSubstring(String key) {
      Node node = get(root, key, 0);
      return node == null ? null : node.strings;
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
      } else if (d < key.length() - 1) {
        return get(node.mid, key, d + 1);
      } else {
        return node;
      }
    }

    public void add(String key) {
      // 将所有后缀都加入
      for (int d = 0; d < key.length(); d++) {
        root = add(root, key.substring(d), key, 0);
      }
    }

    private Node add(Node node, String key, String s, int d) {
      char c = key.charAt(d);
      if (node == null) {
        node = new Node();
        node.c = c;
      }
      if (c < node.c) {
        node.left = add(node.left, key, s, d);
      } else if (c > node.c) {
        node.right = add(node.right, key, s, d);
      } else {
        if (d < key.length() - 1) {
          node.mid = add(node.mid, key, s, d + 1);
        }
        // 路径上每个结点均添加单词
        node.strings.add(s);
      }
      return node;
    }
  }

  public static void tstWithListTest() {
    TSTWithList tstWithList = new TSTWithList();
    tstWithList.add("Rene");
    tstWithList.add("Reri");
    tstWithList.add("Algorithms");
    tstWithList.add("Algo");
    tstWithList.add("Algor");
    tstWithList.add("Tree");
    tstWithList.add("Trie");
    tstWithList.add("TST");
    tstWithList.add("Trie123");
    tstWithList.add("Z-Function");
    System.out.println("Keys with substring Al");
    StringJoiner keysWithSubstring1 = new StringJoiner(" ");
    for (String key : tstWithList.keysWithSubstring("Al")) {
      keysWithSubstring1.add(key);
    }
    System.out.println(keysWithSubstring1);
    System.out.println("Expected: Algo Algor Algorithms");
    System.out.println("\nKeys with substring gor");
    StringJoiner keysWithSubstring2 = new StringJoiner(" ");
    for (String key : tstWithList.keysWithSubstring("gor")) {
      keysWithSubstring2.add(key);
    }
    System.out.println(keysWithSubstring2);
    System.out.println("Expected: Algor Algorithms");
    System.out.println("\nKeys with substring ri");
    StringJoiner keysWithSubstring3 = new StringJoiner(" ");
    for (String key : tstWithList.keysWithSubstring("ri")) {
      keysWithSubstring3.add(key);
    }
    System.out.println(keysWithSubstring3);
    System.out.println("Expected: Algorithms Reri Trie Trie123");
    System.out.println("\nKeys with substring e");
    StringJoiner keysWithSubstring4 = new StringJoiner(" ");
    for (String key : tstWithList.keysWithSubstring("e")) {
      keysWithSubstring4.add(key);
    }
    System.out.println(keysWithSubstring4);
    System.out.println("Expected: Rene Reri Tree Trie Trie123");
  }

  // ===============================================================================================

}
