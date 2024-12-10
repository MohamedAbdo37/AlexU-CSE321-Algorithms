package com.algorithms;

import java.util.List;

public abstract class Median {
    protected List<Integer> list;

    public Median(List<Integer> list){
        this.list = list;
    }

    public abstract int medianIndex();
}
