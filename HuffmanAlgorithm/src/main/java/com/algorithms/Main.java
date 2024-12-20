package com.algorithms;

public class Main {
    public static void main(String[] args) {

        long start;
        long end;
        System.out.println("------------------------------------------------------------");
        if (args.length < 2 ) {
            System.out.println("Program got a wrong number of arguments");
            return;
        }
        if (null == args[0]) {
            System.out.println("Program got a wrong argument");
        } else switch (args[0].toLowerCase()) {
            case "c" -> {
                int bytesNumber = Integer.parseInt(args[2]);
                try {
                    InputOutput inputOutput = new InputOutput(args[1], bytesNumber);
                } catch (InputPathException e) {
                    System.out.println(e.getMessage());
                    return;
                }
                System.out.println("Timer starts");
                start = System.currentTimeMillis();
                end = System.currentTimeMillis();
                System.out.println("Timer ends");
                System.out.println("Time: " + (end - start) + " ms");
            }
            case "d" -> {
                System.out.println("Program starts to decompress the file");
                System.out.println("Timer starts");
                start = System.currentTimeMillis();
                end = System.currentTimeMillis();
                System.out.println("Timer ends");
                System.out.println("Time: " + (end - start) + " ms");
            }
            default -> System.out.println("Program got a wrong argument");
        }

        System.out.println("------------------------------------------------------------");
    }
}