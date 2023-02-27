package com.kkk.hot;

import java.util.Arrays;
import java.util.List;

/**
 * 动态规划 <br>
 * 【隐式动态规划：如果不需要查看所有之前状态则可以直接使用变量保存状态】
 *
 * @author KaiKoo
 */
public class DynamicProgramingHot {

  /**
   * 5. 最长回文子串 <br>
   * 动态规划求解，相比于中心扩散法，空间复杂度为 O(n^2)。
   */
  public String longestPalindrome(String s) {
    int length = s.length();
    boolean[][] dp = new boolean[length][length]; // dp[i][j]表示从i至j的子串是否为回文字符串
    String res = "";
    // dp遍历方向为从下至上，从左至右。
    for (int i = length - 1; i >= 0; i--) {
      for (int j = i; j < length; j++) {
        if (s.charAt(i) == s.charAt(j)) {
          if (j - i > 2) {
            dp[i][j] = dp[i + 1][j - 1];
          } else {
            dp[i][j] = true; // 区间长度小于等于3则直接为true
          }
          // 更新结果
          if (dp[i][j] && j - i + 1 > res.length()) {
            res = s.substring(i, j + 1);
          }
        }
      }
    }
    return res;
  }

  /**
   * 53.最大子数组和 <br>
   * 隐式动态规划，只使用一个变量即可。
   */
  public int maxSubArray(int[] nums) {
    int ans = nums[0], max = nums[0];
    for (int i = 1; i < nums.length; ++i) {
      ans = Math.max(ans, max = nums[i] + Math.max(max, 0));
    }
    return ans;
  }

  /** 62. 不同路径 <br> */
  public int uniquePaths(int m, int n) {
    int[][] dp = new int[m][n]; // dp[i][j]表示可到达该位置的路径数量
    for (int i = 0; i < m; ++i) {
      for (int j = 0; j < n; ++j) {
        if (i == 0 || j == 0) {
          dp[i][j] = 1; // 因为只能下移和右移，故第一行和第一列结果为1。
        } else {
          dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
        }
      }
    }
    return dp[m - 1][n - 1];
  }

  /**
   * 64. 最小路径和 <br>
   * 直接原地DP，不需要辅助空间。
   */
  public int minPathSum(int[][] grid) {
    int m = grid.length, n = grid[0].length;
    // 因为只能下移和右移，故第一行和第一列只能沿着直线移动。
    for (int i = 1; i < m; ++i) {
      grid[i][0] = grid[i - 1][0] + grid[i][0];
    }
    for (int i = 1; i < n; ++i) {
      grid[0][i] = grid[0][i - 1] + grid[0][i];
    }
    for (int i = 1; i < m; ++i) {
      for (int j = 1; j < n; ++j) {
        grid[i][j] = Math.min(grid[i - 1][j], grid[i][j - 1]) + grid[i][j];
      }
    }
    return grid[m - 1][n - 1];
  }

  /**
   * 70. 爬楼梯 <br>
   * 实际上就是计算斐波那契数列，不使用递归，会超出时间限制，使用隐式动态规划。
   */
  public int climbStairs(int n) {
    int p, q = 0, r = 1;
    for (int i = 1; i <= n; ++i) {
      p = q;
      q = r;
      r = p + q;
    }
    return r;
  }

  /**
   * 139. 单词拆分 <br>
   * 可以使用哈希表优化，用单词最后一个字符作为键收集单词，这样可以减少不必要的比对。
   */
  public boolean wordBreak(String s, List<String> wordDict) {
    boolean[] dp = new boolean[s.length() + 1];
    dp[0] = true;
    for (int i = 1, index; i <= s.length(); ++i) {
      for (String word : wordDict) {
        if ((index = i - word.length()) >= 0 // index为单词首字母在字符串中的索引
            && word.equals(s.substring(index, i)) // 当前子串尾部匹配了单词
            && (dp[i] = dp[index])) { // 状态转移
          break;
        }
      }
    }
    return dp[s.length()];
  }

  /**
   * 152. 乘积最大子数组 <br>
   * 隐式动态规划，记录遍历过程中包含尾端元素的乘积最大值和最小值。 <br>
   * nums[i]、nums[i]*max[i-1]、nums[i]*min[i-1]三者最大为max[i]最小为min[i]。
   */
  public int maxProduct(int[] nums) {
    int ans = Integer.MIN_VALUE, max = 1, min = 1, maxN, minN;
    for (int n : nums) {
      max = Math.max(n, Math.max(maxN = max * n, minN = min * n));
      min = Math.min(n, Math.min(maxN, minN));
      ans = Math.max(ans, max);
    }
    return ans;
  }

