package com.algorithms;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Huffman {
    private final InputOutput inputOutput;
    private int bytesNumber;
    private int chunkNumber;
    private HashMap<String, Node> wordsHashMap = new HashMap<String, Node>();
    private HashMap<String, String> keyHashMap = new HashMap<String, String>();

    public Huffman(InputOutput inputOutput, int bytesNumber) {
        this.inputOutput = inputOutput;
        this.bytesNumber = bytesNumber;
        this.chunkNumber = 0;
    }

    public void compress() throws Exception {
        List<String> wordsList = new ArrayList<String>();
        // System.out.println("Compression starts");
        byte[] chunk;
        int c = 0;
        do {
            try{
                chunk = this.inputOutput.readFile(this.chunkNumber++);
                c++;
            } catch (IOException e) {
                System.out.println("File Scanned Successfully at chunk: " + c);
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

        this.buildTree(wordsList);

        String byteCode = "";
        String buffer = "";
        String code;
        this.chunkNumber = 0;
        int b = 0;
        byte[] kb = new byte[1024];
        c = 0;
        do {
            // read the file for compression
            try{
                chunk = this.inputOutput.readFile(this.chunkNumber++);
                c++;
            } catch (IOException e) {
                System.out.println("File Compressed Successfully at chunk: " + c);
                break;
            }

            // compress the chunk
            for (int i = 0; i < chunk.length;) {
                if(b >= 291)
                    b = b;
                String word = "";
                for (int j = 0; j < this.bytesNumber && i < chunk.length; j++) {
                    word = word.concat(this.byteToBinaryString(chunk[i++]));
                }

                // if(i == 4095)
                //     i = i;
                
                if(!this.wordsHashMap.containsKey(word)){
                    System.out.println("word not found: " + word);
                    System.out.println("chunk number: " + c);
                    System.out.println("byte number: " + b);
                }
                code = wordsHashMap.get(word).getValue();
                buffer = buffer.concat(code);

                while (buffer.length() >= 8) { 
                    if(b >= 291)
                        b = b;
                    byteCode = byteCode.concat(buffer.substring(0, 8));
                    buffer = buffer.substring(8);
                    kb[b%1024] = this.binaryStringToByte(byteCode);
                    byteCode = "";
                    b++;

                    if(b % 1024 == 0){
                        this.inputOutput.appendToFile(kb, this.bytesNumber);
                    }
                }

            }

            // discharging the buffer
            while (buffer.length() >= 8) { 
                if(b >= 291)
                    b = b;
                byteCode = byteCode.concat(buffer.substring(0, 8));
                buffer = buffer.substring(8);
                kb[b%1024] = this.binaryStringToByte(byteCode);
                byteCode = "";
                b++;

                if(b % 1024 == 0){
                    this.inputOutput.appendToFile(kb, this.bytesNumber);
                }
            }

        }while(chunk.length == this.inputOutput.getChunkSize());

        if(b >= 291)
            b = b;

        // complete last compressed byte with non-key values
        if(!buffer.isEmpty()){
            int n = 8 - buffer.length();
            String add = "";
            while(add.length() < n){
                if(!this.keyHashMap.containsKey(add + "0"))
                    add = add + "0";
                else if(!this.keyHashMap.containsKey(add + "1"))
                    add = add + "1";
            }
            buffer = buffer + add;
            kb[b%1024] = this.binaryStringToByte(buffer);
            buffer = buffer.substring(8);
            b++;
        }
        

        // write last KB of compressed data
        this.inputOutput.appendToFile(kb, b%1024,this.bytesNumber);
        
        System.out.println("File Compressed to " + b + " bytes");
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

        for(int i = 0; i < wordsList.size(); i++){
            node = this.wordsHashMap.get(wordsList.get(i));
            node.setValue(findCode(node));
            this.keyHashMap.put(node.getValue(), wordsList.get(i));
        }

        this.inputOutput.writeHeader(this.keyHashMap, this.bytesNumber);

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
        System.gc();
        long start;
        long end;
        int bytesNumber = 3;
        String file = "Algorithms - Lectures 7 and 8 (Greedy algorithms).pdf";
        String filePath = "C:\\Users\\Mohamed Abdel-Moneim\\Desktop\\" + file;
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
        System.out.println("Time: " + (end - start)/1000f + " s");
        System.gc(); 
        filePath = "C:\\Users\\Mohamed Abdel-Moneim\\Desktop\\21011213."+ bytesNumber +"."+file+".hc" ;
        try {
            inputOutput = new InputOutput(filePath);
        } catch (InputPathException e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.println("Program starts to decompress the file");
        System.out.println("Timer starts");
        start = System.currentTimeMillis();
        huffman = new Huffman(inputOutput, 0);
        try {
            huffman.decompress();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        end = System.currentTimeMillis();
        System.out.println("Timer ends");
        System.out.println("Time: " + (end - start)/1000f + " s");

    }

    public void decompress() throws FileNotFoundException, ClassNotFoundException, IOException {
        // read file header
        this.keyHashMap = (HashMap<String, String>)this.inputOutput.readHeader();
        this.chunkNumber = 0;
        byte[] chunk;
        List<Byte> bytes;
        String word = "";
        List<String> kb = new ArrayList<>();
        String buffer = "";
        int b = 0;
        do {
            try{
                chunk = this.inputOutput.readFile(this.chunkNumber++);
            } catch (IOException e) {
                System.out.println("File Scanned");
                break;
            }

            // decompress the chunk
            for (int i = 0; i < chunk.length;) {
                String byteCode = String.format("%8s", Integer.toBinaryString(chunk[i++] & 0xFF)).replace(' ', '0');
                buffer =  buffer.concat(byteCode);
                for(int j = 1; j < buffer.length()+1; j++){
                    word = word.concat(buffer.substring(j-1, j));
                    if(this.keyHashMap.containsKey(word)){
                        kb.add(this.keyHashMap.get(word));
                        word = "";
                        buffer = buffer.substring(j);
                        j=0;
                    }
                }

                if(kb.size() > 1023){
                    bytes = this.toByteArray(kb);
                    b += bytes.size();
                    this.inputOutput.writeToFile(bytes);
                    kb.clear();
                }
                word = "";
            }
            // write the decomperssed data
        }while(chunk.length == this.inputOutput.getChunkSize());

        bytes = this.toByteArray(kb);
        b += bytes.size();
        System.out.println(" Decompressed: "+bytes.size());
        System.out.println("Decompressed: " + b + " bytes");
        this.inputOutput.writeToFile(bytes);

    }

    private List<Byte> toByteArray(List<String> kb) {
        // int n = kb.get(0).length()/8;
        List<Byte> bytes = new ArrayList<>();
        for(String b: kb){
            for(int k = 0; k < b.length()/8; k++)
                bytes.add(this.binaryStringToByte(b.substring(k*8, (k+1)*8)));
        }
        return bytes;
    }
    
}
