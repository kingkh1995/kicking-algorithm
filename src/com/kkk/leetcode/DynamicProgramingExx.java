package com.kkk.leetcode;

import com.kkk.supports.TreeNode;
import java.util.Arrays;
import java.util.List;

/**
 * 动态规划 <br>
 * 三个模板： <br>
 * 1、dp[i][j]: i-j的区间；2、dp[i]：0-i区间；3、dp[i]：以i结尾的最大连续区间。 <br>
 * 确定遍历顺序要看需要查看哪个方向的状态，则提前计算出该方向的状态。<br>
 * 494
 *
 * @author KaiKoo
 */
public class DynamicProgramingExx {

  /** 119. 杨辉三角 II <br> */
  public List<Integer> getRow(int rowIndex) {
    Integer[] ans = new Integer[rowIndex + 1];
    for (int i = 0; i <= rowIndex; ++i) {
      for (int j = i; j >= 0; --j) { // 从右往左开始计算，只需要一个数组即可。
        if (j == 0 || j == i) { // 首尾默认是1
          ans[j] = 1;
        } else {
          ans[j] = ans[j - 1] + ans[j];
        }
      }
    }
    return Arrays.asList(ans);
  }

  /**
   * 122. 买卖股票的最佳时机 II <br>
   * 相比【121题】可以多次操作，相比【309题】不存在冷冻期。<br>
   * 则每天分为两种状态，持有股票和不持有股票，因为只需要查看前一天的状态，则使用隐式动态规划。
   */
  public int maxProfit(int[] prices) {
    int n = prices.length, m1 = -prices[0], m2 = 0;
    for (int i = 1; i < n; ++i) {
      int temp = Math.max(m1, m2 - prices[i]); // 昨天持有今天不操作，昨天未持有今天买入。
      m2 = Math.max(m1 + prices[i], m2); // 昨天持有今天卖出，昨天未持有今天继续未持有。
      m1 = temp;
    }
    return m2;
  }

  // ===============================================================================================

  /**
   * 97. 交错字符串 <br>
   * 动态规划，dp[i][j]表示s1的前i个字符和s2的前j个字符可以组成s3的前i+j个字符。<br>
   * 由于只需要查看dp[i - 1][j]和dp[i][j - 1]，故可以使用【滚动数组】优化空间复杂度。
   */
  public boolean isInterleave(String s1, String s2, String s3) {
    int n1 = s1.length(), n2 = s2.length(), n3 = s3.length();
    if (n1 + n2 != n3) {
      return false;
    }
    boolean[] dp = new boolean[n2 + 1];
    dp[0] = true;
    for (int i = 0; i <= n1; ++i) {
      for (int j = 0; j <= n2; ++j) {
        int p = i + j - 1;
        if (i > 0) {
          // dp[i][j] = dp[i - 1][j] && s1.charAt(i - 1) == s3.charAt(p);
          // dp[j]未更新前记录着外层循环上一轮的结果，即dp[i-1][j]。
          dp[j] = dp[j] && s1.charAt(i - 1) == s3.charAt(p);
        }
        if (j > 0 && !dp[j]) { // 上一情况不匹配则再次计算
          // dp[i][j] = dp[i][j - 1] && s2.charAt(j - 1) == s3.charAt(p);
          // 此时dp[j - 1]已在内层循环上一轮被更新为dp[i][j - 1]了。
          dp[j] = dp[j - 1] && s2.charAt(j - 1) == s3.charAt(p);
        }
      }
    }
    return dp[n2];
  }

