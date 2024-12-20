package com.algorithms;

public class Node {
    private final String word;
    private String value;
    private int frequency;
    private Node parent;

    public Node(String word, int frequency) {
        this.word = word;
        this.frequency = frequency;
    }

    public void increaseFrequency() {
        this.frequency++;
    }
    
    public String getWord() {
        return word;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public void setLeft(Node left) {
        left.setParent(this);
        left.setValue("0");
    }

    public void setRight(Node right) {
        right.setParent(this);
        right.setValue("1");
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    
}
