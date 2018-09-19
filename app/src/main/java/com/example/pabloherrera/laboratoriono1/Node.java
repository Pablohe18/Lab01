package com.example.pabloherrera.laboratoriono1;

public class Node {

    String value;
    int count;
    Node left;
    Node right;
    Node linker;
    Node linkerBack;

    Node(String value, int count) {

        this.value = value;
        this.count = count;
        this.left = null;
        this.right = null;
        this.linker = null;
        this.linkerBack = null;

    }

}
