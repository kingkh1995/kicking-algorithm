package com.kkk.hot100;

/**
 * 递归和迭代 <br>
 *
 * @author KaiKoo
 */
public class RecursionAndIterationHot {

  /**
   * 5. 最长回文子串 <br>
   * 中心扩散法，使用迭代，从每一个位置往两边扩散比较字符求解。
   */
  class longestPalindromeSolution {
    char[] charAt;
    int ansL; // 最长回文子串起点索引
    int ansH; // 最长回文子串终点索引

    public String longestPalindrome(String s) {
      if (s.length() < 2) {
        return s;
      }
      charAt = s.toCharArray();
      ansL = 0;
      ansH = 0;
      for (int i = 0; i < s.length(); i++) {
        expandAroundCenter(i, i); // 奇数回文串
        expandAroundCenter(i, i + 1); // 偶数回文串
      }
      return s.substring(ansL, ansH + 1);
    }

    private void expandAroundCenter(int l, int h) {
      while (l >= 0 && h < charAt.length && charAt[l] == charAt[h]) {
        --l;
        ++h;
      }
      // 为开区间需要内缩一格，更新结果。
      if (--h - ++l > ansH - ansL) {
        ansH = h;
        ansL = l;
      }
    }
  }

  /** 121. 买卖股票的最佳时机 <br> */
  public int maxProfit(int[] prices) {
    int ans = 0, min = Integer.MAX_VALUE; // min为遍历过程中的最低价格
    for (int n : prices) {
      ans = Math.max(ans, n - (min = Math.min(min, n)));
    }
    return ans;
  }

  /**
   * 238. 除自身以外数组的乘积 <br>
   * 先从左往右遍历，求出前缀元素乘积，再从右往左遍历，累乘上所有后缀元素。
   */
  public int[] productExceptSelf(int[] nums) {
    int[] ans = new int[nums.length];
    ans[0] = 1;
    for (int i = 1; i < nums.length; ++i) {
      ans[i] = ans[i - 1] * nums[i - 1];
    }
    int m = 1; // 从右往左遍历使用辅助变量
    for (int i = nums.length - 1; i >= 0; --i) {
      ans[i] *= m;
      m *= nums[i];
    }
    return ans;
  }

  /**
   * 283. 移动零 <br>
   * 最佳解法：从左至右遍历数组，非零则移动到数组前面非零区间的最后，最后将剩余设置为0，只需要遍历一次。 <br>
   * 其他解法：使用冒泡排序的思想，如果前一位为0则与后一位交换。
   */
  public void moveZeroes(int[] nums) {
    int index = 0; // 数组后面全0区间的起始位置
    for (int i = 0; i < nums.length; ++i) {
      if (nums[i] != 0) {
        nums[index++] = nums[i];
      }
    }
    while (index < nums.length) {
      nums[index++] = 0;
    }
  }

  /**
   * 647. 回文子串 <br>
   * 与第5题相同使用中心扩散法判断回文子串。
   */
  class countSubstringsSolution {
    char[] charAt;
    int count;

    public int countSubstrings(String s) {
      charAt = s.toCharArray();
      count = 0;
      for (int i = 0; i < s.length(); i++) {
        expandAroundCenter(i, i); // 奇数回文串
        expandAroundCenter(i, i + 1); // 偶数回文串
      }
      return count;
    }

    private void expandAroundCenter(int l, int h) {
      while (l >= 0 && h < charAt.length && charAt[l--] == charAt[h++]) {
        count++;
      }
    }
  }

  // ===============================================================================================

  /**
   * 32. 最长有效括号 <br>
   * 遍历查找，从左往右，统计左右括号的个数，两者最终相等时则表示有效的，一旦右括号数量大于左括号则是无效的， <br>
   * 需要重置区间从当前位置开始，不需要记录区间位置，因为计数总和就是区间的长度。<br>
   * 如果左括号永远大于右括号个数则无法求解，因此需要从右往左再次遍历一遍，左括号数量大于右括号则判断无效。
   */
  public int longestValidParentheses(String s) {
    int ans = 0, left = 0, right = 0;
    for (int i = 0; i < s.length(); i++) {
      if (s.charAt(i) == '(') {
        left++;
      } else {
        right++;
      }
      if (left == right) {
        ans = Math.max(ans, left << 1);
      } else if (right > left) {
        left = right = 0;
      }
    }
    left = right = 0;
    for (int i = s.length() - 1; i >= 0; i--) {
      if (s.charAt(i) == '(') {
        left++;
      } else {
        right++;
      }
      if (left == right) {
        ans = Math.max(ans, left << 1);
      } else if (left > right) {
        left = right = 0;
      }
    }
    return ans;
  }
}
