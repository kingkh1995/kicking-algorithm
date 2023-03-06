package com.kkk.acm;

import java.util.Scanner;

public class SameSonIP {

  /*
  输入：
  255.255.255.0
  192.168.224.256
  192.168.10.4
  255.0.0.0
  193.194.202.15
  232.43.7.59
  255.255.255.0
  192.168.0.254
  192.168.0.1
  输出：
  1
  2
  0
       */

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    while (in.hasNextLine()) {
      String mask = in.nextLine();
      String ip1 = in.nextLine();
      String ip2 = in.nextLine();
      if (!isValidMask(mask) || !isValidIP(ip1) || !isValidIP(ip2)) {
        System.out.println(1);
      } else if (isSameSonIP(mask, ip1, ip2)) {
        System.out.println(0);
      } else {
        System.out.println(2);
      }
    }
  }

  private static boolean isSameSonIP(String mask, String ip1, String ip2) {
    String[] split0 = mask.split("\\.");
    String[] split1 = ip1.split("\\.");
    String[] split2 = ip2.split("\\.");
    for (int i = 0; i < 4; ++i) {
      int m = Integer.parseInt(split0[i]),
          a = Integer.parseInt(split1[i]),
          b = Integer.parseInt(split2[i]);
      if ((m & a) != (m & b)) {
        return false;
      }
    }
    return true;
  }

  private static boolean isValidIP(String ip) {
    String[] split = ip.split("\\.");
    if (split.length != 4) {
      return false;
    }
    for (String s : split) {
      int i = Integer.parseInt(s);
      if (i < 0 || i > 255) {
        return false;
      }
    }
    return true;
  }

  private static boolean isValidMask(String mask) {
    if (!isValidIP(mask)) {
      return false;
    }
    String[] split = mask.split("\\.");
    StringBuilder sb = new StringBuilder();
    for (String s : split) {
      String binaryString = Integer.toBinaryString(Integer.parseInt(s)); // 转二进制，注意会舍去前面的0
      sb.append(String.format("%08d", Integer.parseInt(binaryString))); // 前面补0到8位长度
    }
    return sb.lastIndexOf("1") + 1 == sb.indexOf("0"); // 二进制表示左边全是1右边全是2
  }
}
