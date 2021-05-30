public class bsttest {
    public static void main(String[] args) {
        BSTNode<Integer> root = new BSTNode<>(50);
        root.setLeft(new BSTNode<>(75));
        root.setRight(new BSTNode<>(25));
        boolean and = BST.isBST(root);
        System.out.println(and);
    }
}
