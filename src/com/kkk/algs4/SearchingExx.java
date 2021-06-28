package com.kkk.algs4;

import com.kkk.supports.ArrayUtils;
import com.kkk.supports.NodeUtils;
import com.kkk.supports.TreeNode;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 第三章 查找
 *
 * @author KaiKoo
 */
public class SearchingExx {
  // ===============================================================================================

  /** 3.2.25 完美平衡 利用一组数构建一个和二分查找完全等价的二叉树 */
  /** 先排序，再按二分查找的方式，将中位数设置为根结点，再递归的将左子数组构建成左子树，将右子数组构建为右子树。 */
  private static TreeNode buildBST(int[] arr) {
    return buildBST(arr, 0, arr.length - 1);
  }

  private static TreeNode buildBST(int[] arr, int lo, int hi) {
    if (lo > hi) {
      return null;
    }
    int mid = lo + ((hi - lo) >> 1); // 不用（lo + hi） >> 1 是防止lo和hi直接相加结果溢出
    TreeNode treeNode = new TreeNode(arr[mid]);
    treeNode.left = buildBST(arr, lo, mid - 1);
    treeNode.right = buildBST(arr, mid + 1, hi);
    treeNode.size += size(treeNode.left) + size(treeNode.right);
    return treeNode;
  }

  public static void buildBSTTest() {
    int[] arr = ArrayUtils.distinctSortedArr(10, 1, 20);
    TreeNode treeNode = buildBST(arr);
    System.out.println(treeNode);
  }

  // ===============================================================================================

  /** 3.2.34 线性符号表 使找到下一个结点和下一个结点的操作为常熟操作，即每个添加变量prev next，并在方法中维护。 */
  private static TreeNode put(TreeNode node, int key) {
    // 根结点情况
    if (node == null) {
      return new TreeNode(key);
    }
    int compare = key - node.val;
    if (compare < 0) {
      if (node.left == null) {
        // 临界条件 应该插入左子树且左子树为空则找到插入位置
        TreeNode temp = new TreeNode(key);
        insertNode(temp, node.prev, node);
        node.left = temp;
      } else {
        put(node.left, key);
      }
    } else if (compare > 0) {
      if (node.right == null) {
        // 临界条件 应该插入右子树且右子树为空则找到插入位置
        TreeNode temp = new TreeNode(key);
        insertNode(temp, node, node.next);
        node.right = temp;
      } else {
        put(node.right, key);
      }
    }
    node.size = 1 + size(node.right) + size(node.left);
    return node;
  }

  // 插入元素到两个结点中间，改变prev next
  private static void insertNode(TreeNode node, TreeNode prev, TreeNode next) {
    if (prev != null) {
      node.prev = prev;
      prev.next = node;
    }
    if (next != null) {
      node.next = next;
      next.prev = node;
    }
  }

  // 删除一个结点不改变整体顺序 故整个过程中只需要调用deleteNode一次
  private static TreeNode deleteMin(TreeNode node) {
    if (node.left == null) {
      deleteNode(node);
      return node.right;
    }
    node.left = deleteMin(node.left);
    node.size = 1 + size(node.right) + size(node.left);
    return node;
  }

  // 删除一个结点不改变整体顺序 故整个过程中只需要调用deleteNode一次
  private static TreeNode delete(TreeNode node, int key) {
    // 根结点情况
    if (node == null) {
      return null;
    }
    int compare = key - node.val;
    if (compare < 0) {
      node.left = delete(node.left, key);
    } else if (compare > 0) {
      node.right = delete(node.right, key);
    } else {
      // 直接删除，因为删除之后整体顺序是不变的，故只需要此时改变prev next一次即可
      deleteNode(node);
      if (node.left == null) {
        return node.right;
      } else if (node.right == null) {
        return node.left;
      }
      // 用next替换node的位置
      TreeNode temp = node;
      node = node.next;
      node.right = deleteMin0(temp.right); // 使用原始的deleteMin操作，而不用改变prev next，因为删除node的时候已经变更过了
      node.left = temp.left;
    }
    node.size = 1 + size(node.right) + size(node.left);
    return node;
  }

