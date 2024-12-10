package com.algorithms;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class RandomizedMedian{
    private List<Point> list;
    private boolean isX= true;

    public RandomizedMedian(boolean isX){
        this.isX = isX;
    }

    public int medianIndex(List<Point> list) {
        this.list = list;
        int s = 0, e = this.list.size();
        int m = -1;
        int t = this.list.size()/2;
        while (!(m != t ^ m != (t-1))){
            if(m < this.list.size()/2)
                s = m+1;
            else
                e = m-1;

            this.randomPivot(s,e);

            m = this.partitioning(s,e);
        }

        return m;
    }

    private void randomPivot(int s, int e){
        Random rand = new Random();
        int p = rand.nextInt(e-s);
        if(p==1) p++;
        if(p== e-s) p--;
        this.swap(s,p+s);
    }

    private int partitioning(int s , int e){
        int i =  s;
        if(isX)
            for (int j = s+1; j < e; j++) {
                if(this.list.get(j).x < this.list.get(s).x){
                    i++;
                    swap(i,j);
                }
            }
        else
            for (int j = s+1; j < e; j++) {
                if(this.list.get(j).y < this.list.get(s).y){
                    i++;
                    swap(i,j);
                }
            }
        swap(s,i);
        return i;
    }


    private void swap(int i, int j) {
        Point temp = this.list.get(i);
        this.list.set(i,this.list.get(j));
        this.list.set(j,temp);
    }

}
