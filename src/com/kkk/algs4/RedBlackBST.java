package com.kkk.algs4;

/**
 * 红黑树
 *
 * @author KaiKoo
 */
public class RedBlackBST {

  // ===============================================================================================

  private class RBNode {

    public int val;

    public RBNode left;

    public RBNode right;

    public int size = 1;

    public boolean isRed = true;

    public RBNode(int val) {
      this.val = val;
    }
  }

  // ===============================================================================================

  private RBNode root;

  public int count() {
    return size(this.root);
  }

  private static int size(RBNode node) {
    return node == null ? 0 : node.size;
  }

  private static boolean isRed(RBNode node) {
    return node == null ? false : node.isRed;
  }

  public RBNode min(RBNode node) {
    if (node.left != null) {
      return min(node.left);
    }
    return node;
  }

  public boolean contains(int key) {
    while (root != null) {
      int cmp = key - root.val;
      if (cmp == 0) {
        return true;
      } else if (cmp > 0) {
        root = root.right;
      } else {
        root = root.left;
      }
    }
    return false;
  }

  // ===============================================================================================

  // 将右斜红链接变为左斜红链接
  // 被旋转下去的结点变为红色，旋转上去的结点变为被旋转的结点的原来颜色
  private static RBNode rotateLeft(RBNode node) {
    // right为红结点
    RBNode right = node.right;
    right.isRed = node.isRed;
    node.isRed = true;
    node.right = right.left;
    right.left = node;
    right.size = node.size;
    node.size = size(node.left) + size(node.right) + 1;
    return right;
  }

  // 将左斜红链接变为右斜红链接
  // 被旋转下去的结点变为红色，旋转上去的结点变为被旋转的结点的原来颜色
  private static RBNode rotateRight(RBNode node) {
    // left为红结点
    RBNode left = node.left;
    left.isRed = node.isRed;
    node.isRed = true;
    node.left = left.right;
    left.right = node;
    left.size = node.size;
    node.size = size(node.left) + size(node.right) + 1;
    return left;
  }

  // 变换颜色 不会破坏完美黑色平衡
  private static void flipColors(RBNode node) {
    node.isRed = !node.isRed;
    node.left.isRed = !node.left.isRed;
    node.right.isRed = !node.right.isRed;
  }

  // ===============================================================================================

  public void put(int key) {
    root = put(root, key);
    // 每次插入完成都将根结点设置为黑色
    root.isRed = false;
    if (!isBalanced()) {
      System.out.println("not balanced!");
    }
    if (!is23()) {
      System.out.println("not 23!");
    }
  }

  private RBNode put(RBNode node, int key) {
    if (node == null) {
      // 插入的结点默认为红结点
      return new RBNode(key);
    }
    int cmp = key - node.val;
    // 先插入
    if (cmp < 0) {
      node.left = put(node.left, key);
    } else if (cmp > 0) {
      node.right = put(node.right, key);
    }
    return balance(node);
  }

  // ===============================================================================================

  public void deleteMin() {
    if (root == null) {
      return;
    }
    // 这一步判断是因为在插入的最后一步，会改变根结点从红色到黑色
    // 因为只有根结点左子结点和右子结点都为黑结点时才会改变根结点颜色从红色到黑色，所以这里需要先改回去。
    if (!isRed(root.left) && !isRed(root.right)) {
      root.isRed = true;
    }
    root = deleteMin(root);
    if (root != null) {
      root.isRed = false;
    }
    if (!isBalanced()) {
      System.out.println("not balanced!");
    }
    if (!is23()) {
      System.out.println("not 23!");
    }
  }

  // 注意此时当前结点必然是红色（通过filpcolors红色结点会一直往下移动，如果之前的路径上没有红结点，会把根结点设为红结点）
  private RBNode moveRedLeft(RBNode node) {
    // 因为右子树必然为黑链接，故直接转换颜色
    flipColors(node);
    // 右子结点为3-结点 将右结点右旋一次 再将结点左旋一次
    // 目的是将红链接转换到查询路径上
    if (isRed(node.right.left)) {
      node.right = rotateRight(node.right);
      node = rotateLeft(node);
    }
    return node;
  }

  private RBNode deleteMin(RBNode node) {
    if (node.left == null) {
      // 找到最小值，直接删除，因为变换之后已经不存在2-结点了
      return node.right; // 可以直接return null 因为right必然为null
    }
    // 如果判断左结点为2-结点则 将左结点或者左结点的子节点任意一个变红
    if (!isRed(node.left) && !isRed(node.left.left)) {
      node = moveRedLeft(node);
    }
    // 删除结点
    node.left = deleteMin(node.left);
    // 删除完 再重新平衡
    return balance(node);
  }

  // ===============================================================================================

  public void deleteMax() {
    if (root == null) {
      return;
    }
    // 这一步判断是因为在插入的最后一步，会改变根结点从红色到黑色
    // 因为只有根结点左子结点和右子结点都为黑结点时才会改变根结点颜色从红色到黑色，所以这里需要先改回去。
    if (!isRed(root.left) && !isRed(root.right)) {
      root.isRed = true;
    }
    root = deleteMax(root);
    if (root != null) {
      root.isRed = false;
    }
    if (!isBalanced()) {
      System.out.println("not balanced!");
    }
    if (!is23()) {
      System.out.println("not 23!");
    }
  }

  // 注意此时当前结点必然是红色（通过filpcolors红色结点会一直往下移动，如果之前的路径上没有红结点，会把根结点设为红结点）
  private RBNode moveRedRight(RBNode node) {
    // 因为右子树必然为黑链接，故直接转换颜色
    flipColors(node);
    // 左子结点为3-结点 将结点右旋一次
    // 目的是将红链接转换到查询路径上
    if (isRed(node.left.left)) {
      node = rotateRight(node);
    }
    return node;
  }

