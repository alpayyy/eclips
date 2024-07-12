package com.ias.test.imageprocess.morphology;


import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import com.ias.test.imageprocess.ImageProcessor;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

<<<<<<< Updated upstream:src/com/ias/test/imageprocess/morphology/Morphology.java

public class Morphology extends ImageProcessor {
=======
public class Solution1 extends ImageProcessor {
>>>>>>> Stashed changes:src/com/ias/test/imageprocess/morphology/Solution1.java
	private double blockSize = 15;
	private double c = 10;

	
	private double cannyThreshold1 = 50;
	private double cannyThreshold2 = 150;
	private double thickness = 2;

	public Solution1(double contrastThreshold) {
		super(contrastThreshold);
	}

	@Override
	public Mat processImage(Mat src) {
<<<<<<< Updated upstream:src/com/ias/test/imageprocess/morphology/Morphology.java
	    Mat gray = new Mat();
	    Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
=======

		Mat gray = new Mat();
		Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
>>>>>>> Stashed changes:src/com/ias/test/imageprocess/morphology/Solution1.java

	    Mat equalized = new Mat();
	    Imgproc.equalizeHist(gray, equalized);

	    Mat thresholded = new Mat();
	    Imgproc.adaptiveThreshold(equalized, thresholded, 255, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C,
	            Imgproc.THRESH_BINARY_INV, (int) blockSize, c);

<<<<<<< Updated upstream:src/com/ias/test/imageprocess/morphology/Morphology.java
	    Mat morphKernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3, 3));
	    Mat morphed = new Mat();
	    Imgproc.morphologyEx(thresholded, morphed, Imgproc.MORPH_CLOSE, morphKernel);

	    Mat edges = new Mat();
	    Imgproc.Canny(morphed, edges, cannyThresholdmin, cannyThresholdmax);
=======
		
		Mat morphKernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3, 3));
		Mat morphed = new Mat();
		Imgproc.morphologyEx(thresholded, morphed, Imgproc.MORPH_CLOSE, morphKernel);

		Mat edges = new Mat();
		Imgproc.Canny(morphed, edges, cannyThreshold1, cannyThreshold2);
>>>>>>> Stashed changes:src/com/ias/test/imageprocess/morphology/Solution1.java

	    List<MatOfPoint> contours = new java.util.ArrayList<>();
	    Mat hierarchy = new Mat();
	    Imgproc.findContours(edges, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

	    Mat output = src.clone();
	    
	    // Open the file for writing the coordinates
	    try (FileWriter fileWriter = new FileWriter("contour_coordinates.txt")) {  // Dosyayı proje kök dizininde oluşturuyoruz
	        for (MatOfPoint contour : contours) {
	            Rect rect = Imgproc.boundingRect(contour);

	            if (rect.width < 20 || rect.height < 20) {
	                continue;
	            }

	            Scalar meanColorRect = Core.mean(new Mat(src, rect));
	            double[] meanRectColor = { meanColorRect.val[2], meanColorRect.val[1], meanColorRect.val[0] };

<<<<<<< Updated upstream:src/com/ias/test/imageprocess/morphology/Morphology.java
	            Scalar meanColorImage = Core.mean(src);
	            double[] meanImageColor = { meanColorImage.val[2], meanColorImage.val[1], meanColorImage.val[0] };

	            double contrast = contrastCalculator.calculateContrast(meanRectColor, meanImageColor);

	            if (contrast <= contrastThreshold) {
	                Imgproc.rectangle(output, rect, new Scalar(0, 0, 255), (int) thickness);
=======
			double[] meanImageColor = Core.mean(src).val;
			double contrast = ContrastUtils.calculateContrast(meanRectColor, meanImageColor);

			if (contrast <= contrastThreshold) {
				Imgproc.rectangle(output, rect, new Scalar(0, 0, 255), (int) thickness);
				System.out.println("Solution1 - Rectangle Coordinates: [Top-Left: (" + rect.x + ", " + rect.y
						+ "), Bottom-Right: (" + (rect.x + rect.width) + ", " + (rect.y + rect.height) + ")]");
			}
		}
>>>>>>> Stashed changes:src/com/ias/test/imageprocess/morphology/Solution1.java

	                // Write the rectangle coordinates to the file
	                fileWriter.write(String.format("Rectangle Coordinates: [Top-Left: (%d, %d), Bottom-Right: (%d, %d)]%n",
	                        rect.x, rect.y, rect.x + rect.width, rect.y + rect.height));
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    gray.release();
	    equalized.release();
	    thresholded.release();
	    morphed.release();
	    edges.release();
	    hierarchy.release();

	    return output;
	}
}