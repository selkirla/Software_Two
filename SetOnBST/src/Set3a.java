import java.util.Iterator;

import components.binarytree.BinaryTree;
import components.binarytree.BinaryTree1;
import components.set.Set;
import components.set.SetSecondary;

/**
 * {@code Set} represented as a {@code BinaryTree} (maintained as a binary
 * search tree) of elements with implementations of primary methods.
 *
 * @param <T>
 *            type of {@code Set} elements
 * @mathdefinitions <pre>
 * IS_BST(
 *   tree: binary tree of T
 *  ): boolean satisfies
 *  [tree satisfies the binary search tree properties as described in the
 *   slides with the ordering reported by compareTo for T, including that
 *   it has no duplicate labels]
 * </pre>
 * @convention IS_BST($this.tree)
 * @correspondence this = labels($this.tree)
 *
 * @author Selin Kirbas & [Removed for privacy]
 *
 */
public class Set3a<T extends Comparable<T>> extends SetSecondary<T> {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Elements included in {@code this}.
     */
    private BinaryTree<T> tree;

    /**
     * Returns whether {@code x} is in {@code t}.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} to be searched
     * @param x
     *            the label to be searched for
     * @return true if t contains x, false otherwise
     * @requires IS_BST(t)
     * @ensures isInTree = (x is in labels(t))
     */
    private static <T extends Comparable<T>> boolean isInTree(BinaryTree<T> t,
            T x) {
        assert t != null : "Violation of: t is not null";
        assert x != null : "Violation of: x is not null";

        BinaryTree<T> leftTree = t.newInstance();
        BinaryTree<T> rightTree = t.newInstance();

        //if the tree is empty, x is not in tree (base case)
        if (t.size() == 0) {
            return false;
        }

        T rootLabel = t.root();

        //compare value x with root label of tree
        int compare = x.compareTo(rootLabel);

        //if x is equal to root, it is in tree
        if (compare == 0) {
            return true;

        //if x is less than root, search in left subtree
        } else if (compare < 0) {
            //make left and right subtrees
            rootLabel = t.disassemble(leftTree, rightTree);

            //recursively search in left subtree
            boolean isInLeftSubtree = isInTree(leftTree, x);

            //reassemble tree
            t.assemble(rootLabel, leftTree, rightTree);

            return isInLeftSubtree;
        //if x is greater than root, search in right subtree
        } else {
            //make left and right subtrees
            rootLabel = t.disassemble(leftTree, rightTree);

            //recursively search in right subtree
            boolean isInRightSubtree = isInTree(rightTree, x);

            //reassemble tree
            t.assemble(rootLabel, leftTree, rightTree);

            return isInRightSubtree;
        }
    }

    /**
     * Inserts {@code x} in {@code t}.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} to be searched
     * @param x
     *            the label to be inserted
     * @aliases reference {@code x}
     * @updates t
     * @requires IS_BST(t) and x is not in labels(t)
     * @ensures IS_BST(t) and labels(t) = labels(#t) union {x}
     */
    private static <T extends Comparable<T>> void insertInTree(BinaryTree<T> t,
            T x) {
        assert t != null : "Violation of: t is not null";
        assert x != null : "Violation of: x is not null";

        BinaryTree<T> leftTree = t.newInstance();
        BinaryTree<T> rightTree = t.newInstance();

        if (t.size() == 0) {
            //if the tree is empty, create a new leaf node with x
            t.assemble(x, leftTree, rightTree);
        } else {
            T label = t.root();

            int compare = x.compareTo(label);

            //if x is less than root, insert in left subtree
            if (compare < 0) {
                //make left and right subtrees
                label = t.disassemble(leftTree, rightTree);

                //recursively insert x in left subtree
                insertInTree(leftTree, x);

                //reassemble tree
                t.assemble(label, leftTree, rightTree);

            //if x is greater than root, insert in right subtree
            } else {
                //make left and right subtrees
                label = t.disassemble(leftTree, rightTree);

                //recursively insert x in right subtree
                insertInTree(rightTree, x);

                //reassemble tree
                t.assemble(label, leftTree, rightTree);
            }
        }

    }

