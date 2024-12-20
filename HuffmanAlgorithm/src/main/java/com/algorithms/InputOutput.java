package com.algorithms;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;

public class InputOutput {

    private final String fileName;
    private final String path;
    private int chunkSize;
    private int fileSize;
    private float percentage = 0.1f;

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
        System.out.println("Path: " + this.path + "\nFile name: " + this.fileName);
    }
    
    public byte[] readFile(int chunkNumber) throws IOException{
        FileInputStream fileInputStream = new FileInputStream(this.path + this.fileName);
        if (chunkNumber == 0){
            this.fileSize = fileInputStream.available();
            this.chunkSize = Math.min(this.fileSize, this.chunkSize);
        } else
            this.chunkSize = Math.min(fileInputStream.available(), this.chunkSize);
            
        byte[] chunk= new byte[this.chunkSize];
        long skip = fileInputStream.skip( (long) chunkNumber * this.chunkSize);

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

    public void writeHeader(Map<String,String> header, int n) throws IOException{
        String filePath = this.path + "21011213." + n + "." + this.fileName + ".hc";
        try(FileOutputStream fos = new FileOutputStream(filePath);){
            ObjectOutputStream s = new ObjectOutputStream(fos);
            s.writeObject(header);
            s.close();
        } 
    }
    
}

class InputPathException extends Exception{
    
    public InputPathException(String message) {
        super(message);
    }
}
