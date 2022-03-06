package com.kkk.algs4;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class KMP {

  private static int R = 256;

  private String pat;
  private int[][] dfa;

  public KMP(String pat) {
    this.pat = pat;
    int m = pat.length();
    this.dfa = new int[R][m];
    dfa[pat.charAt(0)][0] = 1;
    for (int x = 0, j = 1; j < m; j++) {
      // 匹配失败时复制值
      for (int c = 0; c < R; c++) {
        dfa[c][j] = dfa[c][x];
      }
      // 匹配成功设置值
      dfa[pat.charAt(j)][j] = j + 1;
      // 更新重启状态
      x = dfa[pat.charAt(j)][x];
    }
  }

  public int search(String txt) {
    int i, j, n = txt.length(), m = pat.length();
    for (i = 0, j = 0; i < n && j < m; i++) {
      j = dfa[txt.charAt(i)][j];
    }
    if (j == m) {
      return i - m;
    } else {
      return n;
    }
  }

  public static void main(String[] args) {
    String text = "abacadabrabracabracadabrabrabracad";

    String pattern1 = "abracadabra";
    int index1 = new KMP(pattern1).search(text);
    System.out.println("Index 1: " + index1 + " Expected: 14");

    String pattern2 = "rab";
    int index2 = new KMP(pattern2).search(text);
    System.out.println("Index 2: " + index2 + " Expected: 8");

    String pattern3 = "bcara";
    int index3 = new KMP(pattern3).search(text);
    System.out.println("Index 3: " + index3 + " Expected: 34");

    String pattern4 = "rabrabracad";
    int index4 = new KMP(pattern4).search(text);
    System.out.println("Index 4: " + index4 + " Expected: 23");

    String pattern5 = "abacad";
    int index5 = new KMP(pattern5).search(text);
    System.out.println("Index 5: " + index5 + " Expected: 0");
  }
}
