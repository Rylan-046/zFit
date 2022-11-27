package edu.cmis.zfit.util;

public class HashTable<K, V> {
    private static final int INITIAL_SIZE = 128;
    private static final double RESIZE_PERCENTAGE = 0.75;

    private Node<K, V>[] table;

    private int size;

    public HashTable() {
        this(INITIAL_SIZE);
    }

    public HashTable(int initialSize) {
        table = new Node[initialSize];
    }

    public V put(K key, V value) {
        int keyHash = hash(key);
        Node<K, V> current = table[keyHash];

        boolean found = false;

        while(current !=null && !found) {
            found = current.key.equals(key);

            if(!found) {
                current = current.next;
            }
        }

        if(found) {
            current.value = value;
        } else {
            if (size >= RESIZE_PERCENTAGE * table.length) {
                resize();
            }

            Node<K, V> newNode = new Node<>();
            newNode.key = key;
            newNode.value = value;
            newNode.next = table[keyHash];
            table[keyHash] = newNode;
            size++;
        }

        return value;
    }


    public V get(K key) {
        int keyHash = hash(key);
        Node<K, V> current = table[keyHash];
        Node<K, V> targetNode = null;

        while(targetNode == null && current != null) {
            if(current.key.equals(key)) {
                targetNode = current;
            } else {
                current = current.next;
            }
        }

        return targetNode != null ? current.value : null;
    }

    public void remove(K key) {
        int keyHash = hash(key);

        if(table[keyHash] != null) {
            // Track previous
            Node<K,V> previous = table[keyHash];
            Node<K,V> current = previous;
            Node<K,V> match = null;

            int index = 0;

            // Find key match and track previous node
            while(match == null && index < size) {
                if(current.key.equals(key)) {
                    match = current;
                } else {
                    previous = current;
                    current = current.next;
                    index++;
                }
            }

            if(match != null) {
                // A match is found, remove the node
                if (index == 0) {
                    // Matches on first entry, no need to handle previous node
                    table[keyHash] = table[keyHash].next;
                } else if (index < size -1) {
                    // Matches on middle point previous to node ahead of match
                    previous.next = match.next;
                }

                size--;
            }
        }
    }

    public boolean containsKey(K key) {
        int keyHash = hash(key);

        Node<K, V> current = table[keyHash];
        boolean exists = false;

        while(current != null && !exists) {
            exists = current.key.equals(key);
            current = current.next;
        }

        return exists;
    }

    public int size() {
        return size;
    }

    private int hash(K key) {
        return (Math.abs(key.hashCode())) % table.length;
    }

    private void resize() {
        int tblSize = (int) (table.length + (table.length - (RESIZE_PERCENTAGE * table.length)));
        Node[] resizeTable = new Node[tblSize];

        for (int i = 0; i < table.length; i++) {

            Node list = table[i];
            while (list != null) {

                Node next = list.next;

                int hash = (Math.abs(list.key.hashCode())) % resizeTable.length;

                list.next = resizeTable[hash];
                resizeTable[hash] = list;
                list = next;
            }
        }
        table = resizeTable;
    }

    private static class Node<K, V> {
        K key;
        V value;
        Node<K,V> next;
    }
}