  private static TreeNode deleteMin0(TreeNode node) {
    if (node.left == null) {
      return node.right;
    }
    node.left = deleteMin0(node.left);
    node.size = 1 + size(node.right) + size(node.left);
    return node;
  }

  // 改变prev next
  private static void deleteNode(TreeNode node) {
    if (node.next != null) {
      node.next.prev = node.prev;
    }
    if (node.prev != null) {
      node.prev.next = node.next;
    }
  }

  public static void threadedSTTest() {
    int[] arr = ArrayUtils.distinctArr(10, 1, 20);
    TreeNode treeNode1 = new TreeNode(arr[0]);
    for (int i = 1; i < arr.length; i++) {
      put(treeNode1, arr[i]);
    }
    System.out.println(treeNode1);
    NodeUtils.threadedPrint(treeNode1);
    for (int i = 0; i < arr.length; i++) {
      treeNode1 = deleteMin(treeNode1);
      NodeUtils.threadedPrint(treeNode1);
    }
    int[] arr1 = ArrayUtils.distinctArr(10, 0, 10);
    ArrayUtils.printArray(arr1);
    TreeNode treeNode2 = new TreeNode(arr1[0]);
    for (int i = 1; i < arr1.length; i++) {
      put(treeNode2, arr1[i]);
    }
    System.out.println(treeNode2);
    NodeUtils.threadedPrint(treeNode2);
    int[] arr2 = ArrayUtils.distinctArr(10, 0, 10);
    for (int i = 0; i < arr2.length; i++) {
      treeNode2 = delete(treeNode2, arr2[i]);
      NodeUtils.threadedPrint(treeNode2);
    }
  }

  // ===============================================================================================

  private static int size(TreeNode node) {
    return node == null ? 0 : node.size;
  }

  // ===============================================================================================

  /** 红黑树测试 */
  public static void RedBlackBSTTest() {
    ThreadLocalRandom random = ThreadLocalRandom.current();
    RedBlackBST rbbst = new RedBlackBST();
    int n = 2500;
    System.out.println("delete");
    for (int i = 0; i < 4 * n; i++) {
      rbbst.put(random.nextInt(n));
    }
    System.out.println(rbbst.count());
    for (int i = 0; i < n; i++) {
      rbbst.delete(i);
    }
    System.out.println(rbbst.count());
    System.out.println("delete max");
    for (int i = 0; i < 4 * n; i++) {
      rbbst.put(random.nextInt(n));
    }
    int count = rbbst.count();
    for (int i = 0; i < count; i++) {
      rbbst.deleteMax();
    }
    System.out.println(rbbst.count());
    System.out.println("delete min");
    for (int i = 0; i < 4 * n; i++) {
      rbbst.put(random.nextInt(n));
    }
    count = rbbst.count();
    for (int i = 0; i < count; i++) {
      rbbst.deleteMin();
    }
    System.out.println(rbbst.count());
  }

  // ===============================================================================================

  /**
   * 3.3.29 <br>
   * ReaBlackBST的最优存储 不用在设置结点是否为红色 如果要设为红色 ，将左右子链接交换，要检测是否为红色只需要比较左右子结点的大小
   */
  /**
   * 1.判断是否为红色： <br>
   * 1）是否为根节点，根节点必然为黑色；2）三种情况：a.有左子结点无右子节点（黑色）b.左右子都有（判断大小得出） <br>
   * c.左右子都无，继续判断是否有兄弟结点，没有兄弟结点必定为红色，有兄弟结点则为黑色（因为是完美黑色平衡） <br>
   * 2.查找算法：先判断结点颜色，如果为红色，则相反方向查找 <br>
   */

  // ===============================================================================================

  /** 3.3.23 非平衡的2-3树 */
  private class TwoThreeNode {

    public int val;

    public TwoThreeNode left;

    public TwoThreeNode right;

    public int size = 1;

    public boolean isRed = false;

    public TwoThreeNode(int val) {
      this.val = val;
    }
  }

