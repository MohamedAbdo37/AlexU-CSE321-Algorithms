package com.algorithms;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Test {
    public static void main(String[] args) throws IOException {

        // HashMap<String, String> map = new HashMap<String, String>(); 

        // // key-value pairs 
        // map.put("rohit", "one"); 
        // map.put("Sam", "two"); 
        // map.put("jainie", "three"); 

        // try(FileOutputStream fos = new FileOutputStream(filePath);){
        //     ObjectOutputStream s = new ObjectOutputStream(fos);
        //     s.writeObject(map);
        //     s.close();
        // } catch (IOException e) {
        //     System.out.println("Error writing to file: " + e.getMessage());
        // }

        // // Generate a large sequence of bytes (e.g., 0 to 255)
        // byte[] byteSequence = new byte[256];
        // for (int i = 0; i < 256; i++) {
        //     byteSequence[i] = (byte) i; // Store values from 0 to 255
        // }

        // try (FileOutputStream fos = new FileOutputStream(filePath, true)) {
        //     fos.write(byteSequence); // Writes all 256 bytes at once
        // } catch (IOException e) {
        //     System.out.println("Error writing to file: " + e.getMessage());
        // }

        // HashMap<String, String> mapRead;
        // try (FileInputStream f = new FileInputStream(filePath)) {
        //     ObjectInputStream s = new ObjectInputStream(f);
        //     System.out.println(f.available());
        //     mapRead = (HashMap<String, String>) s.readObject();

        //     if(map.hashCode() == mapRead.hashCode())
        //         System.out.println("Test 1 pass");
        //     else
        //         System.out.println("Test 1 fail");

        //     if(map.toString().equals(mapRead.toString()))
        //         System.out.println("Test 2 pass");
        //     else
        //         System.out.println("Test 2 fail");
            
        //     if(map.equals(mapRead))
        //         System.out.println("Test 3 pass");
        //     else
        //         System.out.println("Test 3 fail");
            
        //     System.out.println(f.available());
        //     s.close();

        // } catch (ClassNotFoundException e) {
        //     System.out.println("Error reading from file: " + e.getMessage());
        // }
        

        // int n = 255;
        // String str1= "001";
        // String str2= "00010001";
        // try(FileOutputStream fos = new FileOutputStream(filePath);){
        //         ObjectOutputStream s = new ObjectOutputStream(fos);
        //         s.writeObject(n);
        //         s.writeObject(str1);
        //         s.writeObject(str2);
        //         s.close();
        //     } catch (IOException e) {
        //         System.out.println("Error writing to file: " + e.getMessage());
        // }

        // try (FileInputStream f = new FileInputStream(filePath)) {
        //         ObjectInputStream s = new ObjectInputStream(f);
        //         System.out.println(f.available());
        //         int nRead = (int) s.readObject();
        //         String str1Read = (String) s.readObject();
        //         String str2Read = (String) s.readObject();

        //         if(n == nRead && str1.equals(str1Read) && str2.equals(str2Read))
        //             System.out.println("Test 1 pass");
        //         else
        //             System.out.println("Test 1 fail");

        //         System.out.println(f.available());
        //         System.out.println(nRead);
        //         System.out.println(str1Read);
        //         System.out.println(str2Read);
        //         s.close();
        // } catch (ClassNotFoundException e) {
        //         System.out.println("Error reading from file: " + e.getMessage());
        // }

        String filePath1 = "C:\\Users\\Mohamed Abdel-Moneim\\Desktop\\Alice in Wonderland.txt";
        String filePath2 = "C:\\Users\\Mohamed Abdel-Moneim\\Desktop\\extracted.Alice in Wonderland.txt";

        byte[] buffer= new byte[8192];
        int count;
        MessageDigest digest;
        byte[] hash1 = new byte[1];
        byte[] hash2 = new byte[1];
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePath1))) {
            digest = MessageDigest.getInstance("SHA-256");
            while ((count = bis.read(buffer)) > 0) {
                digest.update(buffer, 0, count);
            }
            hash1 = digest.digest();
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePath2))) {
            digest = MessageDigest.getInstance("SHA-256");
            while ((count = bis.read(buffer)) > 0) {
                digest.update(buffer, 0, count);
            }
            hash2 = digest.digest();
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        if(hash1.length == hash2.length)
            System.out.println("test 1 pass");
        else
            System.out.println("test 1 faild");

        for(int i = 0; i < hash1.length;i++){
            if(hash1[i] != hash2[i])
                System.out.println("test 1 faild");
        }
        System.out.println("test 2 pass");
    }
}
