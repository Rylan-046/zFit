package edu.cmis.zfit.util;

public class BinarySearchTree<T extends Comparable> {
    private Node<T> rootNode;

    public void add(T value) {
        rootNode = addNode(rootNode, value, 0);
    }

    public void delete(T value) {
        rootNode = deleteNode(rootNode, value, 0);
    }

    public boolean contains(T value) {
        return contains(rootNode, value, 0);
    }

    public boolean isEmpty() {
        return rootNode == null;
    }

    public int getSize() {
        return getSize(rootNode);
    }

    public T findMinValue() {
        return getSize() > 0 ? findNodeWithMinValue(rootNode).value : null;
    }

    public T findMaxValue() {
       return getSize() > 0 ? findNodeWithMaxValue(rootNode).value : null;
    }

    private Node<T> findNodeWithMinValue(Node<T> node) {
        if (node != null && node.left != null) {
            visit(node.value);
            node = findNodeWithMinValue(node.left);
        }

        return node;
    }

    private Node<T> findNodeWithMaxValue(Node<T> node) {
        if (node != null && node.right != null) {
            visit(node.value);
            node = findNodeWithMaxValue(node.right);
        }

        return node;
    }

    private void visit(T value) {
        System.out.println("VISIT " + value);
    }

    private Node<T> addNode(Node<T> current, T value, int depth) {
        // Reached leaf node
        if (current == null) {
            //System.out.println("Adding node at depth: " + depth);
            return new Node<>(value);
        }

        if(value.compareTo(current.value) < 0) {
            current.left = addNode(current.left, value, depth + 1);
        } else if (value.compareTo(current.value) > 0) {
            current.right = addNode(current.right, value, depth + 1);
        }

        return current;
    }

    private Node<T> deleteNode(Node<T> current, T value, int depth) {
        Node<T> node = null;

        if(current != null) {
            if (value == current.value) {
                if (current.left != null && current.right != null) {
                    node = current.right == null ? current.left : current.right;

                    T minValue = findMinValue(node.right);
                    node.value = minValue;
                    node.right = deleteNode(node.right, minValue, depth + 1);
                }
            } else if (value.compareTo(current.value) < 0) {
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

    private T findMinValue(Node<T> root) {
        return root.left == null ? root.value : findMinValue(root.left);
    }

    private boolean contains(Node<T> current, T value, int depth) {
        boolean contains = false;

        if (current != null) {
            contains = value == current.value || (value.compareTo(current.value) < 0
                    ? contains(current.left, value, depth + 1)
                    : contains(current.right, value, depth + 1));
        } else {
            System.out.println("Reached leaf at depth: " + depth);
        }

        return contains;
    }

    private int getSize(Node<T> current) {
        return current == null ? 0 : getSize(current.left) + 1 + getSize(current.right);
    }

    static class Node<T extends Comparable> {
        T value;
        Node<T> left;
        Node<T> right;

        Node(T value) {
            this.value = value;
            right = null;
            left = null;
        }
    }
}