  private static int size(TwoThreeNode node) {
    return node == null ? 0 : node.size;
  }

  private static boolean isRed(TwoThreeNode node) {
    return node == null ? false : node.isRed;
  }

  public TwoThreeNode min(TwoThreeNode node) {
    if (node.left != null) {
      return min(node.left);
    }
    return node;
  }

  private class TwoThreeST {
    private TwoThreeNode root;

    public void notBalancedPut(int key) {
      if (root == null) {
        root = new TwoThreeNode(key);
      } else {
        notBalancedPut(root, key);
      }
    }

    private void notBalancedPut(TwoThreeNode node, int key) {
      int cmp = key - node.val;
      // 先插入
      if (cmp < 0) {
        // 找到插入位置
        if (node.left == null) {
          TwoThreeNode temp = new TwoThreeNode(key);
          node.left = temp;
          // 判断当前结点类型 因为不追求平衡，如果为3-结点则添加2-结点，新结点为黑色，否则组成3-结点，新结点类型为红色
          if (!isRed(node) && !isRed(node.right)) {
            temp.isRed = true;
          }
        } else {
          // 往左边插入
          notBalancedPut(node.left, key);
        }
      } else if (cmp > 0) {
        // 找到插入位置
        if (node.right == null) {
          TwoThreeNode temp = new TwoThreeNode(key);
          node.right = temp;
          // 判断当前结点类型 因为不追求平衡，如果为3-结点则添加2-结点，新结点为黑色，否则组成3-结点，新结点类型为红色
          if (!isRed(node) && !isRed(node.right)) {
            temp.isRed = true;
          }
        } else {
          // 往右边插入
          notBalancedPut(node.right, key);
        }
      }
      node.size = 1 + size(node.left) + size(node.right);
    }

    private TwoThreeNode notBalancedDeleteMin(TwoThreeNode node) {
      if (node.left == null) {
        // 找到最小值，并判断当前结点是不是3-结点 是则退化为2-结点
        if (isRed(node.right)) {
          node.right.isRed = false;
        }
        return node.right;
      }
      node.left = notBalancedDeleteMin(node.left);
      node.size = 1 + size(node.right) + size(node.left);
      return node;
    }

    private TwoThreeNode notBalancedDelete(TwoThreeNode node, int key) {
      // 不存在情况
      if (node == null) {
        return null;
      }
      int compare = key - node.val;
      if (compare < 0) {
        node.left = notBalancedDelete(node.left, key);
      } else if (compare > 0) {
        node.right = notBalancedDelete(node.right, key);
      } else {
        if (node.left == null) {
          if (isRed(node.right)) {
            node.right.isRed = false;
          }
          return node.right;
        } else if (node.right == null) {
          if (isRed(node.left)) {
            node.left.isRed = false;
          }
          return node.left;
        }
        // 用next替换node的位置
        TwoThreeNode temp = node;
        node = min(node.right);
        node.right = notBalancedDeleteMin(temp.right);
        node.left = temp.left;
        node.isRed = temp.isRed;
      }
      node.size = 1 + size(node.right) + size(node.left);
      return node;
    }
  }

  // ===============================================================================================

  /** 3.3.32 AVL树测试 */
  public static void AVLBSTTest() {
    ThreadLocalRandom random = ThreadLocalRandom.current();
    AVLBST bst = new AVLBST();
    int n = 2500;
    System.out.println("delete");
    for (int i = 0; i < 4 * n; i++) {
      bst.put(random.nextInt(n));
    }
    System.out.println(bst.count());
    for (int i = 0; i < n; i++) {
      bst.delete(i);
    }
    System.out.println(bst.count());
    System.out.println("delete min");
    for (int i = 0; i < 4 * n; i++) {
      bst.put(random.nextInt(n));
    }
    int count = bst.count();
    for (int i = 0; i < count; i++) {
      bst.deleteMin();
    }
    System.out.println(bst.count());
  }

  // ===============================================================================================

