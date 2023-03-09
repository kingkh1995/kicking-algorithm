package com.kkk.acm;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

/**
 * <br>
 *
 * @author KaiKoo
 */
public class SimpleImageExpose {

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    String[] aux = in.nextLine().trim().split(" ");
    int n = aux.length;
    int[] arr = new int[n];
    int sum = 0;
    for (int i = 0; i < n; ++i) {
      arr[i] = Integer.parseInt(aux[i]);
      sum += arr[i];
    }
    int floor =
        128 - BigDecimal.valueOf(sum).divide(BigDecimal.valueOf(n), RoundingMode.FLOOR).intValue();
    int celling =
        128
            - BigDecimal.valueOf(sum)
                .divide(BigDecimal.valueOf(n), RoundingMode.CEILING)
                .intValue();
    int fs = 0, cs = 0;
    for (int num : arr) {
      int a = num + floor;
      if (a < 0) {
        a = 0;
      } else if (a > 255) {
        a = 255;
      }
      fs += a - 128;
      int b = num + celling;
      if (b < 0) {
        b = 0;
      } else if (b > 255) {
        b = 255;
      }
      cs += b - 128;
    }
    int cmp = Math.abs(fs) - Math.abs(cs);
    if (cmp > 0) {
      System.out.println(celling);
    } else if (cmp < 0) {
      System.out.println(floor);
    } else {
      System.out.println(Math.min(floor, celling));
    }
  }
}
