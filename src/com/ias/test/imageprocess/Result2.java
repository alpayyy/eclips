package com.ias.test.imageprocess;

import java.util.ArrayList;

public class Result2 {
    private ArrayList<Rect> values;

    public Result2() {
        this.values = new ArrayList<>();
    }

    public void addRect(Rect rect) {
        this.values.add(rect);
    }

    public ArrayList<Rect> getValues() {
        return values;
    }
}
