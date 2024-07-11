package com.ias.test.imageprocess.colordifference;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ColorDiffContrast extends ImageProcessor {
	public ColorDiffContrast(double contrastThreshold) {
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
		Mat roi;
		Set<Point> errorRects = new HashSet<Point>();
		for (MatOfPoint contour : contours) {
			Rect rect = Imgproc.boundingRect(contour);
			roi = new Mat(src, rect);
			double contrast = ContrastUtils2.GetContrast(roi);
			if (contrast != 0 && contrast < contrastThreshold) {
				Imgproc.rectangle(outputImage, rect, new Scalar(255, 153, 255));
				errorRects.add(new Point(rect.x, rect.y));
			}
		}
		Imgcodecs.imwrite(Paths.get("").toAbsolutePath().toString() + "\\images\\outputimage.png", outputImage);
		blurred.release();
		edges.release();
		hierarchy.release();

		return new Result(outputImage, errorRects.toArray(new Point[errorRects.size()]));
	}
}