  /**
   * 787. K 站中转内最便宜的航班 <br>
   * 动态规划，dp[i][j]：搭乘i次航班到达j花费的最小价格，结果为dp[1][dst]-dp[k+1][dst]中间的最小值。 <br>
   * 因为只需要通过上一轮的结果推导当前轮的结果，则只需要两个一维数组即可。
   */
  public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
    final int INF = 10000 * 101 + 1; // 不能设置为Integer.MAX_VALUE，加法运算会溢出。
    int[] f = new int[n], g = new int[n];
    Arrays.fill(f, INF);
    f[src] = 0; // 初始值
    int ans = INF;
    for (int i = 1; i <= k + 1; ++i) { // 中转k次即可搭载k+1
      Arrays.fill(g, INF); // 初始值
      for (int[] flight : flights) {
        g[flight[1]] = Math.min(g[flight[1]], f[flight[0]] + flight[2]);
      }
      int[] tmp = f; // 切换辅助数组
      f = g;
      g = tmp;
      ans = Math.min(ans, f[dst]); // 更新结果
    }
    return ans == INF ? -1 : ans;
  }

  /**
   * 968. 监控二叉树 <br>
   * 分为两种情况，根节点放置摄像头和不放置摄像头。<br>
   * 根节点放置摄像头，则只需要保证left和right的两棵子树被覆盖即可；<br>
   * 若根节点不放置摄像头，需要保证root的两棵子树被覆盖，且left和right某一个需要放置摄像头；<br>
   * 分为三种情况：0.被覆盖且根节点必须放摄像头；1.被覆盖但根节点不一定放摄像头；2.两颗子树被覆盖。
   */
  class minCameraCoverSolution {
    public int minCameraCover(TreeNode root) {
      return dfs(root)[1]; // 显然状态1为结果
    }

    private int[] dfs(TreeNode root) { // 深度优先搜索并返回三种状态的结果
      if (root == null) {
        return new int[] {Integer.MAX_VALUE >> 1, 0, 0};
      }
      int[] left = dfs(root.left), right = dfs(root.right);
      int s0 = 1 + left[2] + right[2]; // root若放置则left和right均已被覆盖，只需要再覆盖left和right的子树即可。
      int s1 = Math.min(s0, Math.min(left[0] + right[1], left[1] + right[0])); // 状态0时状态1也成立
      int s2 = Math.min(s0, left[1] + right[1]); // 状态0时状态2也成立
      return new int[] {s0, s1, s2};
    }
  }

  // ===============================================================================================
  /** 拔高题 */

  // 分割数组的最大值 将非负整数数组分为非空且连续的m组 使得各数组各自的和的最大值最小 求出该最小值
  public static int splitArray(int[] nums, int m) {
    int n = nums.length;
    int[][] dp = new int[n + 1][m + 1]; // 将前n个元素分为m组
    int[] sum = new int[n + 1];
    for (int i = 1, s = 0; i <= n; i++) {
      s += nums[i - 1];
      sum[i] = s;
    }
    // dp[i][1]=sum[i]
    for (int i = 1; i <= n; i++) {
      for (int j = 1; j <= i && j <= m; j++) { // j不能大于i且不能大于m
        if (j == 1) {
          dp[i][1] = sum[i];
        } else {
          // 将前k个元素分为j-1组 之后的元素单独分为一组 k不能小于j-1 且不能大于i-1
          // 循环求解，得出最小值
          dp[i][j] = Integer.MAX_VALUE;
          for (int k = Math.max(j - 1, 1); k < i; k++) {
            dp[i][j] = Math.min(dp[i][j], Math.max(dp[k][j - 1], sum[i] - sum[k]));
          }
        }
      }
    }
    return dp[n][m];
  }

  /**
   * 给定一个由 0 和 1 组成的矩阵，找出每个元素到最近的 0 的距离 <br>
   * f(i,j)的值肯定是0（元素为0）或其上下左右的结果的最小值加1 问题是如何构建出动态规划数组？ <br>
   * 从四个角落出发构造一遍即可，实际上只需要构造两遍，选择（左上和右下） 或（左下或右上）。
   */
  public int[][] updateMatrix(int[][] matrix) {
    int m = matrix.length, n = matrix[0].length;
    int[][] dist = new int[m][n];
    // 初始化动态规划的数组，元素为 0 则设为 0 否则设置为一个很大的数（m+n）
    for (int i = 0; i < m; ++i) {
      for (int j = 0; j < n; ++j) {
        dist[i][j] = matrix[i][j] == 0 ? 0 : m + n;
      }
    }
    // 只有 水平向左移动 和 竖直向上移动，注意动态规划的计算顺序
    for (int i = 0; i < m; ++i) {
      for (int j = 0; j < n; ++j) {
        if (i - 1 >= 0) {
          dist[i][j] = Math.min(dist[i][j], dist[i - 1][j] + 1);
        }
        if (j - 1 >= 0) {
          dist[i][j] = Math.min(dist[i][j], dist[i][j - 1] + 1);
        }
      }
    }
    // 只有 水平向右移动 和 竖直向下移动，注意动态规划的计算顺序
    for (int i = m - 1; i >= 0; --i) {
      for (int j = n - 1; j >= 0; --j) {
        if (i + 1 < m) {
          dist[i][j] = Math.min(dist[i][j], dist[i + 1][j] + 1);
        }
        if (j + 1 < n) {
          dist[i][j] = Math.min(dist[i][j], dist[i][j + 1] + 1);
        }
      }
    }
    return dist;
  }
}
