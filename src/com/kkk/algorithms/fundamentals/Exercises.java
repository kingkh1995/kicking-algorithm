package com.kkk.algorithms.fundamentals;

import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author KaiKoo
 */
public class Exercises {

    /**
     * 1.1.9
     * 将一个整数（可正可负，正数省去前面的0）用二进制表示并转换为字符串
     * Integer.toBinaryString(int i)
     */
    public static String toBinaryString(int i) {
        // 特殊情况 0
        if (i == 0) {
            return "0";
        }
        char[] chars = new char[32];
        // 从最右边一位开始使用 & 运算
        int index = 32;
        // 终止条件 i==0 不再存在任何一个 1
        for (; i != 0; ) {
            chars[--index] = (char) ('0' + (i & 1));
            // 无符号左移一位 去除掉低位
            i = i >>> 1;
        }
        // String(char value[], int offset, int count) 包含offset
        return new String(chars, index, 32 - index);
    }

    // 1.1.9
    public static void toBinaryStringTest() {
        int i = ThreadLocalRandom.current().nextInt();
        System.out.println(i);
        System.out.println(Integer.toBinaryString(i));
        System.out.println(Exercises.toBinaryString(i));
        System.out.println(Integer.toBinaryString(-i));
        System.out.println(Exercises.toBinaryString(-i));
    }

}
