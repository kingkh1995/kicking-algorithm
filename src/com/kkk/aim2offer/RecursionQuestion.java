package com.kkk.aim2offer;

/**
 * 递归问题
 *
 * @author KaiKoo
 * @date 2020/2/28 20:05
 */
public class RecursionQuestion {

    private final static RecursionQuestion RECURSION_QUESTION = new RecursionQuestion();

    /*
    汉诺塔
     */
    /*
        1                           1
        2           --->>           2
        3                           3
        -   -   -           -   -   -
        x   y   z           x   y   z

        将1至n层从x移动到z
     */
    public void hanoiTower(int n, char from, char inter, char to) {
        if (n == 1) {
            //只有第1层时直接移动
            System.out.println(n + " from " + from + " to " + to);
        } else {
            // 1至n-1层 从 目标杆 放到 辅助杆
            hanoiTower(n - 1, from, to, inter);
            //移动最下层
            System.out.println(n + " from " + from + " to " + to);
            // 1至n-1层 从 辅助杆 放到 目标杆
            hanoiTower(n - 1, inter, from, to);
        }
    }

    /*
    一只青蛙一次可以跳1到n级，那么跳上n级共有多少种方法
     */
    /*
    解题思路：本质上结果为 2^(n-1)
     */
    public int frogJump(int n) {
        int count = 1;
        if (n > 1) {
            for (int i = 1; i < n; i++) {
                count = count + frogJump(n - i);
            }
        }
        return count;
    }

    /*
    用n个m*1的小矩形无重叠地覆盖一个m*n的大矩形，总共有多少种方法
     */
    /*
    解题思路：斐波那契数列 f(n)=f(n-1)+f(n-m)
     */
    public int rectangleCover(int n, int m) {
        if (n < m) {
            return 1;
        } else if (n == m) {
            return 2;
        } else {
            return rectangleCover(n - 1, m) + rectangleCover(n - m, m);
        }
    }

    /*
    顺序打印从1到最大的n位十进制数字，输入3，则打印1 2 ... 998 999
     */
    /*
    解题思路2：使用递归求得n位数字的全排列
     */
    public void print1ToMaxOfNDigits(int n) {
        if (n < 1) {
            System.out.println("invalid input.");
        }
        //从最高位开始顺序组合
        digitCombine(new char[n], 0);
    }

    //求所有数字组合
    private void digitCombine(char[] number, int i) {
        for (int j = 0; j < 10; j++) {
            number[i] = (char) ('0' + j);
            //递归下一位直到最后一位时打印数字
            if (i == number.length - 1) {
                AlgorithmQuestion.printNumberFromArray(number);
            } else {
                digitCombine(number, i + 1);
            }
        }
    }

    /*
    输入一个字符串(有字符重复),字符为ASCII码，打印出该字符串中字符的所有排列组合。如 aab -> a b ab ba aa aab aba baa
     */
    public void characterCombination(String s) {
        if (s != null && s.length() > 0) {
            //统计字符串中字符出现的次数
            int[] index = new int[128];
            for (char c : s.toCharArray()) {
                index[c]++;
            }
            for (int i = 1; i <= s.length(); i++) {
                //从头开始选择i个字符
                selectCharacter(index, new StringBuilder(), i);
            }
        }
    }

    //从所有字符中选择n个字符
    private void selectCharacter(int[] index, StringBuilder sb, int n) {
        for (int i = 0; i < index.length; i++) {
            if (index[i] > 0) {
                if (n > 1) {
                    //选定一个字符后，选择下一个字符
                    sb.append((char) i);
                    index[i]--;
                    selectCharacter(index, sb, n - 1);
                    //复原，进入下一个循环
                    index[i]++;
                    sb.deleteCharAt(sb.length() - 1);
                } else {
                    System.out.println(sb.toString() + (char) i);
                }
            }
        }
    }

