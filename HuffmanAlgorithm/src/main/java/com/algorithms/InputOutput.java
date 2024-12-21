package com.algorithms;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;

public class InputOutput {

    private final String fileName;
    private final String path;
    private int chunkSize;
    private int fileSize;
    private int startPoint;
    private String filePath;

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
        int size =  (int) Math.pow(2, 16);
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
        String[] split = this.fileName.split("\\.");
        this.filePath = this.path +"extracted."+ split[2] +"."+ split[3];
    }
    
    public byte[] readFile(int chunkNumber) throws IOException{
        FileInputStream fileInputStream = new FileInputStream(this.path + this.fileName);
        int size = 0;
        long skip = 0;
        if (chunkNumber == 0){
            this.fileSize = fileInputStream.available();
            size = Math.min(this.fileSize-this.startPoint, this.chunkSize);
            skip = fileInputStream.skip( (long) chunkNumber * this.chunkSize + this.startPoint);
            
        } else{
            skip = fileInputStream.skip( (long) chunkNumber * this.chunkSize + this.startPoint);
            size = Math.min(fileInputStream.available(), this.chunkSize);
        }
        if (skip == -1) {
            System.out.println("End of file");
            throw new IOException("End of file");
        }
        byte[] chunk= new byte[size];
        if (fileInputStream.read(chunk) == -1) {
            throw new IOException("End of file");
        }
        

        return chunk;
    }

    public int getChunkSize() {
        return chunkSize;
    }

    public void appendToFile(byte[] byteCode, int len,int n) {
        String filePath = this.path + "21011213." + n + "." + this.fileName + ".hc";
        try (FileOutputStream fos = new FileOutputStream(filePath, true)) {
            for(int i = 0; i < len ; i++)
                fos.write(byteCode[i]);
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
        FileOutputStream fos = new FileOutputStream(filePath);
        ObjectOutputStream s = new ObjectOutputStream(fos);
        s.writeObject(header);
        s.close();
        fos.close();
    }

    public Map<String,String> readHeader() throws FileNotFoundException, IOException, ClassNotFoundException{
        FileInputStream fileInputStream = new FileInputStream(this.path + this.fileName);
        this.startPoint = fileInputStream.available();
        ObjectInputStream map = new ObjectInputStream(fileInputStream);
        Map<String,String> headHashMap = (Map<String,String>) map.readObject();
        this.startPoint -= fileInputStream.available();
        map.close();
        fileInputStream.close();
        File file = new File(this.filePath);
        if(file.exists())
            file.delete();
        return headHashMap;
    }

    void writeToFile(List<Byte> bytes) {
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
