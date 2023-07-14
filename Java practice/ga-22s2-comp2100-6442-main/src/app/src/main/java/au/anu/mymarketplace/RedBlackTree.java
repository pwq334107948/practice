/**
 * @author Weiqiang Pu, Zeyu Zhang, Chunkun Ouyang
 * @date 2022/9/14  22:59
 */
public class RedBlackTree<K extends Comparable<K>, V> {
    private Node<K, V> root; // The root node of the tree

    /**
     * Initialize empty RBTree
     *
     * @author Weiqiang Pu
     */
    public RedBlackTree() {
        root = null;
    }

    /**
     * Rotate the node, so it becomes the child of its right branch
     * e.g.
     * xp                    xp
     * /                     /
     * [x]                    xr
     * /   \                 /   \
     * xl     xr     == >   [x]     rr
     * / \    / \           /  \
     * ll  lr  rl  rr         xl  rl
     * / \
     * ll  lr
     *
     * @param x the node that needs to rotate
     * @author Weiqiang Pu
     */
    private void leftRotation(Node<K, V> x) {
        if (x != null) {
            Node<K, V> xr = x.right;

            // replace the left node of x
            x.right = xr.left;
            if (xr.left != null) xr.left.parent = x;

            // replace the parent of x to the xr
            xr.parent = x.parent;
            if (x.parent == null) root = xr;
            else {
                if (x.parent.left == x) x.parent.left = xr;
                else x.parent.right = xr;
            }
            xr.left = x;
            x.parent = xr;
        }
    }

    /**
     * Rotate the node, so it becomes the child of its left branch
     * e.g.
     * xp                    xp
     * \                      \
     * [x]                    xl
     * /   \                 /   \
     * xl     xr     == >    ll    [x]
     * / \    / \                  /  \
     * ll  lr  rl  rr               lr   xr
     * / \
     * rl  rr
     *
     * @param x the node that needs to rotate
     * @author Weiqiang Pu
     */
    private void rightRotation(Node<K, V> x) {
        if (x != null) {
            Node<K, V> xl = x.left;

            // replace the left node of x
            x.left = xl.right;
            if (xl.right != null) xl.right.parent = x;

            // replace the parent of x to the xl
            xl.parent = x.parent;
            if (x.parent == null) root = xl;
            else {
                if (x.parent.left == x) x.parent.left = xl;
                else x.parent.right = xl;
            }
            xl.right = x;
            x.parent = xl;
        }
    }

    /**
     * insert the key and value into RedBlackTree
     *
     * @param key   the key needs to insert
     * @param value the corresponding value needs to insert
     * @author Weiqiang Pu
     */
    public void insert(K key, V value) {
        if (key == null) throw new IllegalArgumentException("Cannot insert null");

        // Insert node into tree
        if (root == null) root = new Node<>(key, value);
        else {
            if (get(key) == null) {

                // find the leaf
                Node<K, V> index = root;
                while (index.key != null) {
                    if (index.key.compareTo(key) > 0) index = index.left;
                    else index = index.right;
                }
                Node<K, V> leaf = new Node<>(key, value);
                leaf.parent = index.parent;
                // check the leaf is left or right
                if (index.parent.key.compareTo(leaf.key) > 0) index.parent.left = leaf;
                else index.parent.right = leaf;

                // adjust location and colour
                while (leaf.key != root.key && leaf.parent.colour == Colour.RED) {
                    boolean left = leaf.parent == leaf.parent.parent.left; // Is parent a left node
                    Node<K, V> uncle = left ? leaf.parent.parent.right : leaf.parent.parent.left; // Get opposite "uncle" node to parent

                    if (uncle.colour == Colour.RED) {
                        // Case 1: Recolour
                        leaf.parent.colour = Colour.BLACK;
                        uncle.colour = Colour.BLACK;
                        if (leaf.parent.parent.parent != null && leaf.parent.parent.parent.colour == Colour.BLACK)
                            leaf.parent.parent.colour = Colour.RED;
                        // Check if violated further up the tree
                        leaf = leaf.parent.parent;
                    } else {
                        if (leaf.key == (left ? leaf.parent.right.key : leaf.parent.left.key)) {
                            // Case 2: Left Rotation, uncle is right node, x is on the right / Right Rotation, uncle is left node, x is on the left
                            leaf = leaf.parent;
                            if (left) {
                                // Perform left rotation
                                if (leaf.key == root.key) root = leaf.right; // Update root
                                leftRotation(leaf);
                            } else {
                                // Perform right rotation
                                if (leaf.key == root.key) root = leaf.left; // Update root
                                rightRotation(leaf);
                            }
                        }
                        // Adjust colours to ensure correctness after rotation
                        leaf.parent.colour = Colour.BLACK;
                        leaf.parent.parent.colour = Colour.RED;
                        // Case 3 : Right Rotation, uncle is right node, x is on the left / Left Rotation, uncle is left node, x is on the right
                        leaf = leaf.parent.parent;
                        if (left) {
                            // Perform right rotation
                            if (leaf.key == root.key) root = leaf.left; // Update root
                            rightRotation(leaf);
                        } else {
                            // Perform left rotation
                            if (leaf.key == root.key) root = leaf.right; // Update root
                            leftRotation(leaf);
                        }
                    }
                }
            }
        }
        // Ensure property 2 (root and leaves are black) holds
        root.colour = Colour.BLACK;
    }

