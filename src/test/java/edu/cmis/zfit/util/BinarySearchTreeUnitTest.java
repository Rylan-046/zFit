package edu.cmis.zfit.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BinarySearchTreeUnitTest {

    @Test
    public void findMinValueTest() {
        BinarySearchTree bt = createBinarySearchTree();
        System.out.println("Min " + bt.findMinValue());

        Assertions.assertEquals(3, bt.findMinValue());
    }

    @Test
    public void findMaxValueTest() {
        BinarySearchTree bt = createBinarySearchTree();
        System.out.println("Max " + bt.findMaxValue());

        Assertions.assertEquals(9, bt.findMaxValue());
    }

    private BinarySearchTree createBinarySearchTree() {
        BinarySearchTree binarySearchTree = new BinarySearchTree();

        binarySearchTree.add(6);
        binarySearchTree.add(4);
        binarySearchTree.add(8);
        binarySearchTree.add(3);
        binarySearchTree.add(5);
        binarySearchTree.add(7);
        binarySearchTree.add(9);

        return binarySearchTree;
    }
}