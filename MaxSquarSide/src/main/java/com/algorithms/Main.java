package com.algorithms;


import javax.xml.transform.Source;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.gc();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter testcases file path:");
        String file = scanner.nextLine();
        List <List<Point>> testcases;
        try {
            testcases = new IOManagement(file).getTestCases();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<Integer> results = new ArrayList<>();
        MaxSquareSide max;
        while (!testcases.isEmpty()){
            max = new MaxSquareSide();
            results.add((int) max.calculateMaxSideLength(testcases.getFirst()));
            System.out.println(results.getLast());
            testcases.removeFirst();
            System.gc();
        }
        System.out.println("enter folder path to save the result");
        file = scanner.nextLine();
        try {
            IOManagement.writeOutput(file,results);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}