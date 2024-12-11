package com.algorithms;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class InputOutput {
    
    private final String path;
    private final String fileName;

    public InputOutput(String path) throws InputPathException{
        if(path.contains("/")){
            this.path = path.substring(0, path.lastIndexOf("/")).concat("/");
            this.fileName = path.substring(path.lastIndexOf("/") + 1);
        }
        else if(path.contains("\\")){
            this.path = path.substring(0, path.lastIndexOf("\\")).concat("\\");
            this.fileName = path.substring(path.lastIndexOf("\\") + 1);
        }else {
            throw new InputPathException("Invalid path");
        }
        System.out.println("Path: " + this.path + "\nFile name: " + this.fileName);
    }


    /**
     * Code Source: W3schools.
     * URL: https://www.w3schools.com/java/java_files_read.asp
     * Description: This code was using the Scanner class to read the contents of the text file and display it on the screen.
     * Changes Made:
     *  - Adapted the method to read an array of activities.
     */
    public Activity[] getActivities() {
        Activity[] activities = null;
        try {
            File myObj = new File(this.path + this.fileName);
            try (Scanner myReader = new Scanner(myObj)) {
                String data = myReader.nextLine();
                int numOfActivities = Integer.parseInt(data);
                activities = new Activity[numOfActivities];
                for (int i = 0; i < numOfActivities; i++) {
                    data = myReader.nextLine();
                    String[] tokens = data.split(" ");
                    activities[i] = new Activity(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
        
        return activities;
    }

    /*
     * Code Source: W3schools.
     * URL: https://www.w3schools.com/java/java_files_create.asp
     * Description: This code was using the FileWriter class together with its write() method to write some text to a file 
     * Changes Made:
     *  - Adapted the method to write an array of activities in the same folder as the input file.
     */
    public void saveActivities(ActivitySelection activities) {
        try {
            try (FileWriter myWriter = new FileWriter(this.path + this.fileName.split("\\.")[0] + "_21011213.out")) {
                myWriter.write("Maximum weight: "+ activities.getMaxWeight() + "\n");
                for(Activity activity : activities.getSelectedActivities()){
                    myWriter.write(activity.toString() + "\n");
                }
            }
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }

    
}

class InputPathException extends Exception{
    
    public InputPathException(String message) {
        super(message);
    }
}
