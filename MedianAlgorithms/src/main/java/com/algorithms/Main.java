package com.algorithms;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Sample Folders (file have to be numbered 0 - n):");
        FileManagement.src = scanner.nextLine();
        System.out.println("Enter number of Sample files:");
        int ntest = scanner.nextInt();
        long sumR = 0;
        long sumL = 0;
        long sumN = 0;
        long start, end, time;
        List<Integer> original, list;
        List<double[]> randomOutput = new ArrayList<>();
        List<double[]> linearOutput = new ArrayList<>();
        List<double[]> naiveOutput = new ArrayList<>();
        double[] rand;
        double[] linear;
        double[] naive;
        System.out.println("NOTE: this program will take long");
        System.gc();
        for (int i = 0; i < ntest; i++) {
            original = FileManagement.readFile(i);
            System.out.print(i +"\t(size:\t"+original.size()+" )\t|");
            rand = new double[2];
            linear = new double[2];
            naive = new double[2];
            rand[0] = original.size();
            linear[0] = original.size();
            naive[0] = original.size();
            for (int j = 0; j < 5; j++) {
                list = new ArrayList<>(original);

                start = System.nanoTime();
                try{
                    new DeterministicMedian(list).medianIndex();
                    end = System.nanoTime();
                    time = end - start;
                    sumL += time;
                } catch (OutOfMemoryError e) {
                    end = System.nanoTime();
                    time = end - start;
                    sumL += time;
                    if(j==4) sumL = -sumL;
                }

                System.gc();
                start = System.nanoTime();
                try{
                    new RandomizedMedian(list).medianIndex();
                    end = System.nanoTime();
                    time = end - start;
                    sumR += time;
                }catch (OutOfMemoryError e){
                    end = System.nanoTime();
                    time = end - start;
                    sumR += time;
                    if(j==4) sumR = -sumR;
                }

                System.gc();
                start = System.nanoTime();
                try{
                    Collections.sort(list);
                    end = System.nanoTime();
                    time = end - start;
                    sumN += time;
                } catch (OutOfMemoryError e) {
                    end = System.nanoTime();
                    time = end - start;
                    sumN += time;
                    if(j==4) sumN = -sumN;
                }
            }
            System.out.println("\tR: "+ (sumR/5.0)/1000.0+" μs" + "\t-\tL: "+ (sumL/5.0)/1000.0+" μs" + "\t-\tN: "+ (sumN/5.0)/1000.0+" μs");
            rand[1] = (sumR/5.0)/1000.0;
            linear[1] = (sumL/5.0)/1000.0;
            naive[1] = (sumN/5.0)/1000.0;
            linearOutput.add(linear);
            randomOutput.add(rand);
            naiveOutput.add(naive);
            sumR = 0;
            sumL = 0;
            sumN = 0;
            System.gc();
        }
        System.out.println("any negative value is an estimated value");
        System.out.println("Enter Folder path to save the results:");
        String folder = scanner.nextLine();
        FileManagement.writeResult(randomOutput,folder+"\\RandomizedMedian");
        FileManagement.writeResult(linearOutput,folder+"\\Median of medians");
        FileManagement.writeResult(naiveOutput,folder+"\\Naive");
    }

}