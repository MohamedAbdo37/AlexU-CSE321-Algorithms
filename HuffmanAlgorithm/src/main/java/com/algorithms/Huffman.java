package com.algorithms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Huffman {
    private final InputOutput inputOutput;
    private int bytesNumber;
    private int chunkNumber;
    private HashMap<String, Node> wordsHashMap = new HashMap<String, Node>();
    private Node root;

    public Huffman(InputOutput inputOutput, int bytesNumber) {
        this.inputOutput = inputOutput;
        this.bytesNumber = bytesNumber;
        this.chunkNumber = 0;
    }

    public void compress() throws Exception {
        List<String> wordsList = new ArrayList<String>();
        System.out.println("Compression starts");
        byte[] chunk;
        do {
            try{
                chunk = this.inputOutput.readFile(this.chunkNumber++);
            } catch (IOException e) {
                System.out.println("End of file");
                break;
            }
            
            for (int i = 0; i < chunk.length;) {
                String word = "";
                for (int j = 0; j < this.bytesNumber && i < chunk.length ; j++) 
                    word = word.concat(this.byteToBinaryString(chunk[i++]));
                if(wordsHashMap.containsKey(word))
                    wordsHashMap.get(word).increaseFrequency();
                else{
                    wordsHashMap.put(word, new Node(word, 1));
                    wordsList.add(word);
                }
            }
        }while(chunk.length == this.inputOutput.getChunkSize());

        this.root = this.buildTree(wordsList);
        System.gc();
        
        String byteCode = "";
        String buffer = "";
        String code;
        this.chunkNumber = 0;
        int b = 0;
        byte[] kb = new byte[1024];

        do {
            try{
                chunk = this.inputOutput.readFile(this.chunkNumber++);
            } catch (IOException e) {
                System.out.println("End of file");
                break;
            }
            for (int i = 0; i < chunk.length;) {
                String word = "";
                for (int j = 0; j < this.bytesNumber && i < chunk.length; j++) {
                    word = word.concat(this.byteToBinaryString(chunk[i++]));
                }
                
                code = wordsHashMap.get(word).getValue();
                buffer = buffer.concat(code);

                while (buffer.length() >= 8) { 
                    byteCode = byteCode.concat(buffer.substring(0, 8));
                    buffer = buffer.substring(8);
                    kb[b%1024] = this.binaryStringToByte(byteCode);
                    byteCode = "";
                    b++;
                }

                if(b % 1024 == 0){
                    this.inputOutput.appendToFile(kb, this.bytesNumber);
                }

            }

            for(int i = 0; i< b%1024;i++){
                this.inputOutput.appendToFile(kb[i], this.bytesNumber);
            }

        }while(chunk.length == this.inputOutput.getChunkSize());
        System.out.println("Compressed to " + b + " bytes");
        System.out.println("Compression ends");
    }

    private String byteToBinaryString(byte b) {
        return String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
    }

    public Node buildTree(List<String> wordsList) throws IOException{
        MinHeap heap = new MinHeap(wordsList.size());
        Node node;
        for(String word : wordsList) { 
            node = this.wordsHashMap.get(word);
            heap.insert(node);
        }

        while(heap.getSize() > 1){
            node = this.mergeNodes(heap.remove(),heap.remove());
            heap.insert(node);
        }

        HashMap<String, String> headerHashMap = new HashMap<String, String>();

        for(String word : wordsList) {
            node = this.wordsHashMap.get(word);
            node.setValue(findCode(node));
            headerHashMap.put(node.getValue(), word);
        }

        this.inputOutput.writeHeader(headerHashMap, this.bytesNumber);

        return heap.remove();
    }

    private Node mergeNodes(Node node1, Node node2) {
        Node node = new Node(null, node1.getFrequency() + node2.getFrequency());
        node.setLeft(node1);
        node.setRight(node2);
        node.setValue(null);
        return node;
    }

    private String findCode(Node node) {
        if(node.getValue() == null)
            return "";
        if (node.getValue().length() > 1)
            return node.getValue();
        
        return findCode(node.getParent()) + node.getValue();
    }

    private byte binaryStringToByte(String binaryString) {
        return (byte) Integer.parseInt(binaryString, 2);
    }

    public static void main(String[] args) {
        long start;
        long end;
        int bytesNumber = 2;
        String filePath = "C:\\Users\\Mohamed Abdel-Moneim\\Desktop\\Alice in Wonderland.txt" ;
        InputOutput inputOutput;
        try {
            inputOutput = new InputOutput(filePath, bytesNumber);
        } catch (InputPathException e) {
            System.out.println(e.getMessage());
            return;
        }
        System.err.println("Program starts to compress the file");
        System.out.println("Timer starts");
        start = System.currentTimeMillis();
        Huffman huffman = new Huffman(inputOutput, bytesNumber);
        try {
            huffman.compress();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        end = System.currentTimeMillis();
        System.out.println("Timer ends");
        System.out.println("Time: " + (end - start) + " ms");

        // System.out.println("Program starts to decompress the file");
        // System.out.println("Timer starts");
        // start = System.currentTimeMillis();
        // try {
        //     huffman.decompress();
        // } catch (Exception e) {
        //     System.out.println(e.getMessage());
        // }
        // end = System.currentTimeMillis();
        // System.out.println("Timer ends");
        // System.out.println("Time: " + (end - start) + " ms");

    }

    private void decompress() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    // private void buildHeader() {
    //     int tagByte;
    //     String tag ;
    //     while (true) { 
    //         tagByte = (byte) 0;
    //         tag = String.format("%8s", Integer.toBinaryString( (byte) tagByte & 0xFF)).replace(' ', '0');
    //         if(!(this.headerHashMap.containsKey(tag) || this.headerHashMap.containsValue(tag)))
    //             break;
    //     }
    //     this.inputOutput.appendToFile((byte) tagByte, this.bytesNumber);

    // }
    
}
