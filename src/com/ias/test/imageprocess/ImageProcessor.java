
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

	public void printContrastErrors(String path, double contrastThreshold) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		String outputPath = Paths.get("").toAbsolutePath().toString() + "\\images\\";
		Mat src = null;
		//File is reading if it is exists
		try {
			src = Imgcodecs.imread(path);
			if (src.empty()) {
				throw new FileNotFoundException("File Not Found!!!");
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		Mat resizedOutput = new Mat();
		Size newSize = new Size(800, 600);
		Result result = getContrastErrors(src, contrastThreshold);
		//File is printing
		FileOperation.WriteText(outputPath + "ErrorCoordinates.txt", result.getErrorPointsReport());
		Imgproc.resize(result.get_image(), resizedOutput, newSize);
		//Image is showing on the screen
		HighGui.imshow("Yazı ve Arka Plan Renkleri Çok Yakın Alanlar", resizedOutput);
		HighGui.waitKey();
		Imgcodecs.imwrite(outputPath + "OutputImage.png", result.get_image());
		src.release();
		resizedOutput.release();

	}

	protected abstract Result getContrastErrors(Mat src, double contrastThreshold);
}