  /**
   * 198. 打家劫舍 <br>
   * 因为只需要查看上个状态和上上个状态，则可以使用变量保存以节约空间。
   */
  public int rob(int[] nums) {
    int m2 = 0, m1 = nums[0], max = m1;
    for (int i = 1; i < nums.length; ++i) {
      max = Math.max(nums[i] + m2, m1);
      m2 = m1; // 上上个状态
      m1 = max; // 上个状态
    }
    return max;
  }

  /** 221. 最大正方形 <br> */
  public int maximalSquare(char[][] matrix) {
    int ans = 0, m = matrix.length, n = matrix[0].length;
    int[][] dp = new int[m + 1][n + 1]; // 表示最大正方形的长度
    for (int i = 1; i <= m; ++i) {
      for (int j = 1; j <= n; ++j) {
        if (matrix[i - 1][j - 1] == '1') {
          dp[i][j] = 1 + Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]);
          ans = Math.max(ans, dp[i][j] * dp[i][j]);
        }
      }
    }
    return ans;
  }

  /** 279. 完全平方数 <br> */
  public int numSquares(int n) {
    int[] dp = new int[n + 1];
    for (int i = 1; i <= n; ++i) {
      int count = Integer.MAX_VALUE;
      for (int j = 1; j * j <= i; ++j) {
        count = Math.min(count, dp[i - j * j]);
      }
      dp[i] = count + 1;
    }
    return dp[n];
  }

  /**
   * 300. 最长递增子序列 <br>
   * 每一个元素前面的每一个比其小的元素，都可以与当前元素构成一个严格递增的子序列。
   */
  public int lengthOfLIS(int[] nums) {
    int ans = 1;
    int[] dp = new int[nums.length];
    dp[0] = 1;
    for (int i = 1; i < nums.length; ++i) {
      dp[i] = 1;
      for (int j = 0; j < i; ++j) {
        if (nums[i] > nums[j]) {
          dp[i] = Math.max(1 + dp[j], dp[i]);
        }
      }
      ans = Math.max(ans, dp[i]);
    }
    return ans;
  }

  /**
   * 322. 零钱兑换 <br>
   * 也可以使用BFS或DFS，与279题完全平方数相似。
   */
  public int coinChange(int[] coins, int amount) {
    int[] dp = new int[amount + 1];
    Arrays.fill(dp, amount + 1);
    dp[0] = 0;
    for (int i = 1; i <= amount; ++i) {
      for (int n : coins) {
        if (n <= i) {
          dp[i] = Math.min(dp[i], dp[i - n] + 1);
        }
      }
    }
    return dp[amount] > amount ? -1 : dp[amount];
  }

  /** 746. 使用最小花费爬楼梯 <br> */
  public int minCostClimbingStairs(int[] cost) {
    int n = cost.length;
    int[] dp = new int[n + 1];
    for (int i = 2; i <= n; ++i) {
      dp[i] = Math.min(dp[i - 2] + cost[i - 2], dp[i - 1] + cost[i - 1]);
    }
    return dp[n];
  }

  // ===============================================================================================

  /**
   * 10. 正则表达式匹配 <br>
   * '.' 匹配任意单个字符 '*' 匹配零个或多个前面的那一个元素 <br>
   * 关键是边界条件的处理。
   */
  class isMatchSolution {
    char[] sCharAt;
    char[] pCharAt;

    public boolean isMatch(String s, String p) {
      sCharAt = s.toCharArray();
      pCharAt = p.toCharArray();
      int sl = sCharAt.length, pl = pCharAt.length;
      // dp[i][j]表示s前i位匹配p前j位
      boolean[][] dp = new boolean[sl + 1][pl + 1]; // 长度加一则不需要考虑数组越位情况。
      dp[0][0] = true;
      for (int i = 0; i <= sl; ++i) { // 从空字符串开始，因为空字符串也可能匹配模式串。
        for (int j = 1; j <= pl; ++j) {
          if (matches(i, j)) {
            dp[i][j] = dp[i - 1][j - 1];
          } else if ('*' == pCharAt[j - 1]) {
            // *不需要匹配最后一位元素，则p最后两位可以忽略；*匹配了最后一位元素，则s最后一位可以忽略。
            dp[i][j] = dp[i][j - 2] || (matches(i, j - 1) && dp[i - 1][j]);
          }
        }
      }
      return dp[sl][pl];
    }

    // 判断当前给定位置字符是否匹配
    public boolean matches(int i, int j) {
      return i > 0 && ('.' == pCharAt[j - 1] || sCharAt[i - 1] == pCharAt[j - 1]);
    }
  }

  /** 32. 最长有效括号 <br> */
  public int longestValidParentheses(String s) {
    int ans = 0;
    char[] charAt = s.toCharArray();
    // dp[i]为以i结尾的最长满足区间
    int[] dp = new int[s.length()];
    for (int i = 1; i < s.length(); ++i) {
      // 以闭括号结束才可能有效
      if (charAt[i] == ')') {
        if (charAt[i - 1] == '(') {
          // 最后的一对括号可以和dp[i-2]组合后有效。
          dp[i] = (i > 2 ? dp[i - 2] : 0) + 2;
        } else {
          // 为dp[i - 1]区间左端元素的索引
          int k = i - dp[i - 1];
          // 要求dp[i-1]为有效区间，且dp[i-1]不是[0,i-1]区间，且dp[i-1]区间左侧为开括号，dp[i]才有效。
          if (k > 0 && k < i - 1 && charAt[k - 1] == '(') {
            // [k-1,i]区间都有效了，则还需要加上dp[k-2]，因为与dp[k-2]组合后也有效。
            dp[i] = (k > 2 ? dp[k - 2] : 0) + dp[i - 1] + 2;
          }
        }
        ans = Math.max(ans, dp[i]);
      }
    }
    return ans;
  }

  /**
   * 42. 接雨水 <br>
   * 从左到右扫描，记录过程中的最大值，然后从右往左扫描记录过程中的最大值，两个图形的交集即是结果。
   */
  public int trap(int[] height) {
    int n = height.length, ans = 0;
    if (n == 0) {
      return ans;
    }
    int[] leftMax = new int[n], rightMax = new int[n];
    leftMax[0] = height[0];
    for (int i = 1; i < n; ++i) {
      leftMax[i] = Math.max(leftMax[i - 1], height[i]);
    }
    rightMax[n - 1] = height[n - 1];
    for (int i = n - 2; i >= 0; --i) {
      rightMax[i] = Math.max(rightMax[i + 1], height[i]);
    }
    for (int i = 0; i < n; ++i) {
      ans += Math.min(leftMax[i], rightMax[i]) - height[i];
    }
    return ans;
  }

  /**
   * 72. 编辑距离 <br>
   * 注意处理空字符串
   */
  public int minDistance(String word1, String word2) {
    int m = word1.length(), n = word2.length();
    // dp[i][j]表示将0-(i-1)位置的word1子串转换为0-(j-1)位置的word2子串需要的最小操作数
    int[][] dp = new int[m + 1][n + 1];
    for (int i = 1; i <= m; ++i) {
      dp[i][0] = i; // word2为空串，需要i次删除操作。
    }
    for (int i = 1; i <= n; ++i) {
      dp[0][i] = i; // word1为空串，需要i次插入操作。
    }
    for (int i = 1; i <= m; ++i) {
      for (int j = 1; j <= n; ++j) {
        // 对比上一状态，im1需要一次删除；jm1需要一次插入；im1jm1如果最后一位字符不匹配则要一次替换。
        int im1 = dp[i - 1][j] + 1, jm1 = dp[i][j - 1] + 1;
        int im1jm1 = dp[i - 1][j - 1] + (word1.charAt(i - 1) == word2.charAt(j - 1) ? 0 : 1);
        // 取im1、jm1、im1jm1三者最小
        dp[i][j] = Math.min(Math.min(im1, jm1), im1jm1);
      }
    }
    return dp[m][n];
  }

  /**
   * 309. 最佳买卖股票时机含冷冻期 <br>
   * 每一天结束对应三种状态：1、持有股票；2、不持有股票处于冷冻期；3、不持有股票不处于冷冻期。<br>
   * 买入视作负收益，卖出视作正收益，记录下三种状态下每天的最大收益，因为只会查看前一天的状态，故使用变量保存。
   */
  public int maxProfit(int[] prices) {
    int n = prices.length, m1 = -prices[0], m2 = 0, m3 = 0;
    for (int i = 1; i < n; ++i) {
      int mm1 = Math.max(m1, m3 - prices[i]); // 状态1：昨天就持有股票今天不操作、昨天不持有股票今天买入
      int mm2 = Math.max(m2, m1 + prices[i]); // 状态2：昨天卖出、今天卖出
      int mm3 = Math.max(m2, m3); // 状态3：昨天就不持有股票
      m1 = mm1;
      m2 = mm2;
      m3 = mm3;
    }
    return Math.max(m2, m3); // 最后一天的最大收益只能是不持有股票的状态
  }

  /**
   * 312. 戳气球 <br>
   * 反向思维，由戳气球看成填气球，这样则不需要考虑气球相邻关系的改变。<br>
   * 使用分治的思想，确定了某个气球是第一个填充之后，可以将求该区间的结果拆分为分别求左右区间的结果，<br>
   * 因为左右区间被隔离开来，故两个区间的气球永远不可能作为另外区间的邻居，所以两个问题是独立的。
   */
  public int maxCoins(int[] nums) {
    int n = nums.length + 2; // 创建新气球数组，处理越界问题。
    int[] aux = new int[n];
    aux[0] = aux[n - 1] = 1;
    System.arraycopy(nums, 0, aux, 1, n - 2);
    int[][] dp = new int[n][n]; // dp[i][j]表示气球填满(i,j)开区间的最大值
    // 因为要查找左边和下方的状态，故遍历顺序为从下往上，从左往右。
    for (int i = n - 2; i >= 0; --i) {
      for (int j = i + 2; j < n; ++j) { // 区间至少包含一个气球
        for (int k = i + 1; k < j; ++k) { // 依次将区间内每一个气球作为第一个填充（最后一个戳破）的气球
          // 左边区间结果为dp[i][k]，右边区间结果为dp[k][j]，中间气球由于是第一个填充，则其边界为i和j。
          dp[i][j] = Math.max(dp[i][j], dp[i][k] + dp[k][j] + aux[i] * aux[k] * aux[j]);
        }
      }
    }
    return dp[0][n - 1]; // 返回(0,n-1)开区间的结果，即不填充左右两端新增的气球。
  }

  /**
   * 416. 分割等和子集 <br>
   * 传统的【0 − 1背包问题】，使用自底向上的动态规划，由当前状态推导出后续状态。 <br>
   * 使用dp[i][j]表示0-i范围内的数组是否存在一个子集的总和是j，因为只需要查看i-1的状态，故使用一维数组即可。<br>
   * 从左遍历数组，每添加一个数字则显然dp[i][num]为true，向后推导可知dp[i][j+num]=dp[i-1][j]。<br>
   * 【注意】需要从大到小更新状态，因为dp[j]未更新前表示dp[i-1][j]，一旦更新则表示dp[j]，需要在其被更新前更新后面的状态。
   */
  public boolean canPartition(int[] nums) {
    int n = nums.length;
    if (n < 2) { // 数组长度小于2不可能
      return false;
    }
    int sum = 0, max = 0;
    for (int num : nums) {
      sum += num;
      max = Math.max(max, num);
    }
    if ((sum & 1) == 1) { // 数字总和非偶数不可能
      return false;
    }
    int target = sum / 2;
    if (max > target) { // 最大数超过总和一半不可能
      return false;
    }
    boolean[] dp = new boolean[target + 1];
    dp[0] = true; // 子集可以为空则dp[0][0]默认true
    for (int i = 0; i < n; ++i) {
      int num = nums[i];
      for (int j = target; j >= num; --j) { // 需要从右往左更新，因为是由左边的状态推导出右边的状态。
        dp[j] |= dp[j - num]; // 使用|运算保证状态一旦为true则永远为true
      }
    }
    return dp[target];
  }

  /**
   * 494. 目标和 <br>
   * 【与416题思路类似】，转为求子集和为(sum-target)/2的种数，使用dp[i][j]表示0-i位置有多少个子集的和为j。 <br>
   * 证明：所有元素和为sum，选择一个子集将符号设置-，该子集的元素和为sub，则数组内剩余元素和为sum-sub，<br>
   * 要满足要求，则(sum-sub)-sub=target，即子集和为(sum-target)/2。
   */
  public int findTargetSumWays(int[] nums, int target) {
    int n = nums.length, sum = 0;
    for (int num : nums) {
      sum += num;
    }
    if ((target = sum - target) < 0 || (target & 1) == 1) { // 因为数组内全为正整数，则子集和肯定为非负的偶数。
      return 0;
    }
    target >>= 1;
    int[] dp = new int[target + 1];
    dp[0] = 1; // 处理边界问题
    for (int i = 0; i < n; ++i) {
      int num = nums[i];
      for (int j = target; j >= num; --j) {
        dp[j] += dp[j - num];
      }
    }
    return dp[sum];
  }
}
