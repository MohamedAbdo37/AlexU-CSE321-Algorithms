package com.algorithms;

import java.util.ArrayList;
import java.util.List;

public class DeterministicMedian extends Median{

    public DeterministicMedian(List<Integer> list){
        super(list);
    }

    @Override
    public int medianIndex() {

        List<Integer> l = new ArrayList<>(this.list);

        while (l.size() >=5){
            l = this.toArrayOf5(l);
        }
        this.sort(l);
        return l.get(l.size()/2);
    }

    private List<Integer> toArrayOf5(List<Integer> l) {
        List<Integer> l1 = new ArrayList<>();
        List<Integer> l2 = new ArrayList<>();
        for (int i = 0; i < l.size()/5 ; i++) {
            for (int j = i*5; j <5*(i+1) && j < l.size() ; j++)
                l1.add(j);
            this.sort(l1);
            l2.add(l1.get(l1.size()/2));
            l1.clear();
        }
        return l2;
    }

    private void sort(List<Integer> l){
        int a, b;
        boolean swaped;
        for (int i = 0; i < l.size(); i++) {
            swaped = false;
            for (int j = 0; j < l.size()-(1+i); j++) {
                a = this.getValue(l,j);
                b = this.getValue(l,j+1);
                if ( a >= b) {
                    this.swap(j, (j+1), l);
                    swaped = true;
                }
            }
            if (!swaped) break;
        }
    }

    private void swap(int i, int j, List<Integer> l) {
        int temp  = l.get(i);
        l.set(i,l.get(j));
        l.set(j,temp);
    }

    private int getValue(List<Integer> l, int index){
        return this.list.get(l.get(index));
    }
}