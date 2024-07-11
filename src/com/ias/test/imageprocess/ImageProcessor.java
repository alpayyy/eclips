package com.ias.test.imageprocess;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public abstract class ImageProcessor {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        System.out.println("OpenCV library loaded.");
    }

    protected double contrastThreshold;

    public ImageProcessor(double contrastThreshold) {
        this.contrastThreshold = contrastThreshold;
    }

    public void process(String imagePath) {
        Mat src = loadImage(imagePath);

        if (src != null) {
            Mat result = this.processImage(src);
            displayResult(result);
            src.release();
            result.release();
        } else {
            System.out.println("Failed to load image: " + imagePath);
        }
    }

    protected Mat loadImage(String imagePath) {
        return Imgcodecs.imread(imagePath);
    }

    protected void displayResult(Mat result) {
        Mat resizedOutput = new Mat();
        Size newSize = new Size(800, 600);
        Imgproc.resize(result, resizedOutput, newSize);

        HighGui.imshow("Text and Background Colors Very Close Areas", resizedOutput);
        HighGui.waitKey();

        resizedOutput.release();
    }

    protected abstract Mat processImage(Mat src);
}
