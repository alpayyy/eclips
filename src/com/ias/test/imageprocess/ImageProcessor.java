package com.ias.test.imageprocess;

import java.io.FileNotFoundException;
import java.nio.file.Paths;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public abstract class ImageProcessor {

    public Result2 printContrastErrors(String path, double contrastThreshold) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        String outputPath = Paths.get("").toAbsolutePath().toString() + "\\images\\";
        Mat src = null;
        // File is reading if it is exists
        try {
            src = Imgcodecs.imread(path);
            if (src.empty()) {
                throw new FileNotFoundException("File Not Found!!!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Process the image and get contrast errors
        OpenCVResult result = getContrastErrors(src, contrastThreshold);

        // Check if result or its error points are null
        if (result != null) {
            String report = result.toString();
            FileOperation.WriteText(outputPath + "ErrorCoordinates.txt", report);

            Mat resizedOutput = new Mat();
            Size newSize = new Size(800, 600);
            Imgproc.resize(result.getOutput(), resizedOutput, newSize);

            // Show image on the screen
            HighGui.imshow("Yazı ve Arka Plan Renkleri Çok Yakın Alanlar", resizedOutput);
            HighGui.waitKey();
            Imgcodecs.imwrite(outputPath + "OutputImage.png", result.getOutput());

            src.release();
            resizedOutput.release();
            return result;
        } else {
            System.out.println("Result is null.");
            return null;
        }
    }

    protected abstract OpenCVResult getContrastErrors(Mat src, double contrastThreshold);
}
