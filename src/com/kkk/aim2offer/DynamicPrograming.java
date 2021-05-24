package com.kkk.aim2offer;

/**
 * 动态规划问题 动态规划求解问题的四个特征： ①求一个问题的最优解； ②整体的问题的最优解是依赖于各个子问题的最优解； ③小问题之间还有相互重叠的更小的子问题；
 * ④从上往下分析问题，从下往上求解问题；
 *
 * @author KaiKoo
 * @date 2020/2/7 15:38
 */
/*
    f(i)={pData[i]          (i=0或不满足给定条件)
         {f(i-1)+pData[i]   (i!=0或满足给定条件)
    从前往后计算出所有的f(i)值
 */
public class DynamicPrograming {

    private final static DynamicPrograming DYNAMIC_PROGRAMING = new DynamicPrograming();

    /*
    连续子数组的最大和
     */
    public int findGreatestSumOfSubArray(int[] arr) {
        if (arr == null || arr.length < 1) {
            throw new IllegalArgumentException("invalid input");
        }
        int sum = arr[0];
        int ans = sum;
        //动态规划需要满足的给定条件就是f(i-1)大于0
        for (int i = 1; i < arr.length; i++) {
            if (sum < 0) {
                sum = arr[i];
            } else {
                sum += arr[i];
            }
            ans = Math.max(ans, sum);
        }
        return ans;
    }

    /*
    切绳子
     */
    /*
    解题思路：使用动态规划解决问题，拆分为子问题，最大乘积为拆成两端之后各段最大乘积的乘积
     */
    public long cutRope(int n) {
        //因为至少切成两段，所有2和3时需要特殊处理
        if (n == 2) {
            return 1;
        } else if (n == 3) {
            return 2;
        }
        //f(n)代表长度为n时的最大乘积（可以不切）
        long[] dp = new long[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        dp[2] = 2;
        dp[3] = 3;
        for (int i = 4; i <= n; i++) {
            long max = 0;
            for (int j = 1; j <= i / 2; j++) {
                max = Math.max(max, dp[j] * dp[i - j]);
            }
            dp[i] = max;
        }
        return dp[n];
    }

    /*
    实现一个函数用来匹配包括'.'和'*'的正则表达式。
    '.'表示匹配任意一个字符，'*'表示它前面的字符可以出现任意次（包含0次）,'.*'则表示任意字符出现任意次数。
     */
    /*
    解题思路：使用反向动态规划，构建二维数组dp[length1+1][length2+1]，先假设dp[length1][length2]匹配，求得dp[0][0]
        dp[i][j]表示str的前i位和pattern的前j位是匹配的
     */
    public boolean regexMatch(String str, String pattern) {
        if (str == null || pattern == null) {
            return false;
        }
        char[] sArr = str.toCharArray();
        char[] pArr = pattern.toCharArray();
        int sLength = sArr.length;
        int pLength = pArr.length;
        boolean[][] dp = new boolean[sLength + 1][pLength + 1];
        dp[sLength][pLength] = true;
        //外循环：从str空串开始匹配 因为可能不存在匹配的字符
        for (int i = sLength; i >= 0; i--) {
            //内循环：从pattern最后一个字符开始匹配
            for (int j = pLength - 1; j >= 0; j--) {
                //针对*的判断，如果存在对应匹配则dp[i][j]=dp[i+1][j] 如果不存在则dp[i][j]=dp[i][j+2]
                if (j < pLength - 1 && pArr[j + 1] == '*') {
                    //如果是 * 并且相等 则可能存在对应的匹配也可能不存在
                    if (i < sLength && (sArr[i] == pArr[j] || pArr[j] == '.')) {
                        dp[i][j] = dp[i][j + 2] || dp[i + 1][j];
                    } else {
                        //如果是 * 但不等 则字符串中一定不存在*对应的匹配
                        dp[i][j] = dp[i][j + 2];
                    }
                } else {
                    //若不是 "*",看当前是否相等
                    if (i < sLength && (sArr[i] == pArr[j] || pArr[j] == '.')) {
                        //如果相等
                        dp[i][j] = dp[i + 1][j + 1];
                    }
                    //否则 do nothing
                }
            }
        }
        return dp[0][0];
    }

    //测试用例

    public static void findGreatestSumOfSubArrayTest() {
        System.out.println(DYNAMIC_PROGRAMING
                .findGreatestSumOfSubArray(new int[]{1, -2, 3, 10, -4, 7, 2, -5}));
    }

    public static void cutRopeTest() {
        for (int i = 2; i <= 60; i++) {
            System.out.println(DYNAMIC_PROGRAMING.cutRope(i));
        }
    }

    public static void regexMatchTest() {
        System.out.println(DYNAMIC_PROGRAMING.regexMatch("aa", "."));
        System.out.println(DYNAMIC_PROGRAMING.regexMatch("aaa", "a.a"));
        System.out.println(DYNAMIC_PROGRAMING.regexMatch("aaa", "ab*ac*a"));
        System.out.println(DYNAMIC_PROGRAMING.regexMatch("aaa", "aa.a"));
        System.out.println(DYNAMIC_PROGRAMING.regexMatch("aaa", "ab*a"));
    }
}