    /*
    从1-n中出现了统计1总共出现了多少次
     */
    /*
    解题思路：根据数字规律去递归 如54321
    第一步；统计万位出现1次数 第二步统计之后四位出现1的次数，又分为（1-49999）和（50000-54321）（等于f(4321)）两组统计
    （1-49999）中后四位1出现的次数等于4*（0000-9999中1出现次数），
    （0000-9999)中1出现在每一位的次数都是10^3（固定一位为1，剩余3位随意组合，所以有10^3种）
     */
    public int count1Between1AndN(int n) {
        if (n == 0) {
            return 0;
        } else if (n < 10) {
            return 1;
        }
        String num = String.valueOf(n);
        int count = 0;
        int high = num.charAt(0) - '0';
        //先算出最高位1出现的次数
        if (high > 1) {
            //最高位大于1，则1在最高位出现了10^n次
            count += Math.pow(10, num.length() - 1);
        } else {
            //最高位等于1，则1在最高位出现的次数为低位的值+1
            count += Integer.valueOf(num.substring(1)) + 1;
        }
        //最高位之后出现1的次数
        count += high * (num.length() - 1) * Math.pow(10, num.length() - 2) + count1Between1AndN(
                Integer.valueOf(num.substring(1)));
        return count;
    }

    /*
     掷出n个骰子，总点数为s，求s所有可能值出现的概率
     */
    /*
    解题思路：n个m面的骰子总点数为x的次数 为 n-1个骰子总点数为x-1,x-2至x-m的次数之和
     */
    public void printProbabilityOfPointSum(int n, int m) {
        if (n < 1 || m < 1) {
            throw new IllegalStateException("invalid input.");
        }
        int[] ans = probabilityOfPointSum(n, m);
        for (int i = 0; i < ans.length; i++) {
            if (ans[i] > 0) {
                System.out.print(i + 1 + "(" + ans[i] + ") ");
            }
        }
    }

    private int[] probabilityOfPointSum(int n, int m) {
        int[] ans = new int[n * m];
        if (n == 1) {
            for (int i = 0; i < ans.length; i++) {
                ans[i] = 1;
            }
        } else {
            int[] preAns = probabilityOfPointSum(n - 1, m);
            for (int i = 0; i < ans.length; i++) {
                for (int j = 1; j <= m; j++) {
                    if (i - j >= 0 && i - j < (n - 1) * m) {
                        ans[i] += preAns[i - j];
                    }
                }
            }
        }
        return ans;
    }

    //测试用例

    public static void hanoiTowerTest() {
        RECURSION_QUESTION.hanoiTower(3, 'x', 'y', 'z');
    }

    public static void frogJumpTest() {
        for (int i = 1; i < 10; i++) {
            System.out.println(RECURSION_QUESTION.frogJump(i));
        }
    }

    public static void rectangleCoverTest() {
        for (int j = 2; j < 6; j++) {
            for (int i = 1; i < 10; i++) {
                System.out.print(RECURSION_QUESTION.rectangleCover(i, j) + " ");
            }
            System.out.println();
        }
    }

    public static void print1ToMaxOfNDigitsTest() {
        RECURSION_QUESTION.print1ToMaxOfNDigits(2);
    }

    public static void characterCombinationTest() {
        RECURSION_QUESTION.characterCombination("abcaba");
    }

    public static void count1Between1AndNTest() {
        System.out.println(RECURSION_QUESTION.count1Between1AndN(1234506789));
        System.out.println(RECURSION_QUESTION.count1Between1AndN(1234567890));
        System.out.println(RECURSION_QUESTION.count1Between1AndN(9999));
        System.out.println(RECURSION_QUESTION.count1Between1AndN(9));
        System.out.println(RECURSION_QUESTION.count1Between1AndN(52));
        System.out.println(RECURSION_QUESTION.count1Between1AndN(10));
    }

    public static void printProbabilityOfPointSumTest() {
        RECURSION_QUESTION.printProbabilityOfPointSum(6, 6);
    }
}
