package com.kkk.hot100;

import java.util.LinkedList;
import java.util.Map;

/**
 * 栈和队列 <br>
 *
 * @author KaiKoo
 */
public class StackAndQueueHot {

  /** 20. 有效的括号 <br> */
  public boolean isValid(String s) {
    Map<Character, Character> map = Map.of(')', '(', '}', '{', ']', '[');
    LinkedList<Character> stack = new LinkedList<>();
    for (char c : s.toCharArray()) {
      if (!map.containsKey(c)) {
        stack.push(c);
      } else if (stack.isEmpty() || stack.pop() != map.get(c)) { // Character可以直接使用==比对
        return false;
      }
    }
    return stack.isEmpty(); // 最后需要判断栈是否为空
  }
}
