package com.ias.test.imageprocess.azat;

import org.opencv.core.*;

import org.opencv.imgproc.Imgproc;

import com.ias.test.imageprocess.FileOperation;
import com.ias.test.imageprocess.ImageProcessor;
import com.ias.test.imageprocess.*;

import eclips.azat.ContrastUtils2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Solution2 extends ImageProcessor {
	public Solution2(double contrastThreshold) {
		super(contrastThreshold);
	}

	@Override
	protected Result getContrastErrors(Mat src) {

		// Copying original image
		Mat outputImage = new Mat();
		src.copyTo(outputImage);

		Mat blurred = new Mat();
		Imgproc.GaussianBlur(src, blurred, new Size(1, 1), 0);

		Mat edges = new Mat();
		Imgproc.Canny(blurred, edges, 25, 100);

		List<MatOfPoint> contours = new java.util.ArrayList<>();
		Mat hierarchy = new Mat();
		Imgproc.findContours(edges, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
		String contrastErrors = "";
		Mat roi;
		for (MatOfPoint contour : contours) {
			Rect rect = Imgproc.boundingRect(contour);
			roi = new Mat(src, rect);
			double contrast = ContrastUtils2.GetContrast(roi);
			if (contrast != 0 && contrast < contrastThreshold) {
				Imgproc.rectangle(outputImage, rect, new Scalar(255, 153, 255));
				contrastErrors += String.valueOf(rect.x) + " , "+String.valueOf(rect.y)+ "-> Contrast = " + String.valueOf(contrast) + "\n";
			}
		}
		
		blurred.release();
		edges.release();
		hierarchy.release();

		
		return new Result(outputImage,contrastErrors);
	}
}