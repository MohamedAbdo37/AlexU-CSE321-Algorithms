package com.algorithms;

public class Main {
    public static void main(String[] args) {
        System.out.println("-----------------------------------------------------------");
        
        InputOutput inputOutput;
        try {
            inputOutput = new InputOutput(args[0]);
        } catch (InputPathException e) {
            System.out.println(e.getMessage());
            return;
        }
        Long startTime = System.currentTimeMillis();
        Activity[] activities = inputOutput.getActivities();
        ActivitySelection activitySelection = new ActivitySelection(activities);
        activitySelection.solve();
        Long endTime = System.currentTimeMillis();
        System.out.println("Time: " + (endTime - startTime) + " ms");
        System.out.println("Maximum weight: " + activitySelection.getMaxWeight());
        inputOutput.saveActivities(activitySelection);
        System.out.println("-----------------------------------------------------------");
        
    }
}