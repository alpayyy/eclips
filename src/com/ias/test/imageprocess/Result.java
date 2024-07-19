package com.ias.test.imageprocess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Result {
    private List<Rect> values;

    public Result() {
        this.values = new ArrayList<Rect>();
    }
    public Result(List<Rect> rects) {
    	this.values=rects;
    }

    public void addRect(Rect rect) {
        this.values.add(rect);
    }

    public List<Rect> getValues() {
        return values;
    }
    @Override
    public String toString() {
    	String result="";
    	for(Rect rect:values) {
    		
            result+=String.format("Rectangle Coordinates: [Top-Left: (%d, %d), Bottom-Right: (%d, %d)] \n",
                    rect.getX(), rect.getY(), rect.getX() + rect.getWidth(), rect.getY() + rect.getHeight());
    	}
    	return result;
       
    }
}
