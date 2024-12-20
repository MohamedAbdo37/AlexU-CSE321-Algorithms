package com.algorithms;

public class MinHeap {
    
    private int size;
    private int maxSize;
    private Node[] heap;

    private static final int FRONT = 1;

    public MinHeap(int maxSize){
        this.maxSize = maxSize;
        this.size = 0;
        this.heap = new Node[maxSize+1];
        this.heap[0] = new Node(null, Integer.MIN_VALUE);
    }

    private int parentPosition(int pos){
        return pos/2;
    }

    private Node getParent(int pos){
        return this.heap[this.parentPosition(pos)];
    }

    private int leftChild(int pos){
        return 2 * pos;
    }

    private int rightChild(int pos){
        return (2 * pos) + 1;
    }

    private Node getLeftChild(int pos){
        return this.heap[2 * pos];
    }

    private Node getRightChild(int pos){
        return this.heap[(2 * pos) + 1];
    }

    private boolean isLeaf(int pos){
        if (pos > this.size/2)
            return true;

        return false;
    }

    private void swap(int fPos, int sPos){
        
        Node temp = this.heap[fPos];
        this.heap[fPos] = this.heap[sPos];
        this.heap[sPos] = temp;
    }

    private void minHeapify(int pos){
        if(!this.isLeaf(pos)){
            int swapPos = pos;

            if(this.rightChild(pos) <= size){
                if(this.getLeftChild(pos).getFrequency() < this.getRightChild(pos).getFrequency())
                    swapPos = this.leftChild(pos);
                else
                    swapPos = this.rightChild(pos);
            } else
                swapPos = this.leftChild(pos);

            if (this.heap[pos].getFrequency() > this.getLeftChild(pos).getFrequency() ||
                this.heap[pos].getFrequency() > this.getRightChild(pos).getFrequency() ){
                    this.swap(pos, swapPos);
                    this.minHeapify(swapPos);
                }

        }
    }

    public void insert(Node element){
        if (this.size >= this.maxSize)
            return;

        this.heap[++size] = element;

        int current = this.size;

        while (this.heap[current].getFrequency() < this.getParent(current).getFrequency()){
            this.swap(current, this.parentPosition(current));
            current = this.parentPosition(current);
        }
    }

    public Node remove() {

            Node popped = this.heap[MinHeap.FRONT];
            this.heap[MinHeap.FRONT] = this.heap[this.size--];
            this.minHeapify(MinHeap.FRONT);
            return popped;
        
    }

    public int getSize() {
        return size;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public Node[] getHeap() {
        return heap;
    }

}
