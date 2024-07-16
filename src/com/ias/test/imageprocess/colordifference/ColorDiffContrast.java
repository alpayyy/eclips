package com.ias.test.imageprocess.colordifference;


import org.opencv.core.*;
import org.opencv.core.Rect;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import com.ias.test.imageprocess.*;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ColorDiffContrast extends ImageProcessor {
	
	private final int cannyThreshold1=25;
	private final int cannyThreshold2=100;
	@Override
	protected OpenCVResult getContrastErrors(Mat src,double contrastThreshold) {

		// Copying original image
		Mat outputImage = new Mat();
		src.copyTo(outputImage);
		//Blurring image to remove some small edges
		Mat blurred = new Mat();
		Imgproc.GaussianBlur(src, blurred, new Size(1, 1), 0);
		//Finding edges by using Canny method
		Mat edges = new Mat();
		Imgproc.Canny(blurred, edges, cannyThreshold1, cannyThreshold2);

		List<MatOfPoint> contours = new java.util.ArrayList<>();
		Mat hierarchy = new Mat();
		Imgproc.findContours(edges, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
		Mat roi;
		Set<com.ias.test.imageprocess.Rect> errorRects = new HashSet<com.ias.test.imageprocess.Rect>();
		for (MatOfPoint contour : contours) {
			Rect rect = Imgproc.boundingRect(contour);
			roi = new Mat(src, rect);
			double contrast = ContrastUtils2.GetContrast(roi);
			if (contrast != 0 && contrast < contrastThreshold) {
				Imgproc.rectangle(outputImage, rect, new Scalar(255, 153, 255));
				
				errorRects.add(new com.ias.test.imageprocess.Rect(rect.x, rect.y, rect.width, rect.height));
			}
		}
		Set<com.ias.test.imageprocess.Rect> resultRects=new HashSet<com.ias.test.imageprocess.Rect>();
		
		
		
		Imgcodecs.imwrite(Paths.get("").toAbsolutePath().toString() + "OutputImage.png", outputImage);
		blurred.release();
		edges.release();
		hierarchy.release();
		
		return new OpenCVResult(outputImage, resultRects);
	}
}