    /**
     * Removes and returns the smallest (left-most) label in {@code t}.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} from which to remove the label
     * @return the smallest label in the given {@code BinaryTree}
     * @updates t
     * @requires IS_BST(t) and |t| > 0
     * @ensures <pre>
     * IS_BST(t)  and  removeSmallest = [the smallest label in #t]  and
     *  labels(t) = labels(#t) \ {removeSmallest}
     * </pre>
     */
    private static <T> T removeSmallest(BinaryTree<T> t) {
        assert t != null : "Violation of: t is not null";
        assert t.size() > 0 : "Violation of: |t| > 0";

        T small;

        BinaryTree<T> left = t.newInstance();
        BinaryTree<T> right = t.newInstance();

        T removed = t.disassemble(left, right);

        //check if left subtree is empty/no smaller labels
        if (left.size() == 0) {
            //if left subtree is empty, smallest label is removed root label
            small = removed;
            t.transferFrom(right);
        } else {
            //if left subtree is not empty, recursively remove smallest label from it
            small = removeSmallest(left);

            //reassemble tree with label, updated left subtree, OG right subtree
            t.inOrderAssemble(removed, left, right);
        }

        return small;

    }

    /**
     * Finds label {@code x} in {@code t}, removes it from {@code t}, and
     * returns it.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} from which to remove label {@code x}
     * @param x
     *            the label to be removed
     * @return the removed label
     * @updates t
     * @requires IS_BST(t) and x is in labels(t)
     * @ensures <pre>
     * IS_BST(t)  and  removeFromTree = x  and
     *  labels(t) = labels(#t) \ {x}
     * </pre>
     */
    private static <T extends Comparable<T>> T removeFromTree(BinaryTree<T> t,
            T x) {
        assert t != null : "Violation of: t is not null";
        assert x != null : "Violation of: x is not null";
        assert t.size() > 0 : "Violation of: x is in labels(t)";

        if (t.size() == 0) {
            //label x can't be found
            return null;
        }

        T root = t.root();
        BinaryTree<T> left = t.newInstance();
        BinaryTree<T> right = t.newInstance();
        t.disassemble(left, right);

        if (x.compareTo(root) < 0) {
            //search in left subtree
            T removed = removeFromTree(left, x);
            t.assemble(root, left, right);

            return removed;
        } else if (x.compareTo(root) > 0) {
            //search in right subtree
            T removed = removeFromTree(right, x);
            t.assemble(root, left, right);

            return removed;
        } else {
            //found x in tree
            if (left.size() == 0) {
                //if no left subtree, replace with right subtree
                t.transferFrom(right);
            } else if (right.size() == 0) {
                //if no right subtree, replace with left subtree
                t.transferFrom(left);
            } else {
                //if both subtrees exist, find smallest label in right subtree
                T smallest = removeSmallest(right);

                //assemble new root with smallest label
                t.assemble(smallest, left, right);
            }
            return root;
        }
    }

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {

        this.tree = new BinaryTree1<T>();

    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Set3a() {

        this.createNewRep();

    }

    /*
     * Standard methods -------------------------------------------------------
     */

    @SuppressWarnings("unchecked")
    @Override
    public final Set<T> newInstance() {
        try {
            return this.getClass().getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(
                    "Cannot construct object of type " + this.getClass());
        }
    }

    @Override
    public final void clear() {
        this.createNewRep();
    }

    @Override
    public final void transferFrom(Set<T> source) {
        assert source != null : "Violation of: source is not null";
        assert source != this : "Violation of: source is not this";
        assert source instanceof Set3a<?> : ""
        + "Violation of: source is of dynamic type Set3<?>";
        /*
         * This cast cannot fail since the assert above would have stopped
         * execution in that case: source must be of dynamic type Set3a<?>, and
         * the ? must be T or the call would not have compiled.
         */
        Set3a<T> localSource = (Set3a<T>) source;
        this.tree = localSource.tree;
        localSource.createNewRep();
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */

    @Override
    public final void add(T x) {
        assert x != null : "Violation of: x is not null";
        assert !this.contains(x) : "Violation of: x is not in this";

        insertInTree(this.tree, x);
    }

    @Override
    public final T remove(T x) {
        assert x != null : "Violation of: x is not null";
        assert this.contains(x) : "Violation of: x is in this";

        return removeFromTree(this.tree, x);
    }

    @Override
    public final T removeAny() {
        assert this.size() > 0 : "Violation of: this /= empty_set";

        return removeSmallest(this.tree);
    }

    @Override
    public final boolean contains(T x) {
        assert x != null : "Violation of: x is not null";

        return isInTree(this.tree, x);
    }

    @Override
    public final int size() {
        return this.tree.size();
    }

    @Override
    public final Iterator<T> iterator() {
        return this.tree.iterator();
    }

}
