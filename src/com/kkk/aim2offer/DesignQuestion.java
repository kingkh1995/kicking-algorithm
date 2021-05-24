package com.kkk.aim2offer;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 设计实现类问题
 * 栈，队列操作
 * 返回异常：add，remove，element，push，pop
 * 返回特殊值：offer，poll，peek
 *
 * @author KaiKoo
 * @date 2020/2/27 23:36
 */
public class DesignQuestion {

    private final static DesignQuestion DESIGN_QUESTION = new DesignQuestion();

    /*
    用两个栈实现一个队列
     */
    /*
    解题思路：一个栈用来push，一个栈用来pop
     */
    public static class MyQueue<T> {

        //数据存储栈
        private Deque<T> stack1 = new LinkedList<>();
        //数据取出栈
        private Deque<T> stack2 = new LinkedList<>();

        public void appendTail(T t) {
            stack1.push(t);
        }

        public T deleteHead() {
            //取出栈为空时，将数据存储栈的数据转移到取出栈
            if (stack2.isEmpty()) {
                //两个栈均为空时返回null
                if (stack1.isEmpty()) {
                    return null;
                } else {
                    while (!stack1.isEmpty()) {
                        stack2.push(stack1.pop());
                    }
                }
            }
            //吐出取出栈的栈顶
            return stack2.pop();
        }
    }

    /*
    两个队列用来实现一个栈
     */
    /*
    解题思路：需要一个辅助队列
     */
    public static class MyStack<T> {

        //主队列
        private Queue<T> queue1 = new LinkedList<>();

        //辅助队列
        private Queue<T> queue2 = new LinkedList<>();

        //push的时候即把顺序颠倒，queue1存数据，queue2辅助
        public void push(T t) {
            while (!queue1.isEmpty()) {
                queue2.add(queue1.remove());
            }
            queue1.add(t);
            while (!queue2.isEmpty()) {
                queue1.add(queue2.remove());
            }
        }

        //pop直接从queue1出
        public T pop() {
            return queue1.remove();
        }
    }

    /*
    实现一个栈，包含一个能获取当前最小元素的min函数，min top pop push时间复杂度都是o(1)
     */
    public static class StackWithMin<T extends Comparable> {

        //元素存储栈
        private Deque<T> stack = new LinkedList<>();
        //最小值存储栈
        private Deque<T> minStack = new LinkedList<>();

        public void push(T t) {
            stack.push(t);
            //push时同时存储push该值后的当前最小值
            //element() = peek() = peekfirst()
            T minTop = minStack.element();
            minStack.push(minTop == null || minTop.compareTo(t) > 0 ? t : minTop);
        }

        public T pop() {
            //最小值存储栈也要pop
            minStack.pop();
            return stack.pop();
        }

        public T top() {
            return stack.element();
        }

        public T min() {
            return minStack.element();
        }
    }

    //测试用例

    public static void myQueueTest() {
        MyQueue<Integer> myQueue = new MyQueue<>();
        System.out.print(myQueue.deleteHead());
        myQueue.appendTail(1);
        myQueue.appendTail(2);
        myQueue.appendTail(3);
        System.out.print(myQueue.deleteHead());
        myQueue.appendTail(4);
        myQueue.appendTail(5);
        System.out.print(myQueue.deleteHead());
        System.out.print(myQueue.deleteHead());
        System.out.print(myQueue.deleteHead());
        System.out.print(myQueue.deleteHead());
    }

    public static void myStackTest() {
        MyStack<Integer> myStack = new MyStack<>();
        System.out.print(myStack.pop());
        myStack.push(1);
        myStack.push(2);
        System.out.print(myStack.pop());
        myStack.push(3);
        System.out.print(myStack.pop());
        System.out.print(myStack.pop());
        myStack.push(4);
        myStack.push(5);
        System.out.print(myStack.pop());
        System.out.print(myStack.pop());
    }

    public static void stackWithMinTest() {
        StackWithMin<Integer> stack = new StackWithMin<>();
        System.out.println("top->" + stack.top() + "min->" + stack.min());
        stack.push(3);
        System.out.println("top->" + stack.top() + "min->" + stack.min());
        stack.push(4);
        System.out.println("top->" + stack.top() + "min->" + stack.min());
        stack.push(2);
        System.out.println("top->" + stack.top() + "min->" + stack.min());
        stack.push(1);
        System.out.println("top->" + stack.top() + "min->" + stack.min());
        stack.push(1);
        System.out.println("top->" + stack.top() + "min->" + stack.min());
        stack.pop();
        System.out.println("top->" + stack.top() + "min->" + stack.min());
        stack.pop();
        System.out.println("top->" + stack.top() + "min->" + stack.min());
        stack.push(0);
        System.out.println("top->" + stack.top() + "min->" + stack.min());
    }
}
