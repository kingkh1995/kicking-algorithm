package com.kkk.supports;

/**
 * @author KaiKoo
 */
public class TreeNode {

  public int val;

  public TreeNode left;

  public TreeNode right;

  public int size = 1;

  public TreeNode prev;

  public TreeNode next;

  public TreeNode parent;

  public TreeNode(int val) {
    this.val = val;
  }

  public TreeNode(int val, TreeNode left, TreeNode right) {
    this.val = val;
    this.left = left;
    this.right = right;
  }

  @Override
  public String toString() {
    return "|"
        + (this.left == null ? "*" : this.left.toString())
        + this.val
        + (this.right == null ? "*" : this.right.toString())
        + "|";
  }
}
