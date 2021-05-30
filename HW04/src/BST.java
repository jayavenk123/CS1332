import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Your implementation of a BST.
 *
 * @author Jayanee Venkat
 * @version 1.0
 * @userid jvenkat8
 * @GTID 903628863
 *
 * Collaborators: none
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class BST<T extends Comparable<? super T>> {

    // Do not add new instance variables or modify existing ones.
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("enter something that is not null");
        }
        for (T i : data) {
            if (i == null) {
                throw new IllegalArgumentException("enter something that is not null");
            }
            add(i);
        }
    }

    /**
     * Adds the element to the tree.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     * 
     * This method must be implemented recursively.
     *
     * @param data the data to add
     * @throws IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("can't add null data into BST");
        }
        root = rAdd(root, data);
    }

    /**
     * Private method to helps in public add method
     * @param curr type is BSTNode; keeps track of current node
     * @param data type is generic T; the data that needs to be added to the BST
     * @return type returned is BSTNode
     */
    private BSTNode<T> rAdd(BSTNode<T> curr, T data) {
        if (data == null) {
            throw new IllegalArgumentException("can't add null data into BST");
        }
        if (curr == null) {
            size++;
            return new BSTNode<>(data);
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(rAdd(curr.getLeft(), data));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(rAdd(curr.getRight(), data));
        }
        return curr;
    }
    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data. You MUST use recursion to find and remove the
     * predecessor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     * 
     * This method must be implemented recursively.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        BSTNode<T> dummy = new BSTNode<>(null);
        root = rRemove(root, data, dummy);
        return dummy.getData();
    }

    /**
     * method that actually removes data node from the BST
     * @param curr BSTNode that represents the current data
     * @param data generic type T that represents the data you are removing
     * @param dummy BSTNode that temporarily holds removed node
     * @return BSTNode type
     */
    private BSTNode<T> rRemove(BSTNode<T> curr, T data, BSTNode<T> dummy) {
        if (data == null) {
            throw new IllegalArgumentException("don't enter null data");
        }
        if (curr == null) {
            throw new NoSuchElementException("the element you are trying to remove does not exist");
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(rRemove(curr.getLeft(), data, dummy));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(rRemove(curr.getRight(), data, dummy));
        } else {
            dummy.setData(curr.getData());
            size--;
            if (curr.getLeft() == null && curr.getRight() == null) {
                return null;
            } else if (curr.getLeft() == null) {
                return curr.getRight();
            } else if (curr.getRight() == null) {
                return curr.getLeft();
            } else {
                BSTNode<T> dummy2 = new BSTNode<>(null);
                curr.setLeft(removePredecessor(curr.getLeft(), dummy2));
                curr.setData(dummy2.getData());
            }
        }
        return curr;
    }

    /**
     * private method that helps remove predecessor and return that value
     * @param curr type BSTNode; holds the current value
     * @param dummy type BSTNode; acts as temporary node
     * @return type BSTNode
     */
    private BSTNode<T> removePredecessor(BSTNode<T> curr, BSTNode<T> dummy) {
        if (curr.getRight() == null) {
            dummy.setData(curr.getData());
            return curr.getLeft();
        } else {
            curr.setRight(removePredecessor(curr.getRight(), dummy));
        }
        return curr;
    }

    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     * 
     * This method must be implemented recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    //
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("enter data that is not null");
        }
        T ans = this.nextCompare(root, data);
        if (ans == null) {
            throw new NoSuchElementException("this element is not in the BinarySearchTree");
        }
        return data;
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     * 
     * This method must be implemented recursively.
     *
     * @param data the data to search for in the tree.
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null. enter data with value");
        }
        T c = this.nextCompare(root, data);
        if (c == null) {
            return false;
        } else if (c.equals(data)) {
            return true;
        }
        return false;
    }

    /**
     * Boolean comparing data that searches for data point
     * @param curr represents BSTNode that is the current node we are at when transversing
      * @param data generic type T; the data we are searching for
     * @return boolean value
     */
    private T nextCompare(BSTNode<T> curr, T data) {
        if (curr == null) {
            return null;
        } else if (data.compareTo(curr.getData()) < 0) {
            return nextCompare(curr.getLeft(), data);
        } else if (data.compareTo(curr.getData()) > 0) {
            return nextCompare(curr.getRight(), data);
        }
        return curr.getData();
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * Must be O(n).
     *
     * This method must be implemented recursively.
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> list = new ArrayList<>();
        if (root == null) {
            return list;
        }
        return rPre(root, list);
    }

    /**
     * Helper method for the preorder traversal
     * @param curr BSTNode type; holds the current node information
     * @param l List type; the list returned in Preorder
     * @return a List
     */
    private List<T> rPre(BSTNode<T> curr, List<T> l) {
        l.add(curr.getData());
        if (curr.getLeft() != null) {
            rPre(curr.getLeft(), l);
        }
        if (curr.getRight() != null) {
            rPre(curr.getRight(), l);
        }
        return l;
    }
    /**
     * Generate a in-order traversal of the tree.
     *
     * Must be O(n).
     * 
     * This method must be implemented recursively.
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> l = new ArrayList<>();
        if (root == null) {
            return l;
        }
        return rInorder(root, l);
    }

    /**
     * Inorder helper method; acts recursively
     * @param curr BSTNode type; holds current node information
     * @param l List type; the list that holds information "inorder"
     * @return a List
     */
    private List<T> rInorder(BSTNode<T> curr, List<T> l) {
        if (curr.getLeft() != null) {
            rInorder(curr.getLeft(), l);
        }
        l.add(curr.getData());
        if (curr.getRight() != null) {
            rInorder(curr.getRight(), l);
        }
        return l;
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * Must be O(n).
     * 
     * This method must be implemented recursively.
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> l = new ArrayList<>();
        if (root == null) {
            return l;
        }
        return rPost(root, l);
    }

    /**
     * Postorder private helper method
     * @param curr BSTNode type; holds current node information
     * @param l List type; list that holds data "postorder"
     * @return a List
     */
    private List<T> rPost(BSTNode<T> curr, List<T> l) {
        if (curr.getLeft() != null) {
            rPost(curr.getLeft(), l);
        }
        if (curr.getRight() != null) {
            rPost(curr.getRight(), l);
        }
        l.add(curr.getData());

        return l;
    }
    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        List<T> l = new ArrayList<>();
        Queue<BSTNode<T>> q = new LinkedList<>();
        q.add(root);
        while (!(q.isEmpty())) {
            BSTNode<T> temp = q.remove();
            if (temp == null) {
                continue;
            }
            l.add(temp.getData());
            q.add(temp.getLeft());
            q.add(temp.getRight());
        }
        return l;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child should be -1.
     *
     * Must be O(n).
     * 
     * This method must be implemented recursively.
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return max(root);
    }

    /**
     * method that finds the height; helper method to height()
     * @param curr BSTNode type
     * @return int type
     */
    private int max(BSTNode<T> curr) {
        if (curr == null) {
            return -1;
        }
        int countL = max(curr.getLeft());
        int countR = max(curr.getRight());
        return Math.max(countL, countR) + 1;
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        size = 0;
        root = null;
    }

    /**
     * This method checks whether a binary tree meets the criteria for being
     * a binary search tree.
     *
     * This method is a static method that takes in a BSTNode called
     * {@code treeRoot}, which is the root of the tree that you should check.
     *
     * You may assume that the tree passed in is a proper binary tree; that is,
     * there are no loops in the tree, the parent-child relationship is
     * correct, that there are no duplicates, and that every parent has at
     * most 2 children. So, what you will have to check is that the order
     * property of a BST is still satisfied.
     *
     * Should run in O(n). However, you should stop the check as soon as you
     * find evidence that the tree is not a BST rather than checking the rest
     * of the tree.
     * 
     * This method must be implemented recursively.
     *
     * @param <T> the generic typing
     * @param treeRoot the root of the binary tree to check
     * @return true if the binary tree is a BST, false otherwise
     */
    public static <T extends Comparable<? super T>> boolean isBST(
            BSTNode<T> treeRoot) {
        return cBounds(treeRoot, null, null);
    }

    /**
     * Helper method for cBounds; uses a range of values to see if BST is in order
     * @param curr BSTNode type; holds current node information
     * @param lower generic T type; represents the lower value in the range
     * @param upper generic T type; represents the upper value in the range
     * @param <T> generic label; helps maintain generics
     * @return returns boolean-- true if everything is in order, false if it is not
     */
    private static <T extends Comparable<? super T>> boolean cBounds(BSTNode<T> curr, T lower, T upper) {
        if (curr == null) {
            return true;
        }
        boolean ansU = true;
        boolean ansL = true;
        if (upper == null && lower == null) {
            ansU = true;
            ansL = true;
        } else {
            if ((upper == null && curr.getData().compareTo(lower) < 0)
                    || (lower == null && curr.getData().compareTo(upper) > 0)) {
                return false;
            }
            if ((lower != null && curr.getData().compareTo(lower) < 0)
                    || (upper != null && curr.getData().compareTo(upper) > 0)) {
                return false;
            }
        }
        return ansU && ansL && cBounds(curr.getLeft(), lower,
                curr.getData()) && cBounds(curr.getRight(), curr.getData(), upper);
    }
    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