  /** 3.4.31 Cuckoo散列函数 维护两张散列表和两个散列函数 如果冲突则替换旧键，并将旧键插入另一张散列表中 */
  private class CuckooHashSet<Key> {

    // 两个散列表大小相同，同时公用size和n
    private int size = 0;
    private int n = 16;
    private Key[] aKeys = (Key[]) new Object[n];
    private Key[] bKeys = (Key[]) new Object[n];

    private int aHash(Key key) {
      return 0; // 不实现了
    }

    private int bHash(Key key) {
      return 0; // 不实现了
    }

    // 置换hash函数，两个键值通过两个hash函数的结果都是相同的，会出现死循环无法插入
    private void rehash() {
      // 不实现了
    }

    private void tryResize() {
      if (size > (n >> 1)) {
        resize(n << 1);
      } else if (size < (n >> 2)) {
        resize(n >> 1);
      }
    }

    private void resize(int newSize) {
      if (newSize < 16 || newSize > Integer.MAX_VALUE) {
        return;
      }
      n = newSize;
      Key[] aOldKeys = aKeys;
      aKeys = (Key[]) new Object[newSize];
      Key[] bOldKeys = bKeys;
      bKeys = (Key[]) new Object[newSize];
      // 判断是否需要rehash
      boolean needToRehash;
      do {
        needToRehash = false;
        rehash();
        // 遍历重新插入，如果插入失败，则break 重新置换hash函数 然后重新遍历插入
        for (Key i : aOldKeys) {
          if (i != null && !tryToInsert(i)) {
            needToRehash = true;
            break;
          }
        }
        if (!needToRehash) {
          for (Key i : bOldKeys) {
            if (i != null && !tryToInsert(i)) {
              needToRehash = true;
              break;
            }
          }
        }
      } while (needToRehash);
    }

    // 尝试插入 如果插入失败，需要替换散列函数并重新插入所有元素
    private boolean tryToInsert(Key key) {
      Key temp = key;
      // 出现死循环的时候 插入值会仍然是原值
      // 插入C，数组中的值A和B经过两个hash函数结果完全一致，C与他们hash值也一致，则C先替换A，A再替换B，B再替换C，一直循环插入值会重新变为C
      do {
        int aHash = aHash(temp);
        if (aKeys[aHash] == null) {
          aKeys[aHash] = temp;
          temp = null;
        } else {
          // 发生冲突则替换旧键
          Key old = aKeys[aHash];
          aKeys[aHash] = temp;
          temp = old;
        }
        if (temp != null) {
          int bHash = bHash(temp);
          if (bKeys[bHash] == null) {
            bKeys[bHash] = temp;
            temp = null;
          } else {
            // 发生冲突则替换旧键
            Key old = bKeys[bHash];
            bKeys[bHash] = temp;
            temp = old;
          }
        }
      } while (temp != null && !key.equals(temp)); // 终止条件，插入成功（temp为null）或者temp变为了原值（出现了死循环）
      return temp == null;
    }

    public void put(Key key) {
      if (key == null) {
        return;
      }
      // 如果存在 return
      if (contains(key)) {
        return;
      }
      // 尝试插入，如果插入失败，重新设置hash函数，并重新插入元素，循环直到插入成功。
      while (!tryToInsert(key)) {
        resize(n);
      }
      size++;
      tryResize();
    }

    public boolean contains(Key key) {
      if (key == null) {
        return false;
      }
      // 使用对应的散列函数在两个数组查找一次即可
      int aHash = aHash(key);
      if (key.equals(aKeys[aHash])) {
        return true;
      }
      int bHash = bHash(key);
      if (key.equals(bKeys[bHash])) {
        return true;
      }
      return false;
    }

    public void delete(Key key) {
      if (key == null) {
        return;
      }
      // 如果不存在 return
      if (!contains(key)) {
        return;
      }
      // 存在则找到并删除
      int aHash = aHash(key);
      if (key.equals(aKeys[aHash])) {
        aKeys[aHash] = null;
      } else {
        bKeys[bHash(key)] = null;
      }
      size--;
      tryResize();
    }
  }

  // ===============================================================================================
}
