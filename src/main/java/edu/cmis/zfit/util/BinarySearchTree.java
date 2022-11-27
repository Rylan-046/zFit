package edu.cmis.zfit.util;

public class BinarySearchTree {
    private Node rootNode;

    public void add(int value) {
        rootNode = addNode(rootNode, value, 0);
    }

    public void delete(int value) {
        rootNode = deleteNode(rootNode, value, 0);
    }

    public boolean contains(int value) {
        return contains(rootNode, value, 0);
    }

    public boolean isEmpty() {
        return rootNode == null;
    }

    public int getSize() {
        return getSize(rootNode);
    }

    public Integer findMinValue() {
        return findNodeWithMinValue(rootNode).value;
    }

    public Integer findMaxValue() {
       return findNodeWithMaxValue(rootNode).value;
    }

    private Node findNodeWithMinValue(Node node) {
        if (node.left != null) {
            visit(node.value);
            node = findNodeWithMinValue(node.left);
        }

        return node;
    }

    private Node findNodeWithMaxValue(Node node) {
        if (node.right != null) {
            visit(node.value);
            node = findNodeWithMaxValue(node.right);
        }

        return node;
    }

    private void visit(int value) {
        System.out.println("VISIT " + value);
    }

    private Node addNode(Node current, int value, int depth) {

        // Reached leaf node
        if (current == null) {
//            System.out.println("Adding node at depth: " + depth);
            return new Node(value);
        }

        if (value < current.value) {
            current.left = addNode(current.left, value, depth + 1);
        } else if (value > current.value) {
            current.right = addNode(current.right, value, depth + 1);
        }

        return current;
    }

    private Node deleteNode(Node current, int value, int depth) {
        Node node = null;

        if(current != null) {
            if (value == current.value) {
                if (current.left != null && current.right != null) {
                    node = current.right == null ? current.left : current.right;

                    int minValue = findMinValue(node.right);
                    node.value = minValue;
                    node.right = deleteNode(node.right, minValue, depth + 1);
                }
            } else if (value < current.value) {
                node = current;
                node.left = deleteNode(node.left, value, depth + 1);
            } else {
                node = current;
                node.right = deleteNode(node.right, value, depth + 1);
            }
        }

        System.out.println("Depth: " + depth);

        return node;
    }

    private int findMinValue(Node root) {
        return root.left == null ? root.value : findMinValue(root.left);
    }

    private boolean contains(Node current, int value, int depth) {
        boolean contains = false;

        if (current != null) {
            contains = value == current.value || (value < current.value
                    ? contains(current.left, value, depth + 1)
                    : contains(current.right, value, depth + 1));
        } else {
            System.out.println("Reached leaf at depth: " + depth);
        }

        return contains;
    }

    private int getSize(Node current) {
        return current == null ? 0 : getSize(current.left) + 1 + getSize(current.right);
    }

    static class Node {
        Integer value;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
            right = null;
            left = null;
        }
    }
}
