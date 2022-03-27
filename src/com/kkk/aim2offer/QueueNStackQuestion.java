package com.kkk.aim2offer;

import com.kkk.supports.Stack;

/**
 * 栈和队列问题
 *
 * @author KaiKoo
 */
public class QueueNStackQuestion {

  /**
   * 设计一个能在常数时间内检索到最小元素的栈 <br>
   *
   * @author KaiKoo
   */
  class MinStack {

    private final Stack stack; // 元素存放栈
    private final Stack minStack; // 最小值栈

    /** initialize your data structure here. */
    public MinStack() {
      this.stack = new Stack();
      this.minStack = new Stack();
    }

    public void push(int val) {
      stack.push(val);
      if (minStack.isEmpty() || val < minStack.top()) {
        minStack.push(val);
      } else {
        minStack.push(minStack.top());
      }
    }

    public void pop() {
      stack.pop();
      minStack.pop();
    }

    public int top() {
      return stack.top();
    }

    public int getMin() {
      return minStack.top();
    }
  }
}
