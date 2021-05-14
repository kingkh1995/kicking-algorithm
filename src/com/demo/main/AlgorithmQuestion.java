package com.demo.main;

/**
 * 算法题，突破常规思维
 *
 * @author KaiKoo
 * @date 2020/2/25 12:02
 */
public class AlgorithmQuestion {

    private final static AlgorithmQuestion ALGORITHM_QUESTION = new AlgorithmQuestion();

    /*
    每行每列都递增的 m*n 二维数组中查找一个数
     */
    /*
    解题思路：从右上角或者左下角开始查找 时间复杂度：o(m+n)
     */
    public boolean findFromIncreasing2DArray(int[][] array, int num) {
        if (array != null && array.length > 0 && array[0].length >= 0) {
            //列数
            int columnnum = array.length;
            //行数
            int rownum = array[0].length;
            //从右上角开始
            for (int i = 0, j = columnnum - 1; i < rownum && j >= 0; ) {
                int n = array[i][j];
                if (num == n) {
                    return true;
                } else if (num > n) {
                    i++;
                } else {
                    j--;
                }
            }
        }
        return false;
    }

    /*
    从一个排序数组（有重复数字）的某个旋转中找到最小的数字 例：12345的旋转45123
     */
    /*
    解题思路：二分查找 注意特殊情况和循环终止条件
    时间复杂度：o(logn)
     */
    public int lowestNumOfRotatedArray(int[] rotatedArray) {
        int low = 0;
        int high = rotatedArray.length - 1;
        //特殊情况1：low小于high，即旋转为数组本身，直接返回第一个元素
        if (rotatedArray[low] < rotatedArray[high]) {
            return rotatedArray[0];
        }
        //特殊情况2：low=high=mid 只能顺序查找
        if (rotatedArray[low] == rotatedArray[(low + high) / 2]
                && rotatedArray[low] == rotatedArray[high]) {
            //从最左边开始遍历，返回第一个比第一个元素小的元素，否则返回第一个元素
            for (int i : rotatedArray) {
                if (i < rotatedArray[0]) {
                    return i;
                }
            }
            return rotatedArray[0];
        }
        //普遍情况下low大于等于high，因为旋转数组的最大值和和最小值是相邻的，所以终止条件是low和high相临
        while (low < high - 1) {
            int mid = (low + high) / 2;
            //mid只能大于等于low和high，或者小于等于low和high 如果小于则替代high，如果大于则替代low
            if (rotatedArray[mid] >= rotatedArray[low]) {
                low = mid;
            } else if (rotatedArray[mid] <= rotatedArray[high]) {
                high = mid;
            }
        }
        //这根据之前的原则high必然小于等于low，故返回high
        return rotatedArray[high];
    }

    /*
    顺序打印从1到最大的n位十进制数字，输入3，则打印1 2 ... 998 999
     */
    /*
    解题思路1：不能简单的直接求得最大的数字，因为n的数字可能非常大，使用数组实现。
     */
    public void print1ToMaxOfNDigits(int n) {
        if (n < 1) {
            System.out.println("invalid input.");
        }
        //创建n+1长度的全为0的数组，第一位做用来终止循环判断条件
        char[] number = new char[n + 1];
        for (int i = 0; i < n + 1; i++) {
            number[i] = '0';
        }
        while (true) {
            increment(number);
            //循环模拟加1，如果超出n位则终止
            if (number[0] == '0') {
                printNumberFromArray(number);
            } else {
                break;
            }
        }
    }

    //模拟加1
    private void increment(char[] number) {
        for (int i = number.length - 1; i >= 0; i--) {
            //该位为9时设为0，并且进位继续循环，否则加1并跳出循环
            if (number[i] == '9') {
                number[i] = '0';
            } else {
                number[i]++;
                break;
            }
        }
    }

    public static void printNumberFromArray(char[] number) {
        int i = 0;
        //跳过前面的0
        for (; i < number.length; i++) {
            if (number[i] != '0') {
                break;
            }
        }
        for (; i < number.length; i++) {
            System.out.print(number[i]);
        }
        System.out.print(" ");
    }

    /*
    顺时针打印一个二维数组
     */
    /*
    解题思路：边界条件，最后一圈所有的情况：(长n高2)3步 (长1高n)2步 (长n高1)1步
     */
    public void printRectangleClockwise(int[][] rectangle) {
        int row = rectangle.length;
        int column = rectangle[0].length;
        //总共需要的圈数 (Math.min(row, column) + 1) / 2
        for (int n = 0; n < (Math.min(row, column) + 1) / 2; n++) {
            for (int i = n; i < column - n; i++) {
                System.out.print(rectangle[n][i] + " ");
            }
            // 边界条件判断
            for (int j = 1 + n; j < row - n; j++) {
                System.out.print(rectangle[j][column - n - 1] + " ");
            }
            // 边界条件判断
            if (n < row - n - 1) {
                for (int k = column - n - 2; k >= n; k--) {
                    System.out.print(rectangle[row - n - 1][k] + " ");
                }
            }
            // 边界条件判断
            if (n < column - n - 1) {
                for (int l = row - n - 2; l >= 1 + n; l--) {
                    System.out.print(rectangle[l][n] + " ");
                }
            }
        }
    }

