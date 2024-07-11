package com.ias.test.imageprocess;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;

public class Result {

	private Mat _image;
	private String _errorCoordinates;

	public Mat get_image() {
		return _image;
	}

	public void set_image(Mat _image) {
		this._image = _image;
	}

	public String getErrorCoordinates() {
		return _errorCoordinates;
	}

	public void setErrorCoordinates(Point[] points) {
		String tempCoordinates = "";
		for (int i = 0; i < points.length; i++) {
			tempCoordinates += (i+1) + "-)" + " Contrast ->" + "X: " + points[i].x + " Y: " + points[i].y + "\n";
		}
		this._errorCoordinates = tempCoordinates;
	}

	public Result(Mat image, Point[] points) {
		setErrorCoordinates(points);
		this._image = image;
	}

	public Result(Mat image) {
		this._image = image;
		this._errorCoordinates = "";
	}

}
