package com.algorithms;

import java.awt.*;
import java.io.*;

import java.util.ArrayList;
import java.util.List;

public class IOManagement {
    private List<List<Point>> testCases;

    public IOManagement(String file) throws IOException {
        this.readTestCases(file);
    }

    private void readTestCases(String file) throws IOException {
        this.testCases = new ArrayList<>();
        List<Point> testcase ;
        int x , y, size;
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();
        while (null != line){
            size = Integer.parseInt(line);
            testcase = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                line = reader.readLine();
                x = Integer.parseInt(line.split(" ")[0]);
                y = Integer.parseInt(line.split(" ")[1]);
                testcase.add(new Point(x,y));
            }
            this.testCases.add(testcase);
            line = reader.readLine();
        }
        reader.close();
        System.gc();
    }

    public List<List<Point>> getTestCases(){
        return this.testCases;
    }

    public static void writeOutput(String foledr, List<Integer> resutle) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(foledr+"\\results.txt"));
        for (int i : resutle){
            writer.write(i+"\n");
        }
        writer.close();
    }
}
