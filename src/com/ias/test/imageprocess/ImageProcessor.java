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
        System.out.println("OpenCV kütüphanesi yüklendi.");
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
            System.out.println("Görsel yüklenemedi: " + imagePath);
        }
    }

    protected Mat loadImage(String imagePath) {
        return Imgcodecs.imread(imagePath);
    }

    protected void displayResult(Mat result) {
        Mat resizedOutput = new Mat();
        Size newSize = new Size(800, 600);
        Imgproc.resize(result, resizedOutput, newSize);

        HighGui.imshow("Yazı ve Arka Plan Renkleri Çok Yakın Alanlar", resizedOutput);
        HighGui.waitKey();

        resizedOutput.release();
    }

    protected abstract Mat processImage(Mat src);
}