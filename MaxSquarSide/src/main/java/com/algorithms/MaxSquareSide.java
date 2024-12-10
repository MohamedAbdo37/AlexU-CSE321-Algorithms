package com.algorithms;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MaxSquareSide {
    private boolean sorted;
    private List<Point> points;

    public MaxSquareSide(){
        this.sorted = false;
    }
    public double calculateMaxSideLength(List<Point> points){
        double a, b;
        if (points.size() < 4){
            if(points.size() < 3 )
                return this.calculateDistance(points.get(0),points.get(1));
            a = this.calculateDistance(points.get(0),points.get(1));
            b = this.calculateDistance(points.get(0),points.get(2));
            double c = this.calculateDistance(points.get(1),points.get(2));
            return Math.min(a,Math.min(b,c));
        }
        if(!sorted) {
            this.points = points;
            this.sortX(points);
            sorted = true;
        }
        a = this.calculateMaxSideLength(points.subList(0, points.size()/2));
        b = this.calculateMaxSideLength(points.subList(points.size()/2, points.size()));

        return this.calculateMiddle(Math.min(a,b), points);
    }

    private double calculateMiddle(double min, List<Point> points) {
        double distance = min;
        int middle = (points.getFirst().x + points.getLast().x)/2;
        List<Point> middlePoints = new ArrayList<>();
        for (Point p: points){
            if(Math.abs(p.x - middle) <= min)
                middlePoints.add(p);
        }
        this.sortY(middlePoints);
        Point a, b;
        for (int i = 0; i < middlePoints.size(); i++) {
            a = middlePoints.get(i);
            for (int j = i+1; j < middlePoints.size() ; j++) {
                b = middlePoints.get(j);
                if(Math.abs(a.y - b.y) > min)
                    break;
                distance = Math.min(distance,this.calculateDistance(a,b));
            }
        }
        return distance;
    }

    private void sortY(List<Point> po) {
        if(po.size() <= 1) return;
        int median = new RandomizedMedian(false).medianIndex(po);
        this.sortX(po.subList(0,median));
        this.sortX(po.subList(median+1,po.size()));
    }

    private void sortX(List<Point> po) {
        if(po.size() <= 1) return;
        int median = new RandomizedMedian(true).medianIndex(po);
        this.sortX(po.subList(0,median));
        this.sortX(po.subList(median+1,po.size()));
    }


    private double calculateDistance(Point p1, Point p2) {
        return Math.sqrt(Math.pow((p1.x-p2.x),2) + Math.pow((p1.y-p2.y),2) );
    }
}
