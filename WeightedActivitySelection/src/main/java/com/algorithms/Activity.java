package com.algorithms;
public class Activity {
    
    private final int startTime;
    private final int endTime;
    private final int weight;

    public Activity(int startTime, int endTime, int weight) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.weight = weight;
    }

    public int getStartTime() {
        return startTime;
    } 

    public int getEndTime() {
        return endTime;
    }

    public int getWeight() {
        return weight;
    }
    
    @Override
    public String toString() {
        return "Activity [startTime=" + startTime + ", endTime=" + endTime + ", weight=" + weight + "]";
    }
}
