package com.demo.main;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * 滑动窗口
 *
 * @author KaiKoo
 * @date 2020/3/8 19:40
 */
public class SlidingWindow {

    private final static SlidingWindow SLIDING_WINDOW = new SlidingWindow();

    /*
    找出一个字符串其中不含有重复字符的 最长子串 的长度
     */
    /*
    解题思路：构建一个滑动窗口，遍历整个字符串，如果第一次发现了重复的字符，则直接跳过整个子字符串
    时间复杂度：只需要遍历一遍，即为o(n)
     */
    public int lengthOfLongestUnduplicatedSubString(String s) {
        if (s == null || s == "") {
            return 0;
        }
        //用ans记录下窗口滑动过程中窗口长度的最大值
        int ans = 0;
        //假设字符集为ASCII码(0-127)，这个数组用来代替map
        int[] index = new int[128];
        //i为窗口的前端，j为窗口的后端
        for (int i = 0, j = 0; j < s.length(); j++) {
            ///用数组记录下该字符上一次出现的索引加1，如果为0表示该字符串未出现过
            int charindex = index[s.charAt(j)];
            //如果该字符出现过，需要将窗口前端设为该字符上次出现位置的右边和当前窗口前端的最大值，因为字符串是不允许重复的。
            i = Math.max(charindex, i);
            //结果为当前窗口长度和遍历中的结果的大值
            ans = Math.max(ans, j - i + 1);
            //更新或者设置字符在s中的位置
            index[s.charAt(j)] = j + 1;
        }
        return ans;
    }

    /*
    给定一个数组和滑动窗口的大小，找出所有滑动窗口里数值的最大值。
     */
    /*
    解题思路：使用一个deque维护一个窗口，遍历数组将元素依次加入队列中，大值往队首加，小值往队尾加。
    时间复杂度：o(n)
     */
    public List<Integer> maxInWindows(int[] arr, int size) {
        List<Integer> ans = new ArrayList<>();
        Deque<Integer> deque = new LinkedList<>();
        for (int i = 0; i < arr.length; i++) {
            //先去掉已超出窗口的元素
            while (!deque.isEmpty() && i - deque.peekFirst() >= size) {
                deque.pollFirst();
            }
            if (deque.isEmpty() || arr[i] > arr[deque.peekFirst()]) {
                //如果大于则插入队首
                deque.addFirst(i);
            } else {
                //否则插入队尾，并删除所有比它小的数字，因为之前比它还小的数都没有意义了
                while (arr[i] > arr[deque.peekLast()]) {
                    deque.pollLast();
                }
                deque.addLast(i);
            }
            if (i >= size - 1) {
                ans.add(arr[deque.peekFirst()]);
            }
        }
        return ans;
    }

    //测试用例

    public static void lengthOfLongestUnduplicatedSubStringTest() {
        System.out.println(SLIDING_WINDOW.lengthOfLongestUnduplicatedSubString(null));
        System.out.println(SLIDING_WINDOW.lengthOfLongestUnduplicatedSubString(""));
        System.out.println(SLIDING_WINDOW.lengthOfLongestUnduplicatedSubString("a"));
        System.out.println(SLIDING_WINDOW.lengthOfLongestUnduplicatedSubString("aaaaa"));
        System.out.println(SLIDING_WINDOW.lengthOfLongestUnduplicatedSubString("abcabcdea"));
        System.out.println(SLIDING_WINDOW.lengthOfLongestUnduplicatedSubString("wabcawbc"));
    }

    public static void maxInWindowsTest() {
        System.out.println(SLIDING_WINDOW.maxInWindows(new int[]{2, 3, 4, 2, 6, 2, 5, 1}, 3));
    }
}
