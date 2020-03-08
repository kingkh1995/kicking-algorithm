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
        int ans = 0;
        //假设字符集为ASCII码(0-127)，这个数组用来代替map
        int[] index = new int[128];
        //i为窗口的前端，j为窗口的后端
        for (int i = 0, j = 0; j < s.length(); j++) {
            //找到该字符在目标字符串s中的位置（索引加上1）
            //默认为0，即到目前为止还没出现过该字符串
            int charindex = index[s.charAt(j)];
            //如果当前窗口中存在该字符，则窗口前端右滑至位置的右侧
            //此处故给前端索引i赋值字符位置和当前i的大值，而字符位置即为该位置右侧的索引
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
    解题思路：使用一个deque维护一个窗口，遍历数组并按大小顺序存放元素的下标
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
                //如果大于则插入对首
                deque.addFirst(i);
            } else {
                //否则插入对尾，并删除所有比它小的数字，因为之前比它还小的数都没有意义了
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
