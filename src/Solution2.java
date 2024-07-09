import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import eclips.azat.ContrastUtils2;

import java.util.ArrayList;
import java.util.List;

public class Solution2 extends ImageProcessor {
	public Solution2(double contrastThreshold) {
		super(contrastThreshold);
	}

	@Override
	public Mat processImage(Mat src) {

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
		for (MatOfPoint contour : contours) {
			Rect rect = Imgproc.boundingRect(contour);
			roi = new Mat(src, rect);
			double contrast = ContrastUtils2.GetContrast(roi);
			if (contrast != 0 && contrast < contrastThreshold) {
				Imgproc.rectangle(outputImage, rect, new Scalar(255, 153, 255));
			}
		}
		

		blurred.release();
		edges.release();
		hierarchy.release();

		return outputImage;
	}
}
