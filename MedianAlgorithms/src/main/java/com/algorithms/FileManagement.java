package com.algorithms;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManagement {
    public static String src ;

   public static @NotNull List<Integer>readFile(int n){
       List<Integer> list = new ArrayList<>();

       BufferedReader bf = null;
       try {
           bf = new BufferedReader( new FileReader(src+"/"+n+".txt"));
           String line = bf.readLine();

           while (line != null) {
               list.add(Integer.valueOf(line));
               line = bf.readLine();
           }
           bf.close();

       } catch (IOException e) {
           throw new RuntimeException(e);
       }

       return list;
   }

   public static void writeResult(@NotNull List<double[]> result, String name){
       try {
           BufferedWriter outputWriter = new BufferedWriter(new FileWriter(name+".csv"));
           outputWriter.write("Size, avg.Time (μsec)\n");
           for (double[] doubles : result)
               outputWriter.write(doubles[0] + ", " + doubles[1]+"\n");

           outputWriter.flush();
           outputWriter.close();
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
       System.out.println(name + " file has writen successfully");
   }
}