    /*
    未排序数组(设数字均大于0)中*可能*有一个数字出现的次数超过数组长度的一半，请找出这个数字
     */
    /*
    解题思路：最多遍历两遍，时间复杂度o(n)
     */
    public int findNumberAppearMoreThanHalfTimes(int[] array) {
        int ans = array[0];
        int count = 1;
        //如果存在出现的次数超过数组长度的一半的数字，则遍历过后，ans必然是该数字
        for (int n : array) {
            if (count == 0) {
                ans = n;
                count++;
            } else if (ans == n) {
                count++;
            } else {
                count--;
            }
        }
        // 此时结果，可能0（不存在该数字直接返回），大于等于1（需要再次遍历，判断一次）、
        if (count == 0) {
            return count;
        }
        count = 0;
        for (int n : array) {
            if (n == ans) {
                count++;
            } else {
                count--;
            }
        }
        return count < 1 ? 0 : ans;
    }

    /*
    获取第i个丑数，丑数的因子只能为2,3,5 第一个丑数是1
     */
    /*
    解题思路：利用空间换取时间，之前的丑数乘以2，3，5必然是丑数
     */
    public int getKthUglyNumber(int k) {
        if (k < 1) {
            return 0;
        }
        int[] arr = new int[k];
        arr[0] = 1;
        //计算出从小到大的k个丑数
        for (int i = 1, start2 = 0, start3 = 0, start5 = 0; i < k; i++) {
            arr[i] = Math.min(Math.min(arr[start2] * 2, arr[start3] * 3), arr[start5] * 5);
            if (arr[start2] * 2 == arr[i]) {
                start2++;
            }
            if (arr[start3] * 3 == arr[i]) {
                start3++;
            }
            if (arr[start5] * 5 == arr[i]) {
                start5++;
            }
        }
        return arr[k - 1];
    }

    /*
    找到和为n的所有连续正整数序列
     */
    /*
    时间复杂度：o(n)
     */
    public void findContinuousSequenceSumEqualsN(int n) {
        if (n < 3) {
            return;
        }
        int low = 1, high = 2;
        int sum = low + high;
        while (low < high && high <= (n + 1) / 2) {
            while (sum <= n) {
                if (sum == n) {
                    for (int i = low; i <= high; i++) {
                        System.out.print(i + " ");
                    }
                    System.out.println();
                }
                sum += ++high;
            }
            while (sum > n && low < high) {
                sum -= low++;
            }
        }
    }

    /*
    约瑟夫问题 总人数 n，每次计数到 m 则淘汰，最后剩下的人是第几位
     */
    /*
    解法1：直接根据公式解出
     */
    public int josephusCircle(int n, int m) {
        if (n < 1 || m < 1) {
            return -1;
        }
        int last = 0;
        for (int i = 2; i <= n; i++) {
            last = (last + m) % i;
        }
        return last + 1;
    }

    /*
    在一个长度为n的数组里的所有数字都在0到n-1的范围内。 数组中某些数字是重复的，找出数组中任意一个重复的数字。
     */
    /*
    时间复杂度：o(n)，虽然是嵌套循环但是每个元素最多只会被判断一次。
     */
    public int findDuplicatedNumberInArray(int[] arr) {
        int length = arr.length;
        for (int i = 0; i < length - 1; i++) {
            //将数字都交换到对应的索引处
            while (arr[i] != i) {
                if (arr[arr[i]] == arr[i]) {
                    return arr[i];
                } else {
                    SortQuestion.swap(arr, i, arr[i]);
                }
            }
        }
        return arr[length - 1];
    }

    /*
    切绳子
     */
    /*
    解题思路：根据数学规律，应该分为2和3的组合，并且2的个数小于三个，因为 2*2*2<3*3
     */
    public long cutRope(int n) {
        if (n == 1) {
            return 1;
        }
        long ans = (long) Math.pow(3, n / 3);
        int mod = n % 3;
        if (mod == 1) {
            return ans / 3 * 4;
        } else if (mod == 2) {
            return ans * 2;
        }
        return ans;
    }

