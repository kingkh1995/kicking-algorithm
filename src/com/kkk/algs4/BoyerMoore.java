package com.kkk.algs4;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class BoyerMoore {

  private static int R = 256;

  private String pat;
  private int[] right;

  public BoyerMoore(String pat) {
    this.pat = pat;
    this.right = new int[R];
    // 构造right数组先初始化为-1
    for (int c = 0; c < R; c++) {
      right[c] = -1;
    }
    // 记录字符在模式中的最右位置
    for (int j = 0; j < pat.length(); j++) {
      right[pat.charAt(j)] = j;
    }
  }

  public int search(String txt) {
    int n = txt.length(), m = pat.length();
    int skip;
    for (int i = 0; i <= n - m; i += skip) {
      // 每轮开始将文本指针移动的距离置为0
      skip = 0;
      // 模板指针从右往左扫描
      for (int j = m - 1; j >= 0; j--) {
        // 不匹配则当前一轮扫描结束，文本指针右移对齐。
        if (pat.charAt(j) != txt.charAt(i + j)) {
          skip = j - right[txt.charAt(i + j)];
          // 如果模板指针当前位置在需要右移位置的左边，则将i只能右移一位。
          if (skip < 1) {
            skip = 1;
          }
          break;
        }
      }
      // 文本指针不需要移动则表示匹配到结果
      if (skip == 0) {
        return i;
      }
    }
    // 未找到匹配
    return n;
  }

  public static void main(String[] args) {
    String text = "abacadabrabracabracadabrabrabracad";

    String pattern1 = "abracadabra";
    int index1 = new BoyerMoore(pattern1).search(text);
    System.out.println("Index 1: " + index1 + " Expected: 14");

    String pattern2 = "rab";
    int index2 = new BoyerMoore(pattern2).search(text);
    System.out.println("Index 2: " + index2 + " Expected: 8");

    String pattern3 = "bcara";
    int index3 = new BoyerMoore(pattern3).search(text);
    System.out.println("Index 3: " + index3 + " Expected: 34");

    String pattern4 = "rabrabracad";
    int index4 = new BoyerMoore(pattern4).search(text);
    System.out.println("Index 4: " + index4 + " Expected: 23");

    String pattern5 = "abacad";
    int index5 = new BoyerMoore(pattern5).search(text);
    System.out.println("Index 5: " + index5 + " Expected: 0");
  }
}
