package nl.hva.dmci.ict.se.datastructures;

/**
 * @author Hugo Thunnissen
 *
 * BST with API as described in Sedgewick and Wayne.
 */
public class BinarySearchTree <Key extends Comparable<Key>, Value> {

    protected Node root;

    public BinarySearchTree() {
        
    }

    protected int size (Node node) {
        return node == null ? 0 : node.getSubTreeAmount();
    }

    public int size() {
        return size(root);
    }

    public Value get (Key key) {
        return get(root, key);
    }

    public int rank(Key key) {
        return rank(key, root);
    }

    public int rank(Key key, Node node) {
        if (node == null)
            return 0;

        int comparison = node.getKey().compareTo(key);
        
        if (comparison > 0) {
            return rank(key, node.getLeft());
        } else if (comparison < 0) {
            return 1 + size(node.getLeft()) + rank(key, node.getRight());
        } else {
            return size(node.getLeft());
        }
    }

    private Value get (Node node, Key key) {
        if (null == node)
            return null;

        int comparison = node.getKey().compareTo(key);
        
        if (comparison > 0) {
            return get(node.getLeft(), key);
        } else if (comparison < 0) {
            return get(node.getRight(), key);
        } else {
            return node.getValue();
        }
    }

    public void put(Key key, Value value) {
        root = put(root, key, value);
    }

    protected Node put(Node node, Key key, Value value){
        if (node == null)
            return new Node(value, key, 1);
        
        int comparison = node.getKey().compareTo(key);

        if (comparison > 0) {
            node.setLeft(put(node.getLeft(), key, value));
        } else if (comparison < 0) {
            node.setRight(put(node.getRight(), key, value));
        } else {
            node.setValue(value);
        }
        node.setSubTreeAmount(size(node.getLeft()) + size(node.getRight()) + 1);
        return node;
    }


    /**
     * Node of tree.
     */
    protected class Node {
        private Value value;
        private Key key;
        private int subTreeAmount;
        
        // Nodes
        private Node left;
        private Node right;

        public Node (Value value, Key key, int subTreeAmount) {
            this.value         = value;
            this.key           = key;
            this.subTreeAmount = subTreeAmount;
        }


        public void setValue (Value value) {
            this.value = value;
        }

        public Value getValue () {
            return value;
        }

        public void setKey (Key key) {
            this.key = key;
        }

        public Key getKey () {
            return key;
        }

        public void setSubTreeAmount (int subTreeAmount) {
            this.subTreeAmount = subTreeAmount;
        }

        public int getSubTreeAmount () {
            return subTreeAmount;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getLeft() {
            return left;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        public Node getRight() {
            return right;
        }
    }
}