  private RBNode deleteMax(RBNode node) {
    // 将路径上的左斜红链接全部右旋为右斜红链接
    if (isRed(node.left)) {
      node = rotateRight(node);
    }
    if (node.right == null) {
      // 找到最小值，直接删除，因为变换之后已经不存在2-结点了
      return node.left; // 可以直接return null 因为left必然为null
    }
    // 判断右结点为2-结点 则将右结点或右结点的子结点任意一个变红
    if (!isRed(node.right) && !isRed(node.right.left)) {
      node = moveRedRight(node);
    }
    // 删除结点
    node.right = deleteMax(node.right);
    // 删除完 再重新平衡
    return balance(node);
  }

  // ===============================================================================================

  public void delete(int key) {
    // 判断key是否存在，删除操作就不用判断临界条件了
    if (!contains(key)) {
      return;
    }
    if (!isRed(root.left) && !isRed(root.right)) {
      root.isRed = true;
    }
    root = delete(root, key);
    if (root != null) {
      root.isRed = false;
    }
    if (!isBalanced()) {
      System.out.println("not balanced!");
    }
    if (!is23()) {
      System.out.println("not 23!");
    }
  }

  private RBNode delete(RBNode node, int key) {
    if (key < node.val) {
      // 判断左结点是否为2-结点
      if (!isRed(node.left) && !isRed(node.left.left)) {
        node = moveRedLeft(node);
      }
      node.left = delete(node.left, key);
    } else {
      // 如果为3-结点，则将左斜红链接右旋为右斜红链接，这是为了保证路径上两个黑结点的父结点必然是红节点
      if (isRed(node.left)) {
        node = rotateRight(node);
      }
      // 判断是否等于 right为空表示到达了底部 直接删除即可
      // 因为红黑树是完美黑色平衡的，如果右子结点为空，则左子结点必然为空或者是一个红结点
      // 又因为在上一个if判断中，已经将红链接已经左旋转到了右边，故左边必然不会是左链接 即右结点为空则左子结点必然为空
      if ((key == node.val) && (node.right == null)) {
        return null;
      }
      // 其情况下，如果判断右结点为2-结点 则将右结点或右结点的子结点任意一个变红
      if (!isRed(node.right) && !isRed(node.right.left)) {
        node = moveRedRight(node);
      }
      // 再次判断是否等于 且此时肯定不是底部 右子树也一定不为空 故从右子树取到最小值
      if (key == node.val) {
        // 替换
        node.val = min(node.right).val;
        // 删去最小值
        node.right = deleteMin(node.right);
      } else {
        node.right = delete(node.right, key);
      }
    }
    // 删除完 再重新平衡
    return balance(node);
  }

  // ===============================================================================================

  // 插入和删除结点恢复平衡公共方法
  private RBNode balance(RBNode node) {
    // 第0步，针对moveLeft图像的优化 或者去掉该步直接在第二步处加上判断
    /*        if (isRed(node.right)) {
      node = rotateLeft(node);
    }*/

    // 这一步很关键 将去除2-结点后产生的的图形复原(moveLeft) 会先符合第二步右旋的判断，导致右斜红链被保留，因为旋转不影响未被旋转的其他部分
    // 所以需要先左旋一次，将红链接旋转到左边，这样会产生三个连续的左斜红链接 再进行第二步，会复原图像，接下来直接颜色转换即可
    //      B                      B                            B                          B
    //    R   R   =直接右旋=>     R     R      所以需要先左旋      R   再右旋一次恢复原图形状    R   R
    //  R                                R                    R                       R
    // 如果是moveRight则不用做处理                              R
    //      B                             B
    //    R   R     =之前会左旋一次=>     R     R      然后直接颜色转换即可
    //          R                          R

    // 第一步 左子结点为黑，右子节点为红 则左旋
    if (!isRed(node.left) && isRed(node.right)) {
      node = rotateLeft(node);
    }
    // 第二步 左子结点为红，且左子节点的左子节点为红 右旋
    // 额外加上判断 对moveLeft的图像不做右旋
    if (isRed(node.left) && isRed(node.left.left) && !isRed(node.right)) {
      node = rotateRight(node);
    }
    // 左为红，且左子节点的右子节点为红 这种情况不会存在，因为该情况对应上一步的左子结点为黑，右子节点为红
    // 第三步 左子结点和右子结点都为红
    if (isRed(node.left) && isRed(node.right)) {
      flipColors(node);
    }
    // 设置size
    node.size = 1 + size(node.left) + size(node.right);
    return node;
  }

  // 沿着最左路径计算出黑结点的个数
  public boolean isBalanced() {
    int black = 0;
    for (RBNode x = root; x != null; x = x.left) {
      if (!isRed(x)) {
        black++;
      }
    }
    return isBalanced(root, black);
  }

  // 递归求其他路径的黑结点个数是否相同
  private boolean isBalanced(RBNode x, int black) {
    if (x == null) {
      return black == 0;
    }
    if (!isRed(x)) {
      black--;
    }
    return isBalanced(x.left, black) && isBalanced(x.right, black);
  }

  public boolean is23() {
    return is23(root);
  }

  private boolean is23(RBNode node) {
    if (node == null) {
      return true;
    }
    // 判断右红链接是否存在
    if (isRed(node.right)) {
      return false;
    }
    // 判断左边红链接
    if (isRed(node) && isRed(node.left)) {
      return false;
    }
    return is23(node.left) && is23(node.right);
  }

  // ===============================================================================================
}