    /**
     * delete the node that stores the give key and corresponding value
     *
     * @param key that needs to be deleted
     * @author Weiqiang Pu
     */
    public V delete(K key) {
        Node<K, V> deleted = get(key);
        if (deleted == null) return null;
        else {
            V deletedValue = deleted.value;

            // If strictly internal, copy predecessor's element to deleted node and then make deleted node point to predecessor.
            // Case 1: deleted node has 2 children
            if (deleted.left.key != null && deleted.right.key != null) {
                Node<K, V> successor = successor(deleted);
                deleted.key = successor.key;
                deleted.value = successor.value;
                deleted = successor;
            }
            // Start fixup at replacement node, if it exists.
            Node<K, V> replacement = (deleted.left != null ? deleted.left : deleted.right);
            if (replacement != null) {
                // Link replacement to parent
                replacement.parent = deleted.parent;
                if (deleted.parent == null) root = replacement;
                else if (deleted == deleted.parent.left) deleted.parent.left = replacement;
                else deleted.parent.right = replacement;
                // Null out links, so they are OK to use by fixAfterDeletion.
                deleted.left = deleted.right = deleted.parent = null;
                // Fix replacement
                if (deleted.colour == Colour.BLACK)
                    fixAfterDeletion(replacement);
            } else if (deleted.parent == null) {
                // return if we are the only node.
                root = null;
            } else { //Case 2: No children. Use self as phantom replacement and unlink.
                if (deleted.colour == Colour.BLACK)
                    fixAfterDeletion(deleted);
                if (deleted.parent != null) {
                    if (deleted == deleted.parent.left) deleted.parent.left = null;
                    else if (deleted == deleted.parent.right) deleted.parent.right = null;
                    deleted.parent = null;
                }
            }
            return deletedValue;
        }
    }

    /**
     * fix the tree after deletion
     *
     * @param x the node needs to fix
     * @author Weiqiang Pu
     */
    private void fixAfterDeletion(Node<K, V> x) {
        if (x != null) {
            while (x != root && x.colour == Colour.BLACK) {
                if (x == x.parent.left) {
                    Node<K, V> sib = x.parent.right;
                    if (sib.colour == Colour.RED) {
                        sib.colour = Colour.BLACK;
                        x.parent.colour = Colour.RED;
                        leftRotation(x.parent);
                        sib = x.parent.right;
                    }
                    if (sib.left.colour == Colour.BLACK && sib.right.colour == Colour.BLACK) {
                        sib.colour = Colour.RED;
                        x = x.parent;
                    } else {
                        if (sib.right.colour == Colour.BLACK) {
                            sib.left.colour = Colour.BLACK;
                            sib.colour = Colour.RED;
                            rightRotation(sib);
                            sib = x.parent.right;
                        }
                        sib.colour = x.parent.colour;
                        x.parent.colour = Colour.BLACK;
                        sib.right.colour = Colour.BLACK;
                        leftRotation(x.parent);
                        x = root;
                    }
                } else { // symmetric
                    Node<K, V> sib = x.parent.left;

                    if (sib.colour == Colour.RED) {
                        sib.colour = Colour.BLACK;
                        x.parent.colour = Colour.RED;
                        rightRotation(x.parent);
                        sib = x.parent.left;
                    }

                    if (sib.right.colour == Colour.BLACK && sib.left.colour == Colour.BLACK) {
                        sib.colour = Colour.RED;
                        x = x.parent;
                    } else {
                        if (sib.left.colour == Colour.BLACK) {
                            sib.right.colour = Colour.BLACK;
                            sib.colour = Colour.RED;
                            leftRotation(sib);
                            sib = x.parent.left;
                        }
                        sib.colour = x.parent.colour;
                        x.parent.colour = Colour.BLACK;
                        sib.left.colour = Colour.BLACK;
                        rightRotation(x.parent);
                        x = root;
                    }
                }
            }
            x.colour = Colour.BLACK;
        }
    }

