package com.kkk.hot;

import java.util.ArrayList;
import java.util.List;

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
    // 从右往左遍历时，直接使用nums数组作为辅助。
    for (int i = nums.length - 2; i >= 0; --i) {
      ans[i] *= nums[i + 1];
      nums[i] *= nums[i + 1];
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
   * 299. 猜数字游戏 <br>
   * 遍历求解即可
   */
  public String getHint(String secret, String guess) {
    int l = secret.length(), bulls = 0, cows = 0;
    int[] freqS = new int[10], freqG = new int[10];
    char[] charAtS = secret.toCharArray(), charAtG = guess.toCharArray();
    for (int i = 0; i < l; ++i) {
      if (charAtS[i] == charAtG[i]) {
        ++bulls;
      } else {
        ++freqS[charAtS[i] - '0'];
        ++freqG[charAtG[i] - '0'];
      }
    }
    for (int i = 0; i < 10; ++i) {
      cows += Math.min(freqS[i], freqG[i]);
    }
    return bulls + "A" + cows + "B";
  }

  /** 409. 最长回文串 <br> */
  public int longestPalindrome(String s) {
    int[] count = new int[128];
    for (char c : s.toCharArray()) {
      count[c]++;
    }
    int ans = 0;
    for (int n : count) {
      ans += (n >> 1 << 1); // 或 n & ~1
    }
    return Math.min(ans + 1, s.length()); // 任意单个字符都可以使长度加1，只要不超出s的长度即可。
  }

  /**
   * 448. 找到所有数组中消失的数字 <br>
   * 不使用额外空间，直接在原数组上修改。遍历并修改nums[nums[i]-1]，最终没有被修改的下标+1则是消失的数字。
   */
  public List<Integer> findDisappearedNumbers(int[] nums) {
    int n = nums.length;
    for (int i = 0; i < n; ++i) {
      nums[nums[i] % (n + 1) - 1] += n + 1; // 数字在[1, n]范围内，故每次加上n+1，获取原值时使用%。
    }
    List<Integer> ans = new ArrayList<>(n);
    for (int i = 0; i < n; ++i) {
      if (nums[i] < n + 1) {
        ans.add(i + 1);
      }
    }
    return ans;
  }

  /**
   * 647. 回文子串 <br>
   * 与【5题】相同使用中心扩散法判断回文子串。
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

  /**
   * 50. Pow(x, n) <br>
   * 递归解法：将n二分后，结果自乘，如果n为奇数，则需要再乘上一个x；<br>
   * 迭代解法：根据n的二进制表示，某一位为1，则乘上x^(2^i)，从最低位开始累乘。
   */
  class myPowSolution {
    public double myPow(double x, int n) {
      return n >= 0 ? pow(x, n) : 1D / pow(x, -(long) n); // 处理-2^31，先转为long，再转为转为正数
    }

    private double pow(double x, long n) { // 递归解法
      if (n == 0) {
        return 1D;
      }
      double ans = pow(x, n / 2);
      ans *= ans;
      return (n & 1) == 1 ? ans * x : ans;
    }

    private double pow0(double x, long n) { // 迭代解法
      double ans = 1, temp = x; // temp为x^(2^i)的结果
      while (n > 0) {
        if ((n & 1) == 1) {
          ans *= temp;
        }
        temp *= temp;
        n >>= 1;
      }
      return ans;
    }
  }
}
