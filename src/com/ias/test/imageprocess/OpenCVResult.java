package com.ias.test.imageprocess;

import org.opencv.core.Mat;

public class OpenCVResult extends Result2 {
    private Mat output;

    public OpenCVResult(Mat output) {
        super();
        this.output = output;
    }

    public Mat getOutput() {
        return output;
    }
}
