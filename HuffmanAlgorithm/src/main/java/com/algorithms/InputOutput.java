package com.algorithms;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;

public class InputOutput {

    private final String fileName;
    private final String path;
    private int chunkSize;
    private int fileSize;
    private float percentage = 0.1f;
    private int startPoint;

    public InputOutput(String input, int bytesNumber) throws InputPathException{
        if(input.contains("/")){
            this.path = input.substring(0, input.lastIndexOf("/")).concat("/");
            this.fileName = input.substring(input.lastIndexOf("/") + 1);
        }
        else if(input.contains("\\")){
            this.path = input.substring(0, input.lastIndexOf("\\")).concat("\\");
            this.fileName = input.substring(input.lastIndexOf("\\") + 1);
        }else {
            throw new InputPathException("Invalid path");
        }
        int size =  (int) Math.pow(2, 20);
        this.chunkSize = size - size % bytesNumber;
        this.startPoint = 0;
        System.out.println("Path: " + this.path + "\nFile name: " + this.fileName);
    }

    public InputOutput(String input) throws InputPathException{
        if(input.contains("/")){
            this.path = input.substring(0, input.lastIndexOf("/")).concat("/");
            this.fileName = input.substring(input.lastIndexOf("/") + 1);
        }
        else if(input.contains("\\")){
            this.path = input.substring(0, input.lastIndexOf("\\")).concat("\\");
            this.fileName = input.substring(input.lastIndexOf("\\") + 1);
        }else {
            throw new InputPathException("Invalid path");
        }
        this.chunkSize =  (int) Math.pow(2, 20);
        this.startPoint = 0;
        System.out.println("Path: " + this.path + "\nFile name: " + this.fileName);
    }
    
    public byte[] readFile(int chunkNumber) throws IOException{
        FileInputStream fileInputStream = new FileInputStream(this.path + this.fileName);
        if (chunkNumber == 0){
            this.fileSize = fileInputStream.available();
            this.chunkSize = Math.min(this.fileSize-this.startPoint, this.chunkSize);
        } else
            this.chunkSize = Math.min(fileInputStream.available(), this.chunkSize);
            
        byte[] chunk= new byte[this.chunkSize];
        long skip = fileInputStream.skip( (long) chunkNumber * this.chunkSize + this.startPoint);

        if (skip == -1) {
            throw new IOException("End of file");
        }
        if (fileInputStream.read(chunk) == -1) {
            throw new IOException("End of file");
        }
        if ((((chunkNumber + 1) * this.chunkSize ) / this.fileSize) > this.percentage) {
            System.out.print("||");
            this.percentage += 0.1f;
        }
        if (this.percentage == 1)
            System.out.print(" ]");

        return chunk;
    }

    public int getChunkSize() {
        return chunkSize;
    }

    public void appendToFile(byte byteCode, int n) {
        String filePath = this.path + "21011213." + n + "." + this.fileName + ".hc";
        try (FileOutputStream fos = new FileOutputStream(filePath, true)) {
            fos.write(byteCode);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void appendToFile(byte[] byteCode, int n) {
        String filePath = this.path + "21011213." + n + "." + this.fileName + ".hc";
        try (FileOutputStream fos = new FileOutputStream(filePath, true)) {
            for(byte b: byteCode)
                fos.write(b);
            
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void writeHeader(HashMap<String,String> header, int n) throws IOException{
        String filePath = this.path + "21011213." + n + "." + this.fileName + ".hc";
        FileOutputStream fos = new FileOutputStream(filePath);
        ObjectOutputStream s = new ObjectOutputStream(fos);
        // s.writeObject(header.length);
        // for(String[] head: header){
        //     s.writeObject(head[0]);
        //     s.writeObject(head[1]);
        // }
        s.writeObject(header);
        s.close();
        fos.close();
    }

    public HashMap<String,String> readHeader() throws FileNotFoundException, IOException, ClassNotFoundException{
        FileInputStream fileInputStream = new FileInputStream(this.path + this.fileName);
        this.startPoint = fileInputStream.available();
        ObjectInputStream map = new ObjectInputStream(fileInputStream);
        HashMap<String,String> headHashMap = (HashMap<String,String>) map.readObject();
        this.startPoint -= fileInputStream.available();
        map.close();
        fileInputStream.close();
        return headHashMap;
    }

    void writeToFile(List<Byte> bytes) {
        String[] split = this.fileName.split("\\.");
        String filePath = this.path +"extracted."+ split[2] +"."+ split[3];
        try (FileOutputStream fos = new FileOutputStream(filePath, true)) {
            for(byte b: bytes)
                fos.write(b);
            
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    
}

class InputPathException extends Exception{
    
    public InputPathException(String message) {
        super(message);
    }
}
