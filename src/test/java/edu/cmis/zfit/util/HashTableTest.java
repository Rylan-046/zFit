package edu.cmis.zfit.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HashTableTest {
    private HashTable<String, String> hashTable;

    @BeforeEach
    public void setup() {
        hashTable = new HashTable<>(24);
        hashTable.put("1", "a");
        hashTable.put("2", "b");
        hashTable.put("3", "c");
        hashTable.put("4", "d");
        hashTable.put("5", "e");
    }

    @Test
    public void putExistTest() {
        hashTable.put("2", "z");
        Assertions.assertEquals("z", hashTable.get("2"));
    }

    @Test
    public void putWithoutResizeTest() {
        hashTable.put("6", "f");
        Assertions.assertEquals("f", hashTable.get("6"));
    }

    @Test
    public void putWithResizeTest() {
        hashTable.put("6", "f");
        hashTable.put("7", "g");
        hashTable.put("8", "h");
        hashTable.put("9", "i");
        hashTable.put("10", "j");
        hashTable.put("11", "k");
        hashTable.put("12", "l");

        Assertions.assertEquals("f", hashTable.get("6"));
        Assertions.assertEquals("g", hashTable.get("7"));
        Assertions.assertEquals("h", hashTable.get("8"));
        Assertions.assertEquals("i", hashTable.get("9"));
        Assertions.assertEquals("j", hashTable.get("10"));
        Assertions.assertEquals("k", hashTable.get("11"));
        Assertions.assertEquals("l", hashTable.get("12"));
    }

    @Test
    public void getTest() {
        Assertions.assertEquals("d", hashTable.get("4"));
    }

    @Test
    public void containsKeyTest() {
        Assertions.assertTrue(hashTable.containsKey("3"));
    }

    @Test
    public void notContainsKeyTest() {
        Assertions.assertFalse(hashTable.containsKey("23"));
    }

    @Test
    public void removeFromBeginningTest() {
        hashTable.remove("1");
        Assertions.assertNull(hashTable.get("1"));
    }

    @Test
    public void removeFromMiddleTest() {
        hashTable.remove("3");
        Assertions.assertNull(hashTable.get("3"));
    }

    @Test
    public void removeFromEndTest() {
        hashTable.remove("5");
        Assertions.assertNull(hashTable.get("5"));
    }
}
