package com.kkk.leetcode;

import com.kkk.supports.Queue;
import com.kkk.supports.Stack;

/**
 * 队列和栈
 *
 * @author KaiKoo
 */
public class QueueAndStackExx {

  // ===============================================================================================
  /** 基础题 */

  // 岛屿问题
  // 解法：遍历所有格子 找到第一个陆地 通过遍历相邻的陆地，并将探索过的陆地标记为已探索
  public int numIslands(char[][] grid) {
    int count = 0;
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[0].length; j++) {
        if (grid[i][j] == '1') {
          count++;
          // 找到第一个陆地探索
          bfs(grid, i, j); // 使用BFS或DFS
        }
      }
    }
    return count;
  }

  // 解法1：BFS 使用队列
  private void bfs(char[][] grid, int i, int j) {
    // 起始点先置为0
    grid[i][j] = '0';
    int column = grid.length;
    int row = grid[0].length;
    Queue queue = new Queue();
    // 起始点入队列
    // 只使用一个数字表示坐标 ro必定小于row co=code/row ro=code%row
    queue.enqueue(i * row + j);
    while (!queue.isEmpty()) {
      // 出队列
      int code = queue.dequeue();
      int co = code / row;
      int ro = code % row;
      // 上下左右入队列 如果为1 则设为0并且加入队列
      // 上
      if (co > 0 && grid[co - 1][ro] == '1') {
        grid[co - 1][ro] = '0';
        queue.enqueue((co - 1) * row + ro);
      }
      // 下
      if (co < column - 1 && grid[co + 1][ro] == '1') {
        grid[co + 1][ro] = '0';
        queue.enqueue((co + 1) * row + ro);
      }
      // 左
      if (ro > 0 && grid[co][ro - 1] == '1') {
        grid[co][ro - 1] = '0';
        queue.enqueue(co * row + ro - 1);
      }
      // 右
      if (ro < row - 1 && grid[co][ro + 1] == '1') {
        grid[co][ro + 1] = '0';
        queue.enqueue(co * row + ro + 1);
      }
    }
  }

  // 解法2：DFS 使用递归（即不显示使用栈，而是使用系统的方法栈）
  private void dfs1(char[][] grid, int co, int ro) {
    // 当前点置为0
    grid[co][ro] = '0';
    int column = grid.length;
    int row = grid[0].length;
    // 上下左递归探索
    // 上
    if (co > 0 && grid[co - 1][ro] == '1') {
      dfs1(grid, co - 1, ro);
    }
    // 下
    if (co < column - 1 && grid[co + 1][ro] == '1') {
      dfs1(grid, co + 1, ro);
    }
    // 左
    if (ro > 0 && grid[co][ro - 1] == '1') {
      dfs1(grid, co, ro - 1);
    }
    // 右
    if (ro < row - 1 && grid[co][ro + 1] == '1') {
      dfs1(grid, co, ro + 1);
    }
  }

  // 解法2：DFS 使用栈 与使用队列方式相同
  private void dfs2(char[][] grid, int i, int j) {
    // 起始点先置为0
    grid[i][j] = '0';
    int column = grid.length;
    int row = grid[0].length;
    Stack stack = new Stack();
    // 起始点入栈
    stack.push(i * row + j);
    while (!stack.isEmpty()) {
      // 出栈
      int code = stack.pop();
      int co = code / row;
      int ro = code % row;
      // 上下左右入队列 如果为1 则设为0并且加入队列
      // 上
      if (co > 0 && grid[co - 1][ro] == '1') {
        grid[co - 1][ro] = '0';
        stack.push((co - 1) * row + ro);
      }
      // 下
      if (co < column - 1 && grid[co + 1][ro] == '1') {
        grid[co + 1][ro] = '0';
        stack.push((co + 1) * row + ro);
      }
      // 左
      if (ro > 0 && grid[co][ro - 1] == '1') {
        grid[co][ro - 1] = '0';
        stack.push(co * row + ro - 1);
      }
      // 右
      if (ro < row - 1 && grid[co][ro + 1] == '1') {
        grid[co][ro + 1] = '0';
        stack.push(co * row + ro + 1);
      }
    }
  }

  // ===============================================================================================
  /** 拔高题 */
}
