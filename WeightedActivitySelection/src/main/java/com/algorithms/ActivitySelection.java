package com.algorithms;

import java.util.ArrayList;
import java.util.List;

public class ActivitySelection {

    private List<Activity> activities;
    private final List<Activity> selectedActivities;

    private int maxWeight;
    public ActivitySelection(Activity[] activities) {
        this.activities = List.of(activities);
        this.selectedActivities = new ArrayList<>();
        this.maxWeight = 0;
    }

    public void solve() {
        // sort Activities
        this.activities = this.sortActivities(this.activities);
        
        int n = this.activities.size();

        int[] dp = new int[n + 1];
        int[] prev = new int[n];
        int[] finishTime = new int[n + 1];

        for (int i = 0; i < n; i++) {
            finishTime[i] = activities.get(i).getEndTime();
            dp[i] = 0;
        }
        dp[n] = 0;
        for (int i = 1; i < n+1; i++) {
            int index = this.greatestIndex(finishTime, i-1, activities.get(i-1).getStartTime()) + 1;
            if (dp[index] + activities.get(i-1).getWeight() > dp[i-1]) {
                dp[i] = dp[index] + activities.get(i-1).getWeight();
                prev[i-1] = index - 1;
            }else{
                dp[i] = dp[i-1];
                prev[i-1] = prev[i-2];
            }
        }
        this.maxWeight = dp[n];
        for (int i = n; i >-1; i--) {
            if (dp[i] != this.maxWeight){
                this.selectedActivities.addFirst(this.activities.get(i));
                break;
            }
        }
        
        for(int i = prev[n-1]; i >=0; i = prev[i]) {
            
            this.selectedActivities.addFirst(this.activities.get(i));
        }
        
    }

    private int greatestIndex(int[] finishTime, int i, int startTime) {
        int j = i-1;
        for(; j >= 0; j--) {
            if (finishTime[j] <= startTime) {
                return j;
            }
        }
        return j;
    }

    private List<Activity> sortActivities(List<Activity> activities) {
        if (activities.size() <= 1) {
            return activities;
        }
        int n = activities.size();
        List<Activity> rightActivities = this.sortActivities(activities.subList(0, n/2));
        List<Activity> leftActivities = this.sortActivities(activities.subList(n/2, n));
        return this.merge(rightActivities, leftActivities);
    }

    private List<Activity> merge(List<Activity> activities1, List<Activity> activities2) {
        int size1 = activities1.size();
        int size2 = activities2.size();
        Activity[] mergedActivities = new Activity[size1 + size2];
        int k = 0;
        int i = 0;
        int j = 0;
        while(i < size1 && j < size2) {
            if (activities1.get(i).getEndTime() < activities2.get(j).getEndTime()) {
                mergedActivities[k] = activities1.get(i);
                i++;
            } else {
                mergedActivities[k] = activities2.get(j);
                j++;
            }
            k++;
        }
        for (; i < size1; i++) {
            mergedActivities[k] = activities1.get(i);
            k++;
        }
        for (; j < size2; j++) {
            mergedActivities[k] = activities2.get(j);
            k++;
        }
        return List.of(mergedActivities);
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public List<Activity> getSelectedActivities() {
        return selectedActivities;
    }


}
