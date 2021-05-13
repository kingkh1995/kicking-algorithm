package com.demo.main;

/**
 * 位运算 重要的技巧：{ (n - 1) & n } 一个整数减去1再与原来的整数做与运算的结果为把原整数二进制表示的 最右边的1 变成0
 *
 * @author KaiKoo
 * @date 2020/2/28 20:54
 */
/*
  负数的补码 对应的正数取反然后在最低位加1，补码是为了让0只能有一种表示
    8-> 0000 .... 0000 1000  6-> 0000 .... 0000 0 110
   -8-> 1111 .... 1111 1000 -6-> 1111 .... 1111 1 010
 */
public class BitOperation {

    private final static BitOperation BIT_OPERATION = new BitOperation();

    /*
    一个数二进制表示中1的个数，其中负数用补码表示。
     */
    /*
    解题思路1：用&运算求得1的个数
     */
    public int numberOf1InBinary1(int n) {
        int flag = 1;
        int count = 0;
        for (int i = 0; i < 32; i++) {
            if ((flag & n) != 0) {
                count++;
            }
            flag = flag << 1;
        }
        return count;
    }

    /*
    解题思路2：利用(n - 1) & n不断将n的二进制表示最右边的1变为0
     */
    public int numberOf1InBinary2(int n) {
        int count = 0;
        while (n != 0) {
            n = (n - 1) & n;
            count++;
        }
        return count;
    }

    /*
    一个整数数组中有两个数字只出现了一次，其他数字出现了两次，找出这两个数字 时间复杂度o(n) 空间复杂度o(1)
     */
    /*
    解题思路：异或运算 n^n=0 n^0=n 将数组分为两个数组
    全部数字异或一遍，最后的结果就是两个特殊数字异或的结果，使用(n - 1) & n^n技巧找到两个数字不同的最低位，
    这样就可以将数组分为两个数组，并将两个特殊数字分开来，每个数组全部异或一遍即得到结果。
     */
    public int[] findTwoNumbersAppearOnce(int[] arr) {
        int temp = 0;
        for (int i : arr) {
            temp ^= i;
        }
        temp ^= temp & (temp - 1);
        int temp1 = 0;
        int temp2 = 0;
        for (int i : arr) {
            if ((i & temp) == 0) {
                temp1 ^= i;
            } else {
                temp2 ^= i;
            }
        }
        return new int[]{temp1, temp2};
    }

    /*
    使用位运算实现加法，加法就是 位相加和进位再相加 的结果，直到不需要进位就是最终的结果。
     */
    public int addByBitOperation(int a, int b) {
        int ans, inc;
        do {
            //异或运算得到位相加的结果
            ans = a ^ b;
            //与运算之后在左移一位得到进位的结果
            inc = (a & b) << 1;
            a = ans;
            b = inc;
            //如果需要进位，则把两者相加，重复步骤
        } while (inc != 0);
        return ans;
    }

    //测试用例

    public static void numberOf1InBinary1Test() {
        System.out.println(Integer.toBinaryString(0) + BIT_OPERATION.numberOf1InBinary1(0));
        System.out.println(Integer.toBinaryString(-1) + BIT_OPERATION.numberOf1InBinary1(-1));
        System.out.println(Integer.toBinaryString(8) + BIT_OPERATION.numberOf1InBinary1(8));
        System.out.println(Integer.toBinaryString(-8) + BIT_OPERATION.numberOf1InBinary1(-8));
        System.out.println(Integer.toBinaryString(6) + BIT_OPERATION.numberOf1InBinary1(6));
        System.out.println(Integer.toBinaryString(-6) + BIT_OPERATION.numberOf1InBinary1(-6));
        System.out.println(BIT_OPERATION.numberOf1InBinary2(0));
        System.out.println(BIT_OPERATION.numberOf1InBinary2(-1));
        System.out.println(BIT_OPERATION.numberOf1InBinary2(6));
        System.out.println(BIT_OPERATION.numberOf1InBinary2(-8));
    }

    public static void findTwoNumbersAppearOnceTest() {
        int[] two = BIT_OPERATION
                .findTwoNumbersAppearOnce(new int[]{1, 1, 2, 5, 3, 4, 6, 4, 5, 6});
        System.out.println(two[0] + " " + two[1]);
    }

    public static void addByBitOperation() {
        System.out.println(BIT_OPERATION.addByBitOperation(10, 22));
    }
}
