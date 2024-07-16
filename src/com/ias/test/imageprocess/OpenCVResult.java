package com.ias.test.imageprocess;

import java.util.ArrayList;
import java.util.Set;

import org.opencv.core.Mat;

public class OpenCVResult extends Result2 {
    private Mat output;

    public OpenCVResult(Mat output) {
        super();
        this.output = output;
    }
    public OpenCVResult(Mat output,Set<Rect> rects) {
        super(rects);
        this.output = output;
    }
    
    public void setOutput(Mat output) {
    	this.output=output;
    }
    public Mat getOutput() {
        return output;
    }
}
