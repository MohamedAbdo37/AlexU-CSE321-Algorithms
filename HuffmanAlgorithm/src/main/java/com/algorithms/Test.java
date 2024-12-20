package com.algorithms;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class Test {
    public static void main(String[] args) throws IOException {

        HashMap<String, String> map = new HashMap<String, String>(); 

        // key-value pairs 
        map.put("rohit", "one"); 
        map.put("Sam", "two"); 
        map.put("jainie", "three"); 

        String filePath = "largeExample.dat";

        try(FileOutputStream fos = new FileOutputStream(filePath);){
            ObjectOutputStream s = new ObjectOutputStream(fos);
            s.writeObject(map);
            s.close();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }

        // Generate a large sequence of bytes (e.g., 0 to 255)
        byte[] byteSequence = new byte[256];
        for (int i = 0; i < 256; i++) {
            byteSequence[i] = (byte) i; // Store values from 0 to 255
        }

        try (FileOutputStream fos = new FileOutputStream(filePath, true)) {
            fos.write(byteSequence); // Writes all 256 bytes at once
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }

        HashMap<String, String> mapRead;
        try (FileInputStream f = new FileInputStream(filePath)) {
            ObjectInputStream s = new ObjectInputStream(f);
            System.out.println(f.available());
            mapRead = (HashMap<String, String>) s.readObject();

            if(map.hashCode() == mapRead.hashCode())
                System.out.println("Test 1 pass");
            else
                System.out.println("Test 1 fail");

            if(map.toString().equals(mapRead.toString()))
                System.out.println("Test 2 pass");
            else
                System.out.println("Test 2 fail");
            
            if(map.equals(mapRead))
                System.out.println("Test 3 pass");
            else
                System.out.println("Test 3 fail");
            
            System.out.println(f.available());
            s.close();

        } catch (ClassNotFoundException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
        

    }
}