    /**
     * find the precursor of the given node, that is, the node storing the maximum key which lees than the given key
     *
     * @param x the node that needs to process
     * @return the node storing the maximum key which lees than the given key
     * @author Weiqiang Pu
     */
    private Node<K, V> successor(Node<K, V> x) {
        if (x == null) return null;
        else if (x.right.key != null) {
            x = x.right;
            while (x.left.key != null) x = x.left;
            return x;
        } else {
            Node<K, V> p = x.parent;
            while (p != null && x == p.right) {
                x = p;
                p = p.parent;
            }
            return p;
        }
    }

    /**
     * Return the corresponding node of a key, if it exists in the tree
     *
     * @param x   Node<K,V> The root node of the tree we search for the key {@code key}
     * @param key The key that we are looking for
     * @return the Node<K,V> that stores the key
     * @author Weiqiang Pu
     */
    private Node<K, V> find(Node<K, V> x, K key) {
        if (x == null || x.key == null) return null;
        int compare = key.compareTo(x.key);
        if (compare < 0) return find(x.left, key);
        else if (compare > 0) return find(x.right, key);
        else return x;
    }

    /**
     * @param key T The key we are looking for
     * @return a node if the key of the node is {@code key}.
     * @author Weiqiang Pu
     */
    public Node<K, V> get(K key) {
        return find(root, key);
    }

    /**
     * Removes all the mappings from this tree. The tree will be empty after this call returns.
     *
     * @author Weiqiang Pu
     */
    public void clear() {
        root = null;
    }

    /**
     * Return a tree for test
     *
     * @param tree Tree we want to pre-order traverse
     * @return the result of a pre-order traversal of the tree
     * @author Weiqiang Pu
     */
    private String preOrder(Node<K, V> tree) {
        if (tree != null && tree.key != null) {
            String leftStr = preOrder(tree.left);
            String rightStr = preOrder(tree.right);
            return tree.key + (leftStr.isEmpty() ? leftStr : " " + leftStr)
                    + (rightStr.isEmpty() ? rightStr : " " + rightStr);
        } else return "";
    }

    public String preOrder() {
        return preOrder(root);
    }

    /**
     * This is the helper function of the demonstrate the price-matched product
     * in the Search Interface.
     *
     * @author Zeyu Zhang u7394442, Chunkun Ouyang u7443132
     */


    public Node<K, V> returnNode(K needPrice) {

        //RedBlackTree.Node newNode = new RedBlackTree.Node(maxPrice, null);

        this.insert(needPrice, null);

        Node<K, V> newInsert = this.get(needPrice);

        if (newInsert.parent != null) {
            return newInsert.parent;
        } else {
            return newInsert.left == null ? newInsert.right : newInsert.left;
        }

    }

    public V returnItem2(Node<K, V> n) {
        return n.getValue();
    }

    public static class Node<K extends Comparable<K>, V> {
        private Node<K, V> parent; //Parent node
        private Node<K, V> left, right; //Children nodes
        private Colour colour; // Node colour
        private K key; // Node key
        private V value; // Node value

        Node(K key, V value) {
            this.key = key;
            this.value = value;
            colour = Colour.RED;
            parent = null;

            // Initialise children leaf nodes
            left = new Node<>();
            right = new Node<>();
            left.parent = this;
            right.parent = this;
        }

        // Leaf node
        Node() {
            key = null;
            value = null;
            colour = Colour.BLACK;
        }

        public Colour getColour() {
            return colour;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }

    public enum Colour {RED, BLACK}
}