    /*
    替换空格 将空格替换成%20
     */
    /*
    解题思路：先遍历原字符串，匹配到空格则往字符串末尾添加两个空字符，遍历到原字符串尾端时，
        之后从新字符串尾部反向遍历，将原字符移到末尾并替换空格
        时间复杂度：o(n)
     */
    public String replaceSpace(String s) {
        char[] chars = s.toCharArray();
        return null;
    }

    //测试用例

    public static void findFromIncreasing2DArrayTest() {
        int[][] array = new int[][]{{1, 2, 8, 9}, {2, 4, 9, 12}, {4, 7, 10, 13},
                {6, 8, 11, 15}};
        System.out.println(ALGORITHM_QUESTION.findFromIncreasing2DArray(null, 3));
        System.out.println(ALGORITHM_QUESTION.findFromIncreasing2DArray(new int[][]{}, 3));
        System.out.println(ALGORITHM_QUESTION.findFromIncreasing2DArray(new int[][]{{}, {}}, 3));
        System.out.println(ALGORITHM_QUESTION.findFromIncreasing2DArray(array, 3));
        System.out.println(ALGORITHM_QUESTION.findFromIncreasing2DArray(array, 9));
        System.out.println(ALGORITHM_QUESTION.findFromIncreasing2DArray(array, 5));
    }

    public static void lowestNumOfRotatedArrayTest() {
        System.out.println(ALGORITHM_QUESTION.lowestNumOfRotatedArray(new int[]{1}));
        System.out.println(ALGORITHM_QUESTION.lowestNumOfRotatedArray(new int[]{1, 2, 3, 4, 5}));
        System.out.println(ALGORITHM_QUESTION.lowestNumOfRotatedArray(new int[]{1, 1, 1, 1, 1}));
        System.out.println(ALGORITHM_QUESTION.lowestNumOfRotatedArray(new int[]{1, 0, 1, 1, 1}));
        System.out.println(
                ALGORITHM_QUESTION
                        .lowestNumOfRotatedArray(new int[]{2, 2, 3, 0, 1, 2, 2, 2, 2, 2, 2}));
        System.out.println(ALGORITHM_QUESTION.lowestNumOfRotatedArray(new int[]{3, 4, 5, 1, 2}));
        System.out.println(ALGORITHM_QUESTION.lowestNumOfRotatedArray(new int[]{2, 3, 4, 5, 1}));
        System.out.println(ALGORITHM_QUESTION.lowestNumOfRotatedArray(new int[]{2, 3, 1, 1}));
    }

    public static void print1ToMaxOfNDigitsTest() {
        ALGORITHM_QUESTION.print1ToMaxOfNDigits(2);
    }

    public static void printRectangleClockwiseTest() {
        ALGORITHM_QUESTION.printRectangleClockwise(
                new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}, {13, 14, 15}});
        System.out.println();
        ALGORITHM_QUESTION.printRectangleClockwise(
                new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}});
        System.out.println();
        ALGORITHM_QUESTION.printRectangleClockwise(
                new int[][]{{1, 2, 3, 4, 5}, {6, 7, 8, 9, 10}, {11, 12, 13, 14, 15}});
        System.out.println();
    }

    public static void findNumberAppearMoreThanHalfTimesTest() {
        System.out.println(ALGORITHM_QUESTION
                .findNumberAppearMoreThanHalfTimes(new int[]{1, 2, 1, 2, 1, 2, 3, 3, 3}));
        System.out.println(ALGORITHM_QUESTION
                .findNumberAppearMoreThanHalfTimes(new int[]{1, 2, 3, 2, 2, 2, 5, 2, 4}));
    }

    public static void getKthUglyNumberTest() {
        System.out.println(ALGORITHM_QUESTION.getKthUglyNumber(0));
        System.out.println(ALGORITHM_QUESTION.getKthUglyNumber(1));
        System.out.println(ALGORITHM_QUESTION.getKthUglyNumber(10));
        System.out.println(ALGORITHM_QUESTION.getKthUglyNumber(100));
        System.out.println(ALGORITHM_QUESTION.getKthUglyNumber(1000));
    }

    public static void findContinuousSequenceSumEqualsNTest() {
        ALGORITHM_QUESTION.findContinuousSequenceSumEqualsN(3);
        ALGORITHM_QUESTION.findContinuousSequenceSumEqualsN(9);
        ALGORITHM_QUESTION.findContinuousSequenceSumEqualsN(100);
    }

    public static void josephusCircleTest() {
        System.out.println(ALGORITHM_QUESTION.josephusCircle(41, 5));
    }

    public static void findDuplicatedNumberInArrayTest() {
        System.out.println(ALGORITHM_QUESTION
                .findDuplicatedNumberInArray(new int[]{0, 1, 2, 5, 4, 3, 5, 4, 7, 8}));
    